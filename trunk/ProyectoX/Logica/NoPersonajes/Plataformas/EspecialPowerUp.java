package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Moneda;
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
	protected boolean cambiable; //Determina si la plataforma puede cambiar, o no, su powerUp en el transcurso del juego.

	/**
	 * Crea una Plataforma EspecialPowerUp.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public EspecialPowerUp(PowerUp pwUp, boolean modificable, CargadorSprite cargadorSprite) 
	{
		super(cargadorSprite);
		spriteManager.cargarSprites(nombresSprites);		
		spriteManager.rotarGif(cantFramesMovimiento);
		powerUp = pwUp;
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
			if ( (this.celdaActual.getBloque().hayInferior(this.celdaActual)) && (colisionAbajo(mario)) )
			{//Si la colisión de Mario es desde abajo, sacar al powerUp, sino, no hacer nada.			
				if (hayPowerUp())
				{//Si hay powerUp, sacarlo y agregarlo a la celda superior, sino, no hacer nada.		
					
					celdaSuperior = this.celdaActual.getBloque().getSuperior(this.celdaActual);
					powerUp.setCeldaActual(celdaSuperior);
					celdaSuperior.agregarActor(powerUp);
					this.getSpriteManager().printNextMe(powerUp.getSpriteManager());
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
	 * @param a Actor con el que se colisiona.
	 * @return Verdadero si el Actor a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Actor a)
	{
		return this.celdaActual.getBloque().getInferior(this.celdaActual) == a.getCeldaActual();
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