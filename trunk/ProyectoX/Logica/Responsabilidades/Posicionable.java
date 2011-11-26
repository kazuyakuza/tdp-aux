package ProyectoX.Logica.Responsabilidades;

import ProyectoX.Logica.Mapa.Celda;

/**
 * Interfaz que representa a los Actores del juego que se encuentran en una celda del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Posicionable 
{
	/**
	 * Cambia la Celda Actual por la Celda C.
	 * 
	 * @param c Nueva Celda Actual.
	 * @throws NullPointerException Si c es null.
	 */
	public void setCeldaActual (Celda c) throws NullPointerException;
	
	/**
	 * Devuelve la celda actual del Actor.
	 * 
	 * @return Celda actual del Actor.
	 */
	public Celda getCeldaActual ();
}
