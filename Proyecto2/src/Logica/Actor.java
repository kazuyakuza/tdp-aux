/*******************************************************************************
 * Copyright (c) 2001, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package Proyecto2.src.Logica;

import Proyecto2.src.Excepciones.AccionActorException;
import Proyecto2.src.Excepciones.ColisionException;
import Proyecto2.src.Grafico.Sprite.CargadorSprite;
import Proyecto2.src.Grafico.Sprite.SpriteManager;
import Proyecto2.src.Logica.Mapa.Celda;

/**
 * Representa a todos los objetos virtuales que pueden desarrolar una "actuación" dentro del juego.
 * Por "actuación" se entiende a algún tipo de interacción entre el Juego en si, otro objecto del juego, o el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class Actor
{
	
	//Variables de Instancia
	  //Grafica y Sonido
	protected SpriteManager spriteManager;
	//private SoundManager soundManager;
	  //Logica
	protected Celda celdaActual; 
	protected int PG;//Potencia de la Gravedad.
					 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acción arriba.
					 //Si PG=0, el Actor no es afectado por la Gravedad (está sobre un lugar sólido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acción de caer.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Recibe los nombres de los Sprites para el Actor actual, y crea el SpriteManager.
	 * El SpriteManager carga los sprites con el CargadorSprite pasado por parámetro.
	 * 
	 * @param nombresSprites Nombres de los archivos de las imagenes del Sprite para este Actor.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	protected Actor (String[] nombresSprites, CargadorSprite cargadorSprite)
	{
		spriteManager = new SpriteManager (nombresSprites, cargadorSprite);
		celdaActual = null;
		PG = 0;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el SpriteManager actual por el nuevo sp.
	 * 
	 * @param sp Nuevo SpriteManager.
	 */
	public void setSpriteManager (SpriteManager sp)
	{
		spriteManager = sp;
	}
	
	/**
	 * Cambia la Celda Actual por la Celda C.
	 * 
	 * @param c Nueva Celda Actual.
	 */
	public void setCeldaActual (Celda c)
	{
		celdaActual = c;
		spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Provoca las colisiones con los Actores en la Celda c.
	 * Mueve Actor a la Celda c.
	 * Actualiza el SpriteManager.
	 * 
	 * @param c Celda a la que se mueve el Actor.
	 */
	public void moverseAcelda (Celda c)
	{
		producirColisiones(c);
		celdaActual.sacarActor(this);
		celdaActual = c;
		celdaActual.agregarActor(this);
		spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected abstract void producirColisiones (Celda c);
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (!(PG < 0))
			PG -= efecto;
	}
	
	/*COMANDOS ABSTRACTOS*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor a.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionar (Actor a) throws ColisionException;
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionarPj (Actor actorJugador) throws ColisionException;
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * 
	 * @exception AccionActorException Si se produce algún error al caer.
	 */
	public abstract void caer () throws AccionActorException;
	
	/**
	 * Realiza la acción de morir del Actor.
	 */
	public void morir()
	{
		/*spriteManager.setEliminar();
		celdaActual.sacarActor(this);
		
		spriteManager = null;
		celdaActual = null;
		PG = 0;*/
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el SpriteManager actual.
	 * 
	 * @return SpriteManager actual.
	 */
	public SpriteManager getSpriteManager ()
	{
		return spriteManager;
	}
	
	/**
	 * Devuelve la celda actual del Actor.
	 * 
	 * @return Celda actual del Actor.
	 */
	public Celda getCeldaActual ()
	{
		return celdaActual;
	}
	
    /**
	 * Devuelve la Potencia de la Gravedad sobre este Actor.
	 * 
	 * @return Potencia de la Gravedad sobre este Actor.
	 */
	public int getPG ()
	{
		return PG;
	}

}