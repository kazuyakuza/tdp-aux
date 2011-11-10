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
	protected boolean invulnerable;//Representa el estado en que Mario puede o no ser dañado por los enemigos al colisionar.
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
	 * Especifica la acción "arriba".
	 */
	public void arriba ()
	{
		saltar();
	}
	
	/**
	 * Especifica la acción "abajo".
	 */
	public void abajo ()
	{
		agacharse();
	}
	
	/**
	 * Especifica la acción "izquierda".
	 */
	public void izquierda ()
	{
		moverseAizquierda();
	}
	
	/**
	 * Especifica la acción "derecha".
	 */
	public void derecha ()
	{
		moverseAderecha();
	}
	
	/**
	 * Especifica la acción "A".
	 */
	public void A ()
	{
		accionA();
	}
	
	/**
	 * Especifica la acción "B".
	 */
	public void B ()
	{
		accionB();
	}
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
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
			throw new AccionActorException ("Imposible realizar la acción caer a/desde Celda de posición (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e.getMessage());
		}
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de Mario.
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
	 * Mario realiza la acción de saltar.
	 * 
	 * @exception AccionActorException Si se produce algún error al saltar.
	 */
	public abstract void saltar () throws AccionActorException;
		
	/**
	 * Mario realiza la acción de moverse hacia la izquierda.
	 * 
	 * @exception AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public abstract void moverseAizquierda () throws AccionActorException;
		
	/**
	 * Mario realiza la acción de moverse hace la derecha.
	 * 
	 * @exception AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public abstract void moverseAderecha () throws AccionActorException;
		
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @exception AccionActorException Si se produce algún error al agacharse.
	 */
	public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @exception AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public abstract void accionA () throws AccionActorException;
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @exception AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public abstract void accionB () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @exception AccionActorException Si se produce algún error al crecer.
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
	 * @return True:  si Mario está en estado destructor.
	 *         False: caso contrario.
	 */
	public boolean esDestructor ()
	{
		return destructor;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor.
	 * Mario no provoca nada al colisionar con otros Actores.
	 * 
	 * Los efectos de la colisión la provocan los otros Actores.
	 * 
	 * @exception ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionar (Actor a) throws ColisionException
	{
		
	}
	
	/**
	 * Realiza la acción de colisionar con otro Personaje Seleccionable.
	 * Mario no provoca nada al colisionar con otro Personaje.
	 * 
	 * @exception ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException
	{
		
	}
	
}