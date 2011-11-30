package ProyectoX.Librerias.Threads;

import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;

/**
 * AliveThread con una lista de UpNeeders, que ejecuta cada uno de los Workers contenidos en cada uno.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Updater implements Worker
{
	
	//Atributos de Instancia
	private PositionList<UpNeeder> upNeeders;
	
	//Mi Instancia
	private static Updater updater = new Updater ();
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Updater con el ControlThread control.
	 * Se asigna como Worker de si mismo.
	 * Crea una lista vacía de UpNeeders.
	 */
	private Updater()
	{
		upNeeders = new ListaPositionSimple<UpNeeder> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega el UpNeeder un al Updater.
	 * 
	 * Siempre son agregados al final de la lista.
	 * 
	 * @param un UpNeeder a agregar.
	 * @throws NullPointerException Si el UpNeeder un es null.
	 */
	public void addUpNeeder (UpNeeder un) throws NullPointerException
	{
		if (un == null)
			throw new NullPointerException ("Updater.addUpNeeder()" + "\n" +
					                        "Imposible agregar un UpNeeder null.");
		
		upNeeders.addLast(un);
	}
	/**
	 * Elimina el Upneeder un, y lo devuelve.
	 * 
	 * @param un UpNeeder a eliminar.
	 * @return UpNeeder eliminado.
	 * @throws NullPointerException Si el UpNeeder un es null.
	 * @throws NoExisteUpNeederException Si no hay UpNeeders o si el UpNeeder que se quiere eliminar no pertenece a este Updater.
	 */
	public UpNeeder removeUpNeeder (UpNeeder un) throws NullPointerException, NoExisteUpNeederException
	{
		if (un == null)
			throw new NullPointerException ("Updater.removeUpNeeder()" + "\n" +
											"Imposible eliminar un UpNeeder null.");
		if (upNeeders.isEmpty())
			throw new NoExisteUpNeederException ("Updater.removeUpNeeder()" + "\n" +
					                             "No hay UpNeeders que eliminar.");
		
		Position<UpNeeder> p = upNeeders.first();
		while ((p != upNeeders.last()) && (p.element() != un))
			p = upNeeders.next(p);
		if (p.element() != un)
			throw new NoExisteUpNeederException ("El UpNeeder que está intentando sacar no existe.");
		return upNeeders.remove(p);
	}
	
	/*CONSULTAS*/
		
	/**
	 * Devuelve el Updater.
	 * 
	 * @return Updater.
	 */
	public static Updater getUpdater ()
	{
		return updater;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Trabajo asociado al Worker.
	 * 
	 * En esta clase: para cada UpNeeder:
	 * -> Si necesita ser actualizado
	 *    => Entonces -> Si tiene un Worker, lo ejecuta.
	 * -> Sino, elimina el UpNeeder de la lista.
	 * 
	 * @throws Exception Si el trabajo realizado en el Worker dispara una excepción.
	 */
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
			{
				un.limpiar();
				removeUpNeeder (un);
			}
	}
	
	/**
	 * Elimina referencias a elementos asociados.
	 */
	protected void limpiar ()
	{
		for (UpNeeder un: upNeeders)
			removeUpNeeder (un);
		
		upNeeders = null;
	}

}