package ProyectoX.Librerias.Threads;

import ProyectoX.Excepciones.BoundaryViolationException;

/**
 * Thread que se ejecuta de manera indefinida,ejecutando la operación del Worker asociado.
 * Luego de cada ejecución, se detiene el tiempo indicado por el ControlThread asociado.
 * Se ejecuta hasta que el ControlThread lo indique.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class AliveThread extends Thread
{
	
	//Atributos de Instancia
	private ControlThread controlThread;//Objeto que controla la ejecución de una instancia de esta clase.
	private boolean keepAlive;
	private int sleepTime;
	protected Worker worker;//Worker con la operación a realizar en cada ejecución.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un AliveThread con el ControlThread control y Worker w.
	 * Solicita el tiempo de espera al ControlThread.
	 * Asigna al sleepTime el porcentaje de tiempo devuelto por el ControlThread, según el porcentaje p pasado por parámetro.
	 * 
	 * @param control ControlThread que controla la ejecución del objeto a crear.
	 * @param p Porcentaje de tiempo. (p>=0) y (p<=1)
	 * @param w Worker a asociar al objeto a crear.
	 * @throws NullPointerException Si el ControlThread control es null.
	 * @throws BoundaryViolationException Si el porcentaje p pasado por parámetro no es correcto.
	 */
	public AliveThread (ControlThread control, double p, Worker w) throws NullPointerException, BoundaryViolationException
	{
		super ();
		
		if (control == null)
			throw new NullPointerException ("AliveThread." + "\n" +
					                        "Imposible crear un AliveThread con ControlThread null.");
		if ((p < 0) || (p>1))
			throw new BoundaryViolationException ("AliveThread." + "\n" +
												  "El valor de porcentaje pasado por parámetro debe ser mayor a 0 y menor o igual a 1.");
		
		controlThread = control;
		keepAlive = true;
		sleepTime = (int) (controlThread.getSleepTime() * p);
		worker = w;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * No llamar a este método.
	 * Utilizar el método start().
	 * Para eliminar llamar a kill().
	 * 
	 * Método de ejecución constante, donde se realiza la operación deseada.
	 */
	public final void run ()
	{
		try
		{
			if (worker == null)
				throw new NullPointerException ("AliveThread.run()" + "\n" +
				     							"Imposible crear un AliveThread con un Worker null.");
		}
		catch (Exception e)
		{
			controlThread.error (e);
		}
		
		while (keepAlive)
		{
			try
			{
				worker.work();
				Thread.sleep(sleepTime);
			}
			catch (Exception e)
			{
				controlThread.error (e);
			}
		}
		
		//Limpieza al terminar la ejecución.
		limpiar ();
	}
	
	/**
	 * Detiene la ejecución del AliveThread, haciendo que el mismo sea eliminado.
	 */
	public final void kill ()
	{
		keepAlive = false;
	}
	
	/**
	 * Elimina referencias a elementos asociados.
	 */
	protected void limpiar ()
	{
		controlThread = null;
		worker = null;
	}

}