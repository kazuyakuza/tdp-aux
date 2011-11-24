package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;

/**
 * Representa a Mario en estado MarioChico.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioChico extends Caracteristica
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicaci�n en los �ndices es:
	                                                {dirRecursos + "Mario.gif",      //0: Mario quieto
													 dirRecursos + "Mario-Dead.gif", //1: Mario muerto
		                                             dirRecursos + "Mario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "Mario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "Mario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "Mario-Jump.gif"};//5: Mario saltando
	//Numeros de los Sprites.
	/*protected static int muerto = 0;
	protected static int quieto = 1;
	protected static int caminando = 2;
	protected static int saltando = 5;*/
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Caracteristica para un Mario, con estado MarioChico.
	 */
	public MarioChico ()
	{
		super();
	}
	
	/**
	 * Crea una Caracteristica para Mario, con estado MarioChico.
	 * 
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	public MarioChico (Mario pj)
	{
		super(pj);		
	}
	
	/*COMANDOS IMPLEMENTADOS*/	
	
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Mario realiza la acci�n A.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n A.
	 */
	public void accionA () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
		
	/**
	 * Mario realiza la acci�n B.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n B.
	 */
	public void accionB () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}

	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario = null;		
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario = null;
	}
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public void serDa�ado (Actor a)
	{
		mario.getSpriteManager().cambiarSprite(muerto);
		mario.morir(a);
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
		return 5;
	}
	
	
}