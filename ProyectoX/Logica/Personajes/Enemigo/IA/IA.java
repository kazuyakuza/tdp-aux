package ProyectoX.Logica.Personajes.Enemigo.IA;

import ProyectoX.Excepciones.IAexception;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;

/**
 * Inteligancia Arificial que controla a algunos de los Actores no Seleccionables del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class IA
{
	
	//Variables de Instancia
	protected Enemigo marioneta;
	protected int actuar; //Usado para la frecuencia de movimientos de la marioneta.
	                      //Se realiza un movimiento cuando actuar == 0
	                      //Se elimina la IA cuando actuar < 0
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea una IA con su respectiva marioneta.
	 * 
	 * @param m Enemigo marioneta de la IA a crear.
	 * @param c IAControl para esta IA.
	 * @throws NullPointerException Si m o c es null.
	 */
	protected IA (Enemigo m) throws NullPointerException
	{
		if (m == null)
			throw new NullPointerException ("IA." + "\n" +
					                        "Imposible crear IA. El Enemigo Ingresado es null.");
		
		marioneta = m;
		actuar = 0;
	}
	
	/*COMANDOS*/
	
	/**
	 * Actualiza el valor de actuar.
	 * 
	 * @param act Valor a actualizar.
	 */
	protected abstract void setActuar ();
	
	/**
	 * Limpia la IA actual.
	 */
	public void limpiar ()
	{
		marioneta = null;
		actuar = -1;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve actuar.
	 * 
	 * @return Actuar.
	 */
	public final int getActuar ()
	{
		return actuar;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Le realiza movimientos a la marioneta.
	 * 
	 * @throws IAexception Si se produce algún error en la actuación.
	 */
	public final void actuar () throws IAexception
	{
		if (marioneta != null)
		{
			if (actuar == 0)
			{
				try
				{
					actuacion ();
				}
				catch (Exception e)
				{
					throw new IAexception ("IA.actuar()" + "\n" +
							               "Error al realizar una actuación de una IA." + "\n" +
							               "Detalles del Error:" + "\n" +
							               e.getMessage());
				}
				if (!(actuar < 0))
					setActuar();
			}
			else
				actuar--;
		}
	}
	
	/**
	 * La marioneta realiza la acción de moverse.
	 */
	protected abstract void actuacion ();

}