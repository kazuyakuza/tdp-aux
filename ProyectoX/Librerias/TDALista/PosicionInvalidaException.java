package ProyectoX.Librerias.TDALista;

/**
 * Posicion Invalida Exception: Excepci�n en tiempo de ejecuci�n provocada al intentar asignar una posici�n inv�lida.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class PosicionInvalidaException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public PosicionInvalidaException(String error)
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