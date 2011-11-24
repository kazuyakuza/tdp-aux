package ProyectoX.Logica.NoPersonajes.PowerUps;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Punteable;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;

/**
 * Representa a todos los power ups del juego. Son Actores NoPersonajes que afectan al personaje del Jugador, Mario.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class PowerUp extends Actor implements Punteable
{	
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un PowerUp.
	 */
	protected PowerUp (String[] nombresSprites, CargadorSprite cargadorSprite) throws NullPointerException
	{
		super(nombresSprites, cargadorSprite);
	}
	
	/*COMANDOS*/
	
	/**
	 * Realiza el efecto del Power Up.
	 * @throws NullPointerException si mario es null.
	 */
	public abstract void efecto (Mario mario) throws NullPointerException;
	
    /*METODOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		Celda celdaInferior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			celdaInferior = celdaActual.getBloque().getInferior(celdaActual);
			if (!celdaInferior.isOcupada())
				moverseAcelda(celdaInferior);
			else
				PG = 0;
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("PowerUp.caer()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("PowerUp.caer()" + "\n" +
                                            "Imposible realizar la acción caer a/desde Celda de posición (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}		
	}
	
	/**
	 * Realiza la acción de colisionar con otro Actor.
	 * Mario no provoca nada al colisionar con otros Actores.
	 * 
	 * Los efectos de la colisión la provocan los otros Actores.
	 * 
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionar (Actor a) throws ColisionException
	{
		//No hace nada, un Power Up no produce efectos de colision sobre otros Actores que no son PjSeleccionable.
	}
	
	/**
	 * Realiza la acción de colisionar con otro Personaje Seleccionable.
	 * Mario no provoca nada al colisionar con otro Personaje.
	 * 
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException, NullPointerException
	{		
		Mario mario = checkActorJugador (actorJugador);
		mario.getJugador().asignarPuntos(this.getPuntos(mario));
		this.efecto (mario);
		this.morir(mario);		
	}
	
	/**
	 * Realiza la acción de colisionar con una Bola de Fuego de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (Actor bola) throws ColisionException
	{
		//No hace nada, no tiene efecto sobre este actor.
	}
	
	/**
	 * Realiza las colisiones del PowerUp actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);	
	}
	
	
}