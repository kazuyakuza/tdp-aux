package ProyectoX.Logica.NoPersonajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa al Piso en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Piso extends Actor implements Estructura
{
	
	//Variables de Clase
	private static final String dirRecursos = "Estructuras/";
	private static final String [] nombresSprites = {dirRecursos + "Piso.png"};

	/**
	 * Crea una Plataforma Irrompible.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Piso(CargadorSprite cargadorSprite)
	{
		super(nombresSprites, cargadorSprite);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la acci�n de colisionar con otro Actor a.
	 * No tiene ning�n efecto con este Actor.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n. 
	 */
	public void colisionar (Actor a) throws ColisionException, NullPointerException
	{
		/*No hace nada, no tiene efecto sobre otros Actores.*/
	}
	
	/**
	 * Realiza la acci�n de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{
		/*No hace nada, no tiene efecto sobre otros Actores.*/
	}
	
	/**
	 * Realiza la acci�n de colisionar con una Bola de Fuego de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
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
	 * Realiza la acci�n de morir del Actor.
	 * 
	 * No tiene ning�n efecto en este Actor.
	 */
	public void morir(Actor a) throws NullPointerException
	{
		/*No hace nada, nunca ocurre.*/
	}

}