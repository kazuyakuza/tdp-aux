package ProyectoX.Librerias.TDAMapeo;

/**
 * Interface para un Mapeo.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public interface Mapeo<K,V>
{
	
	/*COMANDOS*/
	
	/**
	 * Añade una entrada [k,v] al Mapeo.
	 * Si el Mapeo ya contiene una entrada con una clave igual a k, entonces reemplaza el valor de dicha entrada por el nuevo valor v.
	 * 
	 * @param k Nueva clave.
	 * @param v Nuevo valor.
	 * @return Null: Entrada insertada normalmente.
	 *         Valor vV: viejo valor asociado a la clave k en el Mapeo.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public V insertar (K k, V v) throws ClaveInvalidaException;
	
	/**
	 * Elimina la entrada que tenga clave igual a k del Mapeo.
	 * 
	 * @param k Clave a eliminar.
	 * @return Valor de la entrada eliminada.
	 * @exception ClaveInvalidaException La clave ingresada k es null.
	 */
	public V eliminar (K k) throws ClaveInvalidaException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de entradas del Mapeo.
	 * 
	 * @return Cantidad de entradas del Mapeo.
	 */
	public int tamanio ();
	
	/**
	 * Verifica si el Mapeo está vacío, y devuelve el resultado.
	 * 
	 * @return True:  el Mapeo no tiene entradas.
	 *         False: el Mapeo tiene almenos una entrada.
	 */
	public boolean esVacio ();
	
	/**
	 * Busca la entrada de clave igual a k en el Mapeo, y devuelve el valor de la entrada.
	 * 
	 * @param k Clave de la entrada a buscar.
	 * @return Null: no existe una entrada en el Mapeo con clave igual a k.
	 *         Demas Casos: valor de la entrada con clave igual a k.
	 * @exception ClaveInvalidaException Si la clave ingresada es null.
	 */
	public V recuperar (K k) throws ClaveInvalidaException;
	
	/**
	 * Devuelve un colección iterable con las claves de las entradas del Mapeo.
	 * 
	 * @return colección iterable con las claves de las entradas del Mapeo.
	 */
	public Iterable<K> claves ();
	
	/**
	 * Devuelve un colección iterable con los valores de las entradas del Mapeo.
	 * 
	 * @return colección iterable con las valores de las entradas del Mapeo.
	 */
	public Iterable<V> valor ();
	
	/**
	 * Devuelve un colección iterable con las entradas del Mapeo.
	 * 
	 * @return colección iterable con las entradas del Mapeo.
	 */
	public Iterable<Entry<K,V>> entradas ();

}