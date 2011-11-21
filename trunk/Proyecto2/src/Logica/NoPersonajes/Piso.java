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
package Proyecto2.src.Logica.NoPersonajes;

import Proyecto2.src.Excepciones.AccionActorException;
import Proyecto2.src.Excepciones.ColisionException;
import Proyecto2.src.Grafico.Sprite.CargadorSprite;
import Proyecto2.src.Logica.Actor;
import Proyecto2.src.Logica.Mapa.Celda;

/**
 * Representa al Piso en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Piso extends Actor implements Estructura
{
	
	//Variables de Clase
	private static final String dirRecursos = "Estructuras/";
	private static final String [] nombresSprites = {dirRecursos + "Piso.png"};

	/**
	 * Crea una Plataforma Irrompible.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Piso(CargadorSprite cargadorSprite)
	{
		super(nombresSprites, cargadorSprite);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la acci�n de colisionar con otro Actor a.
	 * No tiene ning�n efecto con este Actor.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce alg�n error en la colisi�n. 
	 */
	public void colisionar (Actor a) throws ColisionException
	{
		/*No hace nada, no tiene efecto sobre otros Actores.*/
	}
	
	/**
	 * Realiza la acci�n de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException
	{
		/*No hace nada, no tiene efecto sobre otros Actores.*/
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones(Celda c)
	{
		/*No hace nada, nunca ocurre.*/	
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		PG = 0;
	}
	
	/**
	 * Realiza la Acci�n caer, producida por el efecto de la Gravedad.
	 * No tiene ning�n efecto en este Actor.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al caer.
	 */
	public void caer () throws AccionActorException
	{
		/*No hace nada, nunca ocurre.*/
	}
	
	/**
	 * Realiza la acci�n de morir del Actor.
	 * 
	 * No tiene ning�n efecto en este Actor.
	 */
	public void morir()
	{
		/*No hace nada, nunca ocurre.*/
	}

}