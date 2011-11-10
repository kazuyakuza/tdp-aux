package ProyectoX.Excepciones;

/**
 * Excepción producida al ingresar valores fuera del rango de lo esperado.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class BoundaryViolationException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public BoundaryViolationException (String error)
	{
		super(error);
	}	
}