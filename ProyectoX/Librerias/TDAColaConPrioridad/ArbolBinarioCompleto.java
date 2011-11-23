package ProyectoX.Librerias.TDAColaConPrioridad;

import ProyectoX.Librerias.TDAArbol.ArbolVacioException;
import ProyectoX.Librerias.TDALista.Position;

/**
 * Interface para un Arbol Binario Completo: una colecci�n de elementos que son enlazados de forma jer�rquica, donde se distingue un �nico elemento ra�z que no tiene padre, y cada elemento tiene un �nico elemento padre asociado, un elemento hijo izquierdo y un elemento hijo derecho.
 * Tiene las propiedades:
 * + Todo Nodo n perteneciente al �rbol tiene un valor mayor o igual que el de su Nodo-Padre.
 * + Los Nodo-Hoja se encuentra en a lo sumo en dos niveles consecutivos y los Nodo-Hoja faltantes ser�n los de la derecha.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public interface ArbolBinarioCompleto<E> extends BinaryTree<E>
{
	
	/*COMANDOS*/
	
	/**
	 * A�adir: Agrega el elemento Gen�rico elem al �rbol.
	 * 
	 * @param elem Elemento Gen�rico a agregar.
	 * @return Posici�n del NodoArbol agregado con el elemento Gen�rico elem.
	 */
	public Position<E> add (E elem);

	/**
	 * Remover: Elimina el �ltimo NodoArbol del �rbol.
	 * 
	 * Se considera como �ltimo NodoArbol al Nodo extremo derecho que est� en el �ltimo nivel del �rbol.
	 * 
	 * @return Elemento Gen�rico del NodoArbol eliminado.
	 * @exception ArbolVacioException Si el �rbol est� vacio.
	 */
	public E remove () throws ArbolVacioException;

}