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

import java.awt.event.KeyListener;

/**
 * Representa las operaciones básicas que debe tener un Control.
 * 
 * Control: sistema por el cual se puede efectuar determinadas acciones.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface Control extends KeyListener
{
	
	/*CONSULTAS*/
	
	/**
	 * Verifica si se esta realizando la acción "arriba", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean arriba ();
	
	/**
	 * Verifica si se esta realizando la acción "abajo", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean abajo ();
	
	/**
	 * Verifica si se esta realizando la acción "izquierda", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean izquierda ();
	
	/**
	 * Verifica si se esta realizando la acción "derecha", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean derecha ();
	
	/**
	 * Verifica si se esta realizando la acción "A", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean A ();
	
	/**
	 * Verifica si se esta realizando la acción "B", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean B ();
	
	/**
	 * Verifica si se esta realizando la acción "ESC", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean ESC ();
	
	/**
	 * Verifica si se esta realizando la acción "aceptar", y devuelve el resultado.
	 * 
	 * @return True:  se esta realizando la acción.
	 *         False: caso contrario.
	 */
	public boolean aceptar ();

}