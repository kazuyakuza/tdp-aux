package ProyectoX.Logica.NoPersonajes;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
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
	
	/**
	 * Crea una Moneda.
	 * 	 
	 * @param cargadorSprite Clase para cargar los sprites.	 
	 */
	public Moneda(CargadorSprite cargadorSprite) 
	{
		super(nombresSprites, cargadorSprite);			
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
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("Moneda.colisionarPj()" + "\n" +
					                        "Imposible realizar colisión. El PjSeleccionable ingresado es null.");
		
		try
		{
			pj.getJugador().agregarMoneda();
			pj.getJugador().asignarPuntos(5);
			
			morir();
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
	 * Realiza la acción de morir del Actor.
	 */
	public void morir()
	{
		celdaActual.getBloque().getMapa().getNivel().eliminarActor(this);
		super.morir();
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

}