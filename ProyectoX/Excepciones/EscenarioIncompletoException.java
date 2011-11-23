package ProyectoX.Excepciones;

/**
 * Excepci�n producida al manipular un Escenario incompleto.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class EscenarioIncompletoException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public EscenarioIncompletoException (String error)
	{
		super(error);
	}
	
}