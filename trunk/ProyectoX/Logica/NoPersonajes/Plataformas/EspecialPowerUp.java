package ProyectoX.Logica.NoPersonajes.Plataformas;

import java.util.Random;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa a una Plataforma Especial Power Up en el Juego.
 * Es una plataforma Irrompible que contiene un Power Up, el cuál aparece cuando la plataforma 
 * es golpeada desde abajo por Mario.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class EspecialPowerUp extends Irrompible
{
	//Atributos de Clase
	private static final String dirRecursos = "Estructuras/Plataformas/";
	private static final String [] nombresSprites = {dirRecursos + "QuestionBlock-1.png", //0 = Inicial, contiene PowerUp
													 dirRecursos + "QuestionBlock-2.png",
													 dirRecursos + "QuestionBlock-3.png",
													 dirRecursos + "Empty-Block.gif"};	 //3 = Plataforma sin PowerUp
	
	private static int cantFramesMovimiento = 3;
	private static int vacio = 3;
	
	//Atributos de Instancia	
	protected PowerUp powerUp;
	protected ControlCentral controlCentral;
	protected boolean cambiable; //Determina si la plataforma puede cambiar, o no, su powerUp en el transcurso del juego.

	/**
	 * Crea una Plataforma EspecialPowerUp.
	 */
	public EspecialPowerUp(PowerUp pwUp, ControlCentral cc, boolean modificable) 
	{
		super();
		spriteManager.cargarSprites(nombresSprites);		
		spriteManager.rotarGif(cantFramesMovimiento);
		powerUp = pwUp;
		controlCentral = cc;
		cambiable = modificable;
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Extraer su PowerUp.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("EspecialPowerUp.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			Mario mario = checkActorJugador (pj);	
			Celda celdaSuperior;
			if (colisionAbajo(mario))
			{//Si la colisión de Mario es desde abajo, sacar al powerUp, sino, no hacer nada.			
				if (hayPowerUp() && !celdaActual.getSuperior().isOcupada())
				{//Si hay powerUp y en la celda superior no otra estructura, sacar y agregar al powerUp a la celda superior, sino, no hacer nada.		
					
					celdaSuperior = this.celdaActual.getSuperior();
					powerUp.setCeldaActual(celdaSuperior);
					celdaSuperior.agregarActor(powerUp);
					
					ActualizadorNivel.act().agregarPowerUp(powerUp);
					
					this.getSpriteManager().printNextMe(powerUp.getSpriteManager());
					
					{//Agrega movimiento (izquierda o derecha) inicial Random al PowerUp saliente.
						//if ((new Random().nextInt() % 2) == 0)
							powerUp.moverseAizquierda();
						//else
							//powerUp.moverseAderecha();
					}
					
					cambiable = false;
					powerUp = null;
					spriteManager.cambiarSprite(vacio);
				}			
			}
		}
		catch (Exception e)
		{
			throw new ColisionException ("EspecialPowerUp.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Cambia el Powerup que la plataforma contiene, si la misma lo permite.
	 * @param pwUp es el PowerUp nuevo que se cambia por el viejo almacenado, si es que la plataforma es cambiable.
	 */
	public void cambiarPowerUp (PowerUp pwUp)
	{
		if (cambiable)
			powerUp = pwUp;
	}
	
	/**
	 * Verifica si la plataforma permite cambiar su PowerUp.
	 * @return Verdadero si la plataforma permite cambiar a su PowerUp almacenado, falso, en caso contrario.
	 */
	public boolean esCambiable()
	{
		return cambiable;
	}
	
	/**
	 * Verifica si la colisión con el Actor proviene desde abajo.
	 * @param mario Mario con el que se colisiona.
	 * @return Verdadero si Mario a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Mario mario)
	{
		//Mario se encuentra debajo de la plataforma si y solo si para Mario el vectorDistancia = (0,1).
		int [] vector = mario.vectorDistancia(this);
		return (vector[0] == 0 && vector[1] == 1);
	}
	
	/**
	 * Verifica si la plataforma contiene moneda.	 
	 * @return Verdadero si hay monedas en la plataforma, falso, en caso contrario.
	 */
	protected boolean hayPowerUp()
	{
		return powerUp != null;
	}

}