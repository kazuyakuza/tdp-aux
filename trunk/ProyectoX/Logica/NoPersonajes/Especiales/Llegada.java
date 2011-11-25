package ProyectoX.Logica.NoPersonajes.Especiales;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa al lugar de llegada para ganar un Nivel.
 * Cuando un PjSeleccionable colisiona (se ubica en la misma celda) con una Llegada, el Jugador gana y termina el Nivel.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public class Llegada extends Actor
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Vine2.gif", dirRecursos + "Goal-Flag.gif"};
	
	//Atributos de Instancia
	private ControlCentral controlCentral;
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea una Llegada.
	 * 
	 * @param cc ControlCentral del Juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 * @throws NullPointerException Si cc es null.
	 */
	public Llegada (ControlCentral cc, CargadorSprite cargadorSprite) throws NullPointerException
	{
		super (nombresSprites, cargadorSprite);
		
		if (cc == null)
			throw new NullPointerException ("Llegada." + "\n" +
					                        "Imposible crear Llegada con ControlCentral null.");
		
		controlCentral = cc;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual. 
	 */
	public void colisionar (Actor a)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("Llegada.colisionarPj()" + "\n" +
					                        "Imposible realizar colisión. El PjSeleccionable ingresado es null.");
		
		try
		{
			controlCentral.ganarNivel();
		}
		catch (Exception e)
		{
			throw new ColisionException ("Llegada.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 */
	public void colisionarBola (BolaFuego bola)
	{
		//No le afecta.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		//Nada ocurre	
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		//Nunca ocurre.
	}
	
}