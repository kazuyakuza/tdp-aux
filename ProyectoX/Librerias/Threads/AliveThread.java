package ProyectoX.Librerias.Threads;

/**
 * 
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class AliveThread extends Thread
{
	
	private ControlThreads controlThread;
	private boolean keepAlive;
	private int sleepTime;
	protected Worker worker;
	
	public AliveThread (ControlThreads control, Worker w)
	{
		super ();
		controlThread = control;
		keepAlive = true;
		sleepTime = controlThread.getSleepTime();
		worker = w;
	}
	
	public final void run ()
	{
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
	}
	
	public void changeSleepTime (int st)
	{
		sleepTime = st;
	}
	
	public final void kill ()
	{
		keepAlive = false;
	}

}