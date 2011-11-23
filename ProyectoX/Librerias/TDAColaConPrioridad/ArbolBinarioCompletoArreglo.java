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
	 * Crea un Arbol Binario Completo vac�o, con primer elemento del arreglo con valor null.
	 */
	public ArbolBinarioCompletoArreglo ()
	{
		T = new ArrayList<BTPos<E>>();
		T.add(0, null);
	}
	
	/*COMANDOS*/
	
	/*De �rbol*/
	
	/**
	 * Reemplazar: Reemplaza el elemento del NodoArbol en la posici�n v por el nuevo elemento Gen�rico e.
	 * 
	 * @param v Posici�n del NodoArbol al que reemplazarle el elemento.
	 * @param e Nuevo elemento Gen�rico.
	 * @return Elemento Gen�rico reemplazado.
	 * @exception PosicionInvalidaException Si la posici�n es null o incorrecta.
	 */
	public E replace (Position<E> v, E e) throws PosicionInvalidaException 
	{
		BTPos<E> vv = checkPosition(v);//NodoArbol al que se le reemplazar� el elemento Gen�rico.
		E temp = vv.element();//To return. Elemento reemplazado.
		vv.setElement(e);
		return temp;
	}
	
	/*De �rbol Binario Completo*/
	
	/**
	 * A�adir: Agrega el elemento Gen�rico elem al �rbol.
	 * 
	 * @param elem Elemento Gen�rico a agregar.
	 * @return Posici�n del NodoArbol agregado con el elemento Gen�rico elem.
	 */
	public Position<E> add (E elem)
	{
		int i = size() + 1;//Posici�n en la que se agregar� el nuevo elemento.
		BTPos<E> p = new BTPos<E> (elem,i);
		T.add(i,p);
		return p;
	}

	/**
	 * Remover: Elimina el �ltimo NodoArbol del �rbol.
	 * 
	 * Se considera como �ltimo NodoArbol al Nodo extremo derecho que est� en el �ltimo nivel del �rbol.
	 * 
	 * @return Elemento Gen�rico del NodoArbol eliminado.
	 * @exception ArbolVacioException Si el �rbol est� vacio.
	 */
	public E remove () throws ArbolVacioException
	{
		if (isEmpty())
			throw new ArbolVacioException("El �rbol al que est� intentando acceder est� vac�o.");
		return T.remove(size()).element();
	}
	
	/**
	 * Validar Posici�n: Verifica si la posici�n pasada por par�metro es correcta, y devuelve el NodoArbolB perteneciente a esa posici�n.
	 * 
	 * @param v Posici�n a verificar.
	 * @return La BTPos resultante al castear la posici�n verificada.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	private BTPos<E> checkPosition(Position<E> v)throws PosicionInvalidaException
	{
		if (v == null)
			throw new PosicionInvalidaException ("La posici�n a la que esta intentando acceder es null.");
		try
		{
			BTPos<E> b = (BTPos<E>) v; //To return.
			return b;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posici�n a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}
	
	/*CONSULTAS*/
	
	/*De �rbol*/
	
	/**
	 * Ra�z: Devuelve la ra�z del ArbolBC actual.
	 * 
	 * @exception ArbolVacioException Si el �rbolBC est� vac�o.
	 * @return Posici�n del NodoArbolB ra�z del ArbolBC actual.
	 */
	public Position<E> root () throws ArbolVacioException
	{
		if (isEmpty())
			throw new ArbolVacioException ("El �rbol al que est� intentando acceder est� vac�o.");
		return T.get(1);
	}
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos en el �rbolBC.
	 * 
	 * @return Cantidad de elementos contenidos en el �rbolBC.
	 */
	public int size ()
	{
		return (T.size() - 1);
	}

	/**
	 * Es Vacio: Verifica si el �rbol Binario Completo actual est� vac�o, y devuelve el resultado.
	 * 
	 * @return True:  el �rbol no contiene elementos.
	 *         False: el �rbol tiene almenos 1 elemento.
	 */
	public boolean isEmpty ()
	{
		return (size() == 0);
	}
	
	/**
	 * Devuelve una colecci�n iterable con los elementos guardados en el �rbol Binario Completo.
	 * 
	 * El iterador es construido Recorriendo el �rbol Binario Completo por Niveles.
	 * 
	 * @return Colecci�n iterable con los elementos guardados en el �rbol Binario Completo.
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
	 * Devuelve una colecci�n iterable con los NodoArbol del �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol por Niveles.
	 * 
	 * @return Colecci�n iterable con los NodoArbol del �rbol.
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
	 * @param v Posici�n del NodoArbolB al que buscarle el padre.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se pide el Nodo-Padre de la ra�z.
	 * @return Nodo-Padre del NodoArbolB en v.
	 */
	public Position<E> parent (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException
	{
		BTPos<E> vv = checkPosition(v);
		if(isRoot(v))
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder es una ra�z. La ra�z no tiene padre.");
		return T.get(vv.index()/2);
	}

	/**
	 * Hijos: Devuelve una colecci�n iterable de los Hijos del Nodo-Padre que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo-Padre.
	 * @return Iterador con los Hijos.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
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
	 * Verifica solo si tiene Nodo-Hijo izquierdo porque: en un �rbol Binario Completo, si un Nodo tiene Nodo-Hijo derecho, entonces tendr� un Nodo-Hijo izquierdo. Nunca sucede lo inverso.
	 * 
	 * @param v Posici�n del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en v tiene almenos 1 hijo.
	 *         False: el NodoArbolB en v no tiene hijos.
	 */
	public boolean isInternal (Position<E> v) throws PosicionInvalidaException
	{
		return hasLeft(v);
	}

	/**
	 * Es Externo: verifica si el NodoArbolB en la posici�n v es externo y devuelve el resultado.
	 * 
	 * @param v Posici�n del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en v no tiene hijos.
	 *         False: el NodoArbolB en v tiene almenos 1 hijo.
	 */
	public boolean isExternal(Position<E> v) throws PosicionInvalidaException 
	{
		return (!isInternal(v));
	}

	/**
	 * Es Ra�z: Verifica si el NodoArbolB en v es la ra�z del �rbolB actual.
	 * 
	 * @param v Posici�n del NodoArbolB a verificar.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en v es la ra�z del ArbolB actual.
	 *         False: casos restantes.
	 */
	public boolean isRoot (Position<E> v) throws PosicionInvalidaException
	{
		BTPos<E> vv = checkPosition(v);
		return (vv.index() == 1);
	}
	
	/*De �rbol Binario*/
	
	/**
	 * Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbolB en v.
	 * 
	 * @param v Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo izquierdo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo izquierdo.
	 * @return Posici�n del Nodo-Hijo izquierdo.
	 */
	public Position<E> left (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException
	{
		BTPos<E> vv = checkPosition(v);
		if(!hasLeft(v))
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder no tiene hijo izquierdo.");
		return T.get(vv.index()*2);
	}

	/**
	 * Derecho: Devuelve el Nodo-Hijo derecho del NodoArbolB en v.
	 * 
	 * @param v Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo derecho.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo derecho.
	 */
	public Position<E> right (Position<E> v) throws PosicionInvalidaException,ViolacionLimiteException 
	{
		BTPos<E> vv = checkPosition(v);
		if(! hasRight(v))
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder no tiene hijo derecho.");
		return T.get(2*vv.index()+1);
	}
	
	/**
	 * Tiene Izquierdo: Verifica si el NodoArbolB en v tiene Nodo-Hijo izquierdo, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
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
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en v tiene Nodo-Hijo derecho.
	 *         False: caso contrario.
	 */
	public boolean hasRight (Position<E> v) throws PosicionInvalidaException
	{
		return (2*checkPosition(v).index()+1 <= size());
	}

}