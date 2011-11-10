package ProyectoX.Excepciones;

/**
 * Excepci�n producida al Iniciar un Nivel incorrectamente.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class InicioNivelException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public InicioNivelException (String error)
	{
		super(error);
	}
	
}