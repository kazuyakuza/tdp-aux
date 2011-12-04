package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Logica.Personajes.Mario;

/**
 * Representa a los power ups Hongo Verde del juego. El efecto sobre Mario es nulo, pero aumenta en 1 la vida de su Jugador.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class HongoVerde extends PowerUp
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "1-UpMushroom.gif"};
		
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Hongo Verde del juego.
	 */
	public HongoVerde() 
	{
		super (nombresSprites);
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
		mario.getJugador().agregarVida();
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 100;
	}

}