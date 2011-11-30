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

import ProyectoX.Excepciones.BoundaryViolationException;
import ProyectoX.Excepciones.CargaRecursoException;
import ProyectoX.Excepciones.EscenarioIncompletoException;
import ProyectoX.Excepciones.PosicionIncorrectaException;
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
	private BloqueGrafico[][] bloques;
	private int[] actual;
	private SpriteManager spriteCentral; //El Escenario debe centrarse respecto al spriteCentral. Es el sprite que debe mostrar en todo momento. (El sprite del personaje del jugador)
	private double difPosX, difPosY; //Diferencia de Posiciones de impreción de los bloques respecto a la posición del spriteCentral.
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
		bloques = null;
		actual = new int[2];
		spriteCentral = null;
		difPosX = difPosY = 0;
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
		BufferedImage cursor = CargadorSprite.getCargador().crearCombatible(10, 10, Transparency.BITMASK);
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
	 * Agrega el SpriteManager Central (el sprite del personaje del jugador) al Escenario.
	 * 
	 * @param sp SpriteManager Central.
	 * @throws NullPointerException Si sp es null.
	 */
	public void agregarSpriteCentral (SpriteManager sp) throws NullPointerException
	{
		if (sp == null)
			throw new NullPointerException ("Escenario.agregarSpriteCentral()" + "\n" +
					                        "Imposible agregar el SpriteManager Central. El SpriteManager ingresado es null.");
		
		spriteCentral = sp;
	}
	
	/**
	 * Agrega todos los Bloques Gráficos a mostrar.
	 * 
	 * @param bsg Nuevos Bloques GRáficos.
	 * @throws NullPointerException Si bsg es null.
	 */
	public void agregarBloquesGrafico (BloqueGrafico[][] bsg) throws NullPointerException
	{
		if (bsg == null)
			throw new NullPointerException ("Escenario.agregarBloquesGrafico()" + "\n" +
					                        "Imposible agregar el SpriteManager Central. El SpriteManager ingresado es null.");
		
		bloques = bsg;
	}
	
	/**
	 * Agrega el fondo al Escenario.
	 * 
	 * @param fondoNombre Nombre del archivo imagen del fondo.
	 * @throws NullPointerException Si fondoNombre o cargadorSprite son null.
	 * @throws StringEmptyException Si el fondoNombre es igual a vacío.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public void agregarFondo (String fondoNombre) throws NullPointerException, StringEmptyException, CargaRecursoException
	{
		if (fondoNombre == null)
			throw new NullPointerException ("Escenario.agregarFondo()" + "\n" +
					                        "Imposible agregar fondo. Es nombre del fondo es null.");
		if (fondoNombre.equals(""))
			throw new StringEmptyException ("Escenario.agregarFondo()" + "\n" +
					                        "Imposible agregar fondo. Es nombre del fondo es vacío.");
		
		try
		{
			fondo = CargadorSprite.getCargador().obtenerSprite(fondoNombre, this);
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
	 * Cambia el BloqueGrafico actual por el pasado por parámetro bg.
	 * Donde bg es la posición del bloque en la matriz bloques.
	 * bg[0] = fila del BloqueGrafico
	 * bg[1] = columna del BloqueGrafico
	 * 
	 * Las filas y columnas van desde el (0,0).
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 * @throws NullPointerException Si bg es null.
	 * @throws BoundaryViolationException Si bg.length > 2
	 * @throws PosicionIncorrectaException Si la posición es incorrecta.
	 */
	public void setBloqueGraficoActual (int[] bg) throws NullPointerException, BoundaryViolationException, PosicionIncorrectaException
	{
		if (bg == null)
			throw new NullPointerException ("Escenario.setBloqueGraficoActual()" + "\n" +
					                        "La posición del bloque que está queriendo setear como bloque actual es null.");
		if (bg.length > 2)
			throw new BoundaryViolationException ("Escenario.setBloqueGraficoActual()" + "\n" +
                                                  "El arreglo ingresado no corresponde a lo esperado. Debe ser de 2 componentes talque {fila, columna}.");
		if ((bg[0] < 0) || (bg[0] >= bloques.length)
		 || (bg[1] < 0) || (bg[1] >= bloques[0].length))
			throw new PosicionIncorrectaException ("Escenario.setBloqueGraficoActual()" + "\n" +
                                                   "La posición ingresada no corresponde a la matriz de bloques actual.");
		actual = bg;
	}
	
	/**
	 * Elimina todos los elementos del escenario.
	 */
	public void limpiar ()
	{
		fondo = null;
		
		for (int i=0; i < bloques.length; i++)
			for (int j=0; j < bloques[0].length; i++)
				if (bloques[i][j] != null)
					bloques[i][j].limpiar();
		bloques = null;
		actual = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve los Bloques Graficos.
	 * 
	 * @return Bloques Graficos.
	 */
	public BloqueGrafico[][] agregarBloquesGraficos ()
	{
		return bloques;
	}
	
	/**
	 * Devuelve el BloqueGrafico actual.
	 * 
	 * @return BloqueGrafico actual.
	 */
	public BloqueGrafico getBloqueGraficoActual ()
	{
		return bloques[actual[0]][actual[1]];
	}
	
	/**
	 * Devuelve el BloqueGrafico actual.
	 * 
	 * @return BloqueGrafico actual.
	 */
	private BloqueGrafico actual ()
	{
		return getBloqueGraficoActual();
	}
	
	/**
	 * Devuelve la posX del spriteCentral.
	 * 
	 * @return posX del spriteCentral.
	 */
	private double scX ()
	{
		return spriteCentral.posicion()[0] * medidaPixelCelda;
	}
	
	/**
	 * Devuelve la posY del spriteCentral.
	 * 
	 * @return posY del spriteCentral.
	 */
	private double scY ()
	{
		return spriteCentral.posicion()[1] * medidaPixelCelda;
	}
	
	/**
	 * Distancia en el eje x de la posición del spriteCentral al centro del bloque actual.
	 */
	private double dXcB ()
	{
		return Math.abs(scX() - largo/2);//((actual().maxX * medidaPixelCelda)/2));
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Método de actualización del Escenario.
	 * 
	 * @throws EscenarioIncompletoException Si el Escenario no ha sido totalmente inicializado.
	 */
	public void work () throws EscenarioIncompletoException
	{
		if ((fondo == null) || (bloques == null) || (actual == null) || (spriteCentral == null))
			throw new EscenarioIncompletoException ("Escenario.work()" + "\n" +
					                                "El Escenario no ha sido totalmente inicializado." + "\n" +
					                                "(fondo == null) -> " + (fondo == null) + "\n" +
					                                "(bloques == null) -> " + (bloques == null) + "\n" +
					                                "(actual == null) -> " + (actual == null) + "\n" +
					                                "(spriteCentral == null) -> " + (spriteCentral == null));
		
		if (scX() >= largo/2)
		//{
			//System.out.println(/*(actual().maxX * medidaPixelCelda) - largo -*/ dXcB());
			if ((actual().maxX * medidaPixelCelda) > largo)
				difPosX = Math.abs(/*(actual().maxX * medidaPixelCelda) - largo +*/ dXcB());
		//}
		//difPosX = (actual().maxX * medidaPixelCelda)/2 - scX();
		imprimirBloque(actual());
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
				g.drawImage(sp.getSpriteActual(), (int) ((sp.posicion()[0] * medidaPixelCelda) - difPosX)
						                        , (int) ((sp.posicion()[1] * medidaPixelCelda) - difPosY) + difPiso, this);
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