package ProyectoX.Logica;

import java.util.Iterator;

/**
 * Representa a la gravedad en el Juego, provoca la ca�da de los Actores que no se encuentran sobre una Celda s�lida (totalmente ocupada).
 * 
 * Proyecto X
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Gravedad implements Runnable
{
	
	//Atributos de Instancia
	protected ControlCentral controlCentral;
	protected boolean afectar;
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea la Gravedad con el ControlCentral del Juego.
	 * 
	 * @param cc ControlCentral del Juego.
	 */
	public Gravedad (ControlCentral cc)
	{
		controlCentral = cc;
		afectar = true;
	}
	
	/*COMANDOS*/
	
	/**
	 * 
	 */
	public void setAfectar (boolean v)
	{
		afectar = v;
	}
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * Provoca la ca�da del Actor a s� no se encuentra sobre una Celda s�lida (totalemente ocupada).
	 * 	
	 * @param a es el actor al cual la gravedad debe afectar.
	 */
	public void run()
	{	
		while (afectar)
		{
			Iterator<Actor> actores = controlCentral.getActores();
			while (actores.hasNext())
				afectar(actores.next());
			
			try
			{
				Thread.sleep(200);
			}
			catch (InterruptedException e)
			{
				controlCentral.mensajeError("Error", e.getMessage(), true);
			}
		}
	}
	
	/**
	 * Realiza el efecto que la Gravedad ejerce sobre el Actor.
	 * 
	 * @param a Actor al cual la Gravedad afecta.
	 */
	private void afectar (Actor a)
	{
		a.efectoGravedad(1);
		if (a.getPG() == -1)
		{
			a.caer();

			if (a.getCeldaActual().getBloque().debajoDelPiso(a.getCeldaActual().getPosicion()))
				//Si el Actor se encuentra en la �ltima fila del bloque, entonces debe morir por caer al precipicio.
				a.morir();
		}
	}

}