package ProyectoX.Librerias.TDAArbol;

import java.util.Iterator;

import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Interface para un Arbol General: una colección de elementos que son enlazados de forma jerárquica, donde se distingue un único elemento raíz que no tiene padre, y cada elemento tiene un único elemento padre asociado y un número n de elementos hijos.
 * 
 * @param <E>
 */
public interface Tree <E> extends Iterable<E>
{

	/*COMANDOS*/
	
	/**
	 * Reemplaza el Rótulo del NodoArbol que se encuentra en la Posición p.
	 * 
	 * @param p Posición del NodoArbol.
	 * @param e Nuevo Rótulo.
	 * @return Rótulo reemplazado.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta. 
	 */
	public E replace (Position<E> p, E e) throws PosicionInvalidaException;
	
	/*CONSULTAS*/
	
	/**
	 * Raíz: Devuelve la Raíz del Árbol actual.
	 * 
	 * @return Raíz del Árbol.
	 * @exception ArbolVacioException Si se pide la Raíz de un Árbol vacío.
	 */
	public Position<E> root () throws ArbolVacioException;
	
	/**
	 * Tamaño: Devuelve la cantidad de elementos en el Árbol.
	 * 
	 * @return Cantidad de elementos en el Arbol.
	 */
	public int size ();
	
	/**
	 * Está Vacío: Verifica si el Árbol está vacío y devuelve el resultado.
	 * 
	 * @return True:  el Árbol no contiene elementos.
	 *         False: el Árbol contiene almenos el elemento Raíz.
	 */
	public boolean isEmpty ();
	
	/**
	 * Devuelve una colección iterable con los elementos guardados en el Árbol.
	 * 
	 * El iterador es construido Recorriendo el Árbol en PreOrden.
	 * 
	 * @return Colección iterable con los elementos guardados en el Árbol.
	 * @exception ArbolVacioException Si se pide un iterador de un Árbol vacío.
	 */
	public Iterator<E> iterator () throws ArbolVacioException;
	
	/**
	 * Devuelve una colección iterable con los NodoArbol del Árbol.
	 * 
	 * El iterador es construido Recorriendo el Árbol en PreOrden.
	 * 
	 * @return Colección iterable con los NodoArbol del Árbol.
	 * @exception ArbolVacioException Si se pide las posiciones de un Árbol vacío.
	 */
	public Iterable<Position<E>> positions () throws ArbolVacioException;
	
	/**
	 * Padre: devuelve el padre del NodoArbol que está en la Posición p.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return Padre del NodoArbol en la Posición p.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se consulta por el Nodo-Padre de la Raiz. 
	 */
	public Position<E> parent (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Hijos: Devuelve una colección iterable de la Lista de Hijos del Nodo-Padre que está en la posición p.
	 * 
	 * @param p Posición del Nodo-Padre.
	 * @return Iterador con la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public Iterable<Position<E>> children (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Interno: Verifica si el NodoArbol que está en la posición p es interno y devuelve el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p tiene hijos.
	 *         False: caso contrario.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isInternal (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Externo: Verifica si el NodoArbol que está en la posición p es externo y devuelce el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene hijos.
	 *         Falso: caso contrario.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isExternal (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Raíz: Verifica si el NodoArbol que está en la posición p es raíz y devuelve el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene Nodo-Padre.
	 *         False: el NodoArbol en p tiene Nodo-Padre.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isRoot (Position<E> p) throws PosicionInvalidaException;
	
}