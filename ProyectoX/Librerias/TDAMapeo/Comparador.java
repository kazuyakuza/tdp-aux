package ProyectoX.Librerias.TDAMapeo;

import java.util.Comparator;

/**
 * Comparador basado en un orden natural.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.0
 */
public class Comparador<E> implements Comparator<E>
{
	
	/**
	 * Comparar: Compara el elemento Genérico e1 con el elemento Genérico e y devuelve el resultado de la comparación.
	 * 
	 * @param e1 Elemento a comparar con e2.
	 * @param e2 Elemento a comparar con e1.
	 * @return Entero Negativo: e1 es menor a e2.
	 *         Cero: e1 es igual a e2.
	 *         Entero Positivo: e1 es mayor a e2.
	 * @exception ClassCastException: Si el tipo de objeto a comparar no implementan la interface comparable.
	 */
	@SuppressWarnings("unchecked")
	public int compare(E e1, E e2) throws ClassCastException
	{
		return ((Comparable<E>) e1).compareTo(e2);
	}

}