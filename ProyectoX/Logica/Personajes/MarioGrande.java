package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;

/**
 * Representa a Mario en estado MarioGrande (cuando Mario toma el Super Hongo) del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioGrande extends Caracteristica
{
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicación en los índices es:
	                                                {dirRecursos + "Mario-Dead.gif", 	  //0: Mario muerto
		                                             dirRecursos + "SuperMario.gif",      //1: Mario quieto
		                                             dirRecursos + "SuperMario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "SuperMario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "SuperMario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "SuperMario-Jump.gif", //5: Mario saltando
													 dirRecursos + "SuperMario-Duck.gif"};//6: Mario agachado
	
	protected int agachado;
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Caracteristica para Mario, con estado MarioGhico.
	 * 
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	public MarioGrande (Mario pj)
	{
		super(pj);		
		agachado = 6;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @throws AccionActorException Si se produce algún error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		mario.getSpriteManager().cambiarSprite(agachado);
		//Quitar la última celda de la lista de celdas actuales.
	}
	
	/**
	 * Mario realiza la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
		
	/**
	 * Mario realiza la acción B.
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
		//Esta Caracterisica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		mario.setCaracteristica(new MarioBlanco(mario));
		mario = null;
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public void serDañado (Actor a)
	{
		mario.setCaracteristica(new MarioChico(mario));
		//agregar la decoracion Invulnerable.
		mario = null;
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
	 * Retorna el multiplicador bonus que otorga ésta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus ()
	{
		return 5;
	}
	
}