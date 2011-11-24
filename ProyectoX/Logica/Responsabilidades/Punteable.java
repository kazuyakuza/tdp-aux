package ProyectoX.Logica.Responsabilidades;

import ProyectoX.Logica.Personajes.Mario;

/**
 * Interfaz que representa a los Actores del juego que pueden asignan puntos al jugador.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Punteable 
{	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario);
}
