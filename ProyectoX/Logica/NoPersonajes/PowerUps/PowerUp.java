package ProyectoX.Logica.NoPersonajes.PowerUps;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Responsabilidades.Movible;
import ProyectoX.Logica.Responsabilidades.Punteable;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Representa a todos los power ups del juego. Son Actores NoPersonajes que afectan al personaje del Jugador, Mario.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class PowerUp extends Actor implements Punteable, afectableXgravedad, Movible
{	
	//Atributos de Instancia
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acci�n arriba.
                 	 //Si PG=0, el Actor no es afectado por la Gravedad (est� sobre un lugar s�lido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acci�n de caer.
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminaci�n acciones.
	
	//Prioridades en UpNeeder
	//0 = morir
	//1 = moverseAizquierda, moverseAderecha.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un PowerUp.
	 */
	protected PowerUp (String[] nombresSprites) throws NullPointerException
	{
		super(nombresSprites);
		PG = 0;
		upNeeder = new UpNeeder (1);
		Updater.getUpdater().addUpNeeder(upNeeder);
	}
	
	/*COMANDOS*/
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (celdaActual.getInferior().isOcupada())
			PG = 0;
		else
			if (!(PG < 0))
				PG -= efecto;
	}
	
	/**
	 * Realiza el efecto del Power Up.
	 * @throws NullPointerException si mario es null.
	 */
	public abstract void efecto (Mario mario) throws NullPointerException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la Potencia de la Gravedad sobre este Actor.
	 * 
	 * @return Potencia de la Gravedad sobre este Actor.
	 */
	public int getPG ()
	{
		return PG;
	}
	
	/**
	 * Realiza la acci�n de morir del Actor.
	 */
	public void morir ()
	{
		ActualizadorNivel.act().eliminarPowerUp(this);
		upNeeder.notUpdate();
		upNeeder = null;
		
		super.morir();
	}
	
    /*METODOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		if (upNeeder.hayWorkerPrioridad(0))//El PowerUp se va a morir en la proxim� actualizaci�n.
			return;
		
		Celda celdaInferior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			celdaInferior = celdaActual.getInferior();
			if (!celdaInferior.isOcupada())
				moverseAcelda(celdaInferior);
			else
				PG = 0;
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("PowerUp.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("PowerUp.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}		
	}
	
	/**
	 * Realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
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
					if (! upNeeder.hayWorkerPrioridad(1))
						upNeeder.addWorker(1, new Worker ()
						{
							public void work() throws Exception
							{
								moverseAderecha();
							}
						});
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Imposible realizar la acci�n moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
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
					if (! upNeeder.hayWorkerPrioridad(1))
						upNeeder.addWorker(1, new Worker ()
						{
							public void work() throws Exception
							{
								moverseAizquierda();
							}
						});
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Imposible realizar la acci�n moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
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
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (final PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("PowerUp.colisionarPj()" + "\n" +
					                        "Imposible realizar colisi�n. El PjSeleccionable ingresado es null.");
		if (upNeeder.hayWorkerPrioridad(0))
			throw new ColisionException ("PowerUp.colisionarPj()" + "\n" +
							             "Imposible realizar colisi�n. El PowerUp est� muerto.");
			
		try
		{
			Mario mario = checkActorJugador (pj);
			pj.getJugador().asignarPuntos(getPuntos(mario));
			efecto(mario);
			
			if (! upNeeder.hayWorkerPrioridad(0))
				upNeeder.addWorker(0, new Worker ()
				{
					public void work() throws Exception
					{
						morir();
					}
				});
		}
		catch (Exception e)
		{
			throw new ColisionException ("PowerUp.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
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
			throw new NullPointerException ("PowerUp.producirColisiones()" + "\n" +
					                        "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);
	}	
	
}