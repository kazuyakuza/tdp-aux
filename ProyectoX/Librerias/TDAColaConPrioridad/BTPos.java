package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDALista.Position;

/**
 * Clase Nodo Arbol Binario con elemento de tipo Gen�rico que implementa la interface Position.
 * 
 * Implementaci�n de un Nodo Arbol Binario para la clase Arbol Binario Completo Con Arreglo, con referencia a un elemento de tipo Gen�rico y posici�n del Nodo en el arreglo que representa al �rbol.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class BTPos<E> implements Position<E>
{
	
	//Variables de Instancia
	protected E element;
	protected int index;
	
	/**
	 * Crea un Nodo Arbol Binario con elemento Gen�rico elt y posici�n i.
	 * 
	 * @param elt Elemento Gen�rico para el nuevo Nodo.
	 * @param i Posici�n del nuevo Nodo.
	 */
	public BTPos (E elt, int i)
	{
		element = elt;
		index = i;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambiar Elemento: Cambia el elemento Gen�rico actual por el nuevo elemento Gen�rico elem.
	 * 
	 * @param elem Nuevo elemento Gen�rico.
	 * @return Elemento Gen�rico cambiado.
	 */
	public E setElement (E elem)
	{
		E temp = element;
		element = elem;
		return temp;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Elemento: Devuelve el elemento Gen�rico del Nodo actual.
	 * 
	 * @return Elemento Gen�rico del Nodo.
	 */
	public E element ()
	{
		return element;
	}
	
	/**
	 * Posici�n: devuelve la posici�n del NodoArbol actual en el arreglo que representa al �rbol Binario.
	 * 
	 * @return Posici�n del NodoArbol actual.
	 */
	public int index ()
	{
		return index;
	}

}