package ProyectoX.Librerias.Threads;

import ProyectoX.Excepciones.BoundaryViolationException;
import ProyectoX.Librerias.TDAColaConPrioridad.ColaConPrioridadConHeap;
import ProyectoX.Librerias.TDAColaConPrioridad.PriorityQueue;

/**
 * Contiene Worker que deben ser ejecutados.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class UpNeeder
{
	
	//Atributos de Instancia
	private PriorityQueue<Integer,Worker> workers;//Cola Con Prioridad de Workers.
	private boolean[] prioridades; //Guarda las Prioridades de los Workers agregados a la Cola Con Prioridad.
	private boolean needUpdate;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un UpNeeder con una Cola Con Prioridad vac�a de workers.
	 * 
	 * @param maxPrioridad M�ximo n�mero posible en los Workers a insertar en la Cola Con Prioridad. (prioridad min = 0 max = maxPrioridad)
	 */
	public UpNeeder (int maxPrioridad)
	{
		needUpdate = true;
		workers = new ColaConPrioridadConHeap<Integer,Worker> ();
		prioridades = new boolean[maxPrioridad + 1];
		for (int i=0; i <= maxPrioridad; i++)
			prioridades[i] = false;
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega un Worker.
	 * 
	 * @param prioridad Prioridad del Worker a agregar.
	 * @param w Worker a agregar.
	 * @throws NullPointerException
	 */
	public void addWorker (int prioridad, Worker w) throws NullPointerException
	{
		if (w == null)
			throw new NullPointerException ("UpNeeder.addWorker()" + "\n" +
					                        "Imposible agregar un Worker null.");
		if (prioridad >= prioridades.length)
			throw new BoundaryViolationException ("UpNeeder.addWorker()" + "\n" +
                                                  "La prioridad " + prioridad + " del Worker a ingresar supera la m�xima prioridad posible definida al crear el UpNeeder. (max=" + prioridades.length + ")");
		
		prioridades[prioridad] = true;
		workers.insert(prioridad, w);
	}
	
	/**
	 * Devuelve el pr�ximo Worker.
	 * 
	 * @return Pr�ximo Worker.
	 * @throws EmptyUpNeederException Si se solicita un Worker cuando la Cola Workers est� vac�a.
	 */
	public Worker getNextWorker () throws EmptyUpNeederException
	{
		if (workers.isEmpty())
			throw new EmptyUpNeederException ("UpNeeder.getNextWorker()" + "\n" +
					                          "No hay mas Workers en el UpNeeder solicitado.");
		
		int prioridadNextWorker = prioridadNextWorker();
		Worker r = workers.removeMin().getValue();
		if (isEmpty())
			prioridades[prioridadNextWorker] = false;
		else
			if (prioridadNextWorker != prioridadNextWorker())
				prioridades[prioridadNextWorker] = false;
		
		return r;
	}
	
	/**
	 * Indica que el UpNeeder ya no necesita ser actualizado.
	 * 
	 * Es eliminado en el Updater. Y este mismo lo limpia.
	 */
	public void notUpdate ()
	{
		needUpdate = false;
	}
	
	/**
	 * Elimina todos los Workers asociados.
	 */
	public void limpiar ()
	{
		while (! workers.isEmpty())
			workers.removeMin();
		workers = null;
		prioridades = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la prioridad del pr�ximo Worker.
	 * 
	 * @return prioriodad del pr�ximo Worker.
	 * @throws EmptyUpNeederException Si se solicita un Worker cuando la Cola Workers est� vac�a.
	 */
	public int prioridadNextWorker () throws EmptyUpNeederException
	{
		if (workers.isEmpty())
			throw new EmptyUpNeederException ("UpNeeder.prioridadNextWorker()" + "\n" +
					                          "No hay mas Workers en el UpNeeder solicitado.");
		
		return workers.min().getKey();
	}
	
	/**
	 * Verifica si hay almenos un Worker con el valor de prioridad pasada por par�metro, y devuelve el resultado.
	 * 
	 * @param prioridad Valor de prioridad a verificar.
	 * @return True:  existe almenos un Worker con el valor de prioridad pasada por par�metro.
	 *         False: caso contrario.
	 */
	public boolean hayWorkerPrioridad (int prioridad)
	{
		return prioridades[prioridad];
	}
	
	/**
	 * Devuelve la cantidad de Workers.
	 * 
	 * @return Cantidad de Workers.
	 */
	public int size ()
	{
		return workers.size();
	}
	
	/**
	 * Verifica si no hay ning�n Worker, y devuelve el resultado.
	 * 
	 * @return True:  no hay mas Workers.
	 *         False: almenos hay un Worker.
	 */
	public boolean isEmpty ()
	{
		return workers.isEmpty();
	}
	
	/**
	 * Verifica si el UpNeeder necesita ser actualizado, y devuelve el resultado.
	 * 
	 * @return True:  necesita ser actualizado.
	 *         False: caso contrario.
	 */
	public boolean needUpdate ()
	{
		return needUpdate;
	}

}