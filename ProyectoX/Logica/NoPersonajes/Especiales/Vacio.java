package ProyectoX.Logica.NoPersonajes.Especiales;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Responsabilidades.Punteable;

/**
 * Representa al vacío lugar donde todo actor que llegua se muere.
 * Cuando un Actor colisiona (se ubica en la misma celda) con un Vacio, dicho Actor se muere.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Vacio extends Actor implements Punteable
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Vacio.png"};
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminación acciones.
	
	//Prioridades en UpNeeder
	//0 = provocar muertes
	
	/*CONTRUCTOR*/
	
	/**
	 * Crea un Vacio. 
	 */
	public Vacio() 
	{
		super (nombresSprites);
		upNeeder = new UpNeeder (0);
		Updater.getUpdater().addUpNeeder(upNeeder);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * Mata al Actor a.
	 * 
	 * @param a Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si a es null.
	 * @throws ColisionException Si se produce algún error en la colisión. 
	 */
	public void colisionar (final Actor a) throws ColisionException, NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("Vacio.colisionar()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			upNeeder.addWorker(0, new Worker ()
			{
				public void work() throws Exception
				{
					a.morir();
				}
			});
		}
		catch (Exception e)
		{
			throw new ColisionException ("Vacio.colisionar()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Mata al Personaje Seleccionable.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (final PjSeleccionable pj) throws ColisionException, NullPointerException
	{		
		if (pj == null)
			throw new NullPointerException ("Vacio.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			pj.getJugador().asignarPuntos(15);
			
			upNeeder.addWorker(0, new Worker ()
			{
				public void work() throws Exception
				{
					pj.morir();
				}
			});
		}
		catch (Exception e)
		{
			throw new ColisionException ("Vacio.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * Mata la Bola.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (final BolaFuego bola) throws ColisionException, NullPointerException
	{
		if (bola == null)
			throw new NullPointerException ("Vacio.colisionarBola()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			upNeeder.addWorker(0, new Worker ()
			{
				public void work() throws Exception
				{
					bola.morir();
				}
			});
		}
		catch (Exception e)
		{
			throw new ColisionException ("Vacio.colisionarBola()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected void producirColisiones (Celda c)
	{
		//Nada ocurre.
	}
	
	/**
	 * Realiza la acción de morir del Actor.
	 * 
	 * No tiene ningún efecto en este Actor.
	 */
	public void morir()
	{
		//Nunca ocurre.
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 15;
	}

}