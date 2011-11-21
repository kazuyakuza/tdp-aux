package ProyectoX.Librerias.TDAArbol;

/**
 * Lista Vacia Hijos Exception: Excepción en tiempo de ejecución provocada al intentar Hijos a un NodoArbol que no tiene Hijos.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ListaVaciaHijosException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public ListaVaciaHijosException(String error)
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