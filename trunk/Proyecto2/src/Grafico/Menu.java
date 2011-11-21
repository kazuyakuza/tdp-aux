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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Representa el Menú de la Ventana Principal del juego donde se ejecuta el mismo.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class Menu extends JPanel
{
	
	//Variables de Clase
	private int botonLargo = 150;
	private int botonAlto = 30;
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private int largo, alto;
	private JButton NuevoJuego, Info, Salir;

	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Menú para la Ventana Principal vP.
	 * 
	 * @param vP Ventana Principal en la que se colocará el Menú.
	 */
	public Menu (VentanaPrincipal vP)
	{
		super();
		ventanaPrincipal = vP;
		largo = vP.largo();
		alto = vP.alto();
		initGUI();
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa la GUI del Menú.
	 */
	private void initGUI ()
	{
		try
		{
			this.setPreferredSize(new Dimension(largo, alto));
			this.setLayout(null);
			this.setSize(largo, alto);
			{
				NuevoJuego = new JButton();
				this.add(NuevoJuego);
				NuevoJuego.setText("Nuevo Juego");
				NuevoJuego.setBounds((largo/2-botonLargo/2), (alto/2-botonAlto/2-45), botonLargo, botonAlto);
				NuevoJuego.addActionListener(listenerNuevoJuego());
			}
			{
				Info = new JButton();
				this.add(Info);
				Info.setText("Info");
				Info.setBounds((largo/2-botonLargo/2), (alto/2-botonAlto/2), botonLargo, botonAlto);
				Info.addActionListener(listenerInfo());
			}
			{
				Salir = new JButton();
				this.add(Salir);
				Salir.setText("Salir");
				Salir.setBounds((largo/2-botonLargo/2), (alto/2-botonAlto/2+45), botonLargo, botonAlto);
				Salir.addActionListener(listenerSalir());
			}
		}
		catch (Exception e)
		{
			ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
		}
	}
	
	/**
	 * Elimina todos los elementos gráficos del Manú.
	 */
	public void limpiar ()
	{
		this.removeAll();
		NuevoJuego = null;
		Info = null;
		Salir = null;
		ventanaPrincipal = null;
	}
	
	/*Listeners*/
	
	/**
	 * Action Listener para el botón Nuevo Juego.
	 * 
	 * @menu Menú donde está el botón.
	 * @return Action Listener para el botón Nuevo Juego.
	 */
	private ActionListener listenerNuevoJuego ()
	{
		return new ActionListener()
        {
            //Método del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.pedirDatosJugador();
            		ventanaPrincipal.eliminarMenu();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
        		}
            	
            }
           };
	}
	
	/**
	 * Action Listener para el botón Info.
	 * 
	 * @menu Menú donde está el botón.
	 * @return Action Listener para el botón Info
	 */
	private ActionListener listenerInfo ()
	{
		return new ActionListener()
        {
            //Método del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.mostrarInfo();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
        		}
            	
            }
           };
	}
	
	/**
	 * Action Listener para el botón Salir.
	 * 
	 * @menu Menú donde está el botón.
	 * @return Action Listener para el botón Salir.
	 */
	private ActionListener listenerSalir ()
	{
		return new ActionListener()
        {
            //Método del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.salir();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
        		}
            	
            }
           };
	}

}