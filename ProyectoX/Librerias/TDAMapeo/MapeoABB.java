package ProyectoX.Librerias.TDAMapeo;

import ProyectoX.Librerias.TDAArbol.ArbolVacioException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PosicionInvalidaException;

/**
 * Clase MapeoABB que implementa la interface Mapeo.
 * 
 * Mapeo implementado con un Arbol Binario de Búsqueda.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class MapeoABB<K,V> implements Mapeo<K,V>
{
	
	//Variables de Instancia
	protected ABB<K,V> abb;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Mapeo vacío.
	 */
	public MapeoABB ()
	{
		abb = new ABB<K,V> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Añade una entrada [k,v] al Mapeo.
	 * Si el Mapeo ya contiene una entrada con una clave igual a k, 	 * entonces reemplaza el valor de dicha entrada por el nuevo valor v.
	 * 
	 * @param k Nueva clave.
	 * @param v Nuevo valor.
	 * @return Null: Entrada insertada normalmente.
	 *         Valor vV: viejo valor asociado a la clave k en el Mapeo.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public V insertar (K k, V v) throws ClaveInvalidaException
	{
		return abb.insertar(k,v);
	}
	
	/**
	 * Elimina la entrada que tenga clave igual a k del Mapeo.
	 * 
	 * @param k Clave a eliminar.
	 * @return Valor de la entrada eliminada.
	 * @exception ClaveInvalidaException La clave ingresada k es null.
	 */
	public V eliminar (K k) throws ClaveInvalidaException
	{
		return abb.eliminar(k).getValue();
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de entradas del Mapeo.
	 * 
	 * @return Cantidad de entradas del Mapeo.
	 */
	public int tamanio ()
	{
		return abb.size();
	}
	
	/**
	 * Verifica si el Mapeo está vacío, y devuelve el resultado.
	 * 
	 * @return True:  el Mapeo no tiene entradas.
	 *         False: el Mapeo tiene almenos una entrada.
	 */
	public boolean esVacio ()
	{
		return abb.isEmpty();
	}
	
	/**
	 * Busca la entrada de clave igual a k en el Mapeo, y devuelve el valor de la entrada.
	 * 
	 * @param k Clave de la entrada a buscar.
	 * @return Null: no existe una entrada en el Mapeo con clave igual a k.
	 *         Demas Casos: valor de la entrada con clave igual a k.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public V recuperar (K k) throws ClaveInvalidaException
	{
		return abb.buscar(k).getValue();
	}
	
	/**
	 * Devuelve un colección iterable con las claves de las entradas del Mapeo.
	 * 
	 * @return colección iterable con las claves de las entradas del Mapeo.
	 */
	public Iterable<K> claves ()
	{
		ListaPositionSimple<K> listaClaves = new ListaPositionSimple<K> ();
		for (Entry<K,V> entrada: abb.entradas())
			listaClaves.addLast(entrada.getKey());
		return listaClaves;
	}
	
	/**
	 * Devuelve un colección iterable con los valores de las entradas del Mapeo.
	 * 
	 * @return colección iterable con las valores de las entradas del Mapeo.
	 */
	public Iterable<V> valor ()
	{
		ListaPositionSimple<V> listaValores = new ListaPositionSimple<V> ();
		for (Entry<K,V> entrada: abb.entradas())
			listaValores.addLast(entrada.getValue());
		return listaValores;
	}
	
	/**
	 * Devuelve un colección iterable con las entradas del Mapeo.
	 * 
	 * @return colección iterable con las entradas del Mapeo.
	 */
	public Iterable<Entry<K,V>> entradas () throws ArbolVacioException, PosicionInvalidaException
	{
		return abb.entradas();
	}

}