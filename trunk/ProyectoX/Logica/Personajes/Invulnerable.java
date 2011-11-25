package ProyectoX.Logica.Personajes;

import javax.swing.Timer;
import java.awt.event.*;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;

/**
 * Representa a la Caracteristica que Mario puede poseer, que le permite estar en estado invulnerable (no es dañado por los enemigos al colisionar) en el juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Invulnerable extends DecoracionCaracteristica
{
	//Atributos de Instancia
	protected Timer timer;
	
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una decoración de Caracteristica, Invulnerable, que modifica las responsabilidades de la misma en ejecución.
	 * 
	 * @param comp es la Caracteristica a la cual se decora.
	 * @param t es el entero que representa el tiempo de duración de la decoración en milisegundos.
	 */
	public Invulnerable (Caracteristica comp, int t)
	{
		super (comp);
		timer = new Timer (t, new ActionListener ()
		{						
			public void actionPerformed (ActionEvent e)
			{
				mario.producirColisiones(mario.getCeldaActual());
				mario.setCaracteristica(componente);
				mario = null;
				timer.stop();
			}
		});		
	}
	
	/**
	 * Empieza el tiempo de duración del efecto de Invulnerable.
	 */
	public void empezar ()
	{		
		timer.start();
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
		componente = mario.getCaracteristica();
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
		componente = mario.getCaracteristica();
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public void serDañado(Actor a)
	{
		//No hace nada, es invulnerable.
	}

}
