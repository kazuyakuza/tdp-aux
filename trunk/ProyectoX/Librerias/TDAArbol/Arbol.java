package ProyectoX.Librerias.TDAArbol;

import java.util.Iterator;

import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Arbol que implementa la interface Tree.
 *  
 * Arbol General implementado con una estructura de colecci�n de Hijos (usando una Lista Simplemente Enlazada), con referencia al padre de cada Nodo�rbol y r�tulos de elementos de tipo Gen�ricos.
 * 
 * Contiene 2 Constructores:
 *  + Arbol vac�o.
 *  + Arbol con Raiz de r�tulo r.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 * @param <E>
 */
public class Arbol<E> implements Tree<E>
{
	
	//Variables de Instancia.
	protected NodoArbol<E> raiz;//Nodo-Ra�z del �rbol.
	protected int tamanio;//Cantidad de Nodos�rbol en el �rbol.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un �rbol vac�o.
	 */
	public Arbol ()
	{
		raiz = null;
		tamanio = 0;
	}
	
	/**
	 * Crea un �rbol con Ra�z de R�tulo r.
	 * 
	 * @param r R�tulo de la Ra�z del nuevo �rbol.
	 */
	public Arbol (E r)
	{
		raiz = new NodoArbol<E>(r);
		tamanio = 1;
	}
	
	/*COMANDOS*/

	/**
	 * Crea un nuevo Nodo-Ra�z de R�tulo r para el �rbol actual.
	 * 
	 * Si el �rbol no es vac�o, devuelve la Posici�n del Nodo-Ra�z viejo.
	 * 
	 * @param r R�tulo para la nueva Ra�z.
	 * @return Null: si el �rbol era vac�o.
	 *         Posici�n del Nodo-Ra�z viejo: si el �rbol no era vac�o.
	 */
	public Position<E> crearRaiz (E r)
	{
		NodoArbol<E> raizVieja = raiz;
		raiz = new NodoArbol<E>(r);
		tamanio = 1;
		return raizVieja;
	}
	
	/**
	 * Reemplaza el R�tulo del NodoArbol que se encuentra en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @param e Nuevo R�tulo.
	 * @return R�tulo reemplazado.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	public E replace (Position<E> p, E e) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoArbolEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		E r = nodoArbolEnP.element(); //To Return. R�tulo Reemplazado.
		nodoArbolEnP.rotulo(e);
		return r;
	}
	
	/**
	 * Agrega un Nodo de R�tulo r como Hijo Primero al Nodo-Padre en la posici�n pPadre, en su Lista de Hijos.
	 * 
	 * Si el Nodo-Padre en la posici�n pPadre no tiene hijos, se crea una Lista de Hijos para agregar el nuevo Hijo.
	 * 
	 * El hijo es agregado como primer hijo en la Lista de Hijos del Nodo-Padre en la posici�n pPadre.
	 * 
	 * @param pPadre Posici�n del Nodo-Padre al que agregarle el nuevo primer Hijo.
	 * @param r R�tulo para el nuevo Hijo.
	 * @return Posici�n del nuevo primer hijo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public Position<E> agregarPrimerHijo (Position<E> pPadre, E r) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(pPadre);//Nodo-Padre en la posici�n pPadre.
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		if (nodoPadreEnP.cantHijos() == 0)
			nodoPadreEnP.nuevaListaHijos(new ListaPositionSimple<NodoArbol<E>> ());
		nodoPadreEnP.hijos().addFirst(nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de R�tulo r como Hijo �ltimo al Nodo-Padre en la posici�n pPadre, en su Lista de Hijos.
	 * 
	 * Si el Nodo-Padre en la posici�n pPadre no tiene hijos, se crea una Lista de Hijos para agregar el nuevo Hijo.
	 * 
	 * El hijo es agregado como �ltimo hijo en la Lista de Hijos del Nodo-Padre en la posici�n pPadre.
	 * 
	 * @param pPadre Posici�n del Nodo-Padre al que agregarle el nuevo �ltimo Hijo.
	 * @param r R�tulo para el nuevo Hijo.
	 * @return Posici�n del nuevo �ltimo hijo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public Position<E> agregarUltimoHijo (Position<E> pPadre, E r) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(pPadre);//Nodo-Padre en la posici�n pPadre.
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		if (nodoPadreEnP.cantHijos() == 0)
			nodoPadreEnP.nuevaListaHijos(new ListaPositionSimple<NodoArbol<E>> ());
		nodoPadreEnP.hijos().addLast(nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de R�tulo r al Nodo-Padre en la posici�n pPadre, en su Lista de Hijos, a la izquierda del Nodo-Hijo en la posici�n pHerDerecho.
	 * 
	 * @param pPadre Posici�n del Nodo-Padre al que agregarle el nuevo Hijo.
	 * @param pHerDerecho Posici�n del Nodo-Hermano que ser� el hermano a derecha del Nodo-Hijo a agregar.
	 * @param r R�tulo para el nuevo Hijo.
	 * @return Posici�n del nuevo hijo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ListaVaciaHijosException Si el nodo en la posici�n pPadre no tiene hijos.
	 */
	public Position<E> agregarHermanoDelante (Position<E> pPadre, Position<E> pHerDerecho, E r) throws PosicionInvalidaException, ListaVaciaHijosException
	{
		NodoArbol<E> nodoPadreEnP = null;
		try
		{//Nodo-Padre en la posici�n pPadre.
			nodoPadreEnP = validarPosicion(pPadre);
		}
		catch (PosicionInvalidaException e1)
		{	
			throw new PosicionInvalidaException("Nodo Padre: "+e1.obtenerError()); 
		}
		NodoArbol<E> nodoHermanoEnP = null;
		try
		{//Nodo-Hermano en la posici�n pHerDerecho.
			nodoHermanoEnP = validarPosicion(pHerDerecho);
		}
		catch (PosicionInvalidaException e2)
		{	
			throw new PosicionInvalidaException("Nodo Hermano Derecho: "+e2.obtenerError());
		}
		
		//Verificando nodoHermanoEnP tenga como Nodo-Padre a nodoPadreEnP
		if (nodoPadreEnP.cantHijos() == 0)
			throw new ListaVaciaHijosException("El nodo al que est� intentando acceder no tiene hijos. No existe referencia entre el Nodo Hermano Derecho y el Nodo Padre ingresados.");
		if (nodoHermanoEnP.padre() == nodoPadreEnP)
			throw new PosicionInvalidaException("Los nodos a los que est� intentando acceder no tienen relaci�n. No existe referencia entre el Nodo Hermano Derecho y el Nodo Padre ingresados.");
		
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		nodoPadreEnP.hijos().addBefore(buscarPosHijo(nodoPadreEnP, nodoHermanoEnP), nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Agrega un Nodo de R�tulo r al Nodo-Padre en la posici�n pPadre, en su Lista de Hijos, a la derecha del Nodo-Hijo en la posici�n pHerIzquierdo.
	 * 
	 * @param pPadre Posici�n del Nodo-Padre al que agregarle el nuevo Hijo.
	 * @param pHerIzquierdo Posici�n del Nodo-Hermano que ser� el hermano a izquierda del Nodo-Hijo a agregar.
	 * @param r R�tulo para el nuevo Hijo.
	 * @return Posici�n del nuevo hijo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ListaVaciaHijosException Si el nodo en la posici�n pPadre no tiene hijos.
	 */
	public Position<E> agregarHermanoDetras (Position<E> pPadre, Position<E> pHerIzquierdo, E r) throws PosicionInvalidaException, ListaVaciaHijosException
	{
		NodoArbol<E> nodoPadreEnP = null;
		try
		{//Nodo-Padre en la posici�n pPadre.
			nodoPadreEnP = validarPosicion(pPadre);
		}
		catch (PosicionInvalidaException e1)
		{	
			throw new PosicionInvalidaException("Nodo Padre: "+e1.obtenerError()); 
		}
		NodoArbol<E> nodoHermanoEnP = null;
		try
		{//Nodo-Hermano en la posici�n pHerIzquierdo.
			nodoHermanoEnP = validarPosicion(pHerIzquierdo);
		}
		catch (PosicionInvalidaException e2)
		{	
			throw new PosicionInvalidaException("Nodo Hermano Izquierdo: "+e2.obtenerError());
		}
		
		//Verificando nodoHermanoEnP tenga como Nodo-Padre a nodoPadreEnP
		if (nodoPadreEnP.cantHijos() == 0)
			throw new ListaVaciaHijosException("El nodo al que est� intentando acceder no tiene hijos. No existe referencia entre el Nodo Hermano Izquierdo y el Nodo Padre ingresados.");
		if (nodoHermanoEnP.padre() == nodoPadreEnP)
			throw new PosicionInvalidaException("Los nodos a los que est� intentando acceder no tienen relaci�n. No existe referencia entre el Nodo Hermano Izquierdo y el Nodo Padre ingresados.");
		
		NodoArbol<E> nuevoHijo = new NodoArbol<E> (nodoPadreEnP, r);//Nuevo Nodo-Hijo con r para nodoPadreEnP.
		nodoPadreEnP.hijos().addAfter(buscarPosHijo(nodoPadreEnP, nodoHermanoEnP), nuevoHijo);
		tamanio++;
		return nuevoHijo;
	}
	
	/**
	 * Elimina el NodoArbol Externo que est� en la posici�n p.
	 * 
	 * Si el NodoArbol en la posici�n p es un NodoHoja, entonces es eliminado.
	 * Si el NodoArbol en la posici�n p no es un NodoHoja, se dispara una exception.
	 * 
	 * @param p Posici�n del NodoArbol a eliminar.
	 * @return NodoArbol eliminado.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public NodoArbol<E> eliminarExterno (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p);
		if (nodoEnP.cantHijos() != 0)
			throw new PosicionInvalidaException("El nodo que est� intentando eliminar no es un nodo externo. El nodo tiene hijos.");
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
	 * Elimina el NodoArbol Interno que est� en la posici�n p.
	 * 
	 * Si el NodoArbol en la posici�n p es un NodoInterno, entonces es eliminado.
	 * Si el NodoArbol en la posici�n p no es un NodoInterno, se dispara una exception.
	 * 
	 * Los Hijos del NodoArbol eliminado pasan a ser los nuevos Hijos Primeros del Nodo-Padre del NodoArbol eliminado.
	 * Si se quiere eliminar la ra�z se dispara una exception.
	 * 
	 * @param p Posici�n del NodoArbol a eliminar.
	 * @return NodoArbol eliminado.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public NodoArbol<E> eliminarInterno (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p);
		if (nodoEnP.cantHijos() == 0)
			throw new PosicionInvalidaException("El nodo que est� intentando eliminar no es un nodo interno. El nodo no tiene hijos.");
		if (nodoEnP == raiz)
			throw new PosicionInvalidaException("El nodo que est� intentando eliminar es el nodo raiz. No se puede eliminar un nodo raiz que hijos no se puede eliminar mediante este m�todo. Intente utilizar el m�todo limpiar.");
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
	 * Agrega todos los Nodo-Hijo de viejoPadre al nuevoPadre antes del primer hijo del nuevoPadre, quedando el �ltimo Nodo-Hijo de viejoPadre como hermano izquierdo del primer hijo del nuevoPadre.
	 * 
	 * @param viejoPadre Nodo-Padre desde el que se traspasar�n los Nodo-Hijo.
	 * @param nuevoPadre Nodo-Padre al que se le agregar�n los Nodo-Hijo.
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
	
	/**
	 * Busca la posici�n del Nodo-Hijo hijo en la Lista de Hijos del Nodo-Padre padre.
	 * 
	 * @param padre Nodo-Padre de hijo.
	 * @param hijo Nodo-Hijo a buscar.
	 * @return Posici�n del Nodo-Hijo hijo en la Liste de Hijos del Nodo-Padre padre.
	 */
	private Position<NodoArbol<E>> buscarPosHijo (NodoArbol<E> padre, NodoArbol<E> hijo) throws PosicionInvalidaException, ViolacionLimiteException
	{
		ListaPositionSimple<NodoArbol<E>> hijosPadre = padre.hijos();//Lista de los Hijos de padre.
		Position<NodoArbol<E>> posHijo = hijosPadre.first();//To return. Posici�n del hijo en la Lista de Hijos de padre.
		while ((posHijo != null) && (posHijo.element() != hijo))
			posHijo = hijosPadre.next(posHijo);
		return posHijo;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos en el �rbol.
	 * 
	 * @return Cantidad de elementos en el Arbol.
	 */
	public int size ()
	{
		return tamanio;
	}
	
	/**
	 * Est� Vac�o: Verifica si el �rbol est� vac�o y devuelve el resultado.
	 * 
	 * @return True:  el �rbol no contiene elementos.
	 *         False: el �rbol contiene almenos el elemento Ra�z.
	 */
	public boolean isEmpty ()
	{
		return (tamanio == 0);
	}
	
	/**
	 * Es Interno: Verifica si el NodoArbol que est� en la posici�n p es interno y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p tiene hijos.
	 *         False: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isInternal (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		return (nodoEnP.cantHijos() != 0);
	}
	
	/**
	 * Es Externo: Verifica si el NodoArbol que est� en la posici�n p es externo y devuelce el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene hijos.
	 *         Falso: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isExternal (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		return (nodoEnP.cantHijos() == 0);
	}
	
	/**
	 * Es Ra�z: Verifica si el NodoArbol que est� en la posici�n p es ra�z y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene Nodo-Padre.
	 *         False: el NodoArbol en p tiene Nodo-Padre.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isRoot (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		return (nodoEnP.padre() == null);
	}
	
	/**
	 * Ra�z: Devuelve la Ra�z del �rbol actual.
	 * 
	 * @return Ra�z del �rbol.
	 * @exception ArbolVacioException Si se pide la Ra�z de un �rbol vac�o.
	 */
	public Position<E> root () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El �rbol al que est� intentando acceder est� vac�o.");
		return (Position<E>) raiz;
	}
	
	/**
	 * Padre: devuelve el padre del NodoArbol que est� en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return Padre del NodoArbol en la Posici�n p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se consulta por el Nodo-Padre de la Raiz. 
	 */
	public Position<E> parent (Position<E> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		if (nodoEnP == raiz)
			throw new ViolacionLimiteException ("El nodo que esta intentando consultar es el nodo ra�z. El nodo ra�z no tiene padre.");
		return nodoEnP.padre();
	}
	
	/*Iteradores*/
	
	/**
	 * Hijos: Devuelve una colecci�n iterable de la Lista de Hijos del Nodo-Padre que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo-Padre.
	 * @return Iterador con la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public Iterable<Position<E>> children (Position<E> p) throws PosicionInvalidaException
	{
		NodoArbol<E> nodoEnP = validarPosicion(p); //NodoArbol que est� en la posici�n p.
		if (nodoEnP.cantHijos() == 0)
			throw new PosicionInvalidaException("El nodo que est� intentando consultar no tiene hijos.");
		ListaPositionSimple<Position<E>> r = new ListaPositionSimple<Position<E>> ();//To return. Lista Iterador de Hijos.
		for (Position<E> nodoHijo: nodoEnP.hijos())
			r.addLast(nodoHijo);
		return r;
	}
	
	/**
	 * Devuelve una colecci�n iterable con los elementos guardados en el �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los elementos guardados en el �rbol.
	 * @exception ArbolVacioException Si se pide un iterador de un �rbol vac�o.
	 */
	public Iterator<E> iterator () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El �rbol al que est� intentando acceder est� vac�o.");
		ListaPositionSimple<E> l = new ListaPositionSimple<E> ();//To return.Lista Iterable con todos los nodos.
		rotulosEnPreOrden (l, raiz);
		return l.iterator();
	}
	
	/**
	 * Devuelve una colecci�n iterable con los NodoArbol del �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los NodoArbol del �rbol.
	 * @exception ArbolVacioException Si se pide las posiciones de un �rbol vac�o.
	 */
	public Iterable<Position<E>> positions () throws ArbolVacioException
	{
		if (tamanio == 0)
			throw new ArbolVacioException("El �rbol al que est� intentando acceder est� vac�o.");
		ListaPositionSimple<Position<E>> l = new ListaPositionSimple<Position<E>> ();//To return. Lista Iterable con todos los nodos.
		positionsEnPreOrden (l, raiz);
		return l;
	}
	
	/*RECORRIDOS*/
	
	/**
	 * Recorre el �rbol en PreOrden, agregando como �ltimo elemento de la Lista l, al R�tulo del Nodo visitado.
	 * 
	 * La lista Lista l tendr� los r�tulos del Nodo que est� en la posici�n p,
	 * los r�tulos de los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los r�tulos de los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja.
	 * 
	 * @param l Lista donde agregar los r�tulos.
	 * @param p Posici�n del Nodo que contiene el r�tulo a agregar. 
	 */
	public void rotulosEnPreOrden (ListaPositionSimple<E> l, Position<E> p)
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(p);//Nodo-Padre que est� en p, para Listarlo junto a sus hijos.
		l.addLast(nodoPadreEnP.element());
		if (nodoPadreEnP.cantHijos() != 0)
		{
			for (NodoArbol<E> hijoAlistar: nodoPadreEnP.hijos())
				rotulosEnPreOrden(l, hijoAlistar);
		}
	}
	
	/**
	 * Recorre el �rbol en PreOrden, agregando como �ltimo elemento de la Lista l, el Nodo visitado.
	 * 
	 * La lista Lista l tendr� el Nodo que est� en la posici�n p,
	 * los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja.
	 * 
	 * @param l Lista donde agregar los NodoArbol.
	 * @param p Posici�n del Nodo a agregar. 
	 */
	public void positionsEnPreOrden (ListaPositionSimple<Position<E>> l, Position<E> p)
	{
		NodoArbol<E> nodoPadreEnP = validarPosicion(p);//Nodo-Padre que est� en p, para Listarlo junto a sus hijos.
		l.addLast(nodoPadreEnP);
		if (nodoPadreEnP.cantHijos() != 0)
		{
			for (Position<E> hijoAlistar: nodoPadreEnP.hijos())
				positionsEnPreOrden(l, hijoAlistar);
		}
	}
	
}