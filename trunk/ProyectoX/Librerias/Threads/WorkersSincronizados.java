package ProyectoX.Librerias.Threads;

/**
 * Hace que la ejecución de los Workers 1 y 2 se ejecuten de manera sincronizada, de acuerdo a su nivel de sincronización.
 * 
 * Ej: si sincronizacion = 1
 * Por cada ejecución del Worker1 hay 1 ejecución del Worker2.
 * Ej: si sincronizacion = 2
 * Por cada ejecución del Worker1 hay 2 ejecuciones del Worker2.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class WorkersSincronizados
{
	
	//Variables de Instancia
	private int sincronizacion;
	protected Worker worker1, worker2;
	private int ejecucionesW1, ejecucionesW2;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un WorkersSincronizados, donde por cada ejecución de w1 se ejecutará s veces w2.
	 * 
	 * @param w1 Worker1.
	 * @paeam s Nivel de Sincronización.
	 * @param w2 Worker2.
	 */
	public WorkersSincronizados (Worker w1, int s, Worker w2)
	{
		worker1 = w1;
		worker2 = w2;
		sincronizacion = s;
		ejecucionesW1 = ejecucionesW2 = 0;
	}
	
	/*COMANDOS*/
	
	/**
	 * Realiza una ejecución del Worker1.
	 * 
	 * @throws Exception Si se produce alguna excepción al realizar el trabajo.
	 */
	private void work1 () throws Exception
	{
		if (ejecucionesW1 == 0)
		{
			ejecucionesW1++;
			worker1.work();
			ejecucionesW1++;
		}
	}
	
	/**
	 * Realiza una ejecución del Worker2.
	 * 
	 * @throws Exception Si se produce alguna excepción al realizar el trabajo.
	 */
	private void work2 () throws Exception
	{
		while (ejecucionesW1 == 0) {} //Espera a que se empiece a ejecutar el Worker1.
		
		if (ejecucionesW2 <= sincronizacion)
		{
			worker2.work();
			ejecucionesW2++;
			
			if (ejecucionesW2 == sincronizacion)
			{
				ejecucionesW1 = 0;
				ejecucionesW2 = 0;
			}
		}
	}
	
	/**
	 * Limpia el WorkersSincronizados actual.
	 */
	public void limpiar ()
	{
		worker1 = null;
		worker2 = null;
		sincronizacion = -1;
		ejecucionesW1 = -1;
		ejecucionesW2 = 0;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Worker1 sincronizado con el Worker2.
	 * 
	 * @return Worker1 sincronizado con el Worker2.
	 */
	public Worker getWorker1 ()
	{
		return new Worker ()
				{
					public void work() throws Exception
					{
						work1();
					}
				};
	}
	
	/**
	 * Devuelve el Worker2 sincronizado con el Worker1.
	 * 
	 * @return Worker2 sincronizado con el Worker1.
	 */
	public Worker getWorker2 ()
	{
		return new Worker ()
				{
					public void work() throws Exception
					{
						work2();
					}
				};
	}

}