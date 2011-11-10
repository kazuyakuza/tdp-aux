package ProyectoX.Librerias.TDALista;

import java.util.Iterator;

/**
 * Interface para una Lista Position Iterable: una colección de elementos que son enlazados de forma lineal, donde cada elemento posee una posición en la Lista.
 * 
 * @param <E>
 */
public interface PositionList<E> extends Iterable<E>
{
	
	/*COMANDOS*/
	
	/**
	 * Agregar Primero: Inserta un elemento en el frente de la Lista, devolviendo la nueva posición.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addFirst (E e);
	
	/**
	 * Agregar Ultimo: Inserta un elemento en el final de la Lista, devolviendo la nueva posición.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addLast (E e);
	
	/**
	 * Agregar Después: Inserta un elemento en la Lista luego del Nodo que está en la posición p.
	 * 
	 * Se asume que, si la posición p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posición p está contenido en la Lista.
	 * 
	 * @param p Posición del Nodo.
	 * @param e Elemento a insertar.
	 * @exception PosicionInvalidaException Si la posición es nula, o no es de un tipo de dato correcto para la Lista.
	 */
	public void addAfter (Position<E> p, E e) throws PosicionInvalidaException;
	
	/**
	 * Agregar Antes: Inserta un elemento en la Lista antes del Nodo que está en la posición p.
	 * 
	 * Se asume que, si la posición p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posición p está contenido en la Lista.
	 * 
	 * @param p Posición del Nodo.
	 * @param e Elemento a insertar.
	 * @exception PosicionInvalidaException Si la posición es nula, o no es de un tipo de dato correcto para la Lista.
	 */
	public void addBefore (Position<E> p, E e) throws PosicionInvalidaException;
	
	/**
	 * Eliminar: Elimina el Nodo de la Lista que está en la posición p,
	 * y devuelve el elemento del Nodo eliminado.
	 * 
	 * @param p Posición del Nodo a eliminar.
	 * @return Elemento del Nodo eliminado.
	 * @exception PosicionInvalidaException Si la posición es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E remove (Position<E> p) throws PosicionInvalidaException;
	
	/**
	 * Establecer: Reemplaza el elemento guardado en el Nodo al que hacer referencia la posición p,
	 * y se devuelve el elemento eliminado.
	 * 
	 * @param p Posición del Nodo al que se le cambiará el elemento.
	 * @return Elemento eliminado del Nodo.
	 * @exception PosicionInvalidaException Si la posición es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E set (Position<E> p, E e) throws PosicionInvalidaException;
	
	/*CONSULTAS*/
	
	/**
	 * Tamaño: Devuelve la cantidad de elementos actual que contiene la Lista.
	 * 
	 * @return Cantidad de elementos en la Lista.
	 */
	public int size ();
	
	/**
	 * Vacía: Verifica si la Lista es vacía, y devuelve el resultado.
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
	 * Ultimo: Devuelve el último Nodo de la Lista.
	 * 
	 * @return Último Nodo de la Lista.
	 */
	public Position<E> last ();
	
	/**
	 * Siguiente: Devuelve el Nodo siguiente al Nodo que está en la posición p.
	 * 
	 * @param p Posición del Nodo.
	 * @return Nodo siguiente al Nodo en p.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posición a la que está intentando acceder no existe.
	 */
	public Position<E> next (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Previo: Devuelve el Nodo anterior al Nodo que está en la posición p.
	 * 
	 * @param p Posición del Nodo.
	 * @return Nodo anterior al Nodo en p.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posición a la que está intentando acceder no existe.
	 */
	public Position<E> prev (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException;
	
	/**
	 * Iterador: Devuelve un Iterador con todos los elementos contenidos en la Lista.
	 * 
	 * @return Iterador con todos los elementos contenidos en la Lista.
	 */
	public Iterator<E> iterator ();

}