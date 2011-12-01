package ProyectoX.Logica.NoPersonajes.PowerUps;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Responsabilidades.Movible;


/**
 * Representa a los power ups Super Hongo del juego. El efecto sobre Mario es hacerlo crecer a Mario Grande.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class SuperHongo extends PowerUp implements Movible
{
	//Atributos de Clase
	private static final String dirRecursos = "Objetos/";
	private static final String [] nombresSprites = {dirRecursos + "SuperMushroom.gif"};
	
	//Atributos de Instancia
	//protected IAPowerUp miIA;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Super Hongo del juego.
	 */
	public SuperHongo() 
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
		mario.crecerHongo();
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * @param mario es el Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos(Mario mario)
	{
		return 10 * mario.multiplicadorBonus();
	}
	
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.haySiguiente())
			{				
				celdaSiguiente = celdaActual.getSiguiente();
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);					
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.hayAnterior())
			{				
				celdaAnterior = celdaActual.getAnterior();
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);								
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}

}
