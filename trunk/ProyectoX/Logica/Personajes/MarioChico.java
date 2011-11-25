package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;

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
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicación en los índices es:
	                                                {dirRecursos + "Mario-Dead.gif", //0: Mario muerto
													 dirRecursos + "Mario.gif",      //1: Mario quieto
		                                             dirRecursos + "Mario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "Mario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "Mario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "Mario-Jump.gif"};//5: Mario saltando
	
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
	 * Mario realiza la acción de agacharse.
	 * 
	 * @throws AccionActorException Si se produce algún error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public void accionB () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}

	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario.getJugador().getControlCentral().cambiarPlataformasFlor();
		mario = null;		
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario.getJugador().getControlCentral().cambiarPlataformasFlor();
		mario = null;
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public void serDañado (Actor a)
	{
		mario.getSpriteManager().cambiarSprite(muerto);
		mario.morir();
	}	
	
	/**
	 * Realiza la acción de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		//No hace nada.
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