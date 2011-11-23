package ProyectoX.Librerias.TDAMapeo;

/**
 * Entrada Invalida Exception: Excepción en tiempo de ejecución provocada al utilizar una Entrada incorrecta para un determinado Árbol Binario de Búsqueda.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EntradaInvalidaException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public EntradaInvalidaException (String error)
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