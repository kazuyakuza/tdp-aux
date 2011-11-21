package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDAArbol.ArbolVacioException;
import ProyectoX.Librerias.TDALista.Position;

/**
 * Interface para un Arbol Binario Completo: una colección de elementos que son enlazados de forma jerárquica, donde se distingue un único elemento raíz que no tiene padre, y cada elemento tiene un único elemento padre asociado, un elemento hijo izquierdo y un elemento hijo derecho.
 * Tiene las propiedades:
 * + Todo Nodo n perteneciente al Árbol tiene un valor mayor o igual que el de su Nodo-Padre.
 * + Los Nodo-Hoja se encuentra en a lo sumo en dos niveles consecutivos y los Nodo-Hoja faltantes serán los de la derecha.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public interface ArbolBinarioCompleto<E> extends BinaryTree<E>
{
	
	/*COMANDOS*/
	
	/**
	 * Añadir: Agrega el elemento Genérico elem al Árbol.
	 * 
	 * @param elem Elemento Genérico a agregar.
	 * @return Posición del NodoArbol agregado con el elemento Genérico elem.
	 */
	public Position<E> add (E elem);

	/**
	 * Remover: Elimina el último NodoArbol del Árbol.
	 * 
	 * Se considera como último NodoArbol al Nodo extremo derecho que está en el último nivel del Árbol.
	 * 
	 * @return Elemento Genérico del NodoArbol eliminado.
	 * @exception ArbolVacioException Si el Árbol está vacio.
	 */
	public E remove () throws ArbolVacioException;

}