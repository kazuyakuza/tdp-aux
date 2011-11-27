package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;

/**
 * Representa a las Caracter�sticas que Mario puede tener en el juego. 
 * En base a �sta, Mario tiene determinado comportamiento ante los dem�s Actores.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public abstract class Caracteristica 
{
	//Atributos de instancia
	//Numeros de los Sprites.
	protected static int muerto = 0;
	protected static int quieto = 1;
	protected static int caminando = 2;
	protected static int saltando = 5;
	
	protected Mario mario;
	
	/*CONSTRUCTORES*/
	
	/**
	 * 	Crea una Caracteristica para Mario, cada Caracteristica tiene sus propios nombres de sprites.
	 *  Queda sin vincular con un Mario.
	 */
	protected Caracteristica ()
	{
		
	}
	
	/**
	 * Crea una Caracteristica para Mario, cada Caracteristica tiene sus propios nombres de sprites.
	 * Setea al Mario vinculado con pj.
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	protected Caracteristica(Mario pj)
	{
		mario = pj;
		mario.getSpriteManager().cargarSprites(this.getNombresSprites());		
	}
	
	/*METODOS ABSTRACTOS*/
	
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Mario realiza la acci�n A.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n A.
	 */
	public abstract void accionA () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n B.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n B.
	 */
	public abstract void accionB () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public abstract void crecerHongo () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public abstract void crecerFlor () throws AccionActorException;
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public abstract void serDa�ado(Actor a);
	
	/**
	 * Realiza la acci�n de golpear una plataforma Rompible.
	 * @param brick es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si bricks es null.
	 */
	public abstract void golpearRompible (Rompible brick) throws NullPointerException;
	
	/**
	 * Retorna el multiplicador bonus que otorga �sta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public abstract int multiplicadorBonus();	
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public abstract String[] getNombresSprites();
	
	/*COMANDOS*/
	
	/**
	 * Setea al Mario al que la Caracteristica representa con pj.
	 * @param pj es el Mario con el que se setea a la Caracteristica.
	 */
	public void setMario (Mario pj)
	{
		mario = pj;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Retorna el Mario asociado a la Caracteristica.
	 * @return el Mario asociado a la Caracteristica.
	 */
	public Mario getMario()
	{
		return mario;
	}
	
	public int spriteMuerto()
	{
		return muerto;
	}
		
	public int spriteCaminando()
	{
		return caminando;
	}
	
	public int cantSpritesCaminando()
	{
		return 3;
	}
	
	public int spriteSaltando()
	{
		return saltando;
	}
	
	public int spriteQuieto()
	{
		return quieto;
	}
	
	
}