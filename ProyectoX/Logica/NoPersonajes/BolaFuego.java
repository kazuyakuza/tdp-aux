package ProyectoX.Logica.NoPersonajes;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Responsabilidades.Movible;

/**
 * Representa a las Bolas de Fuego, lanzadas por Mario Blanco (Mario con efecto de Flor de Fuego), en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class BolaFuego extends Actor implements Movible//, afectableXgravedad
{
	//Variables de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "FireBall-1.png", // 0 = en movimiento
		                                             dirRecursos + "FireBall-2.png",
		                                             dirRecursos + "FireBall-3.png",
		                                             dirRecursos + "FireBall-4.png",
													 dirRecursos + "FireBallHit-1.png", // 4 = explotando
													 dirRecursos + "FireBallHit-2.png",
													 dirRecursos + "FireBallHit-3.png",
													 dirRecursos + "FireBallHit-4.png",};
	
	private static int enMovimiento = 0;
	private static int cantFramesMovimiento = 4;
	private static int explotando = 4;
	private static int cantFramesExplotando = 4;
	
	//Variables de Instancia
	protected Mario mario;
	//protected IA miIA;
	
	//Prioridades en UpNeeder
	//0 = morir (explotar)
	//1 = moverseAizquierda, moverseAderecha.

	/**
	 * Crea una Plataforma Irrompible.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public BolaFuego(Mario pj, CargadorSprite cargadorSprite) 
	{
		super(nombresSprites, cargadorSprite);
		mario = pj;
		//miIA = new IA();
		
		spriteManager.cambiarSprite(enMovimiento);
		spriteManager.rotarGif(cantFramesMovimiento);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor a.
	 * No tiene ningún efecto con este Actor.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión. 
	 */
	public void colisionar (Actor a) throws ColisionException, NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("BolaFuego.colisionar()" + "\n" +
											"Imposible realizar la colisión, actor nulo.");
		a.colisionarBola(this);
	}
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{
		System.out.println("Bola presente, tocada por el jugador.");
		/*No hace nada, no tiene efecto sobre otros Actores.*/
	}
	
	/**
	 * Realiza la acción de colisionar con una Bola de Fuego de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (Actor bola) throws ColisionException
	{
		//No hace nada, no tiene efecto sobre estos actores.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones(Celda c)
	{
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	/*public void efectoGravedad (int efecto)
	{
		PG = 0;
	}*/
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * No tiene ningún efecto en este Actor.
	 * 
	 * @throws AccionActorException Si se produce algún error al caer.
	 */
	/*public void caer () throws AccionActorException
	{*/
		/*No hace nada, nunca ocurre.*/
	//}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir(Actor a) throws NullPointerException
	{
		celdaActual.getBloque().getMapa().getNivel().eliminarActores(this);
		super.morir(a);
	}
	
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayAnterior(celdaActual))
			{				
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
				{
					moverseAcelda(celdaAnterior);
					
					if (! upNeeder.hayWorkerPrioridad(1))
						upNeeder.addWorker(1, new Worker ()
						{
							public void work() throws Exception
							{
								moverseAizquierda ();
							}
						});
				}
				else
				{
					if (celdaAnterior.getActores().hasNext()) //Si hay un Actor que está ocupando totalmente la Celda anterior.
						explotar(celdaAnterior.getActores().next());
					else
						morir(this);
				}
			}
			else
				morir(this);
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("BolaFuego.moverseAizquierda()" + "\n" +
					                        "Imposible realizar la acción moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("BolaFuego.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().haySiguiente(celdaActual))
			{	
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
				{
					moverseAcelda(celdaSiguiente);
				
					if (! upNeeder.hayWorkerPrioridad(1))
						upNeeder.addWorker(1, new Worker ()
						{
							public void work() throws Exception
							{
								moverseAderecha ();
							}
						});
				}
				else
				{
					if (celdaSiguiente.getActores().hasNext()) //Si hay un Actor que está ocupando totalmente la siguiente Celda.
						explotar(celdaSiguiente.getActores().next());
					else
						morir(this);
				}
			}
			else
				morir(this);
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("BolaFuego.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("BolaFuego.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * La Bola de Fuego explota.
	 * 
	 * @param a Actor contra el que chocó y explotó.
	 */
	public void explotar (final Actor a)
	{
		spriteManager.cambiarSprite(explotando);
		spriteManager.setGif(cantFramesExplotando);
		
		upNeeder.addWorker(0, new Worker ()
		{
			public void work() throws Exception
			{
				morir(a);
			}
		});
	}
	
	/*CONSULTAS*/
	
	/**
	 * Retorna el Mario al que pertenece.
	 * @return el Mario que la creo.
	 */
	public Mario getMario ()
	{
		return mario;
	}

}