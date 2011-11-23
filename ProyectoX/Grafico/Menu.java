package ProyectoX.Grafico;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Representa el Men� de la Ventana Principal del juego donde se ejecuta el mismo.
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
	 * Crea un Men� para la Ventana Principal vP.
	 * 
	 * @param vP Ventana Principal en la que se colocar� el Men�.
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
	 * Inicializa la GUI del Men�.
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
			ventanaPrincipal.mensajeError("Error", "Menu.initGUI()" + "\n" + e.getMessage(), true);
		}
	}
	
	/**
	 * Elimina todos los elementos gr�ficos del Man�.
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
	 * Action Listener para el bot�n Nuevo Juego.
	 * 
	 * @menu Men� donde est� el bot�n.
	 * @return Action Listener para el bot�n Nuevo Juego.
	 */
	private ActionListener listenerNuevoJuego ()
	{
		return new ActionListener()
        {
            //M�todo del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.pedirDatosJugador();
            		ventanaPrincipal.eliminarMenu();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", "Menu.listenerNuevoJuego()" + "\n" + e.getMessage(), true);
         		}
            	
            }
        };
	}
	
	/**
	 * Action Listener para el bot�n Info.
	 * 
	 * @menu Men� donde est� el bot�n.
	 * @return Action Listener para el bot�n Info
	 */
	private ActionListener listenerInfo ()
	{
		return new ActionListener()
        {
            //M�todo del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.mostrarInfo();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", "Menu.listenerInfo()" + "\n" + e.getMessage(), true);
        		}
            	
            }
           };
	}
	
	/**
	 * Action Listener para el bot�n Salir.
	 * 
	 * @menu Men� donde est� el bot�n.
	 * @return Action Listener para el bot�n Salir.
	 */
	private ActionListener listenerSalir ()
	{
		return new ActionListener()
        {
            //M�todo del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.salir();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", "Menu.listenerSalir()" + "\n" + e.getMessage(), true);
        		}
            	
            }
           };
	}

}