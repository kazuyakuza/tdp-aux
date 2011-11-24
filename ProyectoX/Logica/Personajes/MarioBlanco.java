package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.BolaFuego;

/**
 * Representa a Mario en estado MarioBlanco. (cuando Mario toma la Flor de Fuego) del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioBlanco extends Caracteristica
{
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicaci�n en los �ndices es:
	                                                {dirRecursos + "Mario-Dead.gif", 	  //0: Mario muerto	
													 dirRecursos + "FieryMario.gif",      //1: Mario quieto													 	                                             
		                                             dirRecursos + "FieryMario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "FieryMario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "FieryMario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "FieryMario-Jump.gif", //5: Mario saltando
													 dirRecursos + "FieryMario-Duck.gif",//6: Mario agachado
													 dirRecursos + "FieryMario-Fireball.gif"}; //7: Mario
	
	protected int agachado;
	protected int lanzando;

	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Caracteristica para Mario, con estado MarioBlanco.
	 * 
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	public MarioBlanco (Mario pj)
	{
		super(pj);		
		agachado = 6;
		lanzando = 7;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
		
	/**
	 * Mario realiza la acci�n de agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		mario.getSpriteManager().cambiarSprite(agachado);
		//Quitar la �ltima celda de la lista de celdas actuales.
	}
	
	/**
	 * Mario realiza la acci�n A.
	 */
	public void accionA () throws AccionActorException
	{
		mario.getSpriteManager().cambiarSprite(lanzando);
		disparar();
	}
		
	/**
	 * Mario realiza la acci�n B.
	 */
	public void accionB () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 */
	public void crecerHongo () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public void serDa�ado (Actor a)
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario.setCaracteristica(new Invulnerable (mario.getCaracteristica(), 4000));
		((Invulnerable)mario.getCaracteristica()).empezar();		
		mario = null;
	}
	
	/**
	 * Realiza la acci�n de MarioBlanco de lanzar una Bola de Fuego.
	 */
	public void disparar ()
	{
		BolaFuego bola = new BolaFuego (mario, new CargadorSprite ());
		mario.getCeldaActual().agregarActor(bola);
		bola.setCeldaActual(mario.getCeldaActual());
		mario.getJugador().getControlCentral().agregarActor(bola);
		
	}
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public String[] getNombresSprites()
	{
		return nombresSprites;
	}
	
	/**
	 * Retorna el multiplicador bonus que otorga �sta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus ()
	{
		return 10;
	}
	
}