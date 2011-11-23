package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDAArbol.Tree;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Interface para un Arbol Binario: una colección de elementos que son enlazados de forma jerárquica, donde se distingue un único elemento raíz que no tiene padre, y cada elemento tiene un único elemento padre asociado, un elemento hijo izquierdo y un elemento hijo derecho.
 * 
 * @param <E>
 */
public interface BinaryTree<E> extends Tree<E>
{
	/*CONSULTAS*/
	
	/**
	 * Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbolB en p.
	 * 
	 * @param p Posición del Nodo-Padre al que buscarle su Nodo-Hijo izquierdo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo izquierdo.
	 * @return Posición del Nodo-Hijo izquierdo.
	 */
	public Position<E> left (Position<E> p) throws PosicionInvalidaException,ViolacionLimiteException;
	
	/**
	 * Derecho: Devuelve el Nodo-Hijo derecho del NodoArbolB en p.
	 * 
	 * @param p Posición del Nodo-Padre al que buscarle su Nodo-Hijo derecho.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo derecho.
	 */
	public Position<E> right (Position<E> p) throws PosicionInvalidaException,ViolacionLimiteException;
	
	/**
	 * Tiene Izquierdo: Verifica si el NodoArbolB en p tiene Nodo-Hijo izquierdo, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo izquierdo.
	 *         False: caso contrario.
	 */
	public boolean hasLeft (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Tiene Derecho: Verifica si el NodoArbolB en p tiene Nodo-Hijo derecho, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo derecho.
	 *         False: caso contrario.
	 */
	public boolean hasRight (Position<E> p) throws PosicionInvalidaException;

}