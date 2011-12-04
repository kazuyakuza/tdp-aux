package ProyectoX.Logica.NoPersonajes;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Responsabilidades.Movible;

/**
 * Representa a las Bolas de Fuego, lanzadas por Mario Blanco (Mario con efecto de Flor de Fuego), en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class BolaFuego extends Actor implements Movible
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
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminaci�n acciones.
	
	//Prioridades en UpNeeder
	//0 = morir (explotar)
	//1 = moverseAizquierda, moverseAderecha.

	/**
	 * Crea una Plataforma Irrompible.
	 */
	public BolaFuego(Mario pj) 
	{
		super(nombresSprites);
		mario = pj;
		upNeeder = new UpNeeder (1);
		Updater.getUpdater().addUpNeeder(upNeeder);
		spriteManager.cambiarSprite(enMovimiento);
		spriteManager.rotarGif(cantFramesMovimiento);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual. 
	 */
	public void colisionar (Actor a)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 */
	public void colisionarPj (PjSeleccionable pj)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 */
	public void colisionarBola (BolaFuego bola)
	{
		//No le afecta.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual.
	 * @throws NullPointerException Si c es null.
	 */
	protected void producirColisiones (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("BolaFuego.producirColisiones()" + "\n" +
					                         "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionarBola(this);
	}
			
	/**
	 * Realiza la acci�n de morir del Actor.
	 */
	public void morir()
	{		
		ActualizadorNivel.act().eliminarActor(this);		
		super.morir();		
		upNeeder.notUpdate();
		upNeeder = null;
	}
	
	/**
	 * Realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.hayAnterior())
			{
				celdaAnterior = celdaActual.getAnterior();
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
					if (celdaAnterior.getActores().hasNext()) //Si hay un Actor que est� ocupando totalmente la Celda anterior.
						explotar();
					else
					{
						if (! upNeeder.hayWorkerPrioridad(0))
							upNeeder.addWorker(0, new Worker ()
							{
								public void work() throws Exception
								{
									morir();
								}
							});
					}
				}
			}
			else
				if (! upNeeder.hayWorkerPrioridad(0))
					upNeeder.addWorker(0, new Worker ()
					{
						public void work() throws Exception
						{
							morir();
						}
					});
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("BolaFuego.moverseAizquierda()" + "\n" +
					                        "Imposible realizar la acci�n moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("BolaFuego.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acci�n de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.haySiguiente())
			{
				celdaSiguiente = celdaActual.getSiguiente();
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
					if (celdaSiguiente.getActores().hasNext()) //Si hay un Actor que est� ocupando totalmente la siguiente Celda.
						explotar();
					else
					{
						if (! upNeeder.hayWorkerPrioridad(0))
							upNeeder.addWorker(0, new Worker ()
							{
								public void work() throws Exception
								{
									morir();
								}
							});
					}
				}
			}
			else
				if (! upNeeder.hayWorkerPrioridad(0))
					upNeeder.addWorker(0, new Worker ()
					{
						public void work() throws Exception
						{
							morir();
						}
					});
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("BolaFuego.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("BolaFuego.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * La Bola de Fuego explota.
	 * 
	 * @param a Actor contra el que choc� y explot�.
	 */
	public void explotar ()
	{
		spriteManager.cambiarSprite(explotando);
		spriteManager.setGif(cantFramesExplotando);
		
		upNeeder.addWorker(0, new Worker ()
		{
			public void work() throws Exception
			{
				morir();
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