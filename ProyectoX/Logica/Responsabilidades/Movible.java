package ProyectoX.Logica.Responsabilidades;

import ProyectoX.Excepciones.AccionActorException;

/**
 * Interfaz que representa a los Actores del juego que pueden moverse.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Movible 
{
	/**
	 * Realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException;
	
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAizquierda () throws AccionActorException;
}
