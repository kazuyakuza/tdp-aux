package ProyectoX.Logica.Personajes;

import ProyectoX.Logica.Jugador;
import ProyectoX.Logica.Responsabilidades.Posicionable;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Contiene las acciones de un Personaje Seleccionable.
 * Personaje Seleccionable es todo aquel Personaje del Juego que puede ser seleccionado y controlado por el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface PjSeleccionable extends Personaje, Posicionable, afectableXgravedad
{
	
	/*COMANDOS*/
	
	/**
	 * Realiza la acción "arriba".
	 */
	public void arriba ();
	
	/**
	 * Realiza la acción "abajo".
	 */
	public void abajo ();
	
	/**
	 * Realiza la acción "izquierda".
	 */
	public void izquierda ();
	
	/**
	 * Realiza la acción "derecha".
	 */
	public void derecha ();
	
	/**
	 * Realiza la acción "A".
	 */
	public void A ();
	
	/**
	 * Realiza la acción "B".
	 */
	public void B ();
	
	/**
	 * Realiza la acción de pararse.
	 */
	public abstract void pararse ();
	
	/**
	 * No realiza ninguna acción.
	 */
	public void quieto ();
	
	/**
	 * Realiza la acción "morir".
	 */
	public void morir ();
	
	/**
	 * Setea al Jugador que controla al PjSeleccionable con j.
	 * 
	 * @param j Jugador del PjSeleccionable.
	 * @throws NullPointerException Si j es null.
	 */
	public void setJugador (Jugador j) throws NullPointerException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Jugador que controla al PjSeleccionable.
	 * 
	 * @return Jugador que controla al PjSeleccionable.
	 */
	public Jugador getJugador ();
		
}