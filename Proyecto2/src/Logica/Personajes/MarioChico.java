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

import java.util.Iterator;

import Proyecto2.src.Excepciones.AccionActorException;
import Proyecto2.src.Grafico.Sprite.CargadorSprite;
import Proyecto2.src.Logica.Actor;
import Proyecto2.src.Logica.Mapa.Celda;


/**
 * Representa a Mario Chico, uno de los Personajes Seleccionables por el Jugador en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioChico extends Mario
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicación en los índices es:
	                                                {dirRecursos + "Mario-Dead.gif", //0: Mario muerto
		                                             dirRecursos + "Mario.gif",      //1: Mario quieto
		                                             dirRecursos + "Mario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "Mario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "Mario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "Mario-Jump.gif"};//5: Mario saltando
	private static int muerto = 0;
	private static int quieto = 1;
	private static int caminando = 2;
	private static int saltando = 5;
	
	private static int potenciaSalto = 4;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Personaje Seleccionable Mario Chico.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public MarioChico (CargadorSprite cargadorSprite)
	{
		super(nombresSprites, cargadorSprite);
		spriteManager.cambiarSprite(quieto);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Mario realiza la acción de saltar.
	 * 
	 * @exception AccionActorException Si se produce algún error al saltar.
	 */
	public void saltar () throws AccionActorException
	{
		Celda celdaSuperior = celdaActual;
		try 
		{
			if (PG == 0)
			{
				PG = potenciaSalto;
				spriteManager.cambiarSprite(saltando);
				int i=0;
				boolean terminar = false;
				while ((!celdaActual.getBloque().esLimite(celdaActual)) && !terminar && i<potenciaSalto)
				{
					celdaSuperior = celdaActual.getBloque().getSuperior(celdaActual);
					if (!celdaSuperior.isOcupada())
					{
						moverseAcelda(celdaSuperior);
						i++;
					}
					else
						terminar = true;				 
				}
				spriteManager.cambiarSprite(quieto);
			}
		}
		catch (Exception e)
		{
			throw new AccionActorException ("Imposible realizar la acción saltar a/desde Celda de posición (" + celdaSuperior.getPosFila() + "," + celdaSuperior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e.getMessage());
		}
	}
		
	/**
	 * Mario realiza la acción de moverse hacia la izquierda.
	 * 
	 * @exception AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (!celdaActual.getBloque().esLimite(celdaActual))
			{
				spriteManager.cambiarSprite(-caminando);
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
				spriteManager.cambiarSprite(-quieto);
			}
		}
		catch (Exception e)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e.getMessage());
		}
	}
		
	/**
	 * Mario realiza la acción de moverse hace la derecha.
	 * 
	 * @exception AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (!celdaActual.getBloque().esLimite(celdaActual))
			{
				spriteManager.cambiarSprite(caminando);
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
				spriteManager.cambiarSprite(quieto);
			}
		}
		catch (Exception e)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @exception AccionActorException Si se produce algún error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		
	}
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @exception AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		
	}
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @exception AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public void accionB () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionarPj(this);		
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 * 
	 * @exception AccionActorException Si se produce algún error al crecer.
	 */
	public void crecer () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo.
	 */
	public void serDaniado () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de Mario.
	 */
	public void morir ()
	{
		spriteManager.cambiarSprite(muerto);
		super.morir();
	}
	
}