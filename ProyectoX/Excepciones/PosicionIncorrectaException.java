package ProyectoX.Excepciones;

/**
 * Excepci�n producida al ingresar una posici�n incorrecta o no debida.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class PosicionIncorrectaException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public PosicionIncorrectaException (String error)
	{
		super(error);
	}
	
}