package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa a una Plataforma Irrompible en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Irrompible extends Actor implements Plataforma
{
	
	//Variables de Clase
	private static final String dirRecursos = "Estructuras/Plataformas/";
	private static final String [] nombresSprites = {dirRecursos + "Empty-Block.gif"};

	/**
	 * Crea una Plataforma Irrompible.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Irrompible(CargadorSprite cargadorSprite)
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
	 */
	public void colisionarPj (PjSeleccionable pj)
	{
		//No le afecta.
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
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		//Nunca ocurre.
	}

}