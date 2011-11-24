package ProyectoX.Logica.NoPersonajes.Especiales;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa al lugar de llegada para ganar un Nivel.
 * Cuando un PjSeleccionable colisiona (se ubica en la misma celda) con una Llegada, el Jugador gana y termina el Nivel.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public class Llegada extends Actor
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Vine2.gif", dirRecursos + "Goal-Flag.gif"};
	
	//Atributos de Instancia
	private ControlCentral controlCentral;
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea una Llegada.
	 * 
	 * @param cc ControlCentral del Juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 * @throws NullPointerException Si cc es null.
	 */
	public Llegada (ControlCentral cc, CargadorSprite cargadorSprite) throws NullPointerException
	{
		super (nombresSprites, cargadorSprite);
		
		if (cc == null)
			throw new NullPointerException ("Llegada." + "\n" +
					                        "Imposible crear Llegada con ControlCentral null.");
		
		controlCentral = cc;
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
		/*No hace nada, no tiene efecto sobre Actores que no sean Personajes.*/
	}
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{
		controlCentral.ganarNivel();
	}
	
	/**
	 * Realiza la acción de colisionar con una Bola de Fuego de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (Actor bola) throws ColisionException
	{
		//No hace nada, no tiene efecto sobre este actor.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones(Celda c)
	{
		/*No hace nada, nunca ocurre.*/	
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		PG = 0;
	}
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * No tiene ningún efecto en este Actor.
	 * 
	 * @throws AccionActorException Si se produce algún error al caer.
	 */
	public void caer () throws AccionActorException
	{
		/*No hace nada, nunca ocurre.*/
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir(Actor a) throws NullPointerException
	{
		/*No hace nada, nunca ocurre.*/
	}
	
}