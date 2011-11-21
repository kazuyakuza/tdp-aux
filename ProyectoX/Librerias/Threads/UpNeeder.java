package ProyectoX.Librerias.Threads;

import ProyectoX.Librerias.TDAColaConPrioridad.PriorityQueue;
import ProyectoX.Librerias.TDAColaConPrioridad.ColaConPrioridadConHeap;

public class UpNeeder
{
	
	private PriorityQueue<Integer,Worker> workers;
	private boolean needUpdate;
	
	public UpNeeder ()
	{
		needUpdate = true;
		workers = new ColaConPrioridadConHeap<Integer,Worker> ();
	}
	
	public void addWorker (int prioridad, Worker w)
	{
		workers.insert(prioridad, w);
	}
	
	public Worker getNextWorker () throws EmptyUpNeederException
	{
		if (workers.isEmpty())
			throw new EmptyUpNeederException ("No hay mas Workers en el UpNeeder solicitado.");
		return workers.removeMin().getValue();
	}
	
	public int prioridadNextWorker ()
	{
		return workers.min().getKey();
	}
	
	public int size ()
	{
		return workers.size();
	}
	
	public boolean isEmpty ()
	{
		return workers.isEmpty();
	}
	
	public boolean needUpdate ()
	{
		return needUpdate;
	}

}