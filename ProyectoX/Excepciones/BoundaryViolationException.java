package ProyectoX.Excepciones;

/**
 * Excepci�n producida al ingresar valores fuera del rango de lo esperado.
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
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public BoundaryViolationException (String error)
	{
		super(error);
	}	
}