package ProyectoX.Librerias.TDALista;

/**
 * Clase Nodo con elemento de tipo Gen�rico que implementa la interface Position.
 * 
 * Implementaci�n de un Nodo para la clase PositionList, con referencia a un �nico Nodo siguiente,
 * y referencia a un elemento de tipo Gen�rico e.
 * 
 * Contiene 2 constructores:
 *  + Nodo Vac�o
 *  + Nodo con Nodo siguiente n y elemento Gen�rico e.
 *  
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 * @param <E>
 */
public class Nodo<E> implements Position<E>
{
	
	//Variables de Instancia.
	private Nodo<E> siguiente; //Referencia al siguiente Nodo.
	private E elemento; //Elemento del Nodo.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Nodo vac�o.
	 */
	public Nodo ()
	{
		siguiente = null;
		elemento = null;
	}
	
	/**
	 * Crea un Nodo con Nodo siguiente n y con elemento Gen�rico e.
	 * 
	 * @param n Nodo al que referenciar.
	 * @param e Elemento del Nodo.
	 */
	public Nodo (Nodo<E> n, E e)
	{
		siguiente = n;
		elemento = e;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el Nodo siguiente actual por el nuevo Nodo n.
	 * 
	 * @param n Nuevo Nodo siguiente.
	 */
	public void siguiente (Nodo<E> n)
	{
		siguiente = n;
	}
	
	/**
	 * Cambia el elemento Gen�rico actual por el nuevo elemento Gen�rico e.
	 * 
	 * @param e Nuevo elemento Gen�rico.
	 */
	public void elemento (E e)
	{
		elemento = e;
	}
	
	/**
	 * Elimina la referencia al Nodo siguiente y al elemento del Nodo.
	 */
	public void limpiar ()
	{
		siguiente = null;
		elemento = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Nodo siguiente al Nodo actual.
	 * 
	 * @return Nodo siguiente al Nodo actual.
	 */
	public Nodo<E> siguiente ()
	{
		return siguiente;
	}
	
	/**
	 * Elemento: Devuelve el elemento actual del Nodo.
	 * 
	 * @return Elemento actual del Nodo.
	 */
	public E element ()
	{
		return elemento;
	}

}