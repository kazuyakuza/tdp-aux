package ProyectoX.Librerias.Threads;

/**
 * Define los métodos necesario para un ControlThread.
 * Un ControlThread controla objecto de tipo AliveThread.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface ControlThread
{
	
	/**
	 * Recibe una Excepción e de un AliveThread.
	 * 
	 * @param e Excepción producida por un AliveThread.
	 */
	public void error (Exception e);
	
	/**
	 * Devuelve el tiempo indicado de espera entre cada ejecución para un AliveThread.
	 * 
	 * @return tiempo de espera.
	 */
	public int getSleepTime ();

}