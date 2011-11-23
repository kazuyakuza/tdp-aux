package ProyectoX.Librerias.TDACola;

/**
 * Interface para una Cola: una colecci�n de elementos que son insertados y removidos de acuerdo al principio "el primero en entrar es el primero en salir".
 * 
 * @param <E>
 */
public interface Queue<E>
{
	
	/*COMANDOS*/
	
	/**
	 * Encolar: Inserta un elemento en el final de la Cola.
	 * 
	 * Si la Cola est� llena, se crea un nuevo arreglo con el doble de tama�o,
	 * y se pasan los elementos al nuevo arreglo, incluyendo el elemento que se quiere insertar.
	 * 
	 * @param e Elemento que ser� insertado.
	 */
	public void enqueue (E e);
	
	/**
	 * Desencolar: Remueve el elemento en la cabeza de la Cola.
	 * 
	 * Si la Cola est� vac�a, dispara una excepci�n.
	 * 
	 * @return Elemento removido de la Cola.
	 * @exception ColaVaciaException Si la Cola est� vac�a.
	 */
	public E dequeue () throws ColaVaciaException;
	
	/*CONSULTAS*/
	
	/**
	 * Tama�o: Devuelve la cantidad actual de elementos en la Cola.
	 * 
	 * @return Cantidad actual de elementos en la Cola.
	 */
	public int size ();
	
	/**
	 * Vac�a: Verifica si la Cola est� vac�a, y devuelve el restultado.
	 * 
	 * @return True:  si la Cola no contiene elementos.
	 *         False: la Cola contiene almenos 1 elemento.
	 */
	public boolean isEmpty ();
	
	/**
	 * Cabeza: Devuelve el elemento en la Cabeza de la Cola.
	 * 
	 * @return Elemento en la cabeza de la Cola.
	 * @exception ColaVaciaException Si la Cola no contiene elementos.
	 */
	public E front () throws ColaVaciaException;
		
}