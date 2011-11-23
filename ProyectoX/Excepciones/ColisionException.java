package ProyectoX.Excepciones;

/**
 * Excepción producida en una Colisión entre dos Actores.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class ColisionException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ColisionException (String error)
	{
		super(error);
	}
	
}