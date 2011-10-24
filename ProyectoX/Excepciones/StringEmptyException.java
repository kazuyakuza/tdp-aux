package ProyectoX.Excepciones;

/**
 * Excepci�n producida por un String vac�o que no deber�a serlo.
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
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public StringEmptyException (String error)
	{
		super(error);
	}
	
}