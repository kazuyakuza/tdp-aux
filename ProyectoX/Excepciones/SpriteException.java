package ProyectoX.Excepciones;

/**
 * Excepción producida al intentar cargar o manipular un Sprite.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class SpriteException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public SpriteException (String error)
	{
		super(error);
	}
	
}