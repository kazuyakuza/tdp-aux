package ProyectoX.Grafico;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ProyectoX.Excepciones.StringEmptyException;

/**
 * Representa el Panel donde se piden los Datos del Jugador para el juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class PedirDatosJugador extends JPanel
{
	
	//Variables de Clase
	private int botonLargo = 150;
	private int botonAlto = 30;
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private int largo, alto;
	private JButton EmpezarJuego, Cancelar;
	private JLabel jLabel;
	private JTextField NombreJugador;

	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Menú para la Ventana Principal vP.
	 * 
	 * @param vP Ventana Principal en la que se colocará el Menú.
	 */
	public PedirDatosJugador (VentanaPrincipal vP)
	{
		super();
		ventanaPrincipal = vP;
		largo = vP.largo();
		alto = vP.alto();
		initGUI();
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa la GUI del Panel.
	 */
	private void initGUI ()
	{
		try
		{
			this.setPreferredSize(new Dimension(largo, alto));
			this.setLayout(null);
			this.setSize(largo, alto);
			{
				jLabel = new JLabel();
				this.add(jLabel);
				jLabel.setText("Ingrese su Nombre");
				jLabel.setBounds((largo/2-130), (alto/2-50), 120, 30);
			}
			{
				NombreJugador = new JTextField();
				this.add(NombreJugador);
				NombreJugador.setBounds((largo/2+10), (alto/2-50), 200, 30);
			}
			{
				EmpezarJuego = new JButton();
				this.add(EmpezarJuego);
				EmpezarJuego.setText("Empezar Juego");
				EmpezarJuego.setBounds((largo/2-botonLargo-10), (alto/2-botonAlto+20), botonLargo, botonAlto);
				EmpezarJuego.addActionListener(listenerEmpezarJuego());
			}
			{
				Cancelar = new JButton();
				this.add(Cancelar);
				Cancelar.setText("Cancelar");
				Cancelar.setBounds((largo/2+10), (alto/2-botonAlto+20), botonLargo, botonAlto);
				Cancelar.addActionListener(listenerCancelar());
			}
		}
		catch (Exception e)
		{
			ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
		}
	}
	
	/**
	 * Elimina todos los elementos gráficos del Panel.
	 */
	public void limpiar ()
	{
		this.removeAll();
		EmpezarJuego = null;
		Cancelar = null;
		jLabel = null;
		NombreJugador = null;
		ventanaPrincipal = null;
	}
	
	/*Listeners*/
	
	/**
	 * Action Listener para el botón Empezar Juego.
	 * 
	 * @panel Panel donde está el botón.
	 * @return Action Listener para el botón Empezar Juego.
	 */
	private ActionListener listenerEmpezarJuego ()
	{
		return new ActionListener()
        {
            //Método del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		if (NombreJugador.getText().equals(""))
            			throw new StringEmptyException("Falta Ingresar Nombre.");
            		ventanaPrincipal.nuevoJuego(NombreJugador.getText());
            		ventanaPrincipal.eliminarPedirDatosJugador();
            	}
            	catch (StringEmptyException sE)
            	{
            		ventanaPrincipal.mensajeError("Error", "Debe Ingresar su nombre", false);
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
        		}
            	
            }
           };
	}
	
	/**
	 * Action Listener para el botón Cancelar.
	 * 
	 * @panel Panel donde está el botón.
	 * @return Action Listener para el botón Cancelar.
	 */
	private ActionListener listenerCancelar ()
	{
		return new ActionListener()
        {
            //Método del ActionListener
            public void actionPerformed (ActionEvent event)
            {
            	try
            	{
            		ventanaPrincipal.mostrarMenu();
            		ventanaPrincipal.eliminarPedirDatosJugador();
            	}
            	catch (Exception e)
            	{
            		ventanaPrincipal.mensajeError("ERROR", e.getMessage(), true);
        		}
            	
            }
           };
	}

}