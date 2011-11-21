package ProyectoX.Librerias.TDAArbol;

import java.util.Iterator;

import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Interface para un Arbol General: una colecci�n de elementos que son enlazados de forma jer�rquica, donde se distingue un �nico elemento ra�z que no tiene padre, y cada elemento tiene un �nico elemento padre asociado y un n�mero n de elementos hijos.
 * 
 * @param <E>
 */
public interface Tree <E> extends Iterable<E>
{

	/*COMANDOS*/
	
	/**
	 * Reemplaza el R�tulo del NodoArbol que se encuentra en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @param e Nuevo R�tulo.
	 * @return R�tulo reemplazado.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	public E replace (Position<E> p, E e) throws PosicionInvalidaException;
	
	/*CONSULTAS*/
	
	/**
	 * Ra�z: Devuelve la Ra�z del �rbol actual.
	 * 
	 * @return Ra�z del �rbol.
	 * @exception ArbolVacioException Si se pide la Ra�z de un �rbol vac�o.
	 */
	public Position<E> root () throws ArbolVacioException;
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos en el �rbol.
	 * 
	 * @return Cantidad de elementos en el Arbol.
	 */
	public int size ();
	
	/**
	 * Est� Vac�o: Verifica si el �rbol est� vac�o y devuelve el resultado.
	 * 
	 * @return True:  el �rbol no contiene elementos.
	 *         False: el �rbol contiene almenos el elemento Ra�z.
	 */
	public boolean isEmpty ();
	
	/**
	 * Devuelve una colecci�n iterable con los elementos guardados en el �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los elementos guardados en el �rbol.
	 * @exception ArbolVacioException Si se pide un iterador de un �rbol vac�o.
	 */
	public Iterator<E> iterator () throws ArbolVacioException;
	
	/**
	 * Devuelve una colecci�n iterable con los NodoArbol del �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los NodoArbol del �rbol.
	 * @exception ArbolVacioException Si se pide las posiciones de un �rbol vac�o.
	 */
	public Iterable<Position<E>> positions () throws ArbolVacioException;
	
	/**
	 * Padre: devuelve el padre del NodoArbol que est� en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return Padre del NodoArbol en la Posici�n p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se consulta por el Nodo-Padre de la Raiz. 
	 */
	public Position<E> parent (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Hijos: Devuelve una colecci�n iterable de la Lista de Hijos del Nodo-Padre que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo-Padre.
	 * @return Iterador con la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public Iterable<Position<E>> children (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Interno: Verifica si el NodoArbol que est� en la posici�n p es interno y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p tiene hijos.
	 *         False: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isInternal (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Externo: Verifica si el NodoArbol que est� en la posici�n p es externo y devuelce el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene hijos.
	 *         Falso: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isExternal (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Es Ra�z: Verifica si el NodoArbol que est� en la posici�n p es ra�z y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene Nodo-Padre.
	 *         False: el NodoArbol en p tiene Nodo-Padre.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isRoot (Position<E> p) throws PosicionInvalidaException;
	
}