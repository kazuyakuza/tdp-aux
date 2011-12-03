package ProyectoX.Logica;

import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Representa a la gravedad en el Juego, provoca la caída de los Actores que no se encuentran sobre una Celda sólida (totalmente ocupada).
 * 
 * Proyecto X
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Gravedad implements Worker
{
	
	//Atributos de Instancia
	protected ControlCentral controlCentral;
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea la Gravedad con el ControlCentral del Juego.
	 * 
	 * @param cc ControlCentral del Juego.
	 * @throws NullPointerException Si cc es null.
	 */
	public Gravedad (ControlCentral cc) throws NullPointerException
	{
		if (cc == null)
			throw new NullPointerException ("Gravedad." + "\n" +
					                        "Imposible crear Gravedad. El Control Central Ingresado es null.");
		
		controlCentral = cc;
	}
	
	/*COMANDOS*/
	
	/*Métodos en Ejecución*/
	
	/**
	 * Provoca la caída de los Actores afectables por gravedad.
	 */
	public void work()
	{
		for (afectableXgravedad actor: controlCentral.getCaibles())
			if (actor != null)
				afectar(actor);
	}
	
	/**
	 * Realiza el efecto que la Gravedad ejerce sobre el Actor.
	 * 
	 * @param a Actor al cual la Gravedad afecta.
	 * @throws NullPointerException Si a es null.
	 */
	private void afectar (afectableXgravedad a) throws NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("Gravedad.afectar()" + "\n" +
					                        "Imposible afectar al Actor a, a es null.");
		
		if (a.getPG() == -1)
			a.caer();
		else
		{
			a.efectoGravedad(1);
			if (a.getPG() == -1)
				a.caer();
		}
	}

}