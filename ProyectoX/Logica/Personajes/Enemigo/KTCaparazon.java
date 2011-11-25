package ProyectoX.Logica.Personajes.Enemigo;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;

public class KTCaparazon extends CaracteristicaKT
{
	//Atributos de Clase
	private static final String dirRecursos = "Enemigos/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a Goomba, la ubicación en los índices es:
	                                                {dirRecursos + "GreenKoopaTroopa-Shell1.gif", //0: KoopaTroopa quieto
													 dirRecursos + "GreenKoopaTroopa-Shell1.gif", //1: KoopaTroopa quieto
													 dirRecursos + "GreenKoopaTroopa-Shell2.gif"};//2: KoopaTroopa movimiento
	
	/*CONSRUCTORES*/
	
	/**
	 * Crea una CaracteristicaKT para un KoopaTroopa, con estado KoopaTroopaCaparazon.
	 */
	public KTCaparazon() 
	{
		super();
	}
	
	/**
	 * Crea una CaracteristicaKT para KoopaTroopa, con estado KoopaTroopaCaparazon.
	 * 
	 * @param pj es el KoopaTroopa vinculado a la Caracteristica. 
	 */	
	public KTCaparazon (KoopaTroopa kt)
	{
		super(kt);		
	}
	
	/*METODOS IMPLEMENTADOS*/
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		//No hace nada, no puede moverse.
	}
	
	/**
	 * Realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		//No hace nada, no puede moverse.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Muere el KoopaTroopa actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (final Mario mario) throws ColisionException, NullPointerException
	{
		mario.getJugador().asignarPuntos(koopa.getPuntos(mario));
		if (! koopa.getUpNeeder().hayWorkerPrioridad(0))
			koopa.getUpNeeder().addWorker(0, new Worker ()
			{
				public void work() throws Exception
				{
					koopa.morir();
				}
			});			
	}
	
	/**
	 * Retorna los nombres de sprites correspondientes a la CaracteristicaKT.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public String[] getNombresSprites()
	{
		return nombresSprites;
	}

}
