package ProyectoX.Logica;

import java.util.Iterator;

import ProyectoX.Grafico.BloqueGrafico;
import ProyectoX.Grafico.Escenario;
import ProyectoX.Grafico.VentanaPrincipal;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.AliveThread;
import ProyectoX.Librerias.Threads.ControlThread;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.WorkersSincronizados;
import ProyectoX.Logica.Controles.Control;
import ProyectoX.Logica.Controles.Teclado;
import ProyectoX.Logica.Mapa.Bloque;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Mapa.Nivel;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialPowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.FlorFuego;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.SuperHongo;
import ProyectoX.Logica.NoPersonajes.PowerUps.BombaNuclear;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.MarioChico;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Personajes.Enemigo.IA.IAControl;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

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
	private Gravedad gravedad;
	private Updater updater;
	private IAControl iaControl;
	
	//Threads
	private Thread Tactual;
	private AliveThread Tescenario, Tjugador, Tgravedad, TiaControl, Tupdater;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Control Central con el Escenario e, para el usuario de nombre nJ, en la Ventana Principal ventana.
	 * 
	 * @param ventana Ventana Principal donde se va a ejecutar el Juego.
	 * @param nJ Nombre Jugador.
	 * @param e Escenario donde llevar a cabo la parte gr�fica el juego.
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
			
			nivel = new Nivel(1);
			
			gravedad = new Gravedad(this);
			
			updater = new Updater ();
			
			iaControl = new IAControl ();
			
			ventanaPrincipal.agregarEscenario(e);
			escenario.inicializarGrafica();
			escenario.agregarControl(c);
			ventanaPrincipal.repaint();
			
			//Crea y Asigna WorkersSincronizados
			WorkersSincronizados ws1 = new WorkersSincronizados (updater, 1, escenario);
			
			//Crear y Asignar Threads
			Tescenario = new AliveThread (this, 0.5, ws1.getWorker2());
			Tjugador = new AliveThread (this, 1, jugador);
			Tgravedad = new AliveThread (this, 1, gravedad);
			TiaControl = new AliveThread (this, 1, iaControl);
			Tupdater = new AliveThread (this, 0.5, ws1.getWorker1());
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega el Thread que ejecutar� el run de esta clase.
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
	
	public void agregarActor (Actor a)
	{
		nivel.getActores(this).addLast(a);
		for (UpNeeder un: a.getUpNeeders())
			updater.addUpNeeder(un);
	}
	
	public void agregarAfectableXgravedad (afectableXgravedad aXg)
	{
		nivel.getCaibles(this).addFirst(aXg);
		agregarActor((Actor) aXg);
	}
	
	public void agregarPowerUp (PowerUp pu)
	{			
		for (UpNeeder un: pu.getUpNeeders())
			updater.addUpNeeder(un);
		nivel.agregarPowerUp(pu);		
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
		return nivel.getActores(this).iterator();
	}
	
	/**
	 * Devuelve un iterador de los Actores Caibles actuales en Juego.
	 * 
	 * @return Iterador de los Actores Caibles actuales en Juego.
	 */
	public Iterator<afectableXgravedad> getCaibles ()
	{
		return nivel.getCaibles(this).iterator();
	}
	
	/**
	 * Cambia las plataformas EspecialPowerUp para que contenga FlorFuego.
	 */
	public void cambiarPlataformasFlor ()
	{
		for (EspecialPowerUp plataforma: nivel.getEspecialesPowerUp(this))
			if (plataforma.esCambiable())
				plataforma.cambiarPowerUp(new FlorFuego (plataforma.getSpriteManager().getCargadorSprite()));	
	}
	
	/**
	 * Cambia las plataformas EspecialPowerUp para que contenga SuperHongo.
	 */
	public void cambiarPlataformasSuperHongo ()
	{
		for (EspecialPowerUp plataforma: nivel.getEspecialesPowerUp(this))
			if (plataforma.esCambiable())
				plataforma.cambiarPowerUp(new SuperHongo (plataforma.getSpriteManager().getCargadorSprite()));
	}
	
	/**
	 * Realiza la exploci�n de la BombaNuclear en el juego.
	 * Mata a todos los enemigos que se encuentren en un radio de 10 celdas.
	 * 
	 * @param bomba es la BombaNuclear que se activ� en el juego.
	 * @throws NullPointerException si bomba es null.
	 */
	public void explotarBombaNuclear (BombaNuclear bomba) throws NullPointerException
	{
		for (Enemigo enemigo: nivel.getEnemigos(this))			
			if (distancia (bomba.getCeldaActual(),enemigo.getCeldaActual()) <= 10 )			
				((Actor)enemigo).morir();		
	}
	
	/**
	 * Calcula la distancia que hay entre las Celdas.
	 * @param c1 Celda que se desea calcular su distancia a c2.
	 * @param c2 Celda que se desea calcular su distancia a c1.
	 * @return entero que es la distancia entre las Celdas c1 y c2.
	 * @throws NullPointerException si c1 o c2 son null.
	 */
	protected int distancia (Celda c1, Celda c2) throws NullPointerException
	{
		if (c1 == null || c2 == null)
			throw new NullPointerException ("ControlCentral.distancia()" + "\n" +
											"Imposible calcular distancia, alguna celda ess nulas.");
				
		int x = Math.abs(c1.getPosFila() - c2.getPosFila());
		int y = Math.abs(c1.getPosColumna() - c2.getPosColumna());		
		return (int) Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
	}
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * Run para el Thread de esta clase.
	 */
	public void run ()
	{
		try
		{
			//Inicializaci�n L�gica.
			nivel.inicializarNivel((Actor) jugador.personaje, this, cargadorSprite);
			
			//Inicializaci�n Gr�fica.
			Bloque bloqueActual = nivel.getBloqueActual();
			BloqueGrafico[][] bloques = new BloqueGrafico[1][1];
			BloqueGrafico bloqueGrafico = new BloqueGrafico (bloqueActual.getColumnas() - 1, bloqueActual.getFilas() - 1);
			bloques[0][0] = bloqueGrafico;
			//Agregando Piso
			bloqueGrafico.setNivelPiso(bloqueActual.getNivelPiso());
			for (Actor a: nivel.getActores(this))
			{
				bloqueGrafico.agregarSprite(a.spriteManager);
				a.spriteManager.setBloqueGrafico(this, bloqueGrafico);
			}
			
			escenario.agregarFondo(nivel.fondo(), cargadorSprite);
			escenario.agregarBloquesGrafico(bloques);
			escenario.setBloqueGraficoActual(new int[] {0,0});
			escenario.agregarSpriteCentral(((Actor) jugador.personaje).spriteManager);
			
			//Agrego IAs al IAControl
			for (Enemigo e: nivel.getEnemigos(this))
				iaControl.addIA(e.getIA());
			
			//Agregando UpNeeders al Updater
			for (Actor a: nivel.getActores(this))
				for (UpNeeder un: a.getUpNeeders())
					updater.addUpNeeder(un);
			
			//Start Thread's
			Tjugador.start();
			TiaControl.start();
			Tgravedad.start();
			Tupdater.start();
			Tescenario.start();
			
			ventanaPrincipal.repaint();
			
			//test();
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	public void test ()
	{
		/*((Mario) jugador.personaje).crecerHongo();
		((Mario) jugador.personaje).crecerFlor();*/
		
		while (true)
		/*for (int i=0; i<10; i++)*/
		{
			/*try {
				Thread.sleep((int) (getSleepTime()));
				} catch (InterruptedException e) {				
					e.printStackTrace();
				}
				
			((Actor) jugador.personaje).spriteManager.flashear();*/
			ventanaPrincipal.repaint();
		}
		//((Actor) jugador.personaje).spriteManager.cargarSprites(((Mario) jugador.personaje).getCaracteristica().getNombresSprites());*/
	}
	
	/**
	 * Devuelve el tiempo indicado de espera entre cada ejecuci�n para un AliveThread.
	 * 
	 * @return tiempo de espera.
	 */
	public int getSleepTime ()
	{
		return (int) (1000/velocidad);
	}
	
	/**
	 * Recibe una Excepci�n e de un AliveThread.
	 * 
	 * @param e Excepci�n producida por un AliveThread.
	 */
	public void error (Exception e)
	{
		ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
	}
	
	/**
	 * Acci�n ESC (escape) del Juego.
	 * 
	 * Abre un men� de salida del Juego.
	 */
	public void ESC ()
	{
		//men� para salir de la partida
	}
	
	/**
	 * Acci�n aceptar del Juego.
	 */
	public void aceptar ()
	{
		//pausa
	}
	
	/**
	 * Para los Threads.
	 */
	private void pararThreads ()
	{
		Tjugador.kill();
		TiaControl.kill();
		Tgravedad.kill();
		Tupdater.kill();
		Tescenario.kill();
	}
	
	/**
	 * Indica que el Jugador ha ganado el Nivel actual.
	 */
	public void ganarNivel ()
	{
		pararThreads ();
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		ventanaPrincipal.mensajeError("Fin del Juego", jugador.nombre+ " Ganaste" + "\n" +
				                                       "Puntos: " + jugador.puntos, true);
	}
	
	/**
	 * Reinicia el nivel a una posici�n inicial.
	 */
	public void reiniciarNivel ()
	{
		
	}
	
	/**
	 * Indica que el Jugador ha perdido el Nivel actual.
	 */
	public void perderNivel ()
	{
		pararThreads ();
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		ventanaPrincipal.mensajeError("Perdiste", jugador.nombre+ " Perdiste" + "\n" +
                                                  "Puntos: " + jugador.puntos, true);
	}
	
	/**
	 * Muestra un Mensaje de Error, con su t�tulo y mensaje, provocado por o en el Compenente ventana.
	 * 
	 * @param ventana Componente que llama a este m�todo, donde se gener� el error.
	 * @param titulo T�tulo del Mensaje de Error.
	 * @param mensaje Mensaje del Error.
	 */
	public void mensajeError (String titulo, String mensaje, boolean cerrar)
	{
		ventanaPrincipal.mensajeError(titulo, mensaje, cerrar);
	}

}