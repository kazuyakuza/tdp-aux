package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDAArbol.Tree;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Interface para un Arbol Binario: una colecci�n de elementos que son enlazados de forma jer�rquica, donde se distingue un �nico elemento ra�z que no tiene padre, y cada elemento tiene un �nico elemento padre asociado, un elemento hijo izquierdo y un elemento hijo derecho.
 * 
 * @param <E>
 */
public interface BinaryTree<E> extends Tree<E>
{
	/*CONSULTAS*/
	
	/**
	 * Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbolB en p.
	 * 
	 * @param p Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo izquierdo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo izquierdo.
	 * @return Posici�n del Nodo-Hijo izquierdo.
	 */
	public Position<E> left (Position<E> p) throws PosicionInvalidaException,ViolacionLimiteException;
	
	/**
	 * Derecho: Devuelve el Nodo-Hijo derecho del NodoArbolB en p.
	 * 
	 * @param p Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo derecho.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo derecho.
	 */
	public Position<E> right (Position<E> p) throws PosicionInvalidaException,ViolacionLimiteException;
	
	/**
	 * Tiene Izquierdo: Verifica si el NodoArbolB en p tiene Nodo-Hijo izquierdo, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo izquierdo.
	 *         False: caso contrario.
	 */
	public boolean hasLeft (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Tiene Derecho: Verifica si el NodoArbolB en p tiene Nodo-Hijo derecho, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo derecho.
	 *         False: caso contrario.
	 */
	public boolean hasRight (Position<E> p) throws PosicionInvalidaException;

}