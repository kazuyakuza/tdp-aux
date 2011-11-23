package ProyectoX.Librerias.Threads;

/**
 * Excepción producida al pedir un Worker a un UpNeeder vacío.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class EmptyUpNeederException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public EmptyUpNeederException (String error)
	{
		super(error);
	}
	
}