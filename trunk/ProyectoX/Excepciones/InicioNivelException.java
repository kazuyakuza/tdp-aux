package ProyectoX.Excepciones;

/**
 * Excepción producida al Iniciar un Nivel incorrectamente.
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
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public InicioNivelException (String error)
	{
		super(error);
	}
	
}