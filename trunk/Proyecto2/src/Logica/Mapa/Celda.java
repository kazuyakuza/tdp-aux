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
package Proyecto2.src.Logica.Mapa;

import java.util.Iterator;

import Proyecto2.src.Excepciones.AccionActorException;
import Proyecto2.src.Excepciones.PosicionIncorrectaException;
import Proyecto2.src.Librerias.TDALista.ListaPositionSimple;
import Proyecto2.src.Librerias.TDALista.Position;
import Proyecto2.src.Librerias.TDALista.PositionList;
import Proyecto2.src.Logica.Actor;
import Proyecto2.src.Logica.NoPersonajes.Estructura;

/**
 * Representa un espacio en el mapa del Juego.
 * Un espacio es una porción del Mapa que esta totalmende delimitada, y es única en el Mapa.
 * La celda puede contener actores, o estar totalmente ocupada.
 * Si una Celda está totalmente ocupada, ningún otro Actor puede ingresar a la Celda.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Celda
{
	
	//Variables de Instancia
	protected boolean totalmenteOcupada; //True = ningún otro Actor puede ingresar a la Celda.
	protected int posFila, posColumna; //Posición en su Bloque.
	protected PositionList<Actor> actores; //Actores actuales en la Celda.
	protected Bloque bloque; //Bloque al que pertenece la Celda.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Celda con los datos ingresados.
	 * 
	 * @param ocupada Si está totalmente ocupada o no la celda.
	 * @param file Fila de la celda en el Mapa.
	 * @param columna Columna de la celda en el Mapa.
	 * @param b Bloque al que pertenece la Celda.
	 * @exception PosicionIncorrectaException Si se intenta asignar una posición incorrecta a la nueva Celda.
	 */
	public Celda (boolean ocupada, int fila, int columna, Bloque b) throws PosicionIncorrectaException
	{
		if ((fila < 0) || (columna < 0))
			throw new PosicionIncorrectaException ("Imposible asignar la posición (" + fila + "," + columna + ") a la nueva Celda.");
		totalmenteOcupada = ocupada;
		posFila = fila;
		posColumna = columna;
		bloque = b;
		actores = new ListaPositionSimple<Actor> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega un Actor a la Celda.
	 * 
	 * @param actor Actor a agregar.
	 * @exception NullPointerException Si se ingresa un Actor igual a null.
	 * @exception AccionActorException Si se intenta agregar un Actor a una Celda totalmente ocupada.
	 */
	public void agregarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("El Actor que está intentando agregar a la Celda es null.");
		if (totalmenteOcupada)
			throw new AccionActorException("Imposible agregar Actor a la celda de posición (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda está totalmente ocupada.");
		actores.addLast(actor);
	}
	
	/**
	 * Agrega Estructura a la Celda.
	 * 
	 * @param estructura Estructura a agregar.
	 * @exception NullPointerException Si se ingresa una Estructura igual a null.
	 * @exception AccionActorException Si se intenta agregar una Estructura a una Celda no totalmente ocupada.
	 */
	public void agregarEstructura (Estructura estructura) throws NullPointerException, AccionActorException
	{
		if (estructura == null)
			throw new NullPointerException ("El Actor que está intentando agregar a la Celda es null.");
		if (!totalmenteOcupada)
			throw new AccionActorException("Imposible agregar Piso a la celda de posición (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda no está totalmente ocupada.");
		actores.addLast((Actor) estructura);
	}
	
	/**
	 * Saca el Actor pasado por parámetro.
	 * 
	 * @param actor Actor a sacar de la Celda.
	 * @return Actor sacado de la Celda.
	 * @exception NullPointerException Si se ingresa un Actor igual a null.
	 * @exception NullPointerException Si se ingresa un Actor que no pertenece a la Celda.
	 */
	public Actor sacarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("El Actor que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") es null.");
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != actor))
			p = actores.next(p);
		if (p.element() != actor)
			throw new AccionActorException ("El Actor que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma.");
		return actores.remove(p);
	}
	
	/**
	 * Setea si esta totalmenteOcupada o no la Celda.
	 * 
	 * @param ocupada True: la celda esta totalmente ocupada.
	 */
	public void setOcupada (boolean ocupada)
	{
		totalmenteOcupada = ocupada;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve un Iterador con los Actores de la Celda.
	 * 
	 * @return Iterador con los Actores de la Celda.
	 */
	public Iterator<Actor> getActores ()
	{
		return actores.iterator();
	}
	
	/**
	 * Devuelve el estado de la Celda.
	 * 
	 * @return True:  si la Celda está totalmente ocupada.
	 *         False: demas casos.
	 */
	public boolean isOcupada ()
	{
		return totalmenteOcupada;
	}
	
	/**
	 * Devuelve un arreglo de dos componentes con la posición de la Celda.
	 * Componente [0] = posFila.
	 * Componente [1] = posColumna.
	 * 
	 * @return Arreglo de dos componentes con {posFila, posColumna};
	 */
	public int[] getPosicion ()
	{
		return new int[] {posFila, posColumna};
	}
	
	/**
	 * Devuelve la posición horizontal (eje x) de la Celda.
	 * 
	 * @return La posición horizontal de la Celda.
	 */
	public int getPosFila ()
	{
		return posFila;
	}
	
	/**
	 * Devuelve la posición vertical (eje y) de la Celda.
	 * 
	 * @return La posición vertical de la Celda.
	 */
	public int getPosColumna ()
	{
		return posColumna;
	}
	
	/**
	 * Devuelve el Bloque al que pertenece la Celda.
	 * 
	 * @return Bloque al que pertenece la Celda.
	 */
	public Bloque getBloque ()
	{
		return bloque;
	}

}