package ProyectoX.Librerias.Threads;

/**
 * Excepción producida al pedir un UpNeeder a un Updater al que no pertenece.
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
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public NoExisteUpNeederException (String error)
	{
		super(error);
	}
	
}