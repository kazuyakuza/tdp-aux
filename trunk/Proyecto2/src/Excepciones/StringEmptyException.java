package ProyectoX.Excepciones;

/**
 * Excepción producida por un String vacío que no debería serlo.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class StringEmptyException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public StringEmptyException (String error)
	{
		super(error);
	}
	
}