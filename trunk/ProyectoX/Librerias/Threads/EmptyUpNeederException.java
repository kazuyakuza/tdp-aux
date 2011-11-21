package ProyectoX.Librerias.Threads;

/**
 * Excepci�n producida al pedir un Worker a un UpNeeder vac�o.
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
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public EmptyUpNeederException (String error)
	{
		super(error);
	}
	
}