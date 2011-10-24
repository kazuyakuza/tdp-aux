package ProyectoX.Librerias.TDALista;

import java.util.Iterator;

/**
 * Interface para una Lista Position Iterable: una colecci�n de elementos que son enlazados de forma lineal, donde cada elemento posee una posici�n en la Lista.
 * 
 * @param <E>
 */
public interface PositionList<E> extends Iterable<E>
{
	
	/*COMANDOS*/
	
	/**
	 * Agregar Primero: Inserta un elemento en el frente de la Lista, devolviendo la nueva posici�n.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addFirst (E e);
	
	/**
	 * Agregar Ultimo: Inserta un elemento en el final de la Lista, devolviendo la nueva posici�n.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addLast (E e);
	
	/**
	 * Agregar Despu�s: Inserta un elemento en la Lista luego del Nodo que est� en la posici�n p.
	 * 
	 * Se asume que, si la posici�n p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posici�n p est� contenido en la Lista.
	 * 
	 * @param p Posici�n del Nodo.
	 * @param e Elemento a insertar.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista.
	 */
	public void addAfter (Position<E> p, E e) throws PosicionInvalidaException;
	
	/**
	 * Agregar Antes: Inserta un elemento en la Lista antes del Nodo que est� en la posici�n p.
	 * 
	 * Se asume que, si la posici�n p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posici�n p est� contenido en la Lista.
	 * 
	 * @param p Posici�n del Nodo.
	 * @param e Elemento a insertar.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista.
	 */
	public void addBefore (Position<E> p, E e) throws PosicionInvalidaException;
	
	/**
	 * Eliminar: Elimina el Nodo de la Lista que est� en la posici�n p,
	 * y devuelve el elemento del Nodo eliminado.
	 * 
	 * @param p Posici�n del Nodo a eliminar.
	 * @return Elemento del Nodo eliminado.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E remove (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Establecer: Reemplaza el elemento guardado en el Nodo al que hacer referencia la posici�n p,
	 * y se devuelve el elemento eliminado.
	 * 
	 * @param p Posici�n del Nodo al que se le cambiar� el elemento.
	 * @return Elemento eliminado del Nodo.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E set (Position<E> p, E e) throws PosicionInvalidaException;
	
	/*CONSULTAS*/
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos actual que contiene la Lista.
	 * 
	 * @return Cantidad de elementos en la Lista.
	 */
	public int size ();
	
	/**
	 * Vac�a: Verifica si la Lista es vac�a, y devuelve el resultado.
	 * 
	 * @return True:  la Lista no contiene elementos.
	 *         False: la Lista contiene almenos 1 elemento.
	 */
	public boolean isEmpty ();
	
	/**
	 * Primero: Devuelve el primer Nodo de la Lista.
	 * 
	 * @return Primer Nodo de la Lista.
	 */
	public Position<E> first ();
	
	/**
	 * Ultimo: Devuelve el �ltimo Nodo de la Lista.
	 * 
	 * @return �ltimo Nodo de la Lista.
	 */
	public Position<E> last ();
	
	/**
	 * Siguiente: Devuelve el Nodo siguiente al Nodo que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo.
	 * @return Nodo siguiente al Nodo en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posici�n a la que est� intentando acceder no existe.
	 */
	public Position<E> next (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Previo: Devuelve el Nodo anterior al Nodo que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo.
	 * @return Nodo anterior al Nodo en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posici�n a la que est� intentando acceder no existe.
	 */
	public Position<E> prev (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Iterador: Devuelve un Iterador con todos los elementos contenidos en la Lista.
	 * 
	 * @return Iterador con todos los elementos contenidos en la Lista.
	 */
	public Iterator<E> iterator ();

}