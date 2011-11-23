package ProyectoX.Librerias.TDAMapeo;

import ProyectoX.Librerias.TDAArbol.ArbolVacioException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.ViolacionLimiteException;

/**
 * Arbol Binario de B�squeda implementado con NodosArbolB con referencia a su Nodo-Hijo derecho, Nodo-Hijo izquierdo, referencia a su Nodo-Padre y r�tulo de elemento de tipo Entrada<K,V.}
 * El �rbol Binario es un �rbol propio, donde cada Nodo-Interno aloja una entrada [K,V] y los Nodo-Hojas no guardan elementos.
 * 
 * Contiene 2 Constructores:
 *  + Arbol vac�o.
 *  + Arbol con Raiz de r�tulo [K,V].
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class ABB<K,V>
{
	
	//Variables de Instancia
	protected PositionBT<Entry<K,V>> raiz;
	protected int cantElementos;
	protected Comparador<K> c;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un �rbol Binario de B�squeda vac�o. 
	 */
	public ABB ()
	{
		raiz = new NodoArbolB<Entry<K,V>> (null);
		cantElementos = 0;
		c = new Comparador<K> ();
	}
	
	/**
	 * Crea un �rbol Binario de B�squeda con un ra�z que tiene como r�tulo la entrada [l,v].
	 * La ra�z tendr� un Nodo-Hijo izquierdo y un Nodo-Hijo derecho, con r�tulos null.
	 * Se crea el comparador utilizando la clave k.
	 * 
	 * @param k Clave de la Entrada del R�tulo de la ra�z del nuevo ABB.
	 * @param v Valor de la Entrada del R�tulo de la ra�z del nuevo ABB.
	 */
	public ABB (K k, V v)
	{
		raiz = new NodoArbolB<Entry<K,V>> (new Entrada<K,V> (k,v));
		raiz.setLeft (new NodoArbolB<Entry<K,V>> (raiz, null));
		raiz.setRight (new NodoArbolB<Entry<K,V>> (raiz, null));
		cantElementos = 1;
		c = new Comparador<K> ();
	}
	
	/*COMANDOS*/
	
	/*De �rbol*/
	
	/**
	 * Reemplaza la Entrada del NodoArbol que se encuentra en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @param e Nueva Entrada.
	 * @return Entrada reemplazada.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception EntradaInvalidaException Si la Entrada es null. Si la entrada no corresponde al tipo de entradas del �rbol.
	 */
    public Entry<K,V> replace (Position<Entry<K,V>> p, Entry<K,V> e) throws PosicionInvalidaException, EntradaInvalidaException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion(p);
		validarEntrada(e);
		Entry<K,V> viejaEntrada = nodoBenP.element();//To return.
		nodoBenP.setElement(e);
		return viejaEntrada;
	}
	
	/**
	 * Validar Posici�n: Verifica si la posici�n pasada por par�metro es correcta, y devuelve el NodoArbolB perteneciente a esa posici�n.
	 * 
	 * @param p Posici�n a verificar.
	 * @return La PositionBT resultante al castear la posici�n verificada.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	private PositionBT<Entry<K,V>> validarPosicion (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		if (p == null)
			throw new PosicionInvalidaException ("La posici�n a la que esta intentando acceder es null.");
		try
		{
			PositionBT<Entry<K,V>> r = (PositionBT<Entry<K,V>>) p; //To return.
			return r;
		}
		catch (ClassCastException e)
		{
			throw new PosicionInvalidaException("La posici�n a la que esta intentando acceder es de un tipo de posicion incorrecta."); 
		}
	}
	
	/*De �rbol Binario de B�squeda*/
	
	/**
	 * Inserta una entrada [k,v] en el �rbol Binario de B�squeda.
	 * Si existe un NodoArbol con r�tulo de entrada con clave igual a k, entonces cambia el valor de esa entrada por v.
	 * Si no existe un NodoArbol con r�tulo de entrada con clave igual a k, entonces inserta la entrada en un Nodo-Hoja, creando sus hijos vac�os.
	 * 
	 * @param k Clave de la entrada a insertar.
	 * @param v Valor de la entrada a insertar.
	 * @return Null: se insert� la entrada en un Nodo-Hoja, creando sus respectivos hijos.
	 *         Demas Casos: el viejo valor de la entrada con clave k encontrado.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public V insertar (K k, V v) throws ClaveInvalidaException
	{
		if (k == null)
			throw new ClaveInvalidaException ("La clave que est� intentando utilizar es null.");
		PositionBT<Entry<K,V>> pos = (PositionBT<Entry<K,V>>) buscar(k, raiz);//Busco si existe un NodoArbol con r�tulo con entrada de clave k.
		//pos guarda la posici�n de un NodoArbol con entrada de clave k o sin entrada.
		V r = null;//To Return.
		if (pos.element() != null)
		{
			Entrada<K,V> entradaEnPos = (Entrada<K,V>) pos.element();
			r = entradaEnPos.getValue();
			entradaEnPos.valor(v);
		}
		else
		{
			pos.setElement(new Entrada<K,V> (k,v));
			pos.setLeft (new NodoArbolB<Entry<K,V>> (pos, null));
			pos.setRight (new NodoArbolB<Entry<K,V>> (pos, null));
			cantElementos++;
		}
		return r;
	}
	
	/**
	 * Busca el NodoArbol de entrada de clave k.
	 * Si no lo encuentra:
	 * + pos es un Nodo-Hoja con entrada null, y el m�todo devuelve null.
	 * Si lo encuentra:
	 * + elimina la entrada.
	 * + acomoda las entradas del subArbol que tiene como ra�z el NodoArbol que ten�a la entrada eliminada,
	 *   de modo tal que se convierta a Nodo-Hoja un NodoArbol que tenga Nodo-Hijos hojas.
	 * + devuelve la entrada eliminada.
	 * 
	 * @param k Clave a buscar.
	 * @return Null: no existe Entrada con clave igual a k en el �rbol. 
	 *         Demas Casos: Entrada de clave igual a k eliminada.
	 * @exception ClaveInvalidaException La clave ingresada k es null.
	 * @exception ArbolVacioException Si se intenta eliminar en un �rbol vac�o.
	 */
	public Entry<K,V> eliminar (K k) throws ClaveInvalidaException, ArbolVacioException
	{
		if (isEmpty())
			throw new ArbolVacioException ("El �rbol que est� intentando acceder est� vac�o.");
		if (k == null)
			throw new ClaveInvalidaException ("La clave que est� intentando utilizar es null.");
		//Busco si existe un NodoArbol con r�tulo con entrada de clave k.
		PositionBT<Entry<K,V>> pos = (PositionBT<Entry<K,V>>) buscar(k, raiz);
		//pos guarda la posici�n de un NodoArbol con entrada de clave k o sin entrada.
		Entry<K,V> r = pos.element();//To return.
		if (r != null)
		{
			convertirEnHoja (pos);//Acomodar el NodoArbol sin entrada como Hoja.
			cantElementos--;
		}
		return r;
	}
	
	/**
	 * + si el NodoArbol que est� en la posici�n p tiene Nodo-Hijos hojas:
	 *   1) elimina los Nodo-Hojas 2) convierte en Nodo-Hoja al NodoArbol que est� en la posici�n p
	 * + si el NodoArbol que est� en la posici�n p tiene Nodo-Hijo izquierdo interno:
	 *   1) busca la entrada m�xima del subArbol izquierdo del NodoArbol que est� en la posici�n p
	 *   2) mueve la entrada encontrada al NodoArbol que est� en la posici�n p
	 *   3) convierte en Nodo-Hoja al NodoArbol que ten�a la entrada movida
	 * + sino, si el NodoArbol que est� en la posici�n p tiene Nodo-Hijo derecho interno:
	 *   1) busca la entrada m�nima del subArbol derecho del NodoArbol que est� en la posici�n p
	 *   2) mueve la entrada encontrada al NodoArbol que est� en la posici�n p
	 *   3) convierte en Nodo-Hoja al NodoArbol que ten�a la entrada movida
	 * 
	 * Convertir un nodo en hoja = dejar al nodo sin hijos y sin r�tulo.
	 *   
	 * @param p NodoArbol a convertir en hoja.
	 */
	private void convertirEnHoja (Position<Entry<K,V>> p)
	{
		NodoArbolB<Entry<K,V>> nodoBenP = (NodoArbolB<Entry<K, V>>) p;//NodoArbol a convetir en hoja. Este NodoArbol ya no tiene r�tulo (entrada).
		if (! isExternal(left(nodoBenP)))//Si nodoBenP tiene Nodo-Hijo izquierdo interno.
		{//Busco Pr�ximo NodoArbol sin entrada a ser hoja.
			Position<Entry<K,V>> proxHoja = mayor(left(nodoBenP));//NodoArbol con la entrada mayor del subArbol izquierdo del NodoArbol pos.
			Entry<K,V> entradaAmover = proxHoja.element();//Entrada mayor del subArbol izquierdo del NodoArbol pos. Reemplazar� el lugar de la entrada eliminada.
			replace(nodoBenP, entradaAmover);//Mover entrada mayor encontrada al NodoArbol de la entrada eliminada.
			convertirEnHoja (proxHoja);//Convierto en Nodo-Hoja al NodoArbol al que se le extrajo la entrada.
		}
		else
			if (! isExternal(right(nodoBenP)))//Si nodoBenP tiene Nodo-Hijo derecho interno.
			{//Busco Pr�ximo NodoArbol sin entrada a ser hoja.
				Position<Entry<K,V>> proxHoja = menor(right(nodoBenP));//NodoArbol con la entrada menor del subArbol derecho del NodoArbol pos.
				Entry<K,V> entradaAmover = proxHoja.element();//Entrada menor del subArbol derecho del NodoArbol pos. Reemplazar� el lugar de la entrada eliminada.
				replace(nodoBenP, entradaAmover);//Mover entrada menor encontrada al NodoArbol de la entrada eliminada.
				convertirEnHoja (proxHoja);//Convierto en Nodo-Hoja al NodoArbol al que se le extrajo la entrada.
			}
			else
			{//nodoBenP tiene Nodo-Hijos hojas, entonces lo convierto a hoja, eliminando sus hijos.
				nodoBenP.getLeft().setParent(null);//Limpio el Nodo-Hijo izquierdo.
				nodoBenP.getRight().setParent(null);//Limpio el Nodo-Hijo derecho.
				nodoBenP.limpiarMenosPadre();//Convierto en Nodo-Hoja a nodoBenP.
			}
	}
	
	/**
	 * Verifica si la Entrada e es correcta.
	 *
	 * @param e Entrada a verificar.
	 * @exception EntradaInvalidaException Si la Entrada es null. Si la entrada no corresponde al tipo de entradas del �rbol.
	 */
	private void validarEntrada (Entry<K,V> e) throws EntradaInvalidaException
	{
		if (e == null)
			throw new EntradaInvalidaException ("La entrada ingresada es null.");
		if (!(e instanceof Entrada))
			throw new EntradaInvalidaException ("La entrada es inv�lida. No corresponde al tipo de entradas de este �rbol.");
	}
	
	/*CONSULTAS*/
	
	/*De �rbol*/
	
	/**
	 * Ra�z: Devuelve la Ra�z del �rbol actual.
	 * 
	 * @return Ra�z del �rbol.
	 * @exception ArbolVacioException Si se pide la Ra�z de un �rbol vac�o.
	 */
	public Position<Entry<K,V>> root () throws ArbolVacioException
	{
		if (cantElementos == 0)
			throw new ArbolVacioException ("El �rbol binario al que est� intentando acceder est� vac�o.");
		return raiz;
	}
	
	/**
	 * Tama�o: Devuelve la cantidad de elementos en el �rbol.
	 * 
	 * @return Cantidad de elementos en el Arbol.
	 */
	public int size ()
	{
		return cantElementos;
	}
	
	/**
	 * Est� Vac�o: Verifica si el �rbol est� vac�o y devuelve el resultado.
	 * 
	 * @return True:  el �rbol no contiene elementos.
	 *         False: el �rbol contiene almenos el elemento Ra�z.
	 */
	public boolean isEmpty ()
	{
		return (cantElementos == 0);
	}
	
	/**
	 * Devuelve una colecci�n iterable con los elementos guardados en el �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los elementos guardados en el �rbol.
	 * @exception ArbolVacioException Si se pide un iterador de un �rbol vac�o.
	 */
	public Iterable<Entry<K,V>> entradas () throws ArbolVacioException, PosicionInvalidaException
	{
		if (cantElementos == 0)
			throw new ArbolVacioException ("El �rbol binario al que est� intentando acceder est� vac�o.");
		ListaPositionSimple<Entry<K,V>> listaEntradas = new ListaPositionSimple<Entry<K,V>> ();
		rotulosEnPreOrden (listaEntradas, raiz);
		return listaEntradas;
	}
	
	/**
	 * Recorre el �rbol Binario en PreOrden, agregando como �ltimo elemento de la Lista l, al R�tulo del Nodo visitado.
	 * 
	 * La lista Lista l tendr� los r�tulos del Nodo que est� en la posici�n p,
	 * los r�tulos de los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los r�tulos de los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja (que no son agregados).
	 * 
	 * @param l Lista donde agregar los r�tulos.
	 * @param p Posici�n del Nodo que contiene el r�tulo a agregar. 
	 */
	public void rotulosEnPreOrden (ListaPositionSimple<Entry<K,V>> l, Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion (p);
		if (nodoBenP.element() != null)
		{
			l.addLast(nodoBenP.element());
			if (hasLeft(nodoBenP) && (! isExternal(left(nodoBenP))))
				rotulosEnPreOrden (l, nodoBenP.getLeft());
			if (hasRight(nodoBenP) && (! isExternal(right(nodoBenP))))
				rotulosEnPreOrden (l, nodoBenP.getRight());
		}
	}
	
	/**
	 * Devuelve una colecci�n iterable con los NodoArbol del �rbol.
	 * 
	 * El iterador es construido Recorriendo el �rbol en PreOrden.
	 * 
	 * @return Colecci�n iterable con los NodoArbol del �rbol.
	 * @exception ArbolVacioException Si se pide las posiciones de un �rbol vac�o.
	 */
	public Iterable<Position<Entry<K,V>>> positions () throws ArbolVacioException
	{
		if (cantElementos == 0)
			throw new ArbolVacioException ("El �rbol binario al que est� intentando acceder est� vac�o.");
		ListaPositionSimple<Position<Entry<K,V>>> listaPosiciones = new ListaPositionSimple<Position<Entry<K,V>>> ();
		positionsEnPreOrden (listaPosiciones, raiz);
		return listaPosiciones;
	}
	
	/**
	 * Recorre el �rbol en PreOrden, agregando como �ltimo elemento de la Lista l, el Nodo visitado.
	 * 
	 * La lista Lista l tendr� el Nodo que est� en la posici�n p,
	 * los Nodo-Hijos de su Lista de Hijos del nodo en p,
	 * los Nodo-Hijos de los Hijos del Nodo p,
	 * hasta llegar a los NodosHoja (que no son agregados).
	 * 
	 * @param l Lista donde agregar los NodoArbol.
	 * @param p Posici�n del Nodo a agregar. 
	 */
	public void positionsEnPreOrden (ListaPositionSimple<Position<Entry<K,V>>> l, Position<Entry<K,V>> p)
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion (p);
		if (nodoBenP.element() != null)
		{
			l.addLast(nodoBenP);
			if (hasLeft(nodoBenP) && (! isExternal(left(nodoBenP))))
				positionsEnPreOrden (l, nodoBenP.getLeft());
			if (hasRight(nodoBenP) && (! isExternal(right(nodoBenP))))
				positionsEnPreOrden (l, nodoBenP.getRight());
		}
	}
	
	/**
	 * Padre: devuelve el padre del NodoArbol que est� en la Posici�n p.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return Padre del NodoArbol en la Posici�n p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si se consulta por el Nodo-Padre de la Raiz. 
	 */
	public Position<Entry<K,V>> parent (Position<Entry<K,V>> p) throws PosicionInvalidaException, ViolacionLimiteException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion (p);
		if (nodoBenP == raiz)
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder es una ra�z. La ra�z no tiene padre.");
		return nodoBenP.getParent();
	}
	
	/**
	 * Hijos: Devuelve una colecci�n iterable de la Lista de Hijos del Nodo-Padre que est� en la posici�n p.
	 * 
	 * @param p Posici�n del Nodo-Padre.
	 * @return Iterador con la Lista de Hijos.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public Iterable<Position<Entry<K,V>>> children (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion (p);
		if (nodoBenP.element() == null)
			throw new PosicionInvalidaException ("El nodo al que est� intentando acceder es un nodo hoja. Los nodos hoja no tienen hijos.");
		ListaPositionSimple<Position<Entry<K,V>>> listaHijos = new ListaPositionSimple<Position<Entry<K,V>>> ();
		if (hasLeft(nodoBenP) && (! isExternal(left(nodoBenP))))
			listaHijos.addLast(nodoBenP.getLeft());
		if (hasRight(nodoBenP) && (! isExternal(right(nodoBenP))))
			listaHijos.addLast(nodoBenP.getRight());
		return listaHijos;
	}
	
	/*De �rbol Binario*/
	
	/**
	 * Es Interno: Verifica si el NodoArbol que est� en la posici�n p es interno y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p tiene hijos.
	 *         False: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isInternal (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		PositionBT<Entry<K,V>> nodoEnP = validarPosicion(p);
		return ((hasLeft(nodoEnP)) || (hasRight(nodoEnP)));
	}
	
	/**
	 * Es Externo: Verifica si el NodoArbol que est� en la posici�n p es externo y devuelce el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene hijos.
	 *         Falso: caso contrario.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isExternal (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion(p);
		return ((! hasLeft(nodoBenP)) && (! hasRight(nodoBenP)));
	}
	
	/**
	 * Es Ra�z: Verifica si el NodoArbol que est� en la posici�n p es ra�z y devuelve el resultado.
	 * 
	 * @param p Posici�n del NodoArbol.
	 * @return True:  el NodoArbol en p no tiene Nodo-Padre.
	 *         False: el NodoArbol en p tiene Nodo-Padre.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 */
	public boolean isRoot (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		PositionBT<Entry<K,V>> nodoBenP = validarPosicion(p);
		return (nodoBenP == raiz);
	}
	
	/**
	 * Izquierdo: Devuelve el Nodo-Hijo izquierdo del NodoArbolB en p.
	 * 
	 * @param p Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo izquierdo.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo izquierdo.
	 * @return Posici�n del Nodo-Hijo izquierdo.
	 */
	public Position<Entry<K,V>> left (Position<Entry<K,V>> p) throws PosicionInvalidaException,ViolacionLimiteException
	{
		Position<Entry<K,V>> hIzquierdo = validarPosicion(p).getLeft();
		if (hIzquierdo == null)
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder no tiene hijo izquierdo.");
		return hIzquierdo;
	}
	
	/**
	 * Derecho: Devuelve el Nodo-Hijo derecho del NodoArbolB en p.
	 * 
	 * @param p Posici�n del Nodo-Padre al que buscarle su Nodo-Hijo derecho.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @exception ViolacionLimiteException Si el Nodo-Padre no tiene Nodo-Hijo derecho.
	 */
	public Position<Entry<K,V>> right (Position<Entry<K,V>> p) throws PosicionInvalidaException,ViolacionLimiteException
	{
		Position<Entry<K,V>> hDerecho = validarPosicion(p).getLeft();
		if (hDerecho == null)
			throw new ViolacionLimiteException ("El nodo al que est� intentando acceder no tiene hijo derecho.");
		return hDerecho;
	}
	
	/**
	 * Tiene Izquierdo: Verifica si el NodoArbolB en p tiene Nodo-Hijo izquierdo, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo izquierdo.
	 *         False: caso contrario.
	 */
	public boolean hasLeft (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		return (validarPosicion(p).getLeft() != null);
	}
	
	/**
	 * Tiene Derecho: Verifica si el NodoArbolB en p tiene Nodo-Hijo derecho, y devuelve el resultado.
	 * 
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta.
	 * @return True:  el NodoArbolB en p tiene Nodo-Hijo derecho.
	 *         False: caso contrario.
	 */
	public boolean hasRight (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		return (validarPosicion(p).getRight() != null);
	}
	
	/*De �rbol Binario de B�squeda*/
	
	/**
	 * Busca la primera entrada con clave igual a k, en los r�tulos de los NodoArbol del �rbol partiendo de la ra�z del mismo, y devuelve la entrada.
	 * 
	 * @param k Clave a buscar.
	 * @return null si no existe entrada con clave igual a k.
	 *         Primera entrada con clave igual a k encontrada.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public Entry<K,V> buscar (K k) throws ClaveInvalidaException
	{
		if (k == null)
			throw new ClaveInvalidaException ("La clave que est� intentando utilizar es null.");
		Position<Entry<K,V>> posK = buscar (k, raiz);
		if (isInternal(posK))
			return posK.element();
		return null;
	}
	
	/**
	 * Verifica si el NodoArbol que est� en la posici�n p tiene como r�tulo una Entrada de clave igual a k y:
	 * -si es igual, devuelve la posici�n del NodoArbol.
	 * -si es menor, busca en su subArbol izquierdo.
	 * -si es mayor, busca en su subArbol derecho.
	 * 
	 * @param k Clave a buscar.
	 * @param p Posici�n a verificar.
	 * @return Nodo-Hoja: no hay NodoArbol con r�tulo de entrada de clave k.
	 *         Demas Casos: posici�n del NodoArbol con r�tulo de entrada de clave k.
	 */
	private Position<Entry<K,V>> buscar (K k, Position<Entry<K,V>> p)
	{
		Position<Entry<K,V>> r = p;//To return.
		if (! isExternal(p))
		{//Comparo la clave que estoy buscando con la clave del nodo que est� en la posici�n p.
			int comparacion = c.compare(k, p.element().getKey());
			if (comparacion < 0)//Si la clave es mayor a la que estoy buscando, busco en su subarbol izquierdo.
				 r = buscar (k, left(p));
			if (comparacion > 0)//Si la clave es menor a la que estoy buscando, busco en su subarbol derecho.
				 r = buscar (k, right(p));
		//Si r = null -> No existe Nodo con r�tulo de entrada con clave k.
		//Si r = p -> La clave del nodo en p es igual a la clave que busco, devuelve p.
		}
		return r;
	}
	
	/**
	 * Busca el NodoArbol de menor entrada desde el NodoArbol que est� en la posici�n p.
	 * 
	 * @param p Posici�n inicial de la b�squeda.
	 * @return NodoArbol de menor entrada, partiendo desde el NodoArbol que est� en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	public Position<Entry<K,V>> menor (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		Position<Entry<K,V>> min = validarPosicion (p);//To Return.
		while (hasLeft(min))
			min = left(min);
		return parent(min);
	}
	
	/**
	 * Busca el NodoArbol de mayor entrada desde el NodoArbol que est� en la posici�n p.
	 * 
	 * @param p Posici�n inicial de la b�squeda.
	 * @return NodoArbol de mayor entrada, partiendo desde el NodoArbol que est� en p.
	 * @exception PosicionInvalidaException Si la posici�n es nula o incorrecta. 
	 */
	public Position<Entry<K,V>> mayor (Position<Entry<K,V>> p) throws PosicionInvalidaException
	{
		Position<Entry<K,V>> max = validarPosicion (p);//To Return.
		while (hasRight(max))
			max = right(max);
		return parent(max);
	}

}