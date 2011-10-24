package ProyectoX.Excepciones;

/**
 * Excepci�n producida cuando un Actor realiza una acci�n err�nea.
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
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public AccionActorException (String error)
	{
		super(error);
	}
	
}