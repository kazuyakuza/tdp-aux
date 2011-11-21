package ProyectoX.Librerias.TDAColaConPrioridad;

import java.util.ArrayList;
import java.util.Iterator;

import ProyectoX.Librerias.TDAArbol.ArbolVacioException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Arbol Binario Completo implementado con un ArrayList que guarda BTPos.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class ArbolBinarioCompletoArreglo<E> implements ArbolBinarioCompleto<E>
{
	
	//Variables de Instancia
	protected ArrayList<BTPos<E>> T;
	
	/**
	 * Crea un Arbol Binario Completo vacío, con primer elemento del arreglo con valor null.
	 */
	public ArbolBinarioCompletoArreglo ()
	{
		T = new ArrayList<BTPos<E>>();
		T.add(0, null);
	}
	
	/*COMANDOS*/
	
	/*De Árbol*/
	
	/**
	 * Reemplazar: Reemplaza el elemento del NodoArbol en la posición v por el nuevo elemento Genérico e.
	 * 
	 * @param v Posición del NodoArbol al que reemplazarle el elemento.
	 * @param e Nuevo elemento Genérico.
	 * @return Elemento Genérico reemplazado.
	 * @exception PosicionInvalidaException Si la posición es null o incorrecta.
	 */
	public E replace (Position<E> v, E e) throws PosicionInvalidaException 
	{
		BTPos<E> vv = checkPosition(v);//NodoArbol al que se le reemplazará el elemento Genérico.
		E temp = vv.element();//To return. Elemento reemplazado.
		vv.setElement(e);
		return temp;
	}
	
	/*De Árbol Binario Completo*/
	
	/**
	 * Añadir: Agrega el elemento Genérico elem al Árbol.
	 * 
	 * @param elem Elemento Genérico a agregar.
	 * @return Posición del NodoArbol agregado con el elemento Genérico elem.
	 */
	public Position<E> add (E elem)
	{
		int i = size() + 1;//Posición en la que se agregará el nuevo elemento.
		BTPos<E> p = new BTPos<E> (elem,i);
		T.add(i,p);
		return p;
	}

	/**
	 * Remover: Elimina el último NodoArbol del Árbol.
	 * 
	 * Se considera como último NodoArbol al Nodo extremo derecho que está en el último nivel del Árbol.
	 * 
	 * @return Elemento Genérico del NodoArbol eliminado.
	 * @exception ArbolVacioException Si el Árbol está vacio.
	 */
	public E remove () throws ArbolVacioException
	{
		if (isEmpty())
			throw new ArbolVacioException("El árbol al que está intentando acceder está vacío.");
		return T.remove(size()).element();
	}
	
	/**
	 * Validar Posición: Verifica si la posición pasada por parámetro es correcta, y devuelve el NodoArbolB perteneciente a esa posición.
	 * 
	 * @param v Posición a verificar.
	 * @return La BTPos resultante al castear la posición verificada.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta. 
	 */
	private BTPos<E> checkPosition(Position<E> v)throws PosicionInvalidaException
	{
		if (v == null)
			throw new PosicionInvalidaException ("La posición a la que esta intentando acceder es null.");
		try
		{
			BTPos<E> b = (BTPos<E>) v; //To return.
			return b;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posición a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}
	
	/*CONSULTAS*/
	
	/*De Árbol*/
	
	/**
	 * Raíz: Devuelve la raíz del ArbolBC actual.
	 * 
	 * @exception ArbolVacioException Si el ÁrbolBC está vacío.
	 * @return Posición del NodoArbolB raíz del ArbolBC actual.
	 */
	public Position<E> root () throws ArbolVacioException
	{
		if (isEmpty())
			throw new ArbolVacioException ("El árbol al que está intentando acceder está vacío.");
		return T.get(1);
	}
	
	/**
	 * Tamaño: Devuelve la cantidad de elementos en el ÁrbolBC.
	 * 
	 * @return Cantidad de elementos contenidos en el ÁrbolBC.
	 */
	public int size ()
	{
		return (T.size() - 1);
	}

	/**
	 * Es Vacio: Verifica si el Árbol Binario Completo actual está vacío, y devuelve el resultado.
	 * 
	 * @return True:  el Árbol no contiene elementos.
	 *         False: el Árbol tiene almenos 1 elemento.
	 */
	public boolean isEmpty ()
	{
		return (size() == 0);
	}
	
	/**
	 * Devuelve una colección iterable con los elementos guardados en el Árbol Binario Completo.
	 * 
	 * El iterador es construido Recorriendo el Árbol Binario Completo por Niveles.
	 * 
	 * @return Colección iterable con los elementos guardados en el Árbol Binario Completo.
	 */
	public Iterator<E> iterator ()
	{
		ArrayList<E> list = new ArrayList<E> ();
		Iterator<BTPos<E>> iter = T.iterator();
		iter.next();//El primer elemento es null.
		while (iter.hasNext())
			list.add(iter.next().element());
		return list.iterator();
	}

	/**
	 * Devuelve una colección iterable con los NodoArbol del Árbol.
	 * 
	 * El iterador es construido Recorriendo el Árbol por Niveles.
	 * 
	 * @return Colección iterable con los NodoArbol del Árbol.
	 */
	public Iterable<Position<E>> positions ()
	{
		ArrayList<Position<E>> list = new ArrayList<Position<E>> ();
		Iterator<BTPos<E>> iter = T.iterator();
		iter.next();//El primer elemento es null.
		while (iter.hasNext())
			list.add(iter.next());
		return list;
	}
	
	/**
	 * Padre: Devuelve el Nodo-Padre del NodoArbolB en v.
	 * 
	 * @param v Posición del NodoArbolB al que buscarle el padre.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se pide el Nodo-Padre de la raíz.
	 * @return Nodo-Padre del NodoArbolB en v.
	 */
	public Position<E> parent (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException
	{
		BTPos<E> vv = checkPosition(v);
		if(isRoot(v))
			throw new ViolacionLimiteException ("El nodo al que está intentando acceder es una raíz. La raíz no tiene padre.");
		return T.get(vv.index()/2);
	}

	/**
	 * Hijos: Devuelve una colección iterable de los Hijos del Nodo-Padre que está en la posición p.
	 * 
	 * @param p Posición del Nodo-Padre.
	 * @return Iterador con los Hijos.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public Iterable<Position<E>> children (Position<E> p) throws PosicionInvalidaException
	{
		BTPos<E> nodoBenP = checkPosition (p);
		ListaPositionSimple<Position<E>> listaHijos = new ListaPositionSimple<Position<E>> ();
		if (hasLeft(nodoBenP))
			listaHijos.addLast(left(nodoBenP));
		if (hasRight(nodoBenP))
			listaHijos.addLast(right(nodoBenP));
		return listaHijos;
	}
	
	/**
	 * Es Interno: Verifica si el NodoArbolB en v es interno, y devuelve el resultado.
	 *
	 * Verifica solo si tiene Nodo-Hijo izquierdo porque: en un Árbol Binario Completo, si un Nodo tiene Nodo-Hijo derecho, entonces tendrá un Nodo-Hijo izquierdo. Nunca sucede lo inverso.
	 * 
	 * @param v Posición del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en v tiene almenos 1 hijo.
	 *         False: el NodoArbolB en v no tiene hijos.
	 */
	public boolean isInternal (Position<E> v) throws PosicionInvalidaException
	{
		return hasLeft(v);
	}

	/**
	 * Es Externo: verifica si el NodoArbolB en la posición v es externo y devuelve el resultado.
	 * 
	 * @param v Posición del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en v no tiene hijos.
	 *         False: el NodoArbolB en v tiene almenos 1 hijo.
	 */
	public boolean isExternal(Position<E> v) throws PosicionInvalidaException 
	{
		return (!isInternal(v));
	}

	/**
	 * Es Raíz: Verifica si el NodoArbolB en v es la raíz del ÁrbolB actual.
	 * 
	 * @param v Posición del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en v es la raíz del ArbolB actual.
	 *         False: casos restantes.
	 */
	public boolean isRoot (Position<E> v) throws PosicionInvalidaException
	{
		BTPos<E> vv = checkPosition(v);
		return (vv.index() == 1);
	}
	
	/*De Árbol Binario*/
	
	/**
	 * Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbolB en v.
	 * 
	 * @param v Posición del Nodo-Padre al que buscarle su Nodo-Hijo izquierdo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo izquierdo.
	 * @return Posición del Nodo-Hijo izquierdo.
	 */
	public Position<E> left (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException
	{
		BTPos<E> vv = checkPosition(v);
		if(!hasLeft(v))
			throw new ViolacionLimiteException ("El nodo al que está intentando acceder no tiene hijo izquierdo.");
		return T.get(vv.index()*2);
	}

	/**
	 * Derecho: Devuelve el Nodo-Hijo derecho del NodoArbolB en v.
	 * 
	 * @param v Posición del Nodo-Padre al que buscarle su Nodo-Hijo derecho.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo derecho.
	 */
	public Position<E> right (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException 
	{
		BTPos<E> vv = checkPosition(v);
		if(! hasRight(v))
			throw new ViolacionLimiteException ("El nodo al que está intentando acceder no tiene hijo derecho.");
		return T.get(2*vv.index()+1);
	}
	
	/**
	 * Tiene Izquierdo: Verifica si el NodoArbolB en v tiene Nodo-Hijo izquierdo, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en v tiene Nodo-Hijo izquierdo.
	 *         False: caso contrario.
	 */
	public boolean hasLeft (Position<E> v) throws PosicionInvalidaException
	{
		return (2*checkPosition(v).index() <= size());
	}

	/**
	 * Tiene Derecho: Verifica si el NodoArbolB en v tiene Nodo-Hijo derecho, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @return True:  el NodoArbolB en v tiene Nodo-Hijo derecho.
	 *         False: caso contrario.
	 */
	public boolean hasRight (Position<E> v) throws PosicionInvalidaException
	{
		return (2*checkPosition(v).index()+1 <= size());
	}

}