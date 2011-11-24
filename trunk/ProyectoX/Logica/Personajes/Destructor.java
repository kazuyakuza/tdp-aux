package ProyectoX.Logica.Personajes;

import javax.swing.Timer;
import java.awt.event.*;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;

/**
 * Representa a la Caracteristica que Mario puede poseer, que le permite estar en estado destructor (destruye a los enemigos al colisionar) en el juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Destructor extends DecoracionCaracteristica
{
	//Atributos de Instancia
	protected Timer timer;
	
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una decoraci�n de Caracteristica, Invulnerable, que modifica las responsabilidades de la misma en ejecuci�n.
	 * 
	 * @param comp es la Caracteristica a la cual se decora.
	 * @param t es el entero que representa el tiempo de duraci�n de la decoraci�n en milisegundos.
	 */
	public Destructor (Caracteristica comp, int t)
	{		
		super (comp);		
		timer = new Timer (t, new ActionListener ()
		{				
			public void actionPerformed (ActionEvent e)
			{					
				mario.setCaracteristica(componente);			
				mario = null;
				timer.stop();										
			}
		});			
	}
	
	/**
	 * Empieza el tiempo de duraci�n del efecto de Destructor.
	 */
	public void empezar ()
	{			
		timer.start();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
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
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		componente.crecerFlor();
		//componente = componente.getMario().getCaracteristica();
		componente = mario.getCaracteristica();
	}
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public void serDa�ado(Actor a)
	{
		a.morir(mario);		
	}
}