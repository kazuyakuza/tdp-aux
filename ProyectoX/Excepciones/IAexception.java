package ProyectoX.Excepciones;

/**
 * Excepción producida por una Inteligancia Artificial.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class IAexception extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public IAexception (String error)
	{
		super(error);
	}
	
}