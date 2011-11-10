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
public class ControlCentral implements Runnable
{
	
	//Variables de Clase
	public static final int velocidad = 40;
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private Escenario escenario;
	private CargadorSprite cargadorSprite;
	private Jugador jugador;
	private Nivel nivel;
	private PositionList<Actor> actores;
	private Gravedad gravedad;
	
	//Threads
	private Thread Tactual, Tescenario, Tjugador, Tgravedad;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Control Central con el Escenario e, para el usuario de nombre nJ, en la Ventana Principal ventana.
	 * 
	 * @param ventana Ventana Principal donde se va a ejecutar el Juego.
	 * @param nJ Nombre Jugador.
	 * @param e Escenario donde llevar a cabo la parte gráfica el juego.
	 */
	public ControlCentral (VentanaPrincipal ventana, String nJ, Escenario e)
	{
		try
		{
			Tactual = null;
			ventanaPrincipal = ventana;
			escenario = e;
			
			cargadorSprite = new CargadorSprite ();
		
			Mario PJ = new MarioChico (cargadorSprite);
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
			Tescenario = new Thread(escenario);
			Tjugador = new Thread (jugador);
			Tgravedad = new Thread (gravedad);
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
	 */
	public void agregarThread (Thread t)
	{
		if (Tactual == null)
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
			
			//Start Thread's
			Tescenario.start();
			Tjugador.start();
			Tgravedad.start();
			
			//Control Thread's
			//controlThreads();
			
			ventanaPrincipal.repaint();
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	/**
	 * 
	 */
	/*public void controlThreads ()
	{
		long startTime = 0;
		while (true)
		{
			startTime = System.currentTimeMillis();
			try {
				Tjugador.sleep(1000/velocidad);
				Tgravedad.sleep(1000/velocidad);
			    Tescenario.sleep(1000/velocidad);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * Inidica el tiempo de espera de actualización del Thread ingresado.
	 * 
	 * @param t Thread a pausar.
	 */
	/*public void esperar (Thread t)
	{
		long startTime = System.currentTimeMillis();
		do
		{
			Thread.yield();
		}
		while (System.currentTimeMillis()-startTime < (1000/velocidad));
	}*/
	
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
		
		gravedad.setAfectar(false);
		
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
		
		gravedad.setAfectar(false);
		
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