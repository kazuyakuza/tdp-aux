package ProyectoX.Logica.NoPersonajes;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialMonedas;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Responsabilidades.Punteable;

/**
 * Representa a la Moneda del juego.
 * Cuando un Mario agarra una Moneda, su jugador aumenta su cantidad de monedas en 1 y obtiene puntos.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Moneda extends Actor implements Punteable
{	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Coin-1.png",
		                                             dirRecursos + "Coin-2.png",
		                                             dirRecursos + "Coin-3.png",};
		
	private static int cantFramesMovimiento = 3;
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminaci�n acciones.
	
	//Prioridades en UpNeeder
	//0 = morir
	
	/**
	 * Crea una Moneda. 
	 */
	public Moneda() 
	{
		super(nombresSprites);
		upNeeder = new UpNeeder (0);
		Updater.getUpdater().addUpNeeder(upNeeder);
		spriteManager.rotarGif(cantFramesMovimiento);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual.
	 */
	public void colisionar (Actor a)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("Moneda.colisionarPj()" + "\n" +
					                        "Imposible realizar colisi�n. El PjSeleccionable ingresado es null.");
		
		try
		{
			pj.getJugador().agregarMoneda();
			pj.getJugador().asignarPuntos(5);
			
			upNeeder.addWorker(0, new Worker ()
			{
				public void work() throws Exception
				{
					morir();
				}
			});
		}
		catch (Exception e)
		{
			throw new ColisionException ("Moneda.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 */
	public void colisionarBola (BolaFuego bola)
	{
		//No le afecta.
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		//Nada ocurre	
	}
		
	/**
	 * Realiza la acci�n de morir del Actor.
	 */
	public void morir ()
	{
		ActualizadorNivel.act().eliminarActor(this);
		
		super.morir();
	}
	
	/**
	 * Realiza la acci�n de morir del Actor.
	 * 
	 * @param em Atributo para asegurar q es llamado por un EspecialMonedas.
	 */
	public void matate (EspecialMonedas em)
	{
		upNeeder.addWorker(0, new Worker ()
		{
			public void work() throws Exception
			{
				spriteManager.setEliminar();
				
				spriteManager = null;
			}
		});
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos (Mario mario)
	{
		return 5;
	}
	
	/**
	 * Devuelve la cantidad de frames de movimiento.
	 * 
	 * @return Cantidad de frames de movimiento.
	 */
	public int getCantFramesMovimiento ()
	{
		return cantFramesMovimiento;
	}

}