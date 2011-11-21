package ProyectoX.Librerias.Threads;

/**
 * Excepci�n producida al pedir un UpNeeder a un Updater al que no pertenece.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class NoExisteUpNeederException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public NoExisteUpNeederException (String error)
	{
		super(error);
	}
	
}