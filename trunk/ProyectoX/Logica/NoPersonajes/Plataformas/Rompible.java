package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.NoPersonajes.Moneda;
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
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Rompible(CargadorSprite cargadorSprite)
	{
		super(nombresSprites, cargadorSprite);
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
			throw new NullPointerException ("EspecialMonedas.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			Mario mario = checkActorJugador (pj);			
			if ( (this.celdaActual.getBloque().hayInferior(this.celdaActual)) && (colisionAbajo(mario)) )
			{//Si la colisión de Mario es desde abajo, romper plataforma, sino, no hacer nada.			
				mario.golpearRompible(this);											
			}
		}
		catch (Exception e)
		{
			throw new ColisionException ("EspecialMonedas.colisionarPj()" + "\n" +
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
		//Nada ocurre	
	}
	
	/**
	 * Verifica si la colisión con el Actor proviene desde abajo.
	 * @param a Actor con el que se colisiona.
	 * @return Verdadero si el Actor a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Actor a)
	{
		return this.celdaActual.getBloque().getInferior(this.celdaActual) == a.getCeldaActual();
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		spriteManager.setEliminar();
		celdaActual.sacarActor(this);
		celdaActual.setOcupada(false);
		
		spriteManager = null;		
		celdaActual = null;
		
		upNeeder.notUpdate();
		upNeeder = null;
	}
}
