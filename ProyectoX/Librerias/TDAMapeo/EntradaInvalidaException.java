package ProyectoX.Librerias.TDAMapeo;

/**
 * Entrada Invalida Exception: Excepci�n en tiempo de ejecuci�n provocada al utilizar una Entrada incorrecta para un determinado �rbol Binario de B�squeda.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EntradaInvalidaException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public EntradaInvalidaException (String error)
	{
		super(error);
	}
	
	/**
	 * Devuelve la informaci�n del error de la excepci�n.
	 * 
	 * @return Informaci�n del error de la excepci�n.
	 */
	public String obtenerError ()
	{
		return getMessage();
	}
	
}