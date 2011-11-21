package ProyectoX.Librerias.TDAMapeo;

/**
 * Clase Nodo Arbol Binario con elemento de tipo Genérico que implementa la interface PositionBT.
 * 
 * Implementación de un Nodo Arbol Binario para la clase Arbol Binario de Búsqueda, con referencia a un Nodo-Padre,
 * un Nodo-Hijo izquierdo, un Nodo-Hijo derecho y referencia a un elemento de tipo Genérico.
 * 
 * Contiene 2 constructores:
 *  + Nodo Raíz con Rótulo genérico r.
 *  + Nodo con Nodo-Padre p y Rótulo genérico r. 
 *  
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class NodoArbolB<E> implements PositionBT<E>
{
	
	//Variables de Instancia
	protected E rotulo;
	protected PositionBT<E> hIzquierdo, hDerecho, padre;
	
	/*Constructores*/
	
	/**
	 * Crea un NodoArbolB raíz de rótulo r.
	 * 
	 * @param r Rótulo para el nuevo Nodo.
	 */
	public NodoArbolB (E r)
	{
		hIzquierdo = hDerecho = padre = null;
		rotulo = r;
	}
	
	/**
	 * Crea un NodoArbolB hoja de rótulo r y Nodo-Padre p.
	 * 
	 * @param p Nodo-Padre del nuevo Nodo.
	 * @param r Rótulo del nuevo Nodo.
	 */
	public NodoArbolB (PositionBT<E> p, E r)
	{
		hIzquierdo = hDerecho = null;
		padre = p;
		rotulo = r;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambiar Elemento: Cambia el Rótulo Genérico actual por el nuevo Rótulo Genérico r.
	 * 
	 * @param r Nuevo Rótulo Genérico.
	 */
	public void setElement (E r)
	{
		rotulo = r;
	}
	
	/**
	 * Cambiar Padre: Cambia el Nodo-Padre actual por el nuevo Nodo-Padre p.
	 * 
	 * @param p Nuevo Nodo-Padre.
	 */
	public void setParent (PositionBT<E> p)
	{
		padre = p;
	}
	
	/**
	 * Cambiar Izquierdo: Cambia el Nodo-Hijo izquierdo actual por el nuevo Nodo-Hijo hI.
	 * 
	 * @param hI Nuevo Nodo-Hijo izquierdo.
	 */
	public void setLeft (PositionBT<E> hI)
	{
		hIzquierdo = hI;
	}
	
	/**
	 * Cambiar Derecho: Cambia el Nodo-Hijo derecho actual por el nuevo Nodo-Hijo hD.
	 * 
	 * @param hD Nuevo Nodo-Hijo derecho.
	 */
	public void setRight (PositionBT<E> hD)
	{
		hDerecho = hD;
	}
	
	/**
	 * Borra todos los enlaces, menos el Nodo-Padre.
	 */
	public void limpiarMenosPadre ()
	{
		hIzquierdo = hDerecho = null;
		rotulo = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Elemento: Devuelve el Rótulo actual del NodoArbol.
	 * 
	 * @return Rótulo actual del NodoArbol.
	 */
	public E element()
	{
		return rotulo;
	}
	
	/**
	 * Obtener Padre: Devuelve el Nodo-Padre al NodoArbol actual.
	 * 
	 * @return Nodo-Padre del NodoArbol actual.
	 */
	public PositionBT<E> getParent ()
	{
		return padre;
	}
	
	/**
	 * Obtener Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbol actual.
	 * 
	 * @return Nodo-Hijo izquierdo del NodoArbol actual.
	 */
	public PositionBT<E> getLeft ()
	{
		return hIzquierdo;
	}
	
	/**
	 * Obtener Derecho: Devuelve el Nodo-Hijo derecho del NodoArbol actual.
	 * 
	 * @return Nodo-Hijo derecho del NodoArbol actual.
	 */
	public PositionBT<E> getRight ()
	{
		return hDerecho;
	}

}