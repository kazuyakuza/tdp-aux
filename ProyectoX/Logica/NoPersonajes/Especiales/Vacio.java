package ProyectoX.Logica.NoPersonajes.Especiales;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Responsabilidades.Punteable;

/**
 * Representa al vacío lugar donde todo actor que llegua se muere.
 * Cuando un Actor colisiona (se ubica en la misma celda) con un Vacio, dicho Actor se muere.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Vacio extends Actor implements Punteable
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Vacio.png"};
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea una Llegada.
	 * 
	 * @param cc ControlCentral del Juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 * @throws NullPointerException Si cc es null.
	 */
	public Vacio(CargadorSprite cargadorSprite) 
	{
		super (nombresSprites, cargadorSprite);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor a.
	 * Produce la muerte de dicho actor.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión. 
	 */
	public void colisionar (Actor a) throws ColisionException, NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("Vacio.colisionar()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		a.morir(this);
	}
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * Produce la muerte del personaje.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{		
		if (actorJugador == null)
			throw new NullPointerException ("Vacio.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		actorJugador.morir(this);
	}
	
	/**
	 * Realiza la acción de colisionar con una Bola de Fuego de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (BolaFuego bola) throws ColisionException
	{
		//No hace nada, no tiene efecto sobre este actor.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones(Celda c)
	{
		/*No hace nada, nunca ocurre.*/	
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir(Actor a) throws NullPointerException
	{
		/*No hace nada, nunca ocurre.*/
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 15;
	}

}
