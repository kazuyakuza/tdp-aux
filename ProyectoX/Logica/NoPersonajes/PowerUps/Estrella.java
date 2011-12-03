package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Logica.Personajes.Destructor;
import ProyectoX.Logica.Personajes.Mario;

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
	private static final String [] nombresSprites = {dirRecursos + "Starman-1.png",
													 dirRecursos + "Starman-2.png",
		                                             dirRecursos + "Starman-3.png",
		                                             dirRecursos + "Starman-4.png"};
	
	//Atributos de Instancia
	//protected IAPowerUp miIA;
	
	/*CONSTRUCTORES*/	

	/**
	 * Crea una Estrella del juego.
	 */
	public Estrella () 
	{
		super (nombresSprites);
		spriteManager.rotarGif(4);
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
		
		if (mario.getDestructor())
			((Destructor)mario.getCaracteristica()).terminar();
		mario.setCaracteristica(new Destructor (mario.getCaracteristica(), 15000));
		((Destructor)mario.getCaracteristica()).empezar();
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 25;
	}

}