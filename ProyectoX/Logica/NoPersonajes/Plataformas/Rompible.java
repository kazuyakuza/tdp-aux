package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa a una Plataforma Rompible en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Rompible extends Actor implements Plataforma
{
	//Variables de Clase
	private static final String dirRecursos = "Estructuras/Plataformas/";
	private static final String [] nombresSprites = {dirRecursos + "Bricks.gif"};

	/**
	 * Crea una Plataforma Irrompible.
	 */
	public Rompible()
	{
		super(nombresSprites);
	}	

/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual. 
	 */
	public void colisionar (Actor a)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("Rompible.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			Mario mario = checkActorJugador (pj);			
			if (colisionAbajo(mario))
			{//Si la colisión de Mario es desde abajo, romper plataforma, sino, no hacer nada.			
				mario.golpearRompible(this);											
			}
		}
		catch (Exception e)
		{
			throw new ColisionException ("Rompible.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 */
	public void colisionarBola (BolaFuego bola)
	{
		//No le afecta.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		//Nada ocurre.
	}
	
	/**
	 * Verifica si la colisión con el Actor proviene desde abajo.
	 * @param mario Mario con el que se colisiona.
	 * @return Verdadero si Mario a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Mario mario)
	{		
		//Mario se encuentra debajo de la plataforma si y solo si para Mario el vectorDistancia = (0,1).
		int [] vector = mario.vectorDistancia(this);
		return (vector[0] == 0 && vector[1] == 1);
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		ActualizadorNivel.act().eliminarPlataforma(this);
		
		celdaActual.setOcupada(false);
		
		super.morir();
	}
}