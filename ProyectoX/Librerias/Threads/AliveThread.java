package ProyectoX.Librerias.Threads;

import ProyectoX.Excepciones.BoundaryViolationException;

/**
 * Thread que se ejecuta de manera indefinida,ejecutando la operaci�n del Worker asociado.
 * Luego de cada ejecuci�n, se detiene el tiempo indicado por el ControlThread asociado.
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
	private ControlThread controlThread;//Objeto que controla la ejecuci�n de una instancia de esta clase.
	private boolean keepAlive;
	private int sleepTime;
	protected Worker worker;//Worker con la operaci�n a realizar en cada ejecuci�n.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un AliveThread con el ControlThread control y Worker w.
	 * Solicita el tiempo de espera al ControlThread.
	 * Asigna al sleepTime el porcentaje de tiempo devuelto por el ControlThread, seg�n el porcentaje p pasado por par�metro.
	 * 
	 * @param control ControlThread que controla la ejecuci�n del objeto a crear.
	 * @param p Porcentaje de tiempo. (p>=0) y (p<=1)
	 * @param w Worker a asociar al objeto a crear.
	 * @throws NullPointerException Si el ControlThread control es null.
	 * @throws BoundaryViolationException Si el porcentaje p pasado por par�metro no es correcto.
	 */
	public AliveThread (ControlThread control, double p, Worker w) throws NullPointerException, BoundaryViolationException
	{
		super ();
		
		if (control == null)
			throw new NullPointerException ("AliveThread." + "\n" +
					                        "Imposible crear un AliveThread con ControlThread null.");
		if ((p < 0) || (p>1))
			throw new BoundaryViolationException ("AliveThread." + "\n" +
												  "El valor de porcentaje pasado por par�metro debe ser mayor a 0 y menor o igual a 1.");
		
		controlThread = control;
		keepAlive = true;
		sleepTime = (int) (controlThread.getSleepTime() * p);
		worker = w;
	}
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * No llamar a este m�todo.
	 * Utilizar el m�todo start().
	 * Para eliminar llamar a kill().
	 * 
	 * M�todo de ejecuci�n constante, donde se realiza la operaci�n deseada.
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
		
		//Limpieza al terminar la ejecuci�n.
		limpiar ();
	}
	
	/**
	 * Detiene la ejecuci�n del AliveThread, haciendo que el mismo sea eliminado.
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