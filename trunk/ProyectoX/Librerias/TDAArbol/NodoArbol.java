package ProyectoX.Librerias.TDAArbol;

import java.util.Iterator;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Clase Nodo Arbol con elemento de tipo Gen�rico que implementa la interface Position.
 * 
 * Implementaci�n de un Nodo Arbol para la clase Arbol, con referencia a un Nodo-Padre,
 * a una Lista Position Iterable de Hijos y referencia a un elemento de tipo Gen�rico.
 * 
 * Contiene 4 constructores:
 *  + Nodo Ra�z Vac�o.
 *  + Nodo Ra�z con R�tulo gen�rico r.
 *  + Nodo con Nodo-Padre p y R�tulo gen�rico r.
 *  + Nodo con Nodo-Padre p, Lista de Hijos h y R�tulo Gen�rico r. 
 *  
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 * @param <E>
 */
public class NodoArbol<E> implements Position<E>
{
	
	//Variables de Instancia.
	protected NodoArbol<E> padre; //Referencia al Nodo-Padre.
	protected ListaPositionSimple<NodoArbol<E>> hijos; //Lista de hijos del Nodo Actual. 
	protected E rotulo; //R�tulo del Nodo.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Nodo Ra�z Vac�o.
	 */
	public NodoArbol ()
	{
		padre = null;
		hijos = null;
		rotulo = null;
	}
	
	/**
	 * Crea un Nodo Ra�z con R�tulo Gen�rico r.
	 * 
	 * @param r Rotulo Gen�rico.
	 */
	public NodoArbol (E r)
	{
		padre = null;
		hijos = null;
		rotulo = r;
	}
	
	/**
	 * Crea un Nodo con Nodo-Padre p y con R�tulo Gen�rico r.
	 * 
	 * @param p Nodo-Padre al que referenciar.
	 * @param r Rotulo del Nodo.
	 */
	public NodoArbol (NodoArbol<E> p, E r)
	{
		padre = p;
		hijos = null;
		rotulo = r;
	}
	
	/**
	 * Crea un Nodo con Nodo-Padre p, Lista de Hijos h y R�tulo Gen�rico r.
	 * 
	 * @param p Nodo-Padre al que referenciar.
	 * @param h Lista de Hijos al que referenciar.
	 * @param r Rotulo del Nodo. 
	 */
	public NodoArbol (NodoArbol<E> p, ListaPositionSimple<NodoArbol<E>> h, E r)
	{
		padre = p;
		hijos = h;
		rotulo = r;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el Nodo-Padre actual por el nuevo Nodo-Padre p.
	 * 
	 * @param p Nuevo Nodo-Padre.
	 */
	public void nuevoPadre (NodoArbol<E> p)
	{
		padre = p;
	}
	
	/**
	 * Cambia la Lista de Hijos del NodoArbol actual por la nueva Lista nHs.
	 * 
	 * @param nHs Nueva Lista de Hijos.
	 * @return Lista de Hijos viejos.
	 */
	public ListaPositionSimple<NodoArbol<E>> nuevaListaHijos (ListaPositionSimple<NodoArbol<E>> nHs)
	{
		ListaPositionSimple<NodoArbol<E>> r = hijos; //To Return. Lista de Hijos Actuales.
		hijos = nHs;
		return r;
	}
	
	/**
	 * Cambia el R�tulo Gen�rico actual por el nuevo R�tulo Gen�rico r.
	 * 
	 * @param r Nuevo R�tulo Gen�rico.
	 */
	public void rotulo (E r)
	{
		rotulo = r;
	}
	
	/**
	 * Elimina la referencia al Nodo-Padre, a la Lista de Hijos y al Rotulo del NodoArbol.
	 * 
	 * @return Lista de Hijos del NodoArbol "limpiado".
	 */
	public ListaPositionSimple<NodoArbol<E>> limpiar ()
	{
		ListaPositionSimple<NodoArbol<E>> r = hijos; //To Return. Lista de Hijos del NodoArbol.
		padre = null;
		rotulo = null;
		hijos = null;
		return r;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Nodo-Padre al NodoArbol actual.
	 * 
	 * @return Nodo-Padre del NodoArbol actual.
	 */
	public NodoArbol<E> padre ()
	{
		return padre;
	}
	
	/**
	 * Devuelve la Lista de Hijos del NodoArbol actual.
	 * 
	 * @return Lista de Hijos del NodoArbol.
	 */
	public ListaPositionSimple<NodoArbol<E>> hijos ()
	{
		return hijos;
	}
	
	/**
	 * Devuelve la Lista de Hijos del NodoArbol actual en un Iterador.
	 * 
	 * @return Iterador de la Lista de Hijos del NodoArbol.
	 */
	public Iterator<NodoArbol<E>> hijosIterable ()
	{
		Iterator<NodoArbol<E>> r = null;//To return.
		if (hijos != null)
			r = hijos.iterator();
		return r;
	}
	
	/**
	 * Devuelve el Primer Nodo-Hijo del NodoArbol actual.
	 *
	 * @return Posici�n del Primer Nodo-Hijo del NodoArbol actual.
	 */
	public Position<E> primerHijo ()
	{
		NodoArbol<E> r = null;//To return.
		if (hijos != null)
			r = hijos.first().element();
		return r;
	}
	
	/**
	 * Devuelve el �ltimo Nodo-Hijo del NodoArbol actual.
	 *
	 * @return Posici�n del �ltimo Nodo-Hijo del NodoArbol actual.
	 */
	public Position<E> ultimoHijo ()
	{
		NodoArbol<E> r = null;//To return.
		if (hijos != null)
			r = hijos.last().element();
		return r;
	}
	
	/**
	 * Verifica si el NodoArbol actual es primer hijo de su padre, y devuelve el resultado.
	 * 
	 * @return True:  el NodoArbol actual est� primero en la Lista de Hijos de su padre.
	 *         False: casos restantes.
	 */
	public boolean esPrimerHijo ()
	{
		boolean r = false;//To return.
		if (padre != null)
			r = (this == padre.primerHijo());
		return r;
	}
	
	/**
	 * Verifica si el NodoArbol actual es ultimo hijo de su padre, y devuelve el resultado.
	 * 
	 * @return True:  el NodoArbol actual est� ultimo en la Lista de Hijos de su padre.
	 *         False: casos restantes.
	 */
	public boolean esUltimoHijo ()
	{
		boolean r = false;//To return.
		if (padre != null)
			r = (this == padre.ultimoHijo());
		return r;
	}
	
	/**
	 * Cantidad Hijos: devuelve la cantidad de Hijos del NodoArbol actual.
	 * 
	 * @return Cantidad de Hijos del NodoArbol actual.
	 */
	public int cantHijos ()
	{
		int r = 0;//To return.
		if (hijos != null)
			r = hijos.size();
		return r;
	}
	
	/**
	 * Devuelve el R�tulo actual del NodoArbol.
	 * 
	 * @return R�tulo actual del NodoArbol.
	 */
	public E element ()
	{
		return rotulo;
	}
	
	/**
	 * N�mero de Hijo: Devuelve que n�mero de hijo es el Hijo que est� en la posici�n p.
	 * 
	 * Busca si el Nodo en la posici�n p se encuentra en la Lista de Hijos del nodo actual,
	 * y devuelve su "n�mero de Hijo".
	 * 
	 * @param p Posici�n del Nodo a buscar en la Lista de Hijos.
	 * @return N�mero del Nodo en p, en la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el nodo no tiene hijos. Si el nodo no tiene el hijo buscado.
	 */
	public int numHijo (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		if ((hijos == null) || (hijos.size() == 0))
			throw new ViolacionLimiteException("El nodo al que est� intentando acceder no tiene hijos.");
		NodoArbol<E> nodoEnP = validarPosicion(p);
		NodoArbol<E> nodoAux = hijos.first().element(); //Nodo auxiliar para buscar nodoEnP en la Lista de Hijos.
		Iterator<NodoArbol<E>> iteradorListaDeHijos = hijos.iterator();
		int numH = 0;
		while ((nodoAux != hijos.last().element()) && (nodoEnP != nodoAux))
		{
			nodoAux = iteradorListaDeHijos.next();
			numH++;
		}
		if (nodoEnP != nodoAux)
			throw new ViolacionLimiteException("El nodo al que est� intentando acceder no tiene ese hijo en su Lista de Hijos.");
		return numH;
	}
	
	/**
	 * Hijo N�mero: Devuelve la Posici�n en el �rbol del Hijo n�mero n de la Lista de Hijos del Nodo actual.
	 * 
	 * @param n N�mero de Hijo a buscar.
	 * @return Posici�n del Hijo en la posici�n n.
	 * @exception ViolacionLimiteException Si se intenta pedir un n�mero de Hijo mas grande que la cantidad de Hijos total.
	 */
	@SuppressWarnings("unchecked")
	public Position<E> hijoNum (int n) throws ViolacionLimiteException
	{
		if (n > hijos.size())
			throw new ViolacionLimiteException("El hijo "+n+" al que est� intentando acceder, no existe. No hay tantos hijos.");
		Position<E> hijoN = (Position<E>) hijos.first(); //To return.
		Iterator<NodoArbol<E>> iteradorListaDeHijos = hijos.iterator();
		for (int i=1; ((i!=hijos.size()) && (i!=n)); i++)
		{
			hijoN = iteradorListaDeHijos.next();
		}
		return hijoN;
	}
	
	/**
	 * Validar Posici�n: Verifica si la posici�n pasado por par�metro es correcta, y devuelve el NodoArbol perteneciente a esa posici�n.
	 * 
	 * @param p Posici�n a verificar.
	 * @return El NodoArbol resultante al castear la posici�n verificada.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	private NodoArbol<E> validarPosicion (Position<E> p) throws PosicionInvalidaException
	{
		if (p == null)
			throw new PosicionInvalidaException ("La posici�n a la que esta intentando acceder es null.");
		try
		{
			NodoArbol<E> r = (NodoArbol<E>) p; //To return.
			return r;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posici�n a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}

}