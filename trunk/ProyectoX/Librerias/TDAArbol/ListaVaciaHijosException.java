package ProyectoX.Librerias.TDAArbol;

/**
 * Lista Vacia Hijos Exception: Excepci�n en tiempo de ejecuci�n provocada al intentar Hijos a un NodoArbol que no tiene Hijos.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ListaVaciaHijosException extends RuntimeException
{

	/**
	 * Recibe la informaci�n del error, y crea la excepci�n.
	 * 
	 * @param error Informaci�n del error.
	 */
	public ListaVaciaHijosException(String error)
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