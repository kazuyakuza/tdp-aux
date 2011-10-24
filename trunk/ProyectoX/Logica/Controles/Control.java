package ProyectoX.Logica.Controles;

import java.awt.event.KeyListener;

/**
 * Representa las operaciones b�sicas que debe tener un Control.
 * 
 * Control: sistema por el cual se puede efectuar determinadas acciones.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Control extends KeyListener
{
	
	/*CONSULTAS*/
	
	/**
	 * Verifica si se esta realizando la acci�n "arriba", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean arriba ();
	
	/**
	 * Verifica si se esta realizando la acci�n "abajo", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean abajo ();
	
	/**
	 * Verifica si se esta realizando la acci�n "izquierda", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean izquierda ();
	
	/**
	 * Verifica si se esta realizando la acci�n "derecha", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean derecha ();
	
	/**
	 * Verifica si se esta realizando la acci�n "A", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean A ();
	
	/**
	 * Verifica si se esta realizando la acci�n "B", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean B ();
	
	/**
	 * Verifica si se esta realizando la acci�n "ESC", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean ESC ();
	
	/**
	 * Verifica si se esta realizando la acci�n "aceptar", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acci�n.
	 *         False: caso contrario.
	 */
	public boolean aceptar ();

}