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
package Proyecto2.src.Grafico.Sprite;

import java.net.URL;

import Proyecto2.src.Excepciones.CargaRecursoException;

/**
 * Clase utilizada para cargar Recursos.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class CargadorRecurso
{
	
	//Variables de Clase
	private String dirRecursos = "Proyecto2.src/Recursos/";
	
	/*COMANDOS*/
	
	/**
	 * Devuelve el recurso de nombre nom.
	 * 
	 * @param nom Nombre del recurso a devolver.
	 * @return Recurso de nombre nom.
	 * @exception CargaRecursoException Si se produce un error al cargar el recurso solicitado.
	 */
	public Object obtenerRecurso (String nom) throws CargaRecursoException
	{
		Object res = cargarRecurso(dirRecursos + nom);
		return res;
	}
	
	/**
	 * Genera la URL necesaria para cargar el recurso de direccion dir.
	 * 
	 * @param dir Dirección del recurso a cargar.
	 * @return Recurso que está en la dirección dir.
	 * @exception CargaRecursoException Si se produce un error al cargar el recurso solicitado. O si la dirección ingresada es incorrecta y genera una URL null.
	 */
	private Object cargarRecurso (String dir) throws CargaRecursoException
	{
		URL url = null;
		url = getClass().getClassLoader().getResource(dir);
		
		if (url == null)
			throw new CargaRecursoException ("Error al Cargar el Recurso de Dirección: " + dir + "\n" +
                                             "Detalles del error:" + "\n" +
                                             "La dirección ingresada genera una una URL null.");
		
		return cargarRecurso(url);
	}
	
	/**
	 * Lee la url del recurso a cargar, y lo devuelve.
	 * 
	 * @param url Dirección del recurso a cargar.
	 * @return Recurso de dirección url.
	 * @exception CargaRecursoException Si hay un error al leer la URL.
	 */
	public abstract Object cargarRecurso (URL url) throws CargaRecursoException;

}