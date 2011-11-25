package ProyectoX.Logica.Personajes.Enemigo.IA;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Librerias.Threads.Worker;

/**
 * Control de las IA del Juego.
 * Ejecuta una a una cada una de las Inteligencia Artificial.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class IAControl implements Worker
{
	
	//Atributos de Instancia
	//protected ControlCentral controlCentral;
	protected PositionList<IA> IAs;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Control para IAs.
	 */
	public IAControl ()
	{
		IAs = new ListaPositionSimple<IA> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega la IA ia a la Lista de IAs.
	 * 
	 * @param ia IA a agregar.
	 */
	public void addIA (IA ia)
	{
		IAs.addLast(ia);
	}
	
	/**
	 * Elimina la IA ia de la Lista de IAs.
	 * 
	 * @param ia IA a eliminar.
	 * @throws NullPointerException Si ia es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un IA que no pertenece la Lista de IAs.
	 */
	public void eliminarIA (IA ia) throws NullPointerException, AccionActorException
	{
		if (ia == null)
			throw new NullPointerException ("IAControl.eliminarIA()" + "\n" +
                                            "La IA que está intentando eliminar es null.");
		if (IAs.isEmpty())
			throw new AccionActorException ("IAControl.eliminarIA()" + "\n" +
                                            "EL IAControl está vacío.");
			
		Position<IA> p = IAs.first();
		while ((p != IAs.last()) && (p.element() != ia))
			p = IAs.next(p);
		if (p.element() != ia)
			throw new AccionActorException ("IAControl.eliminarIA()" + "\n" +
                                            "La IA que está intentando eliminar no pertenece a este IAControl.");
		IAs.remove(p);
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve un iterador con las IAs.
	 * 
	 * @return Iterador con las IAs.
	 */
	public Iterator<IA> getIAs ()
	{
		return IAs.iterator();
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Ejecuta una actuación de cada una de las IAs.
	 */
	public void work ()
	{
		for (IA ia: IAs)
			if (ia.getActuar() < 0)
			{
				ia.limpiar();
				eliminarIA(ia);
			}
			else
				ia.actuar();
	}

}