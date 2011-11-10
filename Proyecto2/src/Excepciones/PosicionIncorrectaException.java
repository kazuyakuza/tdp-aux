package ProyectoX.Excepciones;

/**
 * Excepción producida al ingresar una posición incorrecta o no debida.
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
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public PosicionIncorrectaException (String error)
	{
		super(error);
	}
	
}