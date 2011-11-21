package ProyectoX.Librerias.TDAArbol;

import java.util.Iterator;

import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Arbol que implementa la interface Tree.
 *  
 * Arbol General implementado con una estructura de colección de Hijos (usando una Lista Simplemente Enlazada), con referencia al padre de cada NodoÁrbol y rótulos de elementos de tipo Genéricos.
 * 
 * Contiene 2 Constructores:
 *  + Arbol vacío.
 *  + Arbol con Raiz de rótulo r.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 * @param <E>
 */
public class Arbol<E> implements Tree<E>
{
	
	//Variables de Instancia.
	protected NodoArbol<E> raiz;//Nodo-Raíz del Árbol.
	protected int tamanio;//Cantidad de NodosÁrbol en el Árbol.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Árbol vacío.
	 */
	public Arbol ()
	{
		raiz = null;
		tamanio = 0;
	}
	
	/**
	 * Crea un Árbol con Raíz de Rótulo r.
	 * 
	 * @param r Rótulo de la Raíz del nuevo Árbol.
	 */
	public Arbol (E r)
	{
		raiz = new NodoArbol<E>(r);
		tamanio = 1;
	}
	
	/*COMANDOS*/

	/**
	 * Crea un nuevo Nodo-Raíz de Rótulo r para el Árbol actual.
	 * 
	 * Si el Árbol no es vacío, devuelve la Posición del Nodo-Raíz viejo.
	 * 
	 * @param r Rótulo para la nueva Raíz.
	 * @return Null: si el Árbol era vacío.
	 *         Posición del Nodo-Raíz viejo: si el Árbol no era vacío.
	 */
	public Position<E> crearRaiz (E r)
	{
		NodoArbol<E> raizVieja = raiz;
		raiz = new NodoArbol<E>(r);
		tamanio = 1;
		return raizVieja;
	}
	
	/**
	 * Reemplaza el Rótulo del NodoArbol que se encuentra en la Posición p.
	 * 
	 * @param p Posición del NodoArbol.
	 * @param e Nuevo Rótulo.
	 * @return Rótulo reemplazado.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta. 
	 */
	public E replace (Position<E> p, E e) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoArbolEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		E r = nodoArbolEnP.element(); //To Return. Rótulo Reemplazado.
		nodoArbolEnP.rotulo(e);
		return r;
	}
	
	/**
	 * Agrega un Nodo de Rótulo r como Hijo Primero al Nodo-Padre en la posición pPadre, en su Lista de Hijos.
	 * 
	 * Si el Nodo-Padre en la posición pPadre no tiene hijos, se crea una Lista de Hijos para agregar el nuevo Hijo.
	 * 
	 * El hijo es agregado como primer hijo en la Lista de Hijos del Nodo-Padre en la posición pPadre.
	 * 
	 * @param pPadre Posición del Nodo-Padre al que agregarle el nuevo primer Hijo.
	 * @param r Rótulo para el nuevo Hijo.
	 * @return Posición del nuevo primer hijo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public Position<E> agregarPrimerHijo (Position<E> pPadre, E r) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(pPadre);//Nodo-Padre en la posición pPadre.
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		if (nodoPadreEnP.cantHijos() == 0)
			nodoPadreEnP.nuevaListaHijos(new ListaPositionSimple<NodoArbol<E>> ());
		nodoPadreEnP.hijos().addFirst(nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de Rótulo r como Hijo Último al Nodo-Padre en la posición pPadre, en su Lista de Hijos.
	 * 
	 * Si el Nodo-Padre en la posición pPadre no tiene hijos, se crea una Lista de Hijos para agregar el nuevo Hijo.
	 * 
	 * El hijo es agregado como último hijo en la Lista de Hijos del Nodo-Padre en la posición pPadre.
	 * 
	 * @param pPadre Posición del Nodo-Padre al que agregarle el nuevo último Hijo.
	 * @param r Rótulo para el nuevo Hijo.
	 * @return Posición del nuevo último hijo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public Position<E> agregarUltimoHijo (Position<E> pPadre, E r) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(pPadre);//Nodo-Padre en la posición pPadre.
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		if (nodoPadreEnP.cantHijos() == 0)
			nodoPadreEnP.nuevaListaHijos(new ListaPositionSimple<NodoArbol<E>> ());
		nodoPadreEnP.hijos().addLast(nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de Rótulo r al Nodo-Padre en la posición pPadre, en su Lista de Hijos, a la izquierda del Nodo-Hijo en la posición pHerDerecho.
	 * 
	 * @param pPadre Posición del Nodo-Padre al que agregarle el nuevo Hijo.
	 * @param pHerDerecho Posición del Nodo-Hermano que será el hermano a derecha del Nodo-Hijo a agregar.
	 * @param r Rótulo para el nuevo Hijo.
	 * @return Posición del nuevo hijo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ListaVaciaHijosException Si el nodo en la posición pPadre no tiene hijos.
	 */
	public Position<E> agregarHermanoDelante (Position<E> pPadre, Position<E> pHerDerecho, E r) throws PosicionInvalidaException, ListaVaciaHijosException
	{
		NodoArbol<E> nodoPadreEnP = null;
		try
		{//Nodo-Padre en la posición pPadre.
			nodoPadreEnP = validarPosicion(pPadre);
		}
		catch (PosicionInvalidaException e1)
		{	
			throw new PosicionInvalidaException("Nodo Padre: "+e1.obtenerError()); 
		}
		NodoArbol<E> nodoHermanoEnP = null;
		try
		{//Nodo-Hermano en la posición pHerDerecho.
			nodoHermanoEnP = validarPosicion(pHerDerecho);
		}
		catch (PosicionInvalidaException e2)
		{	
			throw new PosicionInvalidaException("Nodo Hermano Derecho: "+e2.obtenerError());
		}
		
		//Verificando nodoHermanoEnP tenga como Nodo-Padre a nodoPadreEnP
		if (nodoPadreEnP.cantHijos() == 0)
			throw new ListaVaciaHijosException("El nodo al que está intentando acceder no tiene hijos. No existe referencia entre el Nodo Hermano Derecho y el Nodo Padre ingresados.");
		if (nodoHermanoEnP.padre() == nodoPadreEnP)
			throw new PosicionInvalidaException("Los nodos a los que está intentando acceder no tienen relación. No existe referencia entre el Nodo Hermano Derecho y el Nodo Padre ingresados.");
		
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		nodoPadreEnP.hijos().addBefore(buscarPosHijo(nodoPadreEnP, nodoHermanoEnP), nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de Rótulo r al Nodo-Padre en la posición pPadre, en su Lista de Hijos, a la derecha del Nodo-Hijo en la posición pHerIzquierdo.
	 * 
	 * @param pPadre Posición del Nodo-Padre al que agregarle el nuevo Hijo.
	 * @param pHerIzquierdo Posición del Nodo-Hermano que será el hermano a izquierda del Nodo-Hijo a agregar.
	 * @param r Rótulo para el nuevo Hijo.
	 * @return Posición del nuevo hijo.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ListaVaciaHijosException Si el nodo en la posición pPadre no tiene hijos.
	 */
	public Position<E> agregarHermanoDetras (Position<E> pPadre, Position<E> pHerIzquierdo, E r) throws PosicionInvalidaException, ListaVaciaHijosException
	{
		NodoArbol<E> nodoPadreEnP = null;
		try
		{//Nodo-Padre en la posición pPadre.
			nodoPadreEnP = validarPosicion(pPadre);
		}
		catch (PosicionInvalidaException e1)
		{	
			throw new PosicionInvalidaException("Nodo Padre: "+e1.obtenerError()); 
		}
		NodoArbol<E> nodoHermanoEnP = null;
		try
		{//Nodo-Hermano en la posición pHerIzquierdo.
			nodoHermanoEnP = validarPosicion(pHerIzquierdo);
		}
		catch (PosicionInvalidaException e2)
		{	
			throw new PosicionInvalidaException("Nodo Hermano Izquierdo: "+e2.obtenerError());
		}
		
		//Verificando nodoHermanoEnP tenga como Nodo-Padre a nodoPadreEnP
		if (nodoPadreEnP.cantHijos() == 0)
			throw new ListaVaciaHijosException("El nodo al que está intentando acceder no tiene hijos. No existe referencia entre el Nodo Hermano Izquierdo y el Nodo Padre ingresados.");
		if (nodoHermanoEnP.padre() == nodoPadreEnP)
			throw new PosicionInvalidaException("Los nodos a los que está intentando acceder no tienen relación. No existe referencia entre el Nodo Hermano Izquierdo y el Nodo Padre ingresados.");
		
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		nodoPadreEnP.hijos().addAfter(buscarPosHijo(nodoPadreEnP, nodoHermanoEnP), nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Elimina el NodoArbol Externo que está en la posición p.
	 * 
	 * Si el NodoArbol en la posición p es un NodoHoja, entonces es eliminado.
	 * Si el NodoArbol en la posición p no es un NodoHoja, se dispara una exception.
	 * 
	 * @param p Posición del NodoArbol a eliminar.
	 * @return NodoArbol eliminado.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public NodoArbol<E> eliminarExterno (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p);
		if (nodoEnP.cantHijos() != 0)
			throw new PosicionInvalidaException("El nodo que está intentando eliminar no es un nodo externo. El nodo tiene hijos.");
		if (nodoEnP == raiz)
			raiz = null;
		else
		{
			NodoArbol<E> padreNodoEnP = nodoEnP.padre();
			padreNodoEnP.hijos().remove(buscarPosHijo(padreNodoEnP, nodoEnP));
			nodoEnP.nuevoPadre(null);
		}
		tamanio--;
		return nodoEnP;
	}
	
	/**
	 * Elimina el NodoArbol Interno que está en la posición p.
	 * 
	 * Si el NodoArbol en la posición p es un NodoInterno, entonces es eliminado.
	 * Si el NodoArbol en la posición p no es un NodoInterno, se dispara una exception.
	 * 
	 * Los Hijos del NodoArbol eliminado pasan a ser los nuevos Hijos Primeros del Nodo-Padre del NodoArbol eliminado.
	 * Si se quiere eliminar la raíz se dispara una exception.
	 * 
	 * @param p Posición del NodoArbol a eliminar.
	 * @return NodoArbol eliminado.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public NodoArbol<E> eliminarInterno (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p);
		if (nodoEnP.cantHijos() == 0)
			throw new PosicionInvalidaException("El nodo que está intentando eliminar no es un nodo interno. El nodo no tiene hijos.");
		if (nodoEnP == raiz)
			throw new PosicionInvalidaException("El nodo que está intentando eliminar es el nodo raiz. No se puede eliminar un nodo raiz que hijos no se puede eliminar mediante este método. Intente utilizar el método limpiar.");
		else
		{
			NodoArbol<E> padreNodoEnP = nodoEnP.padre();
			padreNodoEnP.hijos().remove(buscarPosHijo(padreNodoEnP, nodoEnP));
			nodoEnP.nuevoPadre(null);
			pasarHijos(nodoEnP, padreNodoEnP);//Agrega los Hijos de nodoEnP a la Lista de Hijos de padreNodoEnP.
		}
		tamanio--;
		return nodoEnP;
	}
	
	/**
	 * Transfiere los Nodo-Hijo de la Lista de Hijos del Nodo-Padre viejoPadre a la Lista de Hijos del Nodo-Padre nuevoPadre.
	 * 
	 * Agrega todos los Nodo-Hijo de viejoPadre al nuevoPadre antes del primer hijo del nuevoPadre, quedando el último Nodo-Hijo de viejoPadre como hermano izquierdo del primer hijo del nuevoPadre.
	 * 
	 * @param viejoPadre Nodo-Padre desde el que se traspasarán los Nodo-Hijo.
	 * @param nuevoPadre Nodo-Padre al que se le agregarán los Nodo-Hijo.
	 */
	private void pasarHijos (NodoArbol<E> viejoPadre, NodoArbol<E> nuevoPadre) throws PosicionInvalidaException, ViolacionLimiteException
	{
		Position<NodoArbol<E>> viejoPrimerHijo = nuevoPadre.hijos().first();//Nodo del viejo primer hijo del nuevo padre.
        for (NodoArbol<E> hijoApasar: viejoPadre.hijos())
		{
			hijoApasar.nuevoPadre(nuevoPadre);//Actualizo el padre del Nodo-Hijo a pasar.
			viejoPadre.hijos().remove(buscarPosHijo(viejoPadre, hijoApasar));//Elimino Nodo-Hijo a pasar.
			if (viejoPrimerHijo == null)
			{//Nodo-Padre nuevoPadre no tiene Nodo-Hijos.
				nuevoPadre.hijos().addFirst(hijoApasar);
				viejoPrimerHijo = nuevoPadre.hijos().first();
			}
			else//Agrego Nodo-Hijo a pasar a la Lista de Hijos del nuevo padre.
				nuevoPadre.hijos().addBefore(viejoPrimerHijo, hijoApasar);
		}
	}
	
	/**
	 * Validar Posición: Verifica si la posición pasado por parámetro es correcta, y devuelve el NodoArbol perteneciente a esa posición.
	 * 
	 * @param p Posición a verificar.
	 * @return El NodoArbol resultante al castear la posición verificada.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta. 
	 */
	private NodoArbol<E> validarPosicion (Position<E> p) throws PosicionInvalidaException
	{
		if (p == null)
			throw new PosicionInvalidaException ("La posición a la que esta intentando acceder es null.");
		try
		{
			NodoArbol<E> r = (NodoArbol<E>) p; //To return.
			return r;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posición a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}
	
	/**
	 * Busca la posición del Nodo-Hijo hijo en la Lista de Hijos del Nodo-Padre padre.
	 * 
	 * @param padre Nodo-Padre de hijo.
	 * @param hijo Nodo-Hijo a buscar.
	 * @return Posición del Nodo-Hijo hijo en la Liste de Hijos del Nodo-Padre padre.
	 */
	private Position<NodoArbol<E>> buscarPosHijo (NodoArbol<E> padre, NodoArbol<E> hijo) throws PosicionInvalidaException, ViolacionLimiteException
	{
		ListaPositionSimple<NodoArbol<E>> hijosPadre = padre.hijos();//Lista de los Hijos de padre.
		Position<NodoArbol<E>> posHijo = hijosPadre.first();//To return. Posición del hijo en la Lista de Hijos de padre.
		while ((posHijo != null) && (posHijo.element() != hijo))
			posHijo = hijosPadre.next(posHijo);
		return posHijo;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Tamaño: Devuelve la cantidad de elementos en el Árbol.
	 * 
	 * @return Cantidad de elementos en el Arbol.
	 */
	public int size ()
	{
		return tamanio;
	}
	
	/**
	 * Está Vacío: Verifica si el Árbol está vacío y devuelve el resultado.
	 * 
	 * @return True:  el Árbol no contiene elementos.
	 *         False: el Árbol contiene almenos el elemento Raíz.
	 */
	public boolean isEmpty ()
	{
		return (tamanio == 0);
	}
	
	/**
	 * Es Interno: Verifica si el NodoArbol que está en la posición p es interno y devuelve el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p tiene hijos.
	 *         False: caso contrario.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isInternal (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		return (nodoEnP.cantHijos() != 0);
	}
	
	/**
	 * Es Externo: Verifica si el NodoArbol que está en la posición p es externo y devuelce el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene hijos.
	 *         Falso: caso contrario.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isExternal (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		return (nodoEnP.cantHijos() == 0);
	}
	
	/**
	 * Es Raíz: Verifica si el NodoArbol que está en la posición p es raíz y devuelve el resultado.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene Nodo-Padre.
	 *         False: el NodoArbol en p tiene Nodo-Padre.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public boolean isRoot (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		return (nodoEnP.padre() == null);
	}
	
	/**
	 * Raíz: Devuelve la Raíz del Árbol actual.
	 * 
	 * @return Raíz del Árbol.
	 * @exception ArbolVacioException Si se pide la Raíz de un Árbol vacío.
	 */
	public Position<E> root () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El árbol al que está intentando acceder está vacío.");
		return (Position<E>) raiz;
	}
	
	/**
	 * Padre: devuelve el padre del NodoArbol que está en la Posición p.
	 * 
	 * @param p Posición del NodoArbol.
	 * @return Padre del NodoArbol en la Posición p.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se consulta por el Nodo-Padre de la Raiz. 
	 */
	public Position<E> parent (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		if (nodoEnP == raiz)
			throw new ViolacionLimiteException ("El nodo que esta intentando consultar es el nodo raíz. El nodo raíz no tiene padre.");
		return nodoEnP.padre();
	}
	
	/*Iteradores*/
	
	/**
	 * Hijos: Devuelve una colección iterable de la Lista de Hijos del Nodo-Padre que está en la posición p.
	 * 
	 * @param p Posición del Nodo-Padre.
	 * @return Iterador con la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posición es nula o incorrecta.
	 */
	public Iterable<Position<E>> children (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que está en la posición p.
		if (nodoEnP.cantHijos() == 0)
			throw new PosicionInvalidaException("El nodo que está intentando consultar no tiene hijos.");
		ListaPositionSimple<Position<E>> r = new ListaPositionSimple<Position<E>> ();//To return. Lista Iterador de Hijos.
		for (Position<E> nodoHijo: nodoEnP.hijos())
			r.addLast(nodoHijo);
		return r;
	}
	
	/**
	 * Devuelve una colección iterable con los elementos guardados en el Árbol.
	 * 
	 * El iterador es construido Recorriendo el Árbol en PreOrden.
	 * 
	 * @return Colección iterable con los elementos guardados en el Árbol.
	 * @exception ArbolVacioException Si se pide un iterador de un Árbol vacío.
	 */
	public Iterator<E> iterator () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El árbol al que está intentando acceder está vacío.");
		ListaPositionSimple<E> l = new ListaPositionSimple<E> ();//To return.Lista Iterable con todos los nodos.
		rotulosEnPreOrden (l, raiz);
		return l.iterator();
	}
	
	/**
	 * Devuelve una colección iterable con los NodoArbol del Árbol.
	 * 
	 * El iterador es construido Recorriendo el Árbol en PreOrden.
	 * 
	 * @return Colección iterable con los NodoArbol del Árbol.
	 * @exception ArbolVacioException Si se pide las posiciones de un Árbol vacío.
	 */
	public Iterable<Position<E>> positions () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El árbol al que está intentando acceder está vacío.");
		ListaPositionSimple<Position<E>> l = new ListaPositionSimple<Position<E>> ();//To return. Lista Iterable con todos los nodos.
		positionsEnPreOrden (l, raiz);
		return l;
	}
	
	/*RECORRIDOS*/
	
	/**
	 * Recorre el Árbol en PreOrden, agregando como último elemento de la Lista l, al Rótulo del Nodo visitado.
	 * 
	 * La lista Lista l tendrá los rótulos del Nodo que está en la posición p,
	 * los rótulos de los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los rótulos de los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja.
	 * 
	 * @param l Lista donde agregar los rótulos.
	 * @param p Posición del Nodo que contiene el rótulo a agregar. 
	 */
	public void rotulosEnPreOrden (ListaPositionSimple<E> l, Position<E> p)
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(p);//Nodo-Padre que está en p, para Listarlo junto a sus hijos.
		l.addLast(nodoPadreEnP.element());
		if (nodoPadreEnP.cantHijos() != 0)
		{
			for (NodoArbol<E> hijoAlistar: nodoPadreEnP.hijos())
				rotulosEnPreOrden(l, hijoAlistar);
		}
	}
	
	/**
	 * Recorre el Árbol en PreOrden, agregando como último elemento de la Lista l, el Nodo visitado.
	 * 
	 * La lista Lista l tendrá el Nodo que está en la posición p,
	 * los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja.
	 * 
	 * @param l Lista donde agregar los NodoArbol.
	 * @param p Posición del Nodo a agregar. 
	 */
	public void positionsEnPreOrden (ListaPositionSimple<Position<E>> l, Position<E> p)
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(p);//Nodo-Padre que está en p, para Listarlo junto a sus hijos.
		l.addLast(nodoPadreEnP);
		if (nodoPadreEnP.cantHijos() != 0)
		{
			for (Position<E> hijoAlistar: nodoPadreEnP.hijos())
				positionsEnPreOrden(l, hijoAlistar);
		}
	}
	
}