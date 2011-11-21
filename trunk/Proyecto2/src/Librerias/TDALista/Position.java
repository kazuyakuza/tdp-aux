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
package Proyecto2.src.Librerias.TDALista;

/**
 * Interface Position: Establece las operaciones necesarias para un TDA position.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 * @param <E>
 */
public interface Position<E>
{
	
	/**
	 * Elemento: Devuelve el elemento actual de la posici�n.
	 * 
	 * @return Elemento actual de la posici�n.
	 */
	E element();

}