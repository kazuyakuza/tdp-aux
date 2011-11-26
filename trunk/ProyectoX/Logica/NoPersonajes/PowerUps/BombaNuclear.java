package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.Mapa.Bloque;

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
	
	//Atributos de Instancia
	//protected IAPowerUp miIA;
	protected ControlCentral controlCentral; 
	
	/*CONSTRUCTORES*/	

	/**
	 * Crea una Bomba Nuclear del juego.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public BombaNuclear(ControlCentral cc, CargadorSprite cargadorSprite) 
	{
		super (nombresSprites, cargadorSprite);
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

}