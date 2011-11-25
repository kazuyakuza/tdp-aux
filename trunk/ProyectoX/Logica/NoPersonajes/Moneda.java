package ProyectoX.Logica.NoPersonajes;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Responsabilidades.Punteable;

/**
 * Representa a la Moneda del juego.
 * Cuando un Mario agarra una Moneda, su jugador aumenta su cantidad de monedas en 1 y obtiene puntos.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Moneda extends Actor implements Punteable
{	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Coin-1.png",
		                                             dirRecursos + "Coin-2.png",
		                                             dirRecursos + "Coin-3.png",};
		
	private static int cantFramesMovimiento = 3;
	
	/**
	 * Crea una Moneda.
	 * 	 
	 * @param cargadorSprite Clase para cargar los sprites.	 
	 */
	public Moneda(CargadorSprite cargadorSprite) 
	{
		super(nombresSprites, cargadorSprite);			
		spriteManager.rotarGif(cantFramesMovimiento);
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
		//No tiene efectos sobre estos actores.
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
		Mario mario = checkActorJugador (actorJugador);
		mario.getJugador().agregarMoneda();
		mario.getJugador().asignarPuntos(this.getPuntos(mario));
		this.morir(mario);
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
		celdaActual.getBloque().getMapa().getNivel().eliminarActores(this);
		super.morir(a);
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 5;
	}

}
