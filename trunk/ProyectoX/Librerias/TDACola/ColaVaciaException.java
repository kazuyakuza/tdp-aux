package ProyectoX.Librerias.TDACola;

/**
 * Cola Vacía Exception: Excepción en tiempo de ejecución provocada al intentar utilizar los métodos dequeue o front en una Cola vacía.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ColaVaciaException extends RuntimeException
{
	
	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ColaVaciaException(String error)
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