package ProyectoX.Librerias.TDACola;

/**
 * Clase Cola que implementa la interface Queue.
 * 
 * Cola implementada con un arreglo circular de tipo de dato Gen�rico.
 * 
 * Una Cola vac�a es un arreglo con sus componentes en null,
 * y la cabeza y cola tienen valor -1.
 * 
 * Por convenci�n, si no se especifica el tama�o m�ximo de la Cola al llamar al constructor,
 * se toma como tama�o m�ximo base un valor de 20 componentes.
 * 
 * Aclaraci�n: al TDACola se lo menciona con la palabra "Cola", y al final del TDACola se lo menciona con "cola", con "c" min�scula. 
 * 
 * Contiene 3 Constructores:
 *  + Cola Vac�a, con arreglo de tama�o de 20 componentes.
 *  + Cola Vac�a, con arreglo de tama�o de t componentes pasado por par�metro.
 *  + Cola con 1 elemento Gen�rico, con arreglo de tama�o de 20 componentes.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @version 1.1
 * @param <E>
 */
public class Cola<E> implements Queue<E>
{
	
	//Variables de Instancia
	private E[] arreglo; //Arreglo Gen�rico usado para implementar la Cola.
	private final int tamanioMaxBase = 20; //Convenci�n de tama�o m�ximo base de una Cola vac�a.
	private int cabeza, cola; //cabeza: primer elemento en entrar en la Cola (Ser� el primero en salir).
	                          //cola: �ltimo elemento en entrar en la Cola (Ser� el �ltimo en salir).
	private int cantElementos; //Cantidad actual de elementos en la Cola.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Cola vac�a con un arreglo de Tama�o M�ximo Base (20).
	 */
	@SuppressWarnings("unchecked")
	public Cola ()
	{
		arreglo = (E[]) new Object[tamanioMaxBase];
		cabeza = cola = -1;
		cantElementos = 0;
	}
	
	/**
	 * Crea una Cola vac�a con un arreglo de Tama�o t.
	 * 
	 * @param t Tama�o M�ximo de la Cola. 
	 */
	@SuppressWarnings("unchecked")
	public Cola (int t)
	{
		arreglo = (E[]) new Object[t];
		cabeza = cola = -1;
		cantElementos = 0;
	}
	
	/**
	 * Crea una Cola con 1 elemento e, con un arreglo de Tama�o M�ximo Base (20).
	 * 
	 * @param e Elemento que estar� en la cola.
	 */
	@SuppressWarnings("unchecked")
	public Cola (E e)
	{
		arreglo = (E[]) new Object[tamanioMaxBase];
		arreglo[0] = e;
		cabeza = cola = 0;
		cantElementos = 1;
	}
	
	/*COMANDOS*/
	
	/**
	 * Encolar: Inserta un elemento en el final de la Cola.
	 * 
	 * Si la Cola est� llena, se crea un nuevo arreglo con el doble de tama�o,
	 * y se pasan los elementos al nuevo arreglo, incluyendo el elemento que se quiere insertar.
	 * 
	 * @param e Elemento que ser� insertado.
	 */
	public void enqueue (E e)
	{
		if (cantElementos == 0)
		{
			arreglo[++cabeza] = e;
			cola++;
		}
		else
			if (cantElementos == arreglo.length)
				duplicarTamanio(e);
			else
			{
				if (cola == (arreglo.length - 1))
					cola = -1;
				arreglo[++cola] = e;
			}
		cantElementos++;
	}
	
	/**
	 * Desencolar: Remueve el elemento en la cabeza de la Cola.
	 * 
	 * Si la Cola est� vac�a, dispara una excepci�n.
	 * 
	 * @return Elemento removido de la Cola.
	 * @exception ColaVaciaException Si la Cola est� vac�a.
	 */
	public E dequeue () throws ColaVaciaException
	{
		if (cantElementos == 0)
			throw new ColaVaciaException ("La Cola a la que esta intentando acceder est� vac�a.");
		E r = arreglo[cabeza]; //To return. Elemento en la cabeza de la Cola que es desencolado.
		arreglo[cabeza] = null;
		cantElementos--;
		if (cantElementos == 0)
		{
			cabeza = -1;
			cola = -1;
		}
		else
			if (cabeza == (arreglo.length - 1))
				cabeza = 0;
			else cabeza++;
		return r;
	}
	
	/**
	 * Duplicar Tama�o: M�todo soporte para el m�todo enqueue.
	 * 
	 * Crea un nuevo arreglo del doble de tama�o que el arreglo original de la Cola actual.
	 * Copia los enlaces a los elementos del arreglo original al arreglo nuevo, tomando como primer
	 * elemento a copiar la cabeza de la Cola, y como �ltimo elemento a copiar, la cola de la Cola.
	 * Luego agrega el elemento Gen�rico e al nuevo arreglo, y actualiza Cola con el nuevo arreglo.
	 * 
	 * @param e Elemento que ser� insertado.
	 */
	@SuppressWarnings("unchecked")
	private void duplicarTamanio (E e)
	{
		E[] nuevoArreglo = (E[]) new Object[arreglo.length*2];
		int j = 0;
		for (int i=cabeza; i!=cola; i++, j++)
			//Con i se lee el arreglo original.
			//Con j se copian los elementos en el nuevo arreglo.
			//Se declara j afuera del for para luego poder actualizar cola.
		{
			nuevoArreglo[j] = arreglo[i];
			if (i == arreglo.length)
				i = 0;
		}
		//Agrego cola del arreglo original al nuevo arreglo.
		nuevoArreglo[j] = arreglo[cola];
		//Actualizo cabeza y cola de la Cola.
		cabeza = 0;
		cola = j;
		//Actualizo el enlace al arreglo.
		arreglo = nuevoArreglo;
		//Agrego elemento e.
		arreglo[++cola] = e;
		//El incremento de cantidad de elementos se hace en el m�todo enqueue().
	}
	
	/*CONSULTAS*/
	
	/**
	 * Tama�o: Devuelve la cantidad actual de elementos en la Cola.
	 * 
	 * @return Cantidad actual de elementos en la Cola.
	 */
	public int size ()
	{
		return cantElementos;
	}
	
	/**
	 * Vac�a: Verifica si la Cola esta vac�a, y devuelve el restultado.
	 * 
	 * @return True:  si la Cola no contiene elementos.
	 *         False: la Cola contiene almenos 1 elemento.
	 */
	public boolean isEmpty ()
	{
		return (cantElementos == 0);
	}
	
	/**
	 * Cabeza: Devuelve el elemento en la Cabeza de la Cola.
	 * 
	 * @return Elemento en la cabeza de la Cola.
	 * @exception ColaVaciaException Si la Cola no contiene elementos.
	 */
	public E front () throws ColaVaciaException
	{
		if (cantElementos == 0)
			throw new ColaVaciaException ("La Cola a la que est� intentando acceder est� vac�a.");
		return arreglo[cabeza];
	}
	
	/**
	 * Devuelve una cadena con los elementos contenidos en la Cola.
	 * La cadena organiza de izquierda a derecha la Cola.
	 * El primer elemento a izquierda en la cadena ser� la cola de la Cola (�ltimo elemento).
	 * El �ltimo elemento a derecha en la cadena ser� la cabeza de la Cola (primer elemento).
	 * Los elementos ser�n agregados al String usando el m�todo toString de los mismos.
	 * 
	 * @return Cadena con los elementos contenido en la Cola.
	 */
	public String toString ()
	{
		String r = ""; //To return.
		if (cantElementos != 0)
			r += "[cola]";
		for (int i=cola, j=0; i!=cabeza; i--, j++)
		{
			r += arreglo[i].toString();
			if (j < cantElementos)
				r += "|-->|";
			if (i == arreglo.length)
				i = 0;
		}
		//Agrego cabeza de la Cola.
		r += arreglo[cabeza] + "[cabeza]";
		return r;
	}
	
	/**
	 * Devuelve la cabeza de la Cola.
	 * 
	 * Si la Cola est� vac�a, dispara una excepci�n.
	 * 
	 * @return Cabeza de la Cola.
	 * @exception ColaVaciaException Si la Cola est� vac�a.
	 */
	public E cabeza () throws ColaVaciaException
	{
		if (cantElementos == 0)
			throw new ColaVaciaException ("La Cola a la que est� intentando acceder est� vac�a.");
		return front();
	}
	
	/**
	 * Devuelve la cola de la Cola.
	 * 
	 * Si la Cola est� vac�a, dispara una excepci�n.
	 * 
	 * @return cola de la Cola.
	 * @exception ColaVaciaException Si la Cola est� vac�a.
	 */
	public E cola () throws ColaVaciaException
	{
		if (cantElementos == 0)
			throw new ColaVaciaException ("La Cola a la que est� intentando acceder est� vac�a.");
		return arreglo[cola];
	}

}