package ProyectoX.Logica;

import java.util.Iterator;

import ProyectoX.Excepciones.ControlCentralException;
import ProyectoX.Grafico.BloqueGrafico;
import ProyectoX.Grafico.Escenario;
import ProyectoX.Grafico.VentanaPrincipal;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Librerias.Threads.AliveThread;
import ProyectoX.Librerias.Threads.ControlThread;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Logica.Controles.Control;
import ProyectoX.Logica.Controles.Teclado;
import ProyectoX.Logica.Mapa.Bloque;
import ProyectoX.Logica.Mapa.Nivel;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.MarioChico;

/**
 * Representa al Control Central del Juego.
 * 
 * Controla las situaciones que suceden en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class ControlCentral implements Runnable, ControlThread
{
	
	//Variables de Clase
	public static final double velocidad = 4.5;
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private Escenario escenario;
	private CargadorSprite cargadorSprite;
	private Jugador jugador;
	private Nivel nivel;
	private PositionList<Actor> actores;
	private Gravedad gravedad;
	
	//Threads
	private Thread Tactual;
	private AliveThread Tescenario, Tjugador, Tgravedad;
	private Updater Tupdater;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Control Central con el Escenario e, para el usuario de nombre nJ, en la Ventana Principal ventana.
	 * 
	 * @param ventana Ventana Principal donde se va a ejecutar el Juego.
	 * @param nJ Nombre Jugador.
	 * @param e Escenario donde llevar a cabo la parte gráfica el juego.
	 * @throws NullPointerException Si ventana o nJ o e son null.
	 */
	public ControlCentral (VentanaPrincipal ventana, String nJ, Escenario e) throws NullPointerException
	{
		try
		{
			if (ventana == null)
				throw new NullPointerException ("ControlCentral." + "\n" +
						                        "Imposible crear ControlCentral. Ventana es null.");
			if (nJ == null)
				throw new NullPointerException ("ControlCentral." + "\n" +
						                        "Imposible crear ControlCentral. Nombre del Jugador es null.");
			if (e == null)
				throw new NullPointerException ("ControlCentral." + "\n" +
						                        "Imposible crear ControlCentral. Escenario es null.");
			
			Tactual = null;
			ventanaPrincipal = ventana;
			escenario = e;
			
			cargadorSprite = new CargadorSprite ();
		
			Mario PJ = new Mario (new MarioChico(), cargadorSprite);
			Control c = new Teclado();
			jugador = new Jugador (nJ, PJ, c, this);
			PJ.setJugador(jugador);
			
			actores = null;
			
			nivel = new Nivel(1);
			
			gravedad = new Gravedad(this);
			
			ventanaPrincipal.agregarEscenario(e);
			escenario.inicializarGrafica();
			escenario.agregarControl(c);
			ventanaPrincipal.repaint();
			
			//Crear y Asignar Threads
			Tescenario = new AliveThread (this, 0.25, escenario);
			Tjugador = new AliveThread (this, 1, jugador);
			Tgravedad = new AliveThread (this, 1, gravedad);
			Tupdater = new Updater (this, 0.5);
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega el Thread que ejecutará el run de esta clase.
	 * 
	 * @param t Thread para esta clase.
	 * @throws NullPointerException Si t es null.
	 */
	public void agregarThread (Thread t) throws NullPointerException
	{
		if (t == null)
			throw new NullPointerException ("ControlCentral.agregarThread()" + "\n" +
                                            "El thread que se quiere agregar es null.");
		Tactual = t;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Jugador del juego.
	 * 
	 * @return Jugador del juego.
	 */
	public Jugador getJugador ()
	{
		return jugador;
	}
	
	/**
	 * Devuelve un iterador de los Actores actuales en Juego.
	 * 
	 * @return Iterador de los Actores actuales en Juego.
	 */
	public Iterator<Actor> getActores ()
	{
		PositionList<Actor> a = new ListaPositionSimple<Actor> ();
		a.addFirst((Actor) jugador.personaje());
		return a.iterator();
		//return actores.iterator();
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Run para el Thread de esta clase.
	 */
	public void run ()
	{
		try
		{
			//Inicialización Lógica.
			actores = nivel.inicializarNivel((Actor) jugador.personaje, this, cargadorSprite);
			
			//Inicialización Gráfica.
			Bloque bloqueActual = nivel.getBloqueActual();
			BloqueGrafico bloqueGrafico = new BloqueGrafico (bloqueActual.getFilas(), bloqueActual.getColumnas());
			//Agregando Piso
			bloqueGrafico.setNivelPiso(bloqueActual.getNivelPiso());
			for (Actor a: actores)
				bloqueGrafico.agregarSprite(a.spriteManager);
			escenario.agregarFondo(nivel.fondo(), cargadorSprite);
			escenario.setBloqueGraficoActual(bloqueGrafico);
			
			//Agregando UpNeeders al Updater
			for (Actor a: actores)
				for (UpNeeder un: a.getUpNeeders())
					Tupdater.addUpNeeder(un);
			
			//Start Thread's
			Tjugador.start();
			Tgravedad.start();
			Tupdater.start();
			Tescenario.start();
			
			ventanaPrincipal.repaint();
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	/**
	 * Devuelve el tiempo indicado de espera entre cada ejecución para un AliveThread.
	 * 
	 * @return tiempo de espera.
	 */
	public int getSleepTime ()
	{
		return (int) (1000/velocidad);
	}
	
	/**
	 * Recibe una Excepción e de un AliveThread.
	 * 
	 * @param e Excepción producida por un AliveThread.
	 */
	public void error (Exception e)
	{
		ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
	}
	
	/**
	 * Acción ESC (escape) del Juego.
	 * 
	 * Abre un menú de salida del Juego.
	 */
	public void ESC ()
	{
		//menú para salir de la partida
	}
	
	/**
	 * Acción aceptar del Juego.
	 */
	public void aceptar ()
	{
		//pausa
	}
	
	/**
	 * Indica que el Jugador ha ganado el Nivel actual.
	 */
	public void ganarNivel ()
	{
		Position<Actor> p = actores.first();
		while (p.element() != jugador.personaje())
			p = actores.next(p);
		actores.remove(p);
		
		//gravedad.setAfectar(false);
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		escenario.setActualizar(false);
		ventanaPrincipal.mensajeError("Fin del Juego", "Ganaste", true);
	}
	
	/**
	 * Reinicia el nivel a una posición inicial.
	 */
	public void reiniciarNivel ()
	{
		
	}
	
	/**
	 * Indica que el Jugador ha perdido el Nivel actual.
	 */
	public void perderNivel ()
	{
		Position<Actor> p = actores.first();
		while ((p != actores.last()) &&(p.element() != jugador.personaje()))
			p = actores.next(p);
		
		if (p.element() != jugador.personaje())
			try
			{
				throw new ControlCentralException("Imposible encontrar Personaje del Jugador en la lista de Atores.");
			}
			catch (ControlCentralException exception)
			{
				ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
			}
		
		actores.remove(p);
		
		//gravedad.setAfectar(false);
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		escenario.setActualizar(false);
		ventanaPrincipal.mensajeError("Perdiste", "Perdiste", true);
	}
	
	/**
	 * Muestra un Mensaje de Error, con su título y mensaje, provocado por o en el Compenente ventana.
	 * 
	 * @param ventana Componente que llama a este método, donde se generó el error.
	 * @param titulo Título del Mensaje de Error.
	 * @param mensaje Mensaje del Error.
	 */
	public void mensajeError (String titulo, String mensaje, boolean cerrar)
	{
		ventanaPrincipal.mensajeError(titulo, mensaje, cerrar);
	}

}