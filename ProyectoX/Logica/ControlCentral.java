package ProyectoX.Logica;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;

import ProyectoX.Excepciones.ControlCentralException;
import ProyectoX.Grafico.BloqueGrafico;
import ProyectoX.Grafico.Escenario;
import ProyectoX.Grafico.VentanaPrincipal;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Librerias.Threads.AliveThread;
import ProyectoX.Librerias.Threads.ControlThreads;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Logica.Controles.Control;
import ProyectoX.Logica.Controles.Teclado;
import ProyectoX.Logica.Mapa.Bloque;
import ProyectoX.Logica.Mapa.Nivel;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.MarioChico;

/**
 * Representa al Control Central del Juego.
 * 
 * Controla las situaciones que suceden en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class ControlCentral implements Runnable, ControlThreads
{
	
	//Variables de Clase
	public static final double velocidad = 8.5;
	
	//Variables de Instancia
	private VentanaPrincipal ventanaPrincipal;
	private Escenario escenario;
	private CargadorSprite cargadorSprite;
	private Jugador jugador;
	private Nivel nivel;
	private PositionList<Actor> actores;
	private Gravedad gravedad;
	
	//Threads
	private Thread Tactual;
	private AliveThread Tescenario, Tjugador, Tgravedad;
	private Updater updater;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Control Central con el Escenario e, para el usuario de nombre nJ, en la Ventana Principal ventana.
	 * 
	 * @param ventana Ventana Principal donde se va a ejecutar el Juego.
	 * @param nJ Nombre Jugador.
	 * @param e Escenario donde llevar a cabo la parte gráfica el juego.
	 */
	public ControlCentral (VentanaPrincipal ventana, String nJ, Escenario e)
	{
		try
		{
			Tactual = null;
			ventanaPrincipal = ventana;
			escenario = e;
			
			cargadorSprite = new CargadorSprite ();
		
			Mario PJ = new MarioChico (cargadorSprite);
			Control c = new Teclado();
			jugador = new Jugador (nJ, PJ, c, this);
			PJ.setJugador(jugador);
			
			actores = null;
			
			nivel = new Nivel(1);
			
			gravedad = new Gravedad(this);
			
			ventanaPrincipal.agregarEscenario(e);
			escenario.inicializarGrafica();
			escenario.agregarControl(c);
			ventanaPrincipal.repaint();
			
			//Crear y Asignar Threads
			Tescenario = new AliveThread (this, escenario);
			Tjugador = new AliveThread (this, jugador);
			Tgravedad = new AliveThread (this, gravedad);
			updater = new Updater (this);
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega el Thread que ejecutará el run de esta clase.
	 * 
	 * @param t Thread para esta clase.
	 */
	public void agregarThread (Thread t)
	{
		if (Tactual == null)
			Tactual = t;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Jugador del juego.
	 * 
	 * @return Jugador del juego.
	 */
	public Jugador getJugador ()
	{
		return jugador;
	}
	
	/**
	 * Devuelve un iterador de los Actores actuales en Juego.
	 * 
	 * @return Iterador de los Actores actuales en Juego.
	 */
	public Iterator<Actor> getActores ()
	{
		PositionList<Actor> a = new ListaPositionSimple<Actor> ();
		a.addFirst((Actor) jugador.personaje());
		return a.iterator();
		//return actores.iterator();
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Run para el Thread de esta clase.
	 */
	public void run ()
	{
		try
		{
			//Inicialización Lógica.
			actores = nivel.inicializarNivel((Actor) jugador.personaje, this, cargadorSprite);
			
			//Inicialización Gráfica.
			Bloque bloqueActual = nivel.getBloqueActual();
			BloqueGrafico bloqueGrafico = new BloqueGrafico (bloqueActual.getFilas(), bloqueActual.getColumnas());
			//Agregando Piso
			bloqueGrafico.setNivelPiso(bloqueActual.getNivelPiso());
			for (Actor a: actores)
				bloqueGrafico.agregarSprite(a.spriteManager);
			escenario.agregarFondo(nivel.fondo(), cargadorSprite);
			escenario.setBloqueGraficoActual(bloqueGrafico);
			
			//Agregando UpNeeders al Updater
			for (Actor a: actores)
				for (UpNeeder un: a.getUpNeeders())
					updater.addUpNeeder(un);
			
			//Start Thread's
			Tjugador.start();
			Tgravedad.start();
			updater.changeSleepTime((int) (getSleepTime()*0.5));
			updater.start();
			Tescenario.start();
			Tescenario.changeSleepTime((int) (getSleepTime()*0.5));
			
			ventanaPrincipal.repaint();
			
			//test
			//test ();
		}
		catch (Exception exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
	}
	int x = 0; int y = 0;
	double spx = 0; double spy = 0;
	int pg = -1;
	public void test ()
	{
		int i = 0;
		while (true)
		{
			/*if (!Tescenario.isAlive())
			{
				System.out.println("MUERTO Tescenario");
				System.exit(0);
			}
			if (!Tjugador.isAlive())
			{
				System.out.println("MUERTO Tjugador");
				System.exit(0);
			}
			if (!Tgravedad.isAlive())
			{
				System.out.println("MUERTO Tgravedad");
				System.exit(0);
			}*/
			/*try {
			Thread.sleep(getSleepTime());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}*/
			/*{
				if ((x != ((Actor) jugador.personaje()).getCeldaActual().getPosFila())
					    || (y != ((Actor) jugador.personaje()).getCeldaActual().getPosColumna()))
					    {
						System.out.println("En i " + i +":");
						System.out.println(((Actor) jugador.personaje()).getCeldaActual().getPosFila() + "," +
						           ((Actor) jugador.personaje()).getCeldaActual().getPosColumna());
						System.out.println(((Actor) jugador.personaje()).getSpriteManager().posicion()[0] +","+ 
						           ((Actor) jugador.personaje()).getSpriteManager().posicion()[1]);
						 x = ((Actor) jugador.personaje()).getCeldaActual().getPosFila();
					     y = ((Actor) jugador.personaje()).getCeldaActual().getPosColumna();
					    }
			}*/
			{
				//if (i<10)
					//testMarioBot();
			}
			/*{
				if ((spx != ((Actor) jugador.personaje()).spriteManager.posicion()[0])
					    || (spy != ((Actor) jugador.personaje()).spriteManager.posicion()[1]))
					    {
				        System.out.println("En i " + i + " Cambio:");
						System.out.println(((Actor) jugador.personaje()).getCeldaActual().getPosFila() + "," +
						           ((Actor) jugador.personaje()).getCeldaActual().getPosColumna());
						System.out.println(((Actor) jugador.personaje()).getSpriteManager().posicion()[0] +","+ 
						           ((Actor) jugador.personaje()).getSpriteManager().posicion()[1]);
						 spx = ((Actor) jugador.personaje()).spriteManager.posicion()[0];
					     spy = ((Actor) jugador.personaje()).spriteManager.posicion()[1];
					    }
			}*/
			{
				spx = ((Actor) jugador.personaje()).spriteManager.posicion()[0];
			    spy = ((Actor) jugador.personaje()).spriteManager.posicion()[1];
				if (
						((spx - ((Actor) jugador.personaje()).spriteManager.posicion()[0]) > 0.5)
					 || ((spx - ((Actor) jugador.personaje()).spriteManager.posicion()[0]) < - 0.5)
					 ||	((spy - ((Actor) jugador.personaje()).spriteManager.posicion()[1]) > 0.5)
				     || ((spy - ((Actor) jugador.personaje()).spriteManager.posicion()[1]) < - 0.5)
					)
					    {
				          System.out.println("ERROR");
				          ventanaPrincipal.salir();
					    }
			}
			    /*if ( pg != ((Actor) jugador.personaje).PG)
			    {
			    	System.out.println(((Actor) jugador.personaje).PG);
			    	pg = ((Actor) jugador.personaje).PG;
			    	//System.out.println(pg);
			    }*/
			/*{
				if ((5 == ((Actor) jugador.personaje()).getCeldaActual().getPosFila())
					    && (3 == ((Actor) jugador.personaje()).getCeldaActual().getPosColumna()))
					((Actor) jugador.personaje()).moverseAcelda(
					((Actor) jugador.personaje()).getCeldaActual().getBloque().getCelda(0, 3));
			}*/
			/*{
			if ((1 == ((Actor) jugador.personaje()).getCeldaActual().getPosFila())
				    && (3 == ((Actor) jugador.personaje()).getCeldaActual().getPosColumna()))
				System.out.println("CHAN");
			}*/
			i++;
		}
	}
	//boolean izq = false;
	int h1 = 0;// int h2 = 0; int h3 = 0;
	//Random random = new Random ();
	public void testMarioBot ()
	{
		if (h1 < 4)
		{
			jugador.personaje().arriba();
			jugador.personaje().derecha();
			h1++;
		}
		else
			if (h1 < 8)
			{
				jugador.personaje().derecha();
				h1++;
			}
			/*else
				if (h1 < 9)
				{
					capture = true;
					h1++;
				}*/
		/*h1 = random.nextInt();
		h2 = random.nextInt();
		if ((h2 % 2) == 0)
			h2++;
		h1 *= h2;
		if ((h1 % 9) == 0)
			h3 = random.nextInt (7);
		if (h3 == 0)
		{
		if ((h1 % 2) == 0)
		{
			if (((h1 % 4) == 0) || ((h1 % 6) == 0) || ((h1 % 8) == 0))
				jugador.personaje().izquierda();
			else
				jugador.personaje().derecha();
		
			if ((h1 % 11) == 0)
				jugador.personaje().arriba();
		}
		}
		else
		{
			jugador.personaje().arriba();
			h3--;
		}*/
		/*h++;
		if (izq)
			jugador.personaje().izquierda();
		else
			jugador.personaje().derecha();
		if (h == 1)
		{
			h = 0;
			izq = !izq;
		}*/
	}
	
	public int getSleepTime ()
	{
		return (int) (1000/velocidad);
	}
	
	public void error (Exception e)
	{
		ventanaPrincipal.mensajeError("Error", e.getMessage(), true);
	}
	
	/**
	 * Acción ESC (escape) del Juego.
	 * 
	 * Abre un menú de salida del Juego.
	 */
	public void ESC ()
	{
		//menú para salir de la partida
	}
	
	/**
	 * Acción aceptar del Juego.
	 */
	public void aceptar ()
	{
		//pausa
	}
	
	/**
	 * Indica que el Jugador ha ganado el Nivel actual.
	 */
	public void ganarNivel ()
	{
		Position<Actor> p = actores.first();
		while (p.element() != jugador.personaje())
			p = actores.next(p);
		actores.remove(p);
		
		gravedad.setAfectar(false);
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		escenario.setActualizar(false);
		ventanaPrincipal.mensajeError("Fin del Juego", "Ganaste", true);
	}
	
	/**
	 * Reinicia el nivel a una posición inicial.
	 */
	public void reiniciarNivel ()
	{
		
	}
	
	/**
	 * Indica que el Jugador ha perdido el Nivel actual.
	 */
	public void perderNivel ()
	{
		Position<Actor> p = actores.first();
		while ((p != actores.last()) &&(p.element() != jugador.personaje()))
			p = actores.next(p);
		
		if (p.element() != jugador.personaje())
			try
			{
				throw new ControlCentralException("Imposible encontrar Personaje del Jugador en la lista de Atores.");
			}
			catch (ControlCentralException exception)
			{
				ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
			}
		
		actores.remove(p);
		
		gravedad.setAfectar(false);
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
		{
			ventanaPrincipal.mensajeError("Error", exception.getMessage(), true);
		}
		
		escenario.setActualizar(false);
		ventanaPrincipal.mensajeError("Perdiste", "Perdiste", true);
	}
	
	/**
	 * Muestra un Mensaje de Error, con su título y mensaje, provocado por o en el Compenente ventana.
	 * 
	 * @param ventana Componente que llama a este método, donde se generó el error.
	 * @param titulo Título del Mensaje de Error.
	 * @param mensaje Mensaje del Error.
	 */
	public void mensajeError (String titulo, String mensaje, boolean cerrar)
	{
		ventanaPrincipal.mensajeError(titulo, mensaje, cerrar);
	}

}