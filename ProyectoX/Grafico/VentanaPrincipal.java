package ProyectoX.Grafico;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ProyectoX.Logica.ControlCentral;

/**
 * Representa la Ventana Principal del juego donde se ejecuta el mismo.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame
{
	
	//Variables de Clase
	private int largo = 640;
	private int alto = 480;
	
	private String version = "Versión 0.1";
	private String[] autores = {"Javier Eduardo Barrocal","Pablo Isaias Chacar"};
	private String[] infoExtra = {"Proyecto X", "Tecnología de Programación", "2do Cuatrimestre 2011"};
	private String tituloJuego = "Mario TDP 2011";
	
	//Variables de Instancia
	private Menu menu;
	private PedirDatosJugador pnj;
	private JPanel jPanelEscenario;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Ventana Principal, y un Menú dentro de la misma.
	 */
	public VentanaPrincipal ()
	{
		initGUI();
		inicializacionVariables();
		
		//Posicionar VentanaPrincipal en el centro de la pantalla.
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension tamanio = tk.getScreenSize();
		int pX = (int) tamanio.getWidth()/2 - (this.getWidth())/2;
		int pY = (int) tamanio.getHeight()/2 - (this.getHeight())/2;
		setLocation(pX, pY);
		
		setTitle(tituloJuego);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa la GUI de la Ventana Principal.
	 */
	private void initGUI ()
	{
		try
		{
			setPreferredSize(new Dimension(largo, alto));
			{
				mostrarMenu();
			}
			setSize(largo, alto);
			pack();
		}
		catch (Exception e)
		{
		    mensajeError("ERROR", "VentanaPrincipal.initGUI()" + "\n" + e.getMessage(), true);
		}
	}
	
	/**
	 * Inicializa las Varaibles de la Ventana Principal.
	 */
	private void inicializacionVariables ()
	{
		menu = null;
		pnj = null;
		jPanelEscenario = null;
	}
	
	/**
	 * Crea un Menú y lo agrega a la Ventana.
	 */
	protected void mostrarMenu ()
	{
		menu = new Menu (this);
		this.setContentPane(menu);
		this.repaint();
	}
	
	/**
	 * Quita, limpia y elimina el Menú de la Ventana.
	 */
	protected void eliminarMenu ()
	{
		if (menu != null)
		{
			menu.limpiar();
			this.remove(menu);
			menu = null;
			this.repaint();
		}
	}
	
	/**
	 * Crea un Panel y lo agrega a la Ventana.
	 */
	protected void pedirDatosJugador ()
	{
		pnj = new PedirDatosJugador(this);
		this.setContentPane(pnj);
		this.repaint();
	}
	
	/**
	 * Quita, limpia y elimina el Panel de la Ventana.
	 */
	protected void eliminarPedirDatosJugador ()
	{
		if (pnj != null)
		{
			pnj.limpiar();
			this.remove(pnj);
			pnj = null;
			this.repaint();
		}
	}
	
	/**
	 * Agrega el Escenario del juego a la Ventana Principal.
	 * 
	 * @param e Escenario a agregar.
	 */
	public void agregarEscenario (Escenario escenario)
	{
		jPanelEscenario = new JPanel();
		jPanelEscenario.setPreferredSize(new Dimension(largo,alto));
		jPanelEscenario.setSize(largo, alto);
		jPanelEscenario.setLayout (null);
		jPanelEscenario.add(escenario);
		this.setContentPane(jPanelEscenario);
		this.repaint();
	}
	
	/**
	 * Elimina de la Ventana Principal el Escenario del Juego actual.
	 */
	public void quitarEscenarioActual ()
	{
		if (jPanelEscenario != null)
		{
			this.remove(jPanelEscenario);
			jPanelEscenario = null;
			this.repaint();
		}
	}
	
	/**
	 * Crea un Nuevo Juego.
	 * 
	 * Crea un Escenario en un nuevo Thread.
	 * Crea un ControlCentral en un nuevo Thread.
	 * 
	 * @param nombreJugador Nombre del jugador del nuevo juego.
	 */
	public void nuevoJuego (String nombreJugador)
	{
		Escenario e = new Escenario(this);
		ControlCentral cC = new ControlCentral(this, nombreJugador, e);
		Thread t = new Thread (cC);
		cC.agregarThread(t);
		t.start();
	}
	
	/**
	 * Muestra la Información del Juego.
	 */
	public void mostrarInfo ()
	{
		mensajeError (tituloJuego + " " + version,
				      tituloJuego + "\n" +
				      "Versión " + version + "\n" +
				      "\n" +
				      "Autores:" + "\n" +
				      autores[0] + "\n" +
				      autores[1] + "\n" +
				      "\n" +
				      infoExtra[0] + "\n" +
				      infoExtra[1] + "\n" +
				      infoExtra[2] + "\n",false);
	}
	
	/**
	 * Cierra el Juego.
	 */
	public void salir ()
	{
		System.exit(0);
	}
	
	/*CONSULTAS*/
	
	/**
	 * Muestra un Mensaje de Error, con su título y mensaje, provocado por o en el Compenente ventana.
	 * 
	 * @param ventana Componente que llama a este método, donde se generó el error.
	 * @param titulo Título del Mensaje de Error.
	 * @param mensaje Mensaje del Error.
	 */
	public void mensajeError (String titulo, String mensaje, boolean cerrar)
	{
		JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.PLAIN_MESSAGE);
		if (cerrar)
			salir();
	}
	
	/**
	 * Devuelve el largo del frame dentro de la ventana.
	 * 
	 * @return Largo de la ventana.
	 */
	public int largo ()
	{
		return largo - 6;
	}
	
	/**
	 * Devuelve el alto del frame dentro de la ventana.
	 * 
	 * @return Alto de la ventana.
	 */
	public int alto ()
	{
		return alto - 26;
	}

}