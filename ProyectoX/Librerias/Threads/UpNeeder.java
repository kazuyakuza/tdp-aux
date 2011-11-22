package ProyectoX.Librerias.Threads;

import ProyectoX.Librerias.TDAColaConPrioridad.PriorityQueue;
import ProyectoX.Librerias.TDAColaConPrioridad.ColaConPrioridadConHeap;

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
	private boolean needUpdate;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un UpNeeder con una Cola Con Prioridad vacía de workers.
	 */
	public UpNeeder ()
	{
		needUpdate = true;
		workers = new ColaConPrioridadConHeap<Integer,Worker> ();
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
		
		workers.insert(prioridad, w);
	}
	
	/**
	 * Indica que el UpNeeder ya no necesita ser actualizado.
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
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el próximo Worker.
	 * 
	 * @return Próximo Worker.
	 * @throws EmptyUpNeederException Si se solicita un Worker cuando la Cola Workers está vacía.
	 */
	public Worker getNextWorker () throws EmptyUpNeederException
	{
		if (workers.isEmpty())
			throw new EmptyUpNeederException ("UpNeeder.getNextWorker()" + "\n" +
					                          "No hay mas Workers en el UpNeeder solicitado.");
		
		return workers.removeMin().getValue();
	}
	
	/**
	 * Devuelve la prioridad del próximo Worker.
	 * 
	 * @return prioriodad del próximo Worker.
	 * @throws EmptyUpNeederException Si se solicita un Worker cuando la Cola Workers está vacía.
	 */
	public int prioridadNextWorker () throws EmptyUpNeederException
	{
		if (workers.isEmpty())
			throw new EmptyUpNeederException ("UpNeeder.prioridadNextWorker()" + "\n" +
					                          "No hay mas Workers en el UpNeeder solicitado.");
		
		return workers.min().getKey();
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
	 * Verifica si no hay ningún Worker, y devuelve el resultado.
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