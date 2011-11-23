package ProyectoX.Excepciones;

/**
 * Excepci�n producida por el Control Central.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class ControlCentralException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public ControlCentralException (String error)
	{
		super(error);
	}
	
}