package ProyectoX.Librerias.TDAColaConPrioridad;

import java.util.Comparator;

import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDAMapeo.ClaveInvalidaException;
import ProyectoX.Librerias.TDAMapeo.Comparador;
import ProyectoX.Librerias.TDAMapeo.Entrada;
import ProyectoX.Librerias.TDAMapeo.Entry;

/**
 * Clase Cola Con Prioridad Con Heap que implementa la interface PriorityQueue.
 * 
 * Cola con Prioridad implementada con un Heap (�rbol Parcialmente Ordenado implementado sobre un arreglo).
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class ColaConPrioridadConHeap <K,V> implements PriorityQueue<K,V>
{
	
	//Variables de Instancia.
	protected ArbolBinarioCompletoArreglo<Entry<K,V>> heap;
	protected Comparator<K> comp;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Cola con Prioridad vac�a.
	 */
	public ColaConPrioridadConHeap ()
	{
		heap = new ArbolBinarioCompletoArreglo<Entry<K,V>> ();
		comp = new Comparador<K>();	
	}
	
	/*COMANDOS*/
	
	/**
	 * Insertar: Agrega una Entrada [k,v] a la Cola.
	 * 
	 * @param k Clave de la nueva Entrada.
	 * @param v Valor de la nueva Entrada.
	 * @return Entrada [k,v] creada.
	 * @exception ClaveInvalidaException SI la clave es null.
	 */
	public Entry<K,V> insert (K k, V v) throws ClaveInvalidaException
	{
		if (k == null)
			throw new ClaveInvalidaException ("La clave que est� intentando utilizar es null.");
		Entry<K,V> entry = new Entrada<K,V> (k,v);//To return.
		upHeap(heap.add(entry));//Se reconfigura la Cola dejando el elemento m�nimo (m�xima prioridad) en la cabeza de la Cola.
		return entry;
	}
	
	/**
	 * Eliminar M�nimo: Se elimina el elemento de la Cola que tiene menor valor (m�xima prioridad), y se lo devuelve.
	 * 
	 * @return Elemento de menor valor (m�xima prioridad).
	 * @exception ColaConPrioridadVaciaException Si se intenta eliminar en una Cola vac�a.
	 */
	public Entry<K,V> removeMin() throws ColaConPrioridadVaciaException 
	{
		if (isEmpty())
			throw new ColaConPrioridadVaciaException("La Cola al que est� intentando acceder est� vac�a.");
		Entry<K,V> min = heap.root().element();//To return. Elemento m�nimo, de m�xima prioridad.
		if (size() == 1)
			heap.remove();//Si la Cola solo tiene 1 elemento, entonces se elimina el m�nimo.
		else
		{
			heap.replace(heap.root(),heap.remove());//Si la Cola tiene mas elementos, se elimina el m�nimo, y se lo intercambia por el �ltimo elementos de la Cola.
			downHeap(heap.root());//Se reconfigura la Cola dejando el elemento m�nimo (m�xima prioridad) en la cabeza de la Cola.
		}
		return min;
	}
	
	/**
	 * Elevar Entrada: Compara la clave de la Entrada que est� en la posici�n v, con la clave de la Entrada de su padre:
	 * + si son iguales o la clave de la Entrada del padre tiene mayor prioridad, entonces v no se eleva.
	 * + si la clave de la Entrada que est� en la posici�n v tiene mayor prioridad que la de su padre, entonces se intercambian.
	 * Hasta que v sea la entrada de mayor prioridad o hasta que las entradas siguientes tengan mayor prioridad que v.
	 * 
	 * @param v Posici�n de la Entrada a elevar.
	 */
	private void upHeap (Position<Entry<K,V>> v)
	{
		Position<Entry<K,V>> u;//Posici�n de Entradas auxiliar.
		//Ubicar el elemento v en la Cola deacuerdo a su prioridad.
		while (! heap.isRoot(v))
		{
			u = heap.parent(v);
			if (compararEntradas(u.element(), v.element()) <= 0)
				break;
			swap(u,v);//Si el padre tiene menos prioridad que el que se est� agregando, entonces se intercambian.
			v = u;
		}
	}
	
	/**
	 * Bajar Entrada: Compara la clave de la Entrada que est� en la posici�n r, con la clave de la Entrada de su hijo con mayor prioridad:
	 * + si son iguales o la clave de la Entrada del hijo tiene menor prioridad, entonces r no se baja.
	 * + si la clave de la Entrada que est� en la posici�n r tiene menor prioridad que la de su hijo, entonces se intercambian.
	 * Hasta que r sea la entrada de menor prioridad o hasta que r ya no tenga hijos.
	 * 
	 * @param r Posici�n de la Entrada a bajar.
	 */
	private void downHeap (Position<Entry<K,V>> r)
	{
		while(heap.isInternal(r))//Mientras que r tenga hijos.
		{
			Position<Entry<K,V>> s;//Posici�n del hijo de mayor prioridad.
			//Busco el hijo de mayor prioridad.
			if (! heap.hasRight(r))
				s = heap.left(r);
			else
				if (compararEntradas (heap.left(r).element(), heap.right(r).element()) <= 0)
					s = heap.left(r);
				else
					s = heap.right(r);
			//Comparo el hijo con el padre, e intercambio si el hijo tiene mas prioridad que el padre.
			if (compararEntradas (s.element(), r.element()) <= 0)
			{
				swap(r,s);
				r = s;
			}
			else
				break;//Si el hijo tiene menos prioridad que el padre, los hijos subsiguientes tambi�n tendr�n menor prioridad.
		}
	}
	
	/**
	 * Intercambiar: Intercambia las entradas que est�n en las posiciones x e y.
	 * 
	 * @param x Posici�n de una de las entradas a intercambiar.
	 * @param y Posici�n de una de las entradas a intercambiar.
	 */
	private void swap(Position<Entry<K,V>> x, Position<Entry<K,V>> y)
	{
		Entry<K,V> temp = x.element();
		heap.replace(x,y.element());
		heap.replace(y,temp);
	}
	
	/**
	 * Compara las entradas e1 y e2 respecto a sus claves, y devuelve el resultado.
	 * 
	 * @param e1 Entrada a comparar con e2.
	 * @param e2 Entrada a comparar con e1.
	 * @return Entero Negativo: e1 es menor a e2.
	 *         Cero: e1 es igual a e2.
	 *         Entero Positivo: e1 es mayor a e2.
	 */
	private int compararEntradas (Entry<K,V> e1, Entry<K,V> e2)
	{
		return (comp.compare(e1.getKey(),e2.getKey()));
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de Entradas en la Cola.
	 * 
	 * @return Cantidad de Entradas en la Cola.
	 */
	public int size()
	{
		return heap.size();
	}

	/**
	 * Verifica si la Cola est� vac�a, y devuelve el resultado.
	 * 
	 * @return True:  la Cola no contiene elementos.
	 *         False: la Cola contiene almenos 1 Entrada.
	 */
	public boolean isEmpty()
	{
		return (heap.size() == 0);
	}

	/**
	 * Devuelve la Entrada de menor valor (m�xima prioridad) de la Cola Con Prioridad.
	 * 
	 * @return Entrada de menor valor (m�xima prioridad).
	 * @exception ColaConPrioridadVaciaException Si la Cola est� vac�a. 
	 */
	public Entry<K,V> min() throws ColaConPrioridadVaciaException 
	{
		if (isEmpty())
			throw new ColaConPrioridadVaciaException("La Cola al que est� intentando acceder est� vac�a.");
		return heap.root().element();
	}

}