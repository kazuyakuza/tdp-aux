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
package Proyecto2.src.Logica.NoPersonajes.Especiales;

import Proyecto2.src.Excepciones.AccionActorException;
import Proyecto2.src.Excepciones.ColisionException;
import Proyecto2.src.Grafico.Sprite.CargadorSprite;
import Proyecto2.src.Logica.Actor;
import Proyecto2.src.Logica.ControlCentral;
import Proyecto2.src.Logica.Mapa.Celda;

/**
 * Representa al lugar de llegada para ganar un Nivel.
 * Cuando un PjSeleccionable colisiona (se ubica en la misma celda) con una Llegada, el Jugador gana y termina el Nivel.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public class Llegada extends Actor
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Vine2.gif", dirRecursos + "Goal-Flag.gif"};
	
	//Atributos de Instancia
	private ControlCentral controlCentral;
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea una Llegada.
	 * 
	 * @param cc ControlCentral del Juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Llegada (ControlCentral cc, CargadorSprite cargadorSprite)
	{
		super (nombresSprites, cargadorSprite);
		controlCentral = cc;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor a.
	 * No tiene ningún efecto con este Actor.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce algún error en la colisión. 
	 */
	public void colisionar (Actor a) throws ColisionException
	{
		/*No hace nada, no tiene efecto sobre Actores que no sean Personajes.*/
	}
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (Actor actorJugador) throws ColisionException
	{
		controlCentral.ganarNivel();
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
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		PG = 0;
	}
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * No tiene ningún efecto en este Actor.
	 * 
	 * @exception AccionActorException Si se produce algún error al caer.
	 */
	public void caer () throws AccionActorException
	{
		/*No hace nada, nunca ocurre.*/
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		/*No hace nada, nunca ocurre.*/
	}
	
}