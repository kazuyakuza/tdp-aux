package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Jugador;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa al tipo de Personaje Mario del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class Mario extends Actor implements PjSeleccionable
{	
	
	//VARIABLES DE INSTANCIA
	protected boolean invulnerable;//Representa el estado en que Mario puede o no ser da�ado por los enemigos al colisionar.
	protected boolean destructor;//Representa el estado en que Mario puede o no matar a los enemigos al colisionar con ellos.
	protected Jugador jugador;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con los sprites pasados por parametro.
	 * 
	 * @param nombresSprites Nombres de los archivos de las imagenes del Sprite para este Actor.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	protected Mario (String[] nombresSprites, CargadorSprite cargadorSprite)
	{
		super (nombresSprites, cargadorSprite);
		invulnerable = false;
		destructor = false;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acci�n "arriba".
	 */
	public void arriba ()
	{
		saltar();
	}
	
	/**
	 * Especifica la acci�n "abajo".
	 */
	public void abajo ()
	{
		agacharse();
	}
	
	/**
	 * Especifica la acci�n "izquierda".
	 */
	public void izquierda ()
	{
		moverseAizquierda();
	}
	
	/**
	 * Especifica la acci�n "derecha".
	 */
	public void derecha ()
	{
		moverseAderecha();
	}
	
	/**
	 * Especifica la acci�n "A".
	 */
	public void A ()
	{
		accionA();
	}
	
	/**
	 * Especifica la acci�n "B".
	 */
	public void B ()
	{
		accionB();
	}
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @exception AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		Celda celdaInferior = celdaActual;
		try 
		{			 
			 celdaInferior = celdaActual.getBloque().getInferior(celdaActual);
			 if (!celdaInferior.isOcupada())
				 moverseAcelda(celdaInferior);
			 else
				 PG = 0;
		}
		catch (Exception e)
		{
			throw new AccionActorException ("Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e.getMessage());
		}
	}
	
	/**
	 * Realiza la acci�n de morir (ser destruido) de Mario.
	 */
	public void morir ()
	{
		super.morir();
		jugador.quitarVida();
	}
	
	/*COMANDOS*/
	
	/**
	 * Setea al Jugador que controla a Mario con j.
	 * 
	 * @param j Jugador de Mario.
	 */
	public void setJugador (Jugador j)
	{
		jugador = j;
	}
			
	/**
	 * Modifica el estado invulnerable de Mario a "v".
	 * 
	 * @param v Nuevo estado de invulnerabilidad (verdadero o falso) de Mario.
	 */
	public void setInvulnerabilidad (boolean v)
	{
		invulnerable = v;
	}
	
	/**
	 * Modifica el estado de destructor de Mario a "v".
	 * 
	 * @param v Nuevo estado de destructor (verdadero o falso) de Mario.
	 */
	public void setDestructor (boolean v)
	{
		destructor = v;
	}
				
	/*COMANDOS ABSTRACTOS*/
		
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al saltar.
	 */
	public abstract void saltar () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public abstract void moverseAizquierda () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de moverse hace la derecha.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public abstract void moverseAderecha () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al agacharse.
	 */
	public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Mario realiza la acci�n A.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al realizar la acci�n A.
	 */
	public abstract void accionA () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n B.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al realizar la acci�n B.
	 */
	public abstract void accionB () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al crecer.
	 */
	public abstract void crecer () throws AccionActorException;
	
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
	 * Devuelve el estado de invulnerabilidad de Mario.
	 * 
	 * @return True:  Mario es invulnerable.
	 *         False: caso contrario.
	 */
	public boolean esInvulnerable ()
	{
		return invulnerable;
	}
	
	/**
	 * Retorna el estado de destructor de Mario.
	 * 
	 * @return True:  si Mario est� en estado destructor.
	 *         False: caso contrario.
	 */
	public boolean esDestructor ()
	{
		return destructor;
	}
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * Realiza la acci�n de colisionar con otro Actor.
	 * Mario no provoca nada al colisionar con otros Actores.
	 * 
	 * Los efectos de la colisi�n la provocan los otros Actores.
	 * 
	 * @exception ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionar (Actor a) throws ColisionException
	{
		
	}
	
	/**
	 * Realiza la acci�n de colisionar con otro Personaje Seleccionable.
	 * Mario no provoca nada al colisionar con otro Personaje.
	 * 
	 * @exception ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException
	{
		
	}
	
}