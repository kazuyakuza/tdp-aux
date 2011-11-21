package ProyectoX.Librerias.TDAMapeo;

/**
 * Interface para un par [clave,entrada].
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 * @param <K> Elemento Gen�rico que representa la clave de la entrada.
 * @param <V> Elemento Gen�rico que representa el valor de la entrada.
 */
public interface Entry<K,V>
{
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la clave de la entrada.
	 * 
	 * @return La clave de la entrada.
	 */
	public K getKey ();
	
	/**
	 * Devuelve el valor de la entrada.
	 * 
	 * @return El valor de la entrada.
	 */
	public V getValue ();

}