package ProyectoX.Excepciones;

/**
 * Excepci�n producida en una Colisi�n entre dos Actores.
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
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public ColisionException (String error)
	{
		super(error);
	}
	
}