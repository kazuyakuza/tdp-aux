package ProyectoX.Excepciones;

/**
 * Excepción producida por el Control Central.
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
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ControlCentralException (String error)
	{
		super(error);
	}
	
}