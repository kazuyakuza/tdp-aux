package ProyectoX.Excepciones;

/**
 * Excepción producida cuando un Actor realiza una acción errónea.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class AccionActorException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public AccionActorException (String error)
	{
		super(error);
	}
	
}