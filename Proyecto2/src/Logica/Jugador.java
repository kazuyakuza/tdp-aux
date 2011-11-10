package ProyectoX.Logica;

import ProyectoX.Logica.Controles.Control;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa a la Persona Jugador que juega el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Jugador implements Runnable
{
	
	//Variables de Clase
	private static int vidasInicial = 1;
	private static int maxMonedas = 100;
	
	//Variables de Instancia
	private ControlCentral controlCentral;
	protected Control control;
	protected PjSeleccionable personaje;
	protected int monedas, puntos, vidas;
	protected String nombre;
	protected boolean muerto;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Jugador con su respectivo nombre y personaje.
	 * 
	 * @param nom Nombre del Jugador.
	 * @param pj Personaje del Jugador.
	 */
	public Jugador (String nom, PjSeleccionable pj, Control c, ControlCentral cc)
	{
		controlCentral = cc;
		nombre = nom;
		personaje = pj;
		control = c;
		monedas = puntos = 0;
		vidas = vidasInicial;
		muerto = false;
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega una Moneda al Jugador.
	 * 
	 * Si la cantidad de Monedas es igual a 100, se suma una vida y la cantidad de monedas pasa a ser 0.
	 */
	public void agregarMoneda ()
	{
		monedas++;
		if (monedas == maxMonedas)
		{
			monedas = 0;
			agregarVida();
		}
	}
	
	/**
	 * Agrega una vida al Jugador.
	 */
	public void agregarVida ()
	{
		vidas++;
	}
	
	/**
	 * Quita una vida al Jugador.
	 * 
	 * Si la cantidad de vidas del Jugador llega a 0, entonces el Jugador pierde el juego..
	 */
	public void quitarVida ()
	{
		vidas--;
		if (vidas == 0)
		{
			muerto = true;
			controlCentral.perderNivel();
		}
		else
			controlCentral.reiniciarNivel ();
			
	}
	
	/**
	 * Actualiza la cantidad de puntos del Jugador, sumando pts a sus puntos actuales.
	 * 
	 * @param pts Cantidad de puntos a sumar. (Restar si pts < 0)
	 */
	public void asignarPuntos (int pts)
	{
		puntos += pts;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de monedas actual del Jugador.
	 * 
	 * @return Cantidad de monedas del Jugador.
	 */
	public int monedas ()
	{
		return monedas;
	}
	
	/**
	 * Devuelve la cantidad de puntos actual del Jugador.
	 * 
	 * @return Cantidad de puntos del Jugador.
	 */
	public int puntos ()
	{
		return puntos;
	}
	
	/**
	 * Devuelve la cantidad de vidas actual del Jugador.
	 * 
	 * @return Cantidad de vidas del Jugador.
	 */
	public int vidas ()
	{
		return vidas;
	}
	
	/**
	 * Devuelve el estado del Jugador.
	 * 
	 * @return True:  si vidas > 0.
	 *         False: si vidas = 0.
	 */
	public boolean muerto ()
	{
		return muerto;
	}
	
	/**
	 * Devuelve el nombre del Jugador.
	 * 
	 * @return Nombre del Jugador.
	 */
	public String nombre ()
	{
		return nombre;
	}
	
	/**
	 * Devuelve el Personaje actual del Jugador.
	 * 
	 * @return Personaje del Jugador.
	 */
	public PjSeleccionable personaje ()
	{
		return personaje;
	}
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * Realiza las acciones del Jugador en el Juego.
	 */
	public void run ()
	{
		while (!(muerto))
		{
			actuar();

			try
			{
				Thread.sleep(200);
			}
			catch (InterruptedException e)
			{
				controlCentral.mensajeError("Error", e.getMessage(), true);
			}
		}
	}
	
	/**
	 * Hace "actuar" al personaje del Jugador actual.
	 * Para eso, primero verifica que acci�n est� queriendo realizar el Jugador mediante su control, y luego realiza la acci�n deseada.
	 */
	private void actuar ()
	{
		if (control.arriba())
			personaje.arriba();
		if (control.abajo())
			personaje.abajo();
		if (control.izquierda())
			personaje.izquierda();
		if (control.derecha())
			personaje.derecha();
		if (control.A())
			personaje.A();
		if (control.B())
			personaje.B();
		if (control.ESC())
			control.ESC();
		if (control.aceptar())
			control.aceptar();
	}

}