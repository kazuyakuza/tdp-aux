package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Personajes.Mario;

/**
 * Representa a los power ups Flor de Fuego del juego. El efecto sobre Mario es hacerlo crecer a Mario Blanco.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class FlorFuego extends PowerUp
{	
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "FireFlower.gif"};
	
	//Atributos de Instancia
	//protected IAPowerUp miIA;
	
	/*CONSTRUCTORES*/	

	/**
	 * Crea una Flor de Fuego del juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public FlorFuego(CargadorSprite cargadorSprite) 
	{
		super (nombresSprites, cargadorSprite);		
	}
	
	/*METODOS IMPLEMENTADOS*/
	
	/**
	 * Realiza el efecto del Power Up.
	 * @throws NullPointerException si mario es null.
	 */
	public void efecto (Mario mario) throws NullPointerException
	{
		if (mario == null)
			throw new NullPointerException ("PowerUp.efecto()" + "\n" +
            								"Imposible aplicar efecto, mario es null");
		mario.crecerFlor();
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 5 * mario.multiplicadorBonus();
	}
}