package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDALista.Position;

/**
 * Clase Nodo Arbol Binario con elemento de tipo Genérico que implementa la interface Position.
 * 
 * Implementación de un Nodo Arbol Binario para la clase Arbol Binario Completo Con Arreglo, con referencia a un elemento de tipo Genérico y posición del Nodo en el arreglo que representa al Árbol.
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
	 * Crea un Nodo Arbol Binario con elemento Genérico elt y posición i.
	 * 
	 * @param elt Elemento Genérico para el nuevo Nodo.
	 * @param i Posición del nuevo Nodo.
	 */
	public BTPos (E elt, int i)
	{
		element = elt;
		index = i;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambiar Elemento: Cambia el elemento Genérico actual por el nuevo elemento Genérico elem.
	 * 
	 * @param elem Nuevo elemento Genérico.
	 * @return Elemento Genérico cambiado.
	 */
	public E setElement (E elem)
	{
		E temp = element;
		element = elem;
		return temp;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Elemento: Devuelve el elemento Genérico del Nodo actual.
	 * 
	 * @return Elemento Genérico del Nodo.
	 */
	public E element ()
	{
		return element;
	}
	
	/**
	 * Posición: devuelve la posición del NodoArbol actual en el arreglo que representa al Árbol Binario.
	 * 
	 * @return Posición del NodoArbol actual.
	 */
	public int index ()
	{
		return index;
	}

}