package ProyectoX.Logica.Personajes;

import java.util.Iterator;
import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Punteable;
import ProyectoX.Logica.Movible;
import ProyectoX.Logica.Jugador;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa al tipo de Personaje Mario, personaje seleccionable del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Mario extends Actor implements PjSeleccionable, Movible
{	

	//Atributos de Clase
	private static int maxPS = 4;//M�xima Potencia de Salto.
	private int PS = 0;//Potencia de Salto Actual.
	
	//Atributos de Instancia
	protected Caracteristica miCaracteristica;	//Representa al tipo de Mario, chico, grande o blanco.
	protected Jugador jugador;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por par�metro.
	 * 
	 * @param c Caracteristica de Mario con la que se inicializa.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Mario (Caracteristica c, CargadorSprite cargadorSprite)
	{
		super (c.getNombresSprites(), cargadorSprite);
		miCaracteristica = c;
		c.setMario(this);			
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acci�n "arriba".
	 */
	public synchronized void arriba ()
	{
		saltar();
	}
	
	/**
	 * Especifica la acci�n "abajo".
	 */
	public synchronized void abajo ()
	{
		miCaracteristica.agacharse();
	}
	
	/**
	 * Especifica la acci�n "izquierda".
	 */
	public synchronized void izquierda ()
	{
		moverseAizquierda();
	}
	
	/**
	 * Especifica la acci�n "derecha".
	 */
	public synchronized void derecha ()
	{
		moverseAderecha();
	}
	
	/**
	 * Especifica la acci�n "A".
	 */
	public void A ()
	{
		miCaracteristica.accionA();
	}
	
	/**
	 * Especifica la acci�n "B".
	 */
	public void B ()
	{
		miCaracteristica.accionB();
	}
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		Celda celdaInferior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayInferior(celdaActual))
			{
				celdaInferior = celdaActual.getBloque().getInferior(celdaActual);
				if (!celdaInferior.isOcupada())
					moverseAcelda(celdaInferior);
				else
					PG = 0;
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Mario.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Mario.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
		
		if (celdaActual.getBloque().getInferior(celdaActual).isOcupada())
        {
                if (! upNeeder.hayWorkerPrioridad(5))
                        upNeeder.addWorker(5,
                                        new Worker ()
                                {
                                                public void work() throws Exception
                                                {
                                                        spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
                                                }
                                });
        }
        else
        {
                if (! upNeeder.hayWorkerPrioridad(0))
                        upNeeder.addWorker(0,
                                        new Worker ()
                                {
                                                public void work() throws Exception
                                                {
                                                        spriteManager.cambiarSprite(miCaracteristica.spriteSaltando());
                                                }
                                });
        }
	}
	
	/**
	 * Realiza la acci�n de morir (ser destruido) de Mario.
	 * 
	 * @throws NullPointerException Si jugador es null.
	 */
	public void morir (Actor a) throws NullPointerException, AccionActorException
	{				
		if (a == null)
			throw new NullPointerException ("Mario.morir()" + "\n" + 
											"Imposible obtener los puntos perdidos por el causante de la muerte. Actor es null.");		
		if (jugador == null)
			throw new NullPointerException ("Mario.morir()" + "\n" +
                                            "Imposible quitar vida al Jugador. Jugador es null.");
		spriteManager.cambiarSprite(miCaracteristica.spriteMuerto());
		super.morir(a);
		try
		{
			jugador.asignarPuntos(((Punteable)a).getPuntos(this));
			jugador.quitarVida();
		}
		catch (ClassCastException e)
		{	
			throw new AccionActorException("Mario.morir()" + "\n" +
										   "Imposible obtener los puntos del actor causante de la muerte.");
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
	 */
	public void saltar () throws AccionActorException
	{		
		Celda celdaSuperior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");			
			if ((PG == 0) && (PS < maxPS))
			{
				spriteManager.cambiarSprite(miCaracteristica.spriteSaltando());				
				if (celdaActual.getBloque().haySuperior(celdaActual))
				{
					PG++;
					PS++;
					celdaSuperior = celdaActual.getBloque().getSuperior(celdaActual);
					if (!celdaSuperior.isOcupada())
						moverseAcelda(celdaSuperior);
				}
			}
			else
				if (PG != -1)
					PS = 0;
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Mario.saltar()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Mario.saltar()" + "\n" +
                                            "Imposible realizar la acci�n saltar a/desde Celda de posici�n (" + celdaSuperior.getPosFila() + "," + celdaSuperior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
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
			
			if (celdaActual.getBloque().hayAnterior(celdaActual))
			{
				spriteManager.cambiarSprite(-miCaracteristica.spriteCaminando());
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
				
				if (! upNeeder.hayWorkerPrioridad(5))
                    upNeeder.addWorker(5, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		spriteManager.cambiarSprite(-miCaracteristica.spriteQuieto());
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
	 * Mario realiza la acci�n de moverse hace la derecha.
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
			
			if (celdaActual.getBloque().haySiguiente(celdaActual))
			{
				spriteManager.cambiarSprite(miCaracteristica.spriteCaminando());
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
				
				if (! upNeeder.hayWorkerPrioridad(5))
                    upNeeder.addWorker(5, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
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
	 * Setea al Jugador que controla a Mario con j.
	 * 
	 * @param j Jugador de Mario.
	 * @throws NullPointerException Si j es null.
	 */
	public void setJugador (Jugador j) throws NullPointerException
	{
		if (j == null)
			throw new NullPointerException ("Mario.setJugador()" + "\n" +
                                            "Imposible asignar un Jugador null.");
		
		jugador = j;
	}
		
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		miCaracteristica.crecerHongo();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		miCaracteristica.crecerFlor();
	}
				
	/*COMANDOS ABSTRACTOS*/
		
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
	 */
	//public abstract void saltar () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	//public abstract void moverseAizquierda () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	//public abstract void moverseAderecha () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	//public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo.
	 * @param a es el Actor (enemigo) que colisiona con Mario.
	 * @throws AccionActorException Si se produce alg�n error al ser da�ado.
	 */
	public void serDa�ado (Actor a) throws AccionActorException
	{
		miCaracteristica.serDa�ado(a);
	}
	
	/**
	 * Setea la Caracteristica de Mario con c.
	 * 
	 * @param c Caracteristica de Mario.
	 */
	public void setCaracteristica (Caracteristica c)
	{
		miCaracteristica = c;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Jugador que controla a Mario.
	 * 
	 * @return Jugador que controla a Mario.
	 */
	public Jugador getJugador ()
	{
		return jugador;
	}
	
	/**
	 * Retorna la Caracteristica asociada a Mario.
	 * @return la Caracteristica asociada a Mario.
	 */
	public Caracteristica getCaracteristica ()
	{
		return miCaracteristica;
	}
	
	/**
	 * Retorna el multiplicador bonus que aplica Mario sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus()
	{
		return miCaracteristica.multiplicadorBonus();
	}
		
	/**
	 * Devuelve el PowerUp que Mario necesita para evolucionar (crecer).
	 * @return el PowerUP que Mario necesita para evolucionar (crecer).
	 */
	/*public abstract PowerUp crecimiento();
	{
		return miCaracteristica.crecimiento();
	}*/	  
	 
		
	/*M�todos en Ejecuci�n*/
	
	/**
	 * Realiza la acci�n de colisionar con otro Actor.
	 * Mario no provoca nada al colisionar con otros Actores.
	 * 
	 * Los efectos de la colisi�n la provocan los otros Actores.
	 * 
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionar (Actor a) throws ColisionException, NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("Mario.colisionar()" + "\n" +
            								"Imposible colisionar con Actor nulo.");
		a.colisionarPj(this);
	}
	
	/**
	 * Realiza la acci�n de colisionar con otro Personaje Seleccionable.
	 * Mario no provoca nada al colisionar con otro Personaje.
	 * 
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{
		
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionarPj(this);	
	}
	
}