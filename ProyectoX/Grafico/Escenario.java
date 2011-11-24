package ProyectoX.Grafico;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import ProyectoX.Excepciones.CargaRecursoException;
import ProyectoX.Excepciones.EscenarioIncompletoException;
import ProyectoX.Excepciones.StringEmptyException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Grafico.Sprite.SpriteManager;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Controles.Control;

/**
 * Representación gráfica del lugar donde acontecen todas las situaciones del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class Escenario extends Canvas implements Worker
{
	
	//Variables de Clase
	private final int medidaPixelCelda = 32;//Medida de un lado en pixeles de una celda en el Escenario.
	                                        //Celda = si se dividiera el Escenario usando una matriz, cada celda tendría una medida de lado en pixeles.
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private BufferedImage fondo;
	private BufferStrategy bufferStrategy;
	private BloqueGrafico anterior, actual, siguiente;
	private int largo, alto;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Escenario vacío.
	 * 
	 * @param vP Ventana Principal del Juego.
	 * @throws NullPointerException Si vP es null.
	 */
	public Escenario (VentanaPrincipal vP) throws NullPointerException
	{
		super();
		
		if (vP == null)
			throw new NullPointerException ("Escenario." + "\n" +
					                        "Imposible crear Escenario con VentanaPrincipal null.");
		
		ventanaPrincipal = vP;
		largo = ventanaPrincipal.largo();
		alto = ventanaPrincipal.alto();
		fondo = null;
		anterior = null;
		actual = null;
		siguiente = null;
	}
	
	/*COMANDOS*/
	
	/**
	 * Contruye Gráficamente al Escenario.
	 */
	public void inicializarGrafica ()
	{
		try
		{
			this.setBounds (0, 0, largo, alto);
			this.setIgnoreRepaint(true);
			this.requestFocus();
			this.createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
		
			transparentarCursor();
		}
		catch (Exception e)
		{
			ventanaPrincipal.mensajeError("Error", "Escenario.inicializarGrafica()" + "\n" + e.getMessage(), true);
		}
	}
	
	/**
	 * Pone transparente el cursor.
	 */
	private void transparentarCursor ()
	{
		BufferedImage cursor = new CargadorSprite().crearCombatible(10, 10, Transparency.BITMASK);
		Toolkit t = Toolkit.getDefaultToolkit();
		Cursor c = t.createCustomCursor(cursor, new Point(5,5), "null");
		this.setCursor(c);
	}
	
	/**
	 * Agrega el Control c usado por el usuario al Escenario.
	 * 
	 * @param c Control del usuario.
	 * @throws NullPointerException Si c es null.
	 */
	public void agregarControl (Control c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Escenario.agregarControl()" + "\n" +
					                        "Es Control que está intentando agregar es null.");
		
		this.addKeyListener(c);
	}
	
	/**
	 * Agrega el fondo al Escenario.
	 * 
	 * @param fondoNombre Nombre del archivo imagen del fondo.
	 * @param cargadorSprite Cargador de Sprite para cargar la imagen.
	 * @throws NullPointerException Si fondoNombre o cargadorSprite son null.
	 * @throws StringEmptyException Si el fondoNombre es igual a vacío.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public void agregarFondo (String fondoNombre, CargadorSprite cargadorSprite) throws NullPointerException, StringEmptyException, CargaRecursoException
	{
		if (fondoNombre == null)
			throw new NullPointerException ("Escenario.agregarFondo()" + "\n" +
					                        "Imposible agregar fondo. Es nombre del fondo es null.");
		if (fondoNombre.equals(""))
			throw new StringEmptyException ("Escenario.agregarFondo()" + "\n" +
					                        "Imposible agregar fondo. Es nombre del fondo es vacío.");
		if (cargadorSprite == null)
			throw new NullPointerException ("Escenario.agregarFondo()" + "\n" +
					                        "Imposible agregar fondo. El CargadorSprite es null.");
		
		try
		{
			fondo = cargadorSprite.obtenerSprite(fondoNombre, this);
			Graphics2D g = (Graphics2D) fondo.getGraphics();
			g.setPaint(new TexturePaint(fondo, new Rectangle(0, 0, fondo.getWidth(), fondo.getHeight())));
			g.fillRect(0, 0, fondo.getWidth(), fondo.getHeight());
		}
		catch (CargaRecursoException exception)
		{
			throw new CargaRecursoException ("Escenario.agregarFondo()" + "\n" +
                                             "Error al cargar el fondo " + fondoNombre + "." + "\n" +
					                         "Detalles del Error:" + "\n" +
					                         exception.getMessage());
		}
	}
	
	/**
	 * Cambia el BloqueGrafico anterior por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficoAnterior (BloqueGrafico bg)
	{
		anterior = bg;
	}
	
	/**
	 * Cambia el BloqueGrafico actual por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficoActual (BloqueGrafico bg)
	{
		actual = bg;
	}
	
	/**
	 * Cambia el BloqueGrafico siguiente por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficosiguiente (BloqueGrafico bg)
	{
		siguiente = bg;
	}
	
	/**
	 * Elimina todos los elementos del escenario.
	 */
	public void limpiar ()
	{
		fondo = null;
		anterior.limpiar();
		anterior = null;
		actual.limpiar();
		actual = null;
		siguiente.limpiar();
		siguiente = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el BloqueGrafico anterior.
	 * 
	 * @return BloqueGrafico anterior.
	 */
	public BloqueGrafico getBloqueGraficoAnterior ()
	{
		return anterior;
	}
	
	/**
	 * Devuelve el BloqueGrafico actual.
	 * 
	 * @return BloqueGrafico actual.
	 */
	public BloqueGrafico getBloqueGraficoActual ()
	{
		return actual;
	}
	
	/**
	 * Devuelve el BloqueGrafico siguiente.
	 * 
	 * @return BloqueGrafico siguiente.
	 */
	public BloqueGrafico getBloqueGraficosiguiente ()
	{
		return siguiente;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Método de actualización del Escenario.
	 * 
	 * @throws EscenarioIncompletoException Si el Escenario no ha sido totalmente inicializado.
	 */
	public void work () throws EscenarioIncompletoException
	{
		if ((fondo == null) || ((anterior == null) && (actual == null) && (siguiente == null)))
			throw new EscenarioIncompletoException ("Escenario.work()" + "\n" +
					                                "El Escenario no ha sido totalmente inicializado." + "\n" +
					                                "(fondo == null) -> " + (fondo == null) + "\n" +
					                                "(actual == null) -> " + (actual == null));
		
		imprimirBloque(actual);
	}
	
	/**
	 * Imprime en el Escenario el bloque pasado por parámetro.
	 * 
	 * @param bloqueGrafico Bloque que contiene los sprites a imprimir.
	 */
	private void imprimirBloque (BloqueGrafico bloqueGrafico)
	{
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		imprimirFondo(g);
		int difPiso = alto - (medidaPixelCelda * 2) - (medidaPixelCelda * bloqueGrafico.nivelPiso);
		for (SpriteManager sp: bloqueGrafico.sprites)
		{
			if (sp.isEliminar())
				bloqueGrafico.eliminarSprite(sp);
			else
				g.drawImage(sp.getSpriteActual(), ((int) (sp.posicion()[1] * medidaPixelCelda))
						                        , ((int) (sp.posicion()[0] * medidaPixelCelda)) + difPiso, this);
		}
		bufferStrategy.show();
	}
	
	/**
	 * Imprime el fondo en el Graphics2D g.
	 * 
	 * @param g Graphics2D donde imprimir el fondo del Escenario.
	 */
	private void imprimirFondo (Graphics2D g)
	{
		g.drawImage(fondo, 0, 0, largo, alto, 0, 0, fondo.getWidth(), fondo.getHeight(), this);
	}

}