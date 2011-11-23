package ProyectoX.Logica.Personajes;

import ProyectoX.Logica.Jugador;

/**
 * Contiene las acciones de un Personaje Seleccionable.
 * Personaje Seleccionable es todo aquel Personaje del Juego que puede ser seleccionado y controlado por el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface PjSeleccionable extends Personaje
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
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Jugador que controla a Mario.
	 * 
	 * @return Jugador que controla a Mario.
	 */
	public Jugador getJugador ();
		
}