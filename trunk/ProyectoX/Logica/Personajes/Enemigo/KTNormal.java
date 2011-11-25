package ProyectoX.Logica.Personajes.Enemigo;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;

public class KTNormal extends CaracteristicaKT
{
	//Atributos de Clase
	private static final String dirRecursos = "Enemigos/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a Goomba, la ubicaci�n en los �ndices es:
	                                                {dirRecursos + "GreenKoopaTroopa-1.png", //0: KoopaTroopa quieto
													 dirRecursos + "GreenKoopaTroopa-2.png"};//1: KoopaTroopa movimiento
	
	/*CONSRUCTORES*/
	
	/**
	 * Crea una CaracteristicaKT para un KoopaTroopa, con estado KoopaTroopaNormal.
	 */
	public KTNormal() 
	{
		super();
	}
	
	/**
	 * Crea una CaracteristicaKT para KoopaTroopa, con estado KoopaTroopaNormal.
	 * 
	 * @param pj es el KoopaTroopa vinculado a la Caracteristica. 
	 */	
	public KTNormal (KoopaTroopa kt)
	{
		super(kt);		
	}
	
	/*METODOS IMPLEMENTADOS*/
	/**
	 * Realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = koopa.getCeldaActual();
		Celda celdaActual = koopa.getCeldaActual();
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayAnterior(celdaActual))
			{
				koopa.getSpriteManager().cambiarSprite(-movimiento);
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					koopa.moverseAcelda(celdaAnterior);
				
				if (! koopa.getUpNeeder().hayWorkerPrioridad(3))
					koopa.getUpNeeder().addWorker(3, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		koopa.getSpriteManager().cambiarSprite(-quieto);
                    	}
                    });
			}
			
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("KTNormal.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("KTNormal.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acci�n de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = koopa.getCeldaActual();
		Celda celdaActual = koopa.getCeldaActual();
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().haySiguiente(celdaActual))
			{
				koopa.getSpriteManager().cambiarSprite(movimiento);
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					koopa.moverseAcelda(celdaSiguiente);
				
				if (! koopa.getUpNeeder().hayWorkerPrioridad(3))
                    koopa.getUpNeeder().addWorker(3, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		koopa.getSpriteManager().cambiarSprite(quieto);
                    	}
                    });
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("KTNormal.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("KTNormal.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Muere el KoopaTroopa actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (final Mario mario) throws ColisionException, NullPointerException
	{
		Celda celdaActual = koopa.getCeldaActual();
		if (celdaActual.getBloque().getSuperior(celdaActual) == mario.getCeldaActual())
		{
			mario.getJugador().asignarPuntos(60);
			if (! koopa.getUpNeeder().hayWorkerPrioridad(0))
				koopa.getUpNeeder().addWorker(0, new Worker ()
				{
					public void work() throws Exception
					{
						koopa.morir();
					}
				});
		}
		else
		{
			final KoopaTroopa gAux = koopa;
		
			if (! koopa.getUpNeeder().hayWorkerPrioridad(1))
				koopa.getUpNeeder().addWorker(1, new Worker ()
				{
					public void work() throws Exception
					{
						mario.serDa�ado(gAux);
					}
				});
		}		
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
