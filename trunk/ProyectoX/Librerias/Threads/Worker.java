package ProyectoX.Librerias.Threads;

/**
 * Defina las operaciones necesarias de un Worker.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Worker
{
	
	/**
	 * Trabajo a realizar por el Worker.
	 * 
	 * @throws Exception Si se produce alguna excepción al realizar el trabajo.
	 */
	public void work () throws Exception;

}