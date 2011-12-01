package ProyectoX.Logica.Personajes.Enemigo;

import ProyectoX.Logica.Personajes.Personaje;
import ProyectoX.Logica.Personajes.Enemigo.IA.IA;
import ProyectoX.Logica.Responsabilidades.Posicionable;
import ProyectoX.Logica.Responsabilidades.Punteable;

/**
 * Contiene las acciones de un Enemigo.
 * Enemigo es todo aquel Personaje del Juego que debe ser controlado por una IA del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Enemigo extends Personaje, Punteable, Posicionable
{
	
	/*COMANDOS*/
	
	/**
	 * Realiza la acción "izquierda".
	 */
	public void izquierda ();
	
	/**
	 * Realiza la acción "derecha".
	 */
	public void derecha ();
	
	/**
	 * Setea la IA que controla al Enemigo con ia.
	 * 
	 * @param j IA del Enemigo.
	 * @throws NullPointerException Si ia es null.
	 */
	public void setIA (IA ia) throws NullPointerException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la IA que controla al Enemigo.
	 * 
	 * @return IA que controla al Enemigo.
	 */
	public IA getIA ();

}