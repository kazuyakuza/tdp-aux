package ProyectoX.Logica.NoPersonajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa a las Bolas de Fuego, lanzadas por Mario Blanco (Mario con efecto de Flor de Fuego), en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class BolaFuego extends Actor
{
	//Variables de Clase
	private static final String dirRecursos = "Estructuras/";
	private static final String [] nombresSprites = {dirRecursos + "FireBall.gif", dirRecursos + "FireBallHit.gif"};
	
	//Variables de Instancia
	protected Actor mario;

	/**
	 * Crea una Plataforma Irrompible.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */

	public BolaFuego(CargadorSprite cargadorSprite) 
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
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones(Celda c)
	{
		/*No hace nada, nunca ocurre.*/	
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		PG = 0;
	}
	
	/**
	 * Realiza la Acci�n caer, producida por el efecto de la Gravedad.
	 * No tiene ning�n efecto en este Actor.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al caer.
	 */
	public void caer () throws AccionActorException
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
		super.morir(a);
	}

}