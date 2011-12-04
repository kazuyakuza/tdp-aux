package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.Personajes.Mario;

/**
 * Representa a los power ups Bomba Nuclear del juego. El efecto sobre Mario es nulo, provoca la muerte de todos sus enemigos en la pantalla.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class BombaNuclear extends PowerUp
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "BombaNuclear.gif"};
	
	protected ControlCentral controlCentral; 
	
	/*CONSTRUCTORES*/	

	/**
	 * Crea una Bomba Nuclear del juego.
	 */
	public BombaNuclear(ControlCentral cc) 
	{
		super (nombresSprites);
		controlCentral = cc;
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
				
		controlCentral.explotarBombaNuclear(this);				
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return -45;
	}
	
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda() throws AccionActorException
	{
		//Nunca ocurre.
	}
	
	/**
	 * Realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha() throws AccionActorException
	{
		//Nunca ocurre.
	}

}