package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;

public abstract class DecoracionCaracteristica extends Caracteristica
{
	//Atributos de Instancia
	protected Caracteristica componente;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una decoración de Caracteristica que modifica las responsabilidades de la misma en ejecución.
	 * 
	 * @param comp es la Caracteristica a la cual se decora.
	 */
	protected DecoracionCaracteristica (Caracteristica comp)
	{		
		super();
		componente = comp;
		mario = comp.getMario();
		mario.getSpriteManager().cargarSprites(this.getNombresSprites());
	}
	
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @throws AccionActorException Si se produce algún error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		componente.agacharse();
	}
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		componente.accionA();
	}
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public void accionB () throws AccionActorException
	{
		componente.accionB();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		componente.crecerHongo();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		componente.crecerFlor();
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public void serDañado(Actor a)
	{
		componente.serDañado(a);
	}
	
	/**
	 * Realiza la acción de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		componente.golpearRompible(brick);
	}
	
	/**
	 * Retorna el multiplicador bonus que otorga ésta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus()
	{
		return componente.multiplicadorBonus();
	}
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public String[] getNombresSprites()
	{		
		return componente.getNombresSprites();
	}
}
