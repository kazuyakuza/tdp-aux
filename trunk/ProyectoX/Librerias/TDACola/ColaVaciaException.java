package ProyectoX.Librerias.TDACola;

/**
 * Cola Vac�a Exception: Excepci�n en tiempo de ejecuci�n provocada al intentar utilizar los m�todos dequeue o front en una Cola vac�a.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ColaVaciaException extends RuntimeException
{
	
	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public ColaVaciaException(String error)
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