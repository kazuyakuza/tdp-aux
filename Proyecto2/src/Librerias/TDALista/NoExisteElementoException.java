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
 * No Existe Elemento Exception: Excepción en tiempo de ejecución provocada al intentar pedir elemento siguiente de una posición null.
 * 
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 */
@SuppressWarnings("serial")
public class NoExisteElementoException extends RuntimeException
{

	/**
	 * Recibe la información del error, y crea la excepción.
	 * 
	 * @param error Información del error.
	 */
	public NoExisteElementoException(String error)
	{
		super(error);
	}
	
	/**
	 * Devuelve la información del error de la excepción.
	 * 
	 * @return Información del error de la excepción.
	 */
	public String obtenerError ()
	{
		return getMessage();
	}
	
}