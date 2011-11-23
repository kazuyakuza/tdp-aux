package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDAMapeo.ClaveInvalidaException;
import ProyectoX.Librerias.TDAMapeo.Entry;

/**
 * Interface para una Cola Con Prioridad: una colecci�n de elementos con prioridades que soporta la inserci�n en cualquier orden, pero la eliminaci�n se debe hacer deacuerdo a la prioridad de los elementos.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public interface PriorityQueue<K,V>
{
	
	/*COMANDOS*/
	
	/**
	 * Insertar: Agrega una Entrada [k,v] a la Cola.
	 * 
	 * @param k Clave de la nueva Entrada.
	 * @param v Valor de la nueva Entrada.
	 * @return Entrada [k,v] creada.
	 * @exception ClaveInvalidaException SI la clave es null.
	 */
	public Entry<K,V> insert (K k, V v) throws ClaveInvalidaException;
	
	/**
	 * Eliminar M�nimo: Se elimina el elemento de la Cola que tiene menor valor (m�xima prioridad), y se lo devuelve.
	 * 
	 * @return Elemento de menor valor (m�xima prioridad).
	 * @exception ColaConPrioridadVaciaException Si se intenta eliminar en una Cola vac�a.
	 */
	public Entry<K,V> removeMin() throws ColaConPrioridadVaciaException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de Entradas en la Cola.
	 * 
	 * @return Cantidad de Entradas en la Cola.
	 */
	public int size();

	/**
	 * Verifica si la Cola est� vac�a, y devuelve el resultado.
	 * 
	 * @return True:  la Cola no contiene elementos.
	 *         False: la Cola contiene almenos 1 Entrada.
	 */
	public boolean isEmpty();

	/**
	 * Devuelve la Entrada de menor valor (m�xima prioridad) de la Cola Con Prioridad.
	 * 
	 * @return Entrada de menor valor (m�xima prioridad).
	 * @exception ColaConPrioridadVaciaException Si la Cola est� vac�a. 
	 */
	public Entry<K,V> min() throws ColaConPrioridadVaciaException;

}