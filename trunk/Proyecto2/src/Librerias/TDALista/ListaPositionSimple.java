package ProyectoX.Librerias.TDALista;

import java.util.Iterator;

/**
 * Lista Position Simplemente Enlazada Iterable que implementa la interface PositionList.
 *  
 * Lista Position implementada con una Lista Simplemente Enlazada, con enlace al primer y �ltimo de la lista.
 * 
 * La Lista guarda elementos de tipo Gen�rico.
 * 
 * La Lista vac�a no contiene ning�n nodo, y su tama�o es cero.
 * 
 * Contiene 2 Constructores:
 *  + Lista vac�a.
 *  + Lista con 1 elemento Gen�rico.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.5
 * @param <E>
 */
public class ListaPositionSimple<E> implements Iterable<E>, PositionList<E>
{
	
	//Variables de Instancia
	private Nodo<E> primero, ultimo; //Primero: primer elemento de la Lista.
	                                 //�ltimo: �ltimo elemento de la Lista.
	private int tamanio; //Cantidad de elementos en la Lista.
	
	/*CONTRUCTORES*/
	
	/**
	 * Crea una Lista vac�a.
	 */
	public ListaPositionSimple ()
	{
		primero = null;
		ultimo = null;
		tamanio = 0;
	}
	
	/**
	 * Crea una Lista con un elemento Gen�rico e.
	 * 
	 * @param e Elemento Gen�rico a agregar a la Lista creada.
	 */
	public ListaPositionSimple (E e)
	{
		primero = ultimo = new Nodo<E> (null, e);
		tamanio = 1;
	}
	
	/*COMANDOS*/
	
	/**
	 * Agregar Primero: Inserta un elemento en el frente de la Lista, devolviendo la nueva posici�n.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addFirst (E e)
	{
		primero = new Nodo<E> (primero, e);
		if (tamanio == 0) //Si no hay elementos en la Lista, el primer y ultimo elemento de la Lista son el mismo.
			ultimo = primero;
		tamanio++;
	}
	
	/**
	 * Agregar �ltimo: Inserta un elemento en el final de la Lista, devolviendo la nueva posici�n.
	 * 
	 * @param e Elemento a ser insertado.
	 */
	public void addLast (E e)
	{
		Nodo<E> aux = new Nodo<E> (null, e);
		if (tamanio == 0) //Si no hay elementos en la Lista, el primer y �ltimo elemento de la Lista son el mismo.
			primero = aux;
		else //Si hay elementos en la lista, el �ltimo elemento actual referenciar� al nuevo elemento.
			ultimo.siguiente(aux);
		ultimo = aux;
		tamanio++;
	}
	
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
	public void addAfter (Position<E> p, E e) throws PosicionInvalidaException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p
		Nodo<E> nuevoNodo = new Nodo<E> (nodoEnP.siguiente(), e); //Nuevo Nodo a agregar con el elemento e
		nodoEnP.siguiente(nuevoNodo);
		tamanio++;
		if (nuevoNodo.siguiente() == null)
			ultimo = nuevoNodo;
	}
	
	/**
	 * Agregar Antes: Inserta un elemento en la Lista antes del Nodo que est� en la posici�n p.
	 * 
	 * Se asume que, si la posici�n p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posici�n p est� contenido en la Lista.
	 * 
	 * Si la posici�n p se refiere al primer nodo de la Lista,
	 * entonces se utiliza el m�todo addFirst para agregar el elemento e.
	 * 
	 * @param p Posici�n del Nodo.
	 * @param e Elemento a insertar.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista.
	 */
	public void addBefore (Position<E> p, E e) throws PosicionInvalidaException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p.
		if (nodoEnP == primero) //Si la posici�n refiere al primer Nodo de la Lista,
			addFirst(e);        //agregar antes de esa posici�n es igual a agregar al principio de la Lista.
		else //Si la posici�n refiere a cualquier otro Nodo, se busca su anterior en la Lista.
		{
			Nodo<E> auxNodo = primero; //Nodo auxiliar para buscar el anterior a nodoEnP.
			//Busco el anterior a nodoEnP.
			while (auxNodo.siguiente() != nodoEnP)
				auxNodo = auxNodo.siguiente();
			Nodo<E> nuevoNodo = new Nodo<E> (auxNodo.siguiente(), e); //Nuevo nodo a agregar con el elemento e.
			auxNodo.siguiente(nuevoNodo);
			tamanio++;
		}
	}
	
	/**
	 * Eliminar: Elimina el Nodo de la Lista que est� en la posici�n p, y devuelve el elemento del Nodo eliminado.
	 * 
	 * Se asume que, si la posici�n p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posici�n p est� contenido en la Lista.
	 * 
	 * Si la posici�n p se refiere al primer Nodo de la Lista,
	 * entonces se utiliza el m�todo eliminarPrimero para eliminar el Nodo que est� en p.
	 * 
	 * @param p Posici�n del Nodo a eliminar.
	 * @return Elemento del Nodo eliminado.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E remove (Position<E> p) throws PosicionInvalidaException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p.
		E r = nodoEnP.element(); //To return. Elemento eliminado.
		if (nodoEnP == primero) //Caso especial: La posici�n refiere al primer elemento de la lista.
			eliminarPrimero();
		else
			{
			Nodo<E> auxNodo = primero; //Nodo auxiliar para buscar el anterior a nodoEnP.
			//Busco el anterior a nodoEnP.
			while (auxNodo.siguiente() != nodoEnP)
				auxNodo = auxNodo.siguiente();
			auxNodo.siguiente(nodoEnP.siguiente());
			nodoEnP.limpiar(); //Elimina los enlaces del nodo eliminado.
			if (auxNodo.siguiente() == null) //Caso especial: La posici�n refier al ultimo elemento de la Lista.
				ultimo = auxNodo;            //Por tanto se actualiza el �ltimo elemento de la Lista.
			}
		tamanio--;
		return r;
	}
	
	/**
	 * Establecer: Reemplaza el elemento guardado en el Nodo al que hace referencia la posici�n p, y se devuelve el elemento eliminado.
	 * 
	 * Se asume que, si la posici�n p no es nula y es de un tipo de dato correcto,
	 * el Nodo en la posici�n p est� contenido en la Lista.
	 * 
	 * @param p Posici�n del Nodo al que se le cambiar� el elemento.
	 * @return Elemento eliminado del Nodo.
	 * @exception PosicionInvalidaException Si la posici�n es nula, o no es de un tipo de dato correcto para la Lista. 
	 */
	public E set (Position<E> p, E e) throws PosicionInvalidaException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p.
		E r = nodoEnP.element(); //To return. Elemento eliminado.
		nodoEnP.elemento(e); //Se actualiza el elemento.
		return r;
	}
	
	/**
	 * Verifica si la posici�n pasado por par�metro es correcta, y devuelve el Nodo perteneciente a esa posici�n.
	 * 
	 * @param p Posici�n a verificar.
	 * @return El Nodo resultante al castear la posici�n verificada.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	private Nodo<E> validarPosicion (Position<E> p) throws PosicionInvalidaException
	{
		if (p == null)
			throw new PosicionInvalidaException ("La posici�n a la que esta intentando acceder es null.");
		try
		{
			Nodo<E> r = (Nodo<E>) p; //To return.
			return r;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posici�n a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}
	
	/**
	 * Elimina el primer elemento de la Lista.
	 */
	private void eliminarPrimero ()
	{
		Nodo<E> aux = primero.siguiente(); //Guarda referencia al Nodo siguiente al primero.
		primero.limpiar(); //Elimina los enlaces del Nodo eliminado.
		primero = aux; //Actualiza primer elemento de la Lista.
	}
	
	/*CONSULTAS*/
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos actual que contiene la Lista.
	 * 
	 * @return Cantidad de elementos en la Lista.
	 */
	public int size ()
	{
		return tamanio;
	}
	
	/**
	 * Vac�a: Verifica si la Lista es vac�a, y devuelve el resultado.
	 * 
	 * @return True:  la Lista no contiene elementos.
	 *         False: la Lista contiene almenos 1 elemento.
	 */
	public boolean isEmpty ()
	{
		return (tamanio == 0);
	}
	
	/**
	 * Primero: Devuelve el primer Nodo de la lista.
	 * 
	 * @return Primer Nodo de la lista.
	 */
	public Position<E> first ()
	{
		return primero;
	}
	
	/**
	 * �ltimo: Devuelve el �ltimo Nodo de la Lista.
	 * 
	 * @return �ltimo Nodo de la Lista.
	 */
	public Position<E> last ()
	{
		return ultimo;
	}
	
	/**
	 * Siguiente: Devuelve el Nodo siguiente al Nodo que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo.
	 * @return Nodo siguiente al Nodo en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posici�n a la que est� intentando acceder no existe.
	 */
	public Position<E> next (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p.
		if (nodoEnP == ultimo)
			throw new ViolacionLimiteException("La posici�n a la que est� intentando acceder no existe," +
					                            " porque no hay siguiente del �ltimo.");
		return nodoEnP.siguiente();
	}
	
	/**
	 * Previo: Devuelve el Nodo anterior al Nodo que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo.
	 * @return Nodo anterior al Nodo en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si la posici�n a la que est� intentando acceder no existe.
	 */
	public Position<E> prev (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		Nodo<E> nodoEnP = validarPosicion(p); //Nodo que est� en la posici�n p.
		if (nodoEnP == primero)
			throw new ViolacionLimiteException("La posici�n a la que est� intentando acceder no existe," +
					                            "porque no hay anterior del primero.");
		Nodo<E> auxNodo = primero; //Nodo auxiliar para buscar el anterior a nodoEnP.
		//Busco el anterior a nodoEnP.
		while (auxNodo.siguiente() != nodoEnP)
			auxNodo = (auxNodo.siguiente());
		return auxNodo;
	}
	
	/**
	 * Devuelve una cadena con los elementos contenidos en la Lista.
	 * La cadena organiza de izquierda a derecha la Lista.
	 * El primer elemento a izquierda en la cadena ser� el primer elemento de la Lista.
	 * El �ltimo elemento a derecha en la cadena ser� el �ltimo elemento de la Lista.
	 * Los elementos ser�n agregados al String usando el m�todo toString de los mismos.
	 * 
	 * @return Cadena con los elementos contenido en la Lista.
	 */
	public String toString ()
	{
		String r = ""; //To return.
		if (tamanio != 0)
			r += "[Primero]";
		Nodo<E> auxNodo = primero;
		while (auxNodo.siguiente() != null)
		{
			r += auxNodo.element().toString();
			auxNodo = (auxNodo.siguiente());
			if (auxNodo.siguiente() != null)
				r += "|-->|";
		}
		if (auxNodo != null)
			r += "|-->|" + auxNodo.element().toString();
		if (tamanio != 0)
			r += "[�ltimo]";
		return r;
	}
	
	/**
	 * Devuelve un Iterador con todos los elementos contenidos en la Lista (la Lista "this").
	 * 
	 * @return Iterador con todos los elementos contenidos en la Lista (la Lista "this").
	 */
	public Iterator<E> iterator ()
	{
		return new ElementoIterador<E> (this);
	}
	
	/**
	 * Iterador para la una Lista Position Simplemente Enlazada Iterable.
	 * 
	 * Referencia a los Nodos de la Lista (a la Lista "this").
	 */
	@SuppressWarnings("hiding")
	public class ElementoIterador<E> implements Iterator<E>
	{
		
		protected PositionList<E> lista; //Referencia a la Lista Original (a la Lista "this").
		protected Position<E> cursor; //Cursor del Iterador. Siempre se�ala al elemento siguiente al actual.
		
		/**
		 * Crea un Elemento Iterador para la Lista l.
		 * 
		 * @param l Lista a la que se har� el Iterador.
		 */
		public ElementoIterador (PositionList<E> l)
		{
			lista = l;
			if (l.isEmpty())
				cursor = null;
			else
				cursor = lista.first();
		}
		
		/**
		 * Tiene Siguinte: Verifica si la posici�n actual tiene un elemento siguiente en el Iterador.
		 * 
		 * @return True:  el elemento actual tiene elemento siguiente.
		 *         False: caso contrario.
		 */
		public boolean hasNext ()
		{
			return (cursor != null);
		}
		
		/**
		 * Siguiente: Actualiza el cursor al siguiente elemento del Iterador.
		 * 
		 * @return Elemento de la posici�n actual.
		 * @exception NoExisteElementoException Si se intenta pedir el elemento siguiente de una posici�n null.
		 */
		public E next() throws NoExisteElementoException
		{
			if (cursor == null)
				throw new NoExisteElementoException("No existe elemento siguiente al actual. El elemento actual es null");
			E r = cursor.element(); //To return. Elemento contenido en la posici�n actual.
			if (cursor == lista.last())
				cursor = null;
			else
				cursor = lista.next(cursor);
			return r;
		}

		/**
		 * M�todo necesario por implementar java.util.iterator
		 */
		public void remove() {}
		
	}

}