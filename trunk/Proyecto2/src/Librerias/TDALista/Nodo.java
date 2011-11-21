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
 * Clase Nodo con elemento de tipo Genérico que implementa la interface Position.
 * 
 * Implementación de un Nodo para la clase PositionList, con referencia a un único Nodo siguiente,
 * y referencia a un elemento de tipo Genérico e.
 * 
 * Contiene 2 constructores:
 *  + Nodo Vacío
 *  + Nodo con Nodo siguiente n y elemento Genérico e.
 *  
 * @author Javier E. Barrocal (LU: 87158)
 * @author Emiliano Brustle (LU: 88964)
 * @version 1.1
 * @param <E>
 */
public class Nodo<E> implements Position<E>
{
	
	//Variables de Instancia.
	private Nodo<E> siguiente; //Referencia al siguiente Nodo.
	private E elemento; //Elemento del Nodo.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Nodo vacío.
	 */
	public Nodo ()
	{
		siguiente = null;
		elemento = null;
	}
	
	/**
	 * Crea un Nodo con Nodo siguiente n y con elemento Genérico e.
	 * 
	 * @param n Nodo al que referenciar.
	 * @param e Elemento del Nodo.
	 */
	public Nodo (Nodo<E> n, E e)
	{
		siguiente = n;
		elemento = e;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el Nodo siguiente actual por el nuevo Nodo n.
	 * 
	 * @param n Nuevo Nodo siguiente.
	 */
	public void siguiente (Nodo<E> n)
	{
		siguiente = n;
	}
	
	/**
	 * Cambia el elemento Genérico actual por el nuevo elemento Genérico e.
	 * 
	 * @param e Nuevo elemento Genérico.
	 */
	public void elemento (E e)
	{
		elemento = e;
	}
	
	/**
	 * Elimina la referencia al Nodo siguiente y al elemento del Nodo.
	 */
	public void limpiar ()
	{
		siguiente = null;
		elemento = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Nodo siguiente al Nodo actual.
	 * 
	 * @return Nodo siguiente al Nodo actual.
	 */
	public Nodo<E> siguiente ()
	{
		return siguiente;
	}
	
	/**
	 * Elemento: Devuelve el elemento actual del Nodo.
	 * 
	 * @return Elemento actual del Nodo.
	 */
	public E element ()
	{
		return elemento;
	}

}