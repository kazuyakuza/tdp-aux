package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.Invulnerable;
import ProyectoX.Logica.Personajes.Destructor;

/**
 * Representa a los power ups Estrella del juego. El efecto sobre Mario es hacerlo Invulnerable y Destructor.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Estrella extends PowerUp
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "Starman.gif"};
	
	//Atributos de Instancia
	//protected IAPowerUp miIA;
	
	/*CONSTRUCTORES*/	

	/**
	 * Crea una Estrella del juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Estrella(CargadorSprite cargadorSprite) 
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
		mario.setCaracteristica(new Invulnerable (mario.getCaracteristica (), 15000));
		mario.setCaracteristica(new Destructor (mario.getCaracteristica (), 15000));
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
