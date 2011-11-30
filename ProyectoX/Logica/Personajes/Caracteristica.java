package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;

import ProyectoX.Librerias.Threads.UpNeeder;

/**
 * Representa a las Características que Mario puede tener en el juego. 
 * En base a ésta, Mario tiene determinado comportamiento ante los demás Actores.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public abstract class Caracteristica 
{
	//Atributos de Clase
	private static int maxPS = 4;//Máxima Potencia de Salto.
	private int PS = 0;//Potencia de Salto Actual.
	
	//Numeros de los Sprites.
	protected static int muerto = 0;
	protected static int quieto = 1;
	protected static int caminando = 2;
	protected static int saltando = 5;
	
	//Atributos de Instancia
	protected Mario mario;
		
	/*CONSTRUCTORES*/
	
	/**
	 * 	Crea una Caracteristica para Mario, cada Caracteristica tiene sus propios nombres de sprites.
	 *  Queda sin vincular con un Mario.
	 */
	protected Caracteristica ()
	{
		
	}
	
	/**
	 * Crea una Caracteristica para Mario, cada Caracteristica tiene sus propios nombres de sprites.
	 * Setea al Mario vinculado con pj.
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	protected Caracteristica(Mario pj)
	{
		mario = pj;
		mario.getSpriteManager().cargarSprites(this.getNombresSprites());		
	}
	
	/*METODOS ABSTRACTOS*/
	
	/**
	 * Mario realiza la acción de agacharse.
	 * 
	 * @throws AccionActorException Si se produce algún error al agacharse.
	 */
	public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Mario realiza la acción A.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción A.
	 */
	public abstract void accionA () throws AccionActorException;
		
	/**
	 * Mario realiza la acción B.
	 * 
	 * @throws AccionActorException Si se produce algún error al realizar la acción B.
	 */
	public abstract void accionB () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public abstract void crecerHongo () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public abstract void crecerFlor () throws AccionActorException;
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public abstract void serDañado(Actor a);
	
	/**
	 * Realiza la acción de golpear una plataforma Rompible.
	 * @param brick es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si bricks es null.
	 */
	public abstract void golpearRompible (Rompible brick) throws NullPointerException;
	
	/**
	 * Retorna el multiplicador bonus que otorga ésta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public abstract int multiplicadorBonus();	
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public abstract String[] getNombresSprites();
	
	/*COMANDOS*/
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		Celda celdaInferior = mario.getCeldaActual();
		try 
		{
			if (mario.getCeldaActual() == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (mario.getCeldaActual().getBloque().hayInferior(mario.getCeldaActual()))
			{
				celdaInferior = mario.getCeldaActual().getBloque().getInferior(mario.getCeldaActual());
				if (!celdaInferior.isOcupada())
					mario.moverseAcelda(celdaInferior);
				else
					mario.setPG(this, 0);
			}
			
			if (mario.getCeldaActual().getBloque().getInferior(mario.getCeldaActual()).isOcupada())
	        {
				if (! mario.getUpNeeder().hayWorkerPrioridad(5))
					mario.getUpNeeder().addWorker(5,
							new Worker ()
							{
								public void work() throws Exception
								{
									if (mario.miraIzq())
										mario.getSpriteManager().cambiarSprite(-quieto);
									else
										mario.getSpriteManager().cambiarSprite(quieto);
								}
							});
			}
	        else
	        {
	        	if (! mario.getUpNeeder().hayWorkerPrioridad(0))
	        		mario.getUpNeeder().addWorker(0,
	        				new Worker ()
	        				{
	        					public void work() throws Exception
	        					{
	        						if (mario.miraIzq())
	        							mario.getSpriteManager().cambiarSprite(-saltando);
									else
										mario.getSpriteManager().cambiarSprite(saltando);
	                            }
	        				});
	        }
		
		}
		catch (NullPointerException e1)
		{	
			throw new AccionActorException ("Caracteristica.caer()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteritica.caer()" + "\n" +
                                            "Imposible realizar la acción caer a/desde Celda de posición (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}	
	
	/**
	 * Mario realiza la acción de saltar.
	 * 
	 * @throws AccionActorException Si se produce algún error al saltar.
	 */
	public void saltar () throws AccionActorException
	{		
		Celda celdaSuperior = mario.getCeldaActual();
		try 
		{
			if (mario.getCeldaActual() == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");			
			if ((mario.getPG() == 0) && (PS < maxPS))
			{
				if (mario.miraIzq())
					mario.getSpriteManager().cambiarSprite(-saltando);
				else
					mario.getSpriteManager().cambiarSprite(saltando);
				if (mario.getCeldaActual().getBloque().haySuperior(mario.getCeldaActual()))
				{
					mario.setPG(this, mario.getPG()+1);
					PS++;					
					celdaSuperior = mario.getCeldaActual().getBloque().getSuperior(mario.getCeldaActual());
					if (!celdaSuperior.isOcupada())
						mario.moverseAcelda(celdaSuperior);
					else //Mario colisiona una Estructura desde abajo.
					{
						mario.producirColisiones(celdaSuperior);
					}
				}
			}
			else
				if (mario.getPG() != -1)
					PS = 0;
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Caracteristica.saltar()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.saltar()" + "\n" +
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
		Celda celdaAnterior = mario.getCeldaActual();
		try 
		{
			if (mario.getCeldaActual() == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (mario.getCeldaActual().getBloque().hayAnterior(mario.getCeldaActual()))
			{
				mario.mirarIzq(this, true);
				mario.getSpriteManager().cambiarSprite(-caminando);
				celdaAnterior = mario.getCeldaActual().getBloque().getAnterior(mario.getCeldaActual());
				if (!celdaAnterior.isOcupada())
					mario.moverseAcelda(celdaAnterior);
				
				if (! mario.getUpNeeder().hayWorkerPrioridad(5))
					mario.getUpNeeder().addWorker(5, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		mario.getSpriteManager().cambiarSprite(-quieto);
                    	}
                    });
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Caracteristica.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.moverseAizquierda()" + "\n" +
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
		Celda celdaSiguiente = mario.getCeldaActual();
		try 
		{
			if (mario.getCeldaActual() == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (mario.getCeldaActual().getBloque().haySiguiente(mario.getCeldaActual()))
			{
				mario.mirarIzq(this, false);
				mario.getSpriteManager().cambiarSprite(caminando);
				celdaSiguiente = mario.getCeldaActual().getBloque().getSiguiente(mario.getCeldaActual());
				if (!celdaSiguiente.isOcupada())
					mario.moverseAcelda(celdaSiguiente);
								
				if (! mario.getUpNeeder().hayWorkerPrioridad(5))
					mario.getUpNeeder().addWorker(5, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		mario.getSpriteManager().cambiarSprite(quieto);
                    	}
                    });								
			}
		}
		catch (NullPointerException e1)
		{	
			throw new AccionActorException ("Caracteristica.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Setea al Mario al que la Caracteristica representa con pj.
	 * @param pj es el Mario con el que se setea a la Caracteristica.
	 */
	public void setMario (Mario pj)
	{
		mario = pj;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Retorna el Mario asociado a la Caracteristica.
	 * @return el Mario asociado a la Caracteristica.
	 */
	public Mario getMario()
	{
		return mario;
	}
			
	public int spriteMuerto()
	{
		return muerto;
	}
		
	public int spriteCaminando()
	{
		return caminando;
	}
	
	public int cantSpritesCaminando()
	{
		return 3;
	}
	
	public int spriteSaltando()
	{
		return saltando;
	}
	
	public int spriteQuieto()
	{
		return quieto;
	}
	
	
}