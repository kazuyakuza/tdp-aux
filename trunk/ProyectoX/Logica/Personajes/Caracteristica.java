package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Responsabilidades.Posicionable;

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
		
	//Numeros de los Sprites.
	protected static int muerto = 0;
	protected static int quieto = 1;
	protected static int caminando = 2;
	protected static int saltando = 5;
	
	//Atributos de Instancia
	protected int PS = 0;//Potencia de Salto Actual.
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
			
			if (mario.getCeldaActual().hayInferior())
			{
				celdaInferior = mario.getCeldaActual().getInferior();
				if (!celdaInferior.isOcupada())
				{
					if (mario.miraIzq())
						mario.getSpriteManager().cambiarSprite(-saltando);
					else
						mario.getSpriteManager().cambiarSprite(saltando);
					mario.moverseAcelda(celdaInferior);
				}
				else
				{
					if (mario.miraIzq())
						mario.getSpriteManager().cambiarSprite(-spriteQuieto());
					else
						mario.getSpriteManager().cambiarSprite(spriteQuieto());
					mario.setPG(0);
					if (! mario.getUpNeeder().hayWorkerPrioridad(1))
						mario.getUpNeeder().addWorker(1, new Worker ()
			            {
			            	public void work() throws Exception
			            	{
			            		PS = 0;
			            	}
			            });
				}
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
			
			if (condicionSaltar())
			{
				if (mario.miraIzq())
					mario.getSpriteManager().cambiarSprite(-saltando);
				else
					mario.getSpriteManager().cambiarSprite(saltando);
				if (mario.getCeldaActual().haySuperior())
				{
					celdaSuperior = mario.getCeldaActual().getSuperior();
					if (!celdaSuperior.isOcupada())
					{
						mario.setPG(mario.getPG()+1);
						this.PS++;
						mario.moverseAcelda(celdaSuperior);
					}
					else //Mario colisiona una Estructura desde abajo.
					{
						mario.producirColisiones(celdaSuperior);
					}
				}
			}
			else
				if ((mario.getPG() != -1) && (mario.getCeldaActual().getInferior().isOcupada()))
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
			
			if (mario.getCeldaActual().hayAnterior())
			{
				mario.mirarIzq(true);
				mario.getSpriteManager().cambiarSprite(-caminando);
				mario.getSpriteManager().setGif(cantSpritesCaminando());
				celdaAnterior = mario.getCeldaActual().getAnterior();
				if (!celdaAnterior.isOcupada())
					mario.moverseAcelda(celdaAnterior);
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
			
			if (mario.getCeldaActual().haySiguiente())
			{
				mario.mirarIzq(false);
				mario.getSpriteManager().cambiarSprite(caminando);
				mario.getSpriteManager().setGif(cantSpritesCaminando());
				celdaSiguiente = mario.getCeldaActual().getSiguiente();
				if (!celdaSiguiente.isOcupada())
					mario.moverseAcelda(celdaSiguiente);								
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
			
	/**
	 * Retorna el índice del arreglo donde se encuentra el sprite que representa el estado muerto de Mario.
	 * @return un entero que es el índice del arreglo de sprite donde está el de Mario muerto.
	 */
	public int spriteMuerto()
	{
		return muerto;
	}
		
	/**
	 * Retorna el índice del arreglo donde se encuentra el sprite que representa el estado de Mario caminando.
	 * @return un entero que es el índice del arreglo de sprite donde está el de Mario caminando.
	 */
	public int spriteCaminando()
	{
		return caminando;
	}
	
	/**
	 * Retorna la cantidad de sprites que representan a Mario caminando.
	 * @return entero equivalente a la cantidad de sprites que animan a Mario caminando.
	 */
	public int cantSpritesCaminando()
	{
		return 3;
	}
	
	/**
	 * Retorna el índice del arreglo donde se encuentra el sprite que representa el estado de Mario saltando.
	 * @return un entero que es el índice del arreglo de sprite donde está el de Mario saltando.
	 */
	public int spriteSaltando()
	{
		return saltando;
	}
	
	/**
	 * Retorna el índice del arreglo donde se encuentra el sprite que representa el estado quieto de Mario.
	 * @return un entero que es el índice del arreglo de sprite donde está el de Mario quieto.
	 */
	public int spriteQuieto()
	{
		return quieto;
	}
	
	/**
	 * Calcula un vector que representa la distancia de Mario al Actor a en el eje cartesiano.
	 * El vector es de tamñano 2 (x,y). 
	 * En el índice 0 se ubica la distancia de Mario al Actor a en el eje x. Si el valor es positivo, el Actor se encuentra a la derecha de Mario, sino a la izquierda.	 * 
	 * En el índice 1 se ubica la distancia de Mario al Actor a en el eje y. Si el valor es positivo, el Actor se encuentra por encima (arriba) de Mario, sino por debajo (abajo).
	 * 
	 * @param a Actor Posicionable que se utiliza para calcular la distancia hacia Mario.
	 * @return un arreglo de dos componentes (x,y) que contiene la distancia de Mario hacia el Actor a en el eje cartesinano.
	 */
	public int[] vectorDistancia (Posicionable a)
	{
		int [] vector = new int[2];
		vector[0] = a.getCeldaActual().getPosColumna() - mario.getCeldaActual().getPosColumna();
		vector[1] = mario.getCeldaActual().getPosFila() - a.getCeldaActual().getPosFila();
		return vector;
	}
	
	/**
	 * Verifica que se cumpla la condicón para saltar.
	 * @return verdadero si se cumple la condición para saltar, falso, en caso contrario.
	 */
	protected boolean condicionSaltar ()
	{		
		return (mario.getPG() == 0) && (PS < maxPS);
	}

}