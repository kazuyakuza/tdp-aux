package ProyectoX.Librerias.TDAColaConPrioridad;

/**
 * Cola Vac�a Exception: Excepci�n en tiempo de ejecuci�n provocada al intentar utilizar m�todos en una Cola Con Prioridad vac�a.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ColaConPrioridadVaciaException extends RuntimeException
{
	
	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public ColaConPrioridadVaciaException(String error)
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