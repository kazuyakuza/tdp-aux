package ProyectoX.Librerias.TDALista;

/**
 * No Existe Elemento Exception: Excepción en tiempo de ejecución provocada al intentar pedir elemento siguiente de una posición null.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class NoExisteElementoException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public NoExisteElementoException(String error)
	{
		super(error);
	}
	
	/**
	 * Devuelve la información del error de la excepción.
	 * 
	 * @return Información del error de la excepción.
	 */
	public String obtenerError ()
	{
		return getMessage();
	}
	
}