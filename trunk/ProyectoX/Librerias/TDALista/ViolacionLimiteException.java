package ProyectoX.Librerias.TDALista;

/**
 * Violacion de Limite Exception: Excepción en tiempo de ejecución provocada al sobrepasar los límites.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ViolacionLimiteException extends RuntimeException
{
	
	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ViolacionLimiteException(String error)
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