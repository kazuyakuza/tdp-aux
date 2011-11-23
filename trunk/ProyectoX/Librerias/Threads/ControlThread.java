package ProyectoX.Librerias.Threads;

/**
 * Define los m�todos necesario para un ControlThread.
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
	 * Recibe una Excepci�n e de un AliveThread.
	 * 
	 * @param e Excepci�n producida por un AliveThread.
	 */
	public void error (Exception e);
	
	/**
	 * Devuelve el tiempo indicado de espera entre cada ejecuci�n para un AliveThread.
	 * 
	 * @return tiempo de espera.
	 */
	public int getSleepTime ();

}