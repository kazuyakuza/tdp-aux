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
package Proyecto2.src.Logica.Personajes;

/**
 * Contiene las acciones de un Personaje Seleccionable.
 * Personaje Seleccionable es todo aquel Personaje del Juego que puede ser seleccionado y controlado por el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface PjSeleccionable extends Personaje
{
	
	/*COMANDOS*/
	
	/**
	 * Realiza la acci�n "arriba".
	 */
	public void arriba ();
	
	/**
	 * Realiza la acci�n "abajo".
	 */
	public void abajo ();
	
	/**
	 * Realiza la acci�n "izquierda".
	 */
	public void izquierda ();
	
	/**
	 * Realiza la acci�n "derecha".
	 */
	public void derecha ();
	
	/**
	 * Realiza la acci�n "A".
	 */
	public void A ();
	
	/**
	 * Realiza la acci�n "B".
	 */
	public void B ();
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo.
	 */
	public void serDaniado ();

}