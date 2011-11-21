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
package Proyecto2.src.Grafico;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Proyecto2.src.Excepciones.CargaRecursoException;
import Proyecto2.src.Excepciones.EscenarioIncompletoException;
import Proyecto2.src.Grafico.Sprite.CargadorSprite;
import Proyecto2.src.Grafico.Sprite.SpriteManager;
import Proyecto2.src.Logica.Controles.Control;

/**
 * Representación gráfica del lugar donde acontecen todas las situaciones del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class Escenario extends Canvas implements Runnable
{
	
	//Variables de Clase
	private final int medidaPixelCelda = 32;//Medida de un lado en pixeles de una celda en el Escenario.
	                                        //Celda = si se dividiera el Escenario usando una matriz, cada celda tendría una medida de lado en pixeles.
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private BufferedImage fondo;
	private BufferStrategy bufferStrategy;
	private BloqueGrafico anterior, actual, siguiente;
	private boolean actualizar; //Indica si el Escenario debe actualizarce o no.
	                            //Si deja de actualizarce, el Thread creado para este Escenario concluye.
	private int largo, alto;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Escenario vacío.
	 * 
	 * @param vP Ventana Principal del Juego.
	 */
	public Escenario (VentanaPrincipal vP)
	{
		super();
		ventanaPrincipal = vP;
		largo = ventanaPrincipal.largo();
		alto = ventanaPrincipal.alto();
		fondo = null;
		anterior = null;
		actual = null;
		siguiente = null;
		actualizar = true;
	}
	
	/*COMANDOS*/
	
	/**
	 * Contruye Gráficamente al Escenario.
	 */
	public void inicializarGrafica ()
	{
		try
		{
			this.setBounds (0, 0, largo, alto);
			this.setIgnoreRepaint(true);
			this.requestFocus();
			this.createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
		
			transparentarCursor();
		}
		catch (Exception e)
		{
			ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
		}
	}
	
	/**
	 * Pone transparente el cursor.
	 */
	private void transparentarCursor ()
	{
		BufferedImage cursor = new CargadorSprite().crearCombatible(10, 10, Transparency.BITMASK);
		Toolkit t = Toolkit.getDefaultToolkit();
		Cursor c = t.createCustomCursor(cursor, new Point(5,5), "null");
		this.setCursor(c);
	}
	
	/**
	 * Agrega el Control c usado por el usuario al Escenario.
	 * 
	 * @param c Control del usuario.
	 */
	public void agregarControl (Control c)
	{
		this.addKeyListener(c);
	}
	
	/**
	 * Agrega el fondo al Escenario.
	 * 
	 * @param fondoNombre Nombre del archivo imagen del fondo.
	 * @param cargadorSprite Cargador de Sprite para cargar la imagen.
	 * @exception CargaRecursoException Error al cargar el Sprite.
	 */
	public void agregarFondo (String fondoNombre, CargadorSprite cargadorSprite) throws CargaRecursoException
	{
		try
		{
			fondo = cargadorSprite.obtenerSprite(fondoNombre, this);
			Graphics2D g = (Graphics2D) fondo.getGraphics();
			g.setPaint(new TexturePaint(fondo, new Rectangle(0, 0, fondo.getWidth(), fondo.getHeight())));
			g.fillRect(0, 0, fondo.getWidth(), fondo.getHeight());
		}
		catch (CargaRecursoException exception)
		{
			throw new CargaRecursoException ("Error al cargar el fondo " + fondoNombre + "." + "\n" +
					                         "Detalles del Error:" + "\n" +
					                         exception.getMessage());
		}
	}
	
	/**
	 * Indica si se debe seguir actualizando el Escenario.
	 * 
	 * @param act Si se debe seguir actualizando el Escenario.
	 */
	public void setActualizar (boolean act)
	{
		actualizar = act;
	}
	
	/**
	 * Cambia el BloqueGrafico anterior por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficoAnterior (BloqueGrafico bg)
	{
		anterior = bg;
	}
	
	/**
	 * Cambia el BloqueGrafico actual por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficoActual (BloqueGrafico bg)
	{
		actual = bg;
	}
	
	/**
	 * Cambia el BloqueGrafico siguiente por el pasado por parámetro bg.
	 * 
	 * @param bg Nuevo BloqueGrafico.
	 */
	public void setBloqueGraficosiguiente (BloqueGrafico bg)
	{
		siguiente = bg;
	}
	
	/**
	 * Elimina todos los elementos del escenario.
	 */
	public void limpiar ()
	{
		actualizar = false;
		fondo = null;
		anterior.limpiar();
		anterior = null;
		actual.limpiar();
		actual = null;
		siguiente.limpiar();
		siguiente = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el BloqueGrafico anterior.
	 * 
	 * @return BloqueGrafico anterior.
	 */
	public BloqueGrafico getBloqueGraficoAnterior ()
	{
		return anterior;
	}
	
	/**
	 * Devuelve el BloqueGrafico actual.
	 * 
	 * @return BloqueGrafico actual.
	 */
	public BloqueGrafico getBloqueGraficoActual ()
	{
		return actual;
	}
	
	/**
	 * Devuelve el BloqueGrafico siguiente.
	 * 
	 * @return BloqueGrafico siguiente.
	 */
	public BloqueGrafico getBloqueGraficosiguiente ()
	{
		return siguiente;
	}
	
	/**
	 * Devuelve si se debe actualizar o no el Escenario.
	 * 
	 * @return True:  se debe actualizar el Escenario.
	 *         False: no se debe actualizar el Escenario.
	 */
	public boolean getActualizar ()
	{
		return actualizar;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Método de actualización del Escenario.
	 * 
	 * @exception EscenarioIncompletoException Si el Escenario no ha sido totalmente inicializado.
	 */
	public void run () throws EscenarioIncompletoException
	{
		if ((fondo == null) || (actual == null))
			throw new EscenarioIncompletoException ("El Escenario no ha sido totalmente inicializado." + "\n" +
					                                "(fondo == null) -> " + (fondo == null) + "\n" +
					                                "(actual == null) -> " + (actual == null));
		while (actualizar)
		{
			imprimirBloque(actual);
			
			try
			{
				Thread.sleep(200);
			}
			catch (InterruptedException e)
			{
				ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
			}
		}
	}
	
	/**
	 * Imprime en el Escenario el bloque pasado por parámetro.
	 * 
	 * @param bloqueGrafico Bloque que contiene los sprites a imprimir.
	 */
	public void imprimirBloque (BloqueGrafico bloqueGrafico)
	{
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		imprimirFondo(g);
		//int difPiso = alto - (bloqueGrafico.getNivelPiso() * medidaPixelCelda);
		for (SpriteManager sp: bloqueGrafico.sprites)
		{
			if (sp.isEliminar())
				bloqueGrafico.eliminarSprite(sp);
			else
				g.drawImage(sp.getSpriteActual(), ((int) (sp.posicion()[1] * medidaPixelCelda))// - difPiso
					                            , ((int) (sp.posicion()[0] * medidaPixelCelda))/* - difPiso*/, this);
		}
		bufferStrategy.show();
	}
	
	/**
	 * Imprime el fondo en el Graphics2D g.
	 * 
	 * @param g Graphics2D donde imprimir el fondo del Escenario.
	 */
	private void imprimirFondo (Graphics2D g)
	{
		g.drawImage(fondo, 0, 0, largo, alto, 0, 0, fondo.getWidth(), fondo.getHeight(), this);
	}

}