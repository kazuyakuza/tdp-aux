package ProyectoX.Librerias.TDAMapeo;

/**
 * Entrada que implementa la interface Entry.
 * Representa un par [K,V], donde K es la clave de la entrada, y V es el valor de la entrada.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 * @param <K> Elemento Genérico que representa la clave de la entrada.
 * @param <V> Elemento Genérico que representa el valor de la entrada.
 */
public class Entrada<K,V> implements Entry<K,V>
{
	
	//Variables de Instancia.
	protected K clave;
	protected V valor;
	
	/**
	 * Crea una Entrada con clave k y valor v.
	 * 
	 * @param k Clave de la nueva entrada.
	 * @param v Valor de la nueva entrada.
	 */
	public Entrada (K k, V v)
	{
		clave = k;
		valor = v;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia la clave de la entrada por la nueva clave k.
	 * 
	 * @param k Nueva clave.
	 */
	public void clave (K k)
	{
		clave = k;
	}
	
	/**
	 * Cambia el valor de la entrada por el nuevo valor v.
	 * 
	 * @param v Nuevo valor.
	 */
	public void valor (V v)
	{
		valor = v;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la clave de la entrada.
	 * 
	 * @return La clave de la entrada.
	 */
	public K getKey ()
	{
		return clave;
	}
	
	/**
	 * Devuelve el valor de la entrada.
	 * 
	 * @return El valor de la entrada.
	 */
	public V getValue ()
	{
		return valor;
	}

}