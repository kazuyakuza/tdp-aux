package ProyectoX.Librerias.TDAColaConPrioridad;

/**
 * Cola Vacía Exception: Excepción en tiempo de ejecución provocada al intentar utilizar métodos en una Cola Con Prioridad vacía.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ColaConPrioridadVaciaException extends RuntimeException
{
	
	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ColaConPrioridadVaciaException(String error)
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