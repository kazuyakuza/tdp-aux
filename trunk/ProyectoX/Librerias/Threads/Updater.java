package ProyectoX.Librerias.Threads;

import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;

public class Updater extends AliveThread implements Worker
{
	
	private PositionList<UpNeeder> upNeeders;
	
	public Updater(ControlThreads control)
	{
		super(control, null);
		worker = this;
		upNeeders = new ListaPositionSimple<UpNeeder> ();
	}
	
	public void addUpNeeder (UpNeeder un)
	{
		upNeeders.addLast(un);
	}
	
	public UpNeeder removeUpNeeder (UpNeeder un) throws NullPointerException, NoExisteUpNeederException
	{
		if (un == null)
			throw new NullPointerException ("El UpNeeder que está intentando sacar es null.");
		Position<UpNeeder> p = upNeeders.first();
		while ((p != upNeeders.last()) && (p.element() != un))
			p = upNeeders.next(p);
		if (p.element() != un)
			throw new NoExisteUpNeederException ("El UpNeeder que está intentando sacar no existe.");
		return upNeeders.remove(p);
	}
	
	public void work() throws Exception
	{
		for (UpNeeder un: upNeeders)
			if (un.needUpdate())
			{
				if (!un.isEmpty())
				{
					un.getNextWorker().work();
				}
			}
			else
				removeUpNeeder (un);
	}

}