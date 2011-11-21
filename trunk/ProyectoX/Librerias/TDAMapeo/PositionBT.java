package ProyectoX.Librerias.TDAMapeo;

import ProyectoX.Librerias.TDALista.Position;

/**
 * Interface Position Binary Tree: Establece las operaciones necesarias para un TDA position para un arbol binario. 
 *  
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public interface PositionBT<E> extends Position<E>
{
	
	/*COMANDOS*/
	
	/**
	 * Cambiar Elemento: Cambia el Rótulo Genérico actual por el nuevo Rótulo Genérico r.
	 * 
	 * @param r Nuevo Rótulo Genérico.
	 */
	public void setElement (E r);
	
	/**
	 * Cambiar Padre: Cambia el Nodo-Padre actual por el nuevo Nodo-Padre p.
	 * 
	 * @param p Nuevo Nodo-Padre.
	 */
	public void setParent (PositionBT<E> p);
	
	/**
	 * Cambiar Izquierdo: Cambia el Nodo-Hijo izquierdo actual por el nuevo Nodo-Hijo hI.
	 * 
	 * @param hI Nuevo Nodo-Hijo izquierdo.
	 */
	public void setLeft (PositionBT<E> hI);
	
	/**
	 * Cambiar Derecho: Cambia el Nodo-Hijo derecho actual por el nuevo Nodo-Hijo hD.
	 * 
	 * @param hD Nuevo Nodo-Hijo derecho.
	 */
	public void setRight (PositionBT<E> hD);
	
	/*CONSULTAS*/
	
	/**
	 * Obtener Padre: Devuelve el Nodo-Padre al NodoArbol actual.
	 * 
	 * @return Nodo-Padre del NodoArbol actual.
	 */
	public PositionBT<E> getParent ();
	
	/**
	 * Obtener Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbol actual.
	 * 
	 * @return Nodo-Hijo izquierdo del NodoArbol actual.
	 */
	public PositionBT<E> getLeft ();
	
	/**
	 * Obtener Derecho: Devuelve el Nodo-Hijo derecho del NodoArbol actual.
	 * 
	 * @return Nodo-Hijo derecho del NodoArbol actual.
	 */
	public PositionBT<E> getRight ();

}