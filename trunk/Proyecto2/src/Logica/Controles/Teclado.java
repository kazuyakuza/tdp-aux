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
package Proyecto2.src.Logica.Controles;

import java.awt.event.KeyEvent;

/**
 * Representa al Control Teclado.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Teclado implements Control
{
	
	//Variables de Instancia
	private boolean arriba, abajo, izquierda, derecha, A, B, ESC, aceptar;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Control Teclado.
	 */
	public Teclado ()
	{
		arriba = abajo = izquierda = derecha = A = B = ESC = aceptar = false;
	}

	/*COMANDOS*/
	
	/**
	 * Actualiza las acciones que se están realizando deacuerdo a la tecla que generó el evento.
	 * 
	 * @param e Evento generado por un tecla.
	 */
	public void keyPressed (KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP : arriba = true;
			break;
			case KeyEvent.VK_DOWN : abajo = true;
			break;
			case KeyEvent.VK_LEFT : izquierda = true;
			break;
			case KeyEvent.VK_RIGHT : derecha = true;
			break;
			case KeyEvent.VK_A : A = true;
			break;
			case KeyEvent.VK_S : B = true;
			break;
			case KeyEvent.VK_ESCAPE : ESC = true;
			break;
			case KeyEvent.VK_ENTER : aceptar = true;
			break;
		}
	}

	/**
	 * Actualiza las acciones que se están realizando deacuerdo a la tecla que generó el evento.
	 * 
	 * @param e Evento generado por un tecla.
	 */
	public void keyReleased (KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP : arriba = false;
			break;
			case KeyEvent.VK_DOWN : abajo = false;
			break;
			case KeyEvent.VK_LEFT : izquierda = false;
			break;
			case KeyEvent.VK_RIGHT : derecha = false;
			break;
			case KeyEvent.VK_A : A = false;
			break;
			case KeyEvent.VK_S : B = false;
			break;
			case KeyEvent.VK_ESCAPE : ESC = false;
			break;
			case KeyEvent.VK_ENTER : aceptar = false;
			break;
		}
	}

	/**
	 * Méteodo necesario para implementar la interface Control.
	 */
	public void keyTyped (KeyEvent e) {}

	/*CONSULTAS*/
	
	/**
	 * Verifica si se esta realizando la acción "arriba", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean arriba ()
	{
		return arriba;
	}
	
	/**
	 * Verifica si se esta realizando la acción "abajo", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean abajo ()
	{
		return abajo;
	}
	
	/**
	 * Verifica si se esta realizando la acción "izquierda", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean izquierda ()
	{
		return izquierda;
	}
	
	/**
	 * Verifica si se esta realizando la acción "derecha", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean derecha ()
	{
		return derecha;
	}
	
	/**
	 * Verifica si se esta realizando la acción "A", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean A ()
	{
		return A;
	}
	
	/**
	 * Verifica si se esta realizando la acción "B", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean B ()
	{
		return B;
	}
	
	/**
	 * Verifica si se esta realizando la acción "ESC", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean ESC ()
	{
		return ESC;
	}
	
	/**
	 * Verifica si se esta realizando la acción "aceptar", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean aceptar ()
	{
		return aceptar;
	}

}