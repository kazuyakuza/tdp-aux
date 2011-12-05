package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Responsabilidades.Posicionable;

/**
 * Representa a las Caracter�sticas que Mario puede tener en el juego. 
 * En base a �sta, Mario tiene determinado comportamiento ante los dem�s Actores.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */

public abstract class Caracteristica 
{
	//Atributos de Clase
	private static int maxPS = 4;//M�xima Potencia de Salto.
		
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
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	public abstract void agacharse () throws AccionActorException;
	
	/**
	 * Realiza la acci�n de pararse.
	 */
	public abstract void pararse ();
	
	/**
	 * Mario realiza la acci�n A.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n A.
	 */
	public abstract void accionA () throws AccionActorException;
		
	/**
	 * Mario realiza la acci�n B.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n B.
	 */
	public abstract void accionB () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public abstract void crecerHongo () throws AccionActorException;
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public abstract void crecerFlor () throws AccionActorException;
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public abstract void serDa�ado(Actor a);
	
	/**
	 * Realiza la acci�n de golpear una plataforma Rompible.
	 * @param brick es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si bricks es null.
	 */
	public abstract void golpearRompible (Rompible brick) throws NullPointerException;
	
	/**
	 * Retorna el multiplicador bonus que otorga �sta Caracteristica sobre los puntos.
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
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
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
					if (! mario.getUpNeeder().hayWorkerPrioridad(2))
						mario.getUpNeeder().addWorker(2, new Worker ()
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
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteritica.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}	
	
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
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
                                            "Imposible realizar la acci�n saltar." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.saltar()" + "\n" +
                                            "Imposible realizar la acci�n saltar a/desde Celda de posici�n (" + celdaSuperior.getPosFila() + "," + celdaSuperior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
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
				if (mario.getCeldaActual().getInferior().isOcupada())
				{
					mario.getSpriteManager().cambiarSprite(-caminando);
					mario.getSpriteManager().setGif(cantSpritesCaminando());
				}
				celdaAnterior = mario.getCeldaActual().getAnterior();
				if (!celdaAnterior.isOcupada())
					mario.moverseAcelda(celdaAnterior);
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Caracteristica.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverseAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
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
				if (mario.getCeldaActual().getInferior().isOcupada())
				{
					mario.getSpriteManager().cambiarSprite(caminando);
					mario.getSpriteManager().setGif(cantSpritesCaminando());
				}
				celdaSiguiente = mario.getCeldaActual().getSiguiente();
				if (!celdaSiguiente.isOcupada())
					mario.moverseAcelda(celdaSiguiente);								
			}
		}
		catch (NullPointerException e1)
		{	
			throw new AccionActorException ("Caracteristica.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Caracteristica.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
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
		
	/**
	 * Modifica la Celda actual del actor por la Celda c, actualizando su ubicaci�n.
	 * @param c es la nueva Celda para el Actor.
	 * @throws NullPointerException si c es null.
	 */
	protected void actualizarCelda (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Actor.ActualizarCelda()" + "\n" +
                                            "Imposible moverse a la Celda c. c es null");
		
		mario.getCeldaActual().sacarActor(mario);
		mario.setCeldaActual(c);
		mario.getCeldaActual().agregarActor(mario);	
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
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado muerto de Mario.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario muerto.
	 */
	public int spriteMuerto()
	{
		return muerto;
	}
		
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado de Mario caminando.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario caminando.
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
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado de Mario saltando.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario saltando.
	 */
	public int spriteSaltando()
	{
		return saltando;
	}
	
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado quieto de Mario.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario quieto.
	 */
	public int spriteQuieto()
	{
		return quieto;
	}
	
	/**
	 * Calcula un vector que representa la distancia de Mario al Actor a en el eje cartesiano.
	 * El vector es de tam�ano 2 (x,y). 
	 * En el �ndice 0 se ubica la distancia de Mario al Actor a en el eje x. Si el valor es positivo, el Actor se encuentra a la derecha de Mario, sino a la izquierda.	 * 
	 * En el �ndice 1 se ubica la distancia de Mario al Actor a en el eje y. Si el valor es positivo, el Actor se encuentra por encima (arriba) de Mario, sino por debajo (abajo).
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
	 * Verifica que se cumpla la condic�n para saltar.
	 * @return verdadero si se cumple la condici�n para saltar, falso, en caso contrario.
	 */
	protected boolean condicionSaltar ()
	{		
		return (mario.getPG() == 0) && (PS < maxPS);
	}

}