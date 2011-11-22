package ProyectoX.Logica.Personajes;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;


/**
 * Representa a Mario Chico, uno de los Personajes Seleccionables por el Jugador en el Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioChico extends Mario
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioChico, la ubicación en los índices es:
	                                                {dirRecursos + "Mario-Dead.gif", //0: Mario muerto
		                                             dirRecursos + "Mario.gif",      //1: Mario quieto
		                                             dirRecursos + "Mario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "Mario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "Mario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "Mario-Jump.gif"};//5: Mario saltando
	//Numeros de los Sprites.
	private static int muerto = 0;
	private static int quieto = 1;
	private static int caminando = 2;
	private static int saltando = 5;
	
	private static int maxPS = 4;//Máxima Potencia de Salto.
	private int PS = 0;//Potencia de Salto Actual.
	
	//Prioridades para el UpNeeder
	//5 = spriteManager.cambiarSprite(-quieto)
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un Personaje Seleccionable Mario Chico.
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public MarioChico (CargadorSprite cargadorSprite)
	{
		super(nombresSprites, cargadorSprite);
		spriteManager.cambiarSprite(quieto);
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Mario realiza la acción de saltar.
	 * 
	 * @throws AccionActorException Si se produce algún error al saltar.
	 */
	public void saltar () throws AccionActorException
	{
		Celda celdaSuperior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");

			if ((PG == 0) && (PS < maxPS))
			{
				spriteManager.cambiarSprite(saltando);
				if (!celdaActual.getBloque().esLimite(celdaActual))
				{
					PG++;
					PS++;
					celdaSuperior = celdaActual.getBloque().getSuperior(celdaActual);
					if (!celdaSuperior.isOcupada())
						moverseAcelda(celdaSuperior);
				}
			}
			else
				if (PG != -1)
					PS = 0;
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("MarioChico.saltar()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("MarioChico.saltar()" + "\n" +
                                            "Imposible realizar la acción saltar a/desde Celda de posición (" + celdaSuperior.getPosFila() + "," + celdaSuperior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
		
	/**
	 * Mario realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (!celdaActual.getBloque().esLimite(celdaActual))
			{
				spriteManager.cambiarSprite(-caminando);
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
				
				upNeeder.addWorker(5,
						new Worker ()
						{
							public void work() throws Exception
							{
								spriteManager.cambiarSprite(-quieto);
							}
						});
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("MarioChico.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("MarioChico.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
		
	/**
	 * Mario realiza la acción de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (!celdaActual.getBloque().esLimite(celdaActual))
			{
				spriteManager.cambiarSprite(caminando);
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
				
				upNeeder.addWorker(5,
						new Worker ()
						{
							public void work() throws Exception
							{
								spriteManager.cambiarSprite(quieto);
							}
						});
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("MarioChico.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("MarioChico.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @throws AccionActorException Si se produce algún error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		
	}
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		
	}
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public void accionB () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual.
	 * @throws NullPointerException Si c es null. 
	 */
	protected void producirColisiones (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("MarioChico.producirColisiones()" + "\n" +
                                            "Imposible producir las colisiones. La Celda c es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionarPj(this);		
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecer () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo.
	 */
	public void serDaniado () throws AccionActorException
	{
		
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de Mario.
	 */
	public void morir ()
	{
		spriteManager.cambiarSprite(muerto);
		super.morir();
	}
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce algún error al caer.
	 */
	public void caer () throws AccionActorException
	{
		super.caer();
		
		upNeeder.addWorker(5,
				new Worker ()
				{
					public void work() throws Exception
					{
						spriteManager.cambiarSprite(quieto);
					}
				});
	}
	
}