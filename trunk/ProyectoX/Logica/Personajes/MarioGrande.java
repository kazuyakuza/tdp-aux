package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Responsabilidades.Posicionable;

/**
 * Representa a Mario en estado MarioGrande (cuando Mario toma el Super Hongo) del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioGrande extends Caracteristica
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioGrande, la ubicaci�n en los �ndices es:
	                                                {dirRecursos + "Mario-Dead.gif", 	  //0: Mario muerto
													 dirRecursos + "SuperMario.gif",      //1: Mario quieto		                                             
		                                             dirRecursos + "SuperMario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "SuperMario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "SuperMario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "SuperMario-Jump.gif", //5: Mario saltando
													 dirRecursos + "SuperMario-Duck.gif"};//6: Mario agachado
	
	//Numeros de los Sprites.
	protected static int agachado = 6;
	
	//Atribtuos de Instancia
	protected Celda celdaGrande;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Caracteristica para Mario, con estado MarioGhico.
	 * 
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	public MarioGrande (Mario pj)
	{
		super(pj);
		celdaGrande = mario.getCeldaActual().getSuperior();		
		celdaGrande.agregarActor(mario);			
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		if (mario.izq)
			mario.getSpriteManager().cambiarSprite(-agachado);
		else
			mario.getSpriteManager().cambiarSprite(agachado);
		
		celdaGrande.sacarActor(mario);
		//celdaGrande = mario.getCeldaActual();
		celdaGrande = null;
	}
	
	/**
	 * Mario realiza la acci�n A.
	 */
	public void accionA () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
		
	/**
	 * Mario realiza la acci�n B.
	 */
	public void accionB () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo. Dicho efecto evoluciona a Mario.
	 */
	public void crecerHongo () throws AccionActorException
	{
		//Esta Caracterisica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		mario.setCaracteristica(new MarioBlanco(mario));
		//mario = null;
	}
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public void serDa�ado (Actor a)
	{
		mario.setCaracteristica(new MarioChico(mario));
		mario.setCaracteristica(new Invulnerable (mario.getCaracteristica(), 5000));
		((Invulnerable)mario.getCaracteristica()).empezar();
		mario.getJugador().getControlCentral().cambiarPlataformasSuperHongo();
		//mario = null;
	}
	
	/**
	 * Realiza la acci�n de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		if (brick == null)
			throw new NullPointerException ("MarioGrande.golpearRompible()" + "\n" + 
											"Imposible golpear plataforma Rompible, brick es null.");
		brick.morir();
	}
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public String[] getNombresSprites()
	{
		return nombresSprites;
	}
	
	/**
	 * Retorna el multiplicador bonus que otorga �sta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus ()
	{
		return 5;
	}
	
	/**
	 * Calcula un vector que representa la distancia m�s cercana de Mario al Actor a en el eje cartesiano.
	 * El vector es de tam�ano 2 (x,y). 
	 * En el �ndice 0 se ubica la distancia de Mario al Actor a en el eje x. Si el valor es positivo, el Actor se encuentra a la derecha de Mario, sino a la izquierda.	 * 
	 * En el �ndice 1 se ubica la distancia de Mario al Actor a en el eje y. Si el valor es positivo, el Actor se encuentra por encima (arriba) de Mario, sino por debajo (abajo).
	 * 
	 * @param a Actor Posicionable que se utiliza para calcular la distancia hacia Mario.
	 * @return un arreglo de dos componentes (x,y) que contiene la distancia m�s cercana de Mario hacia el Actor a en el eje cartesinano.
	 */
	public int[] vectorDistancia (Posicionable a)
	{
		int [] vector = new int[2];
		//if (distancia (mario.getCeldaActual(), a.getCeldaActual()) <= distancia (celdaGrande, a.getCeldaActual()))
		
		if ( mario.getCeldaActual().distancia(a.getCeldaActual()) <= celdaGrande.distancia(a.getCeldaActual()) )
		{//Si la celda inferior de Mario (celdaActual) es la m�s cercana al Actor.
			vector = super.vectorDistancia(a);
		}
		else 
		{//Sino, la celda m�s cercana de Mario al Actor es la celda superior (celdaGrande). 
			vector[0] = a.getCeldaActual().getPosColumna() - celdaGrande.getPosColumna();
			vector[1] = celdaGrande.getPosFila() - a.getCeldaActual().getPosFila();
		}
		return vector;
	}
	
	/**
	 * Calcula la distancia que hay entre las Celdas.
	 * @param c1 Celda que se desea calcular su distancia a c2.
	 * @param c2 Celda que se desea calcular su distancia a c1.
	 * @return entero que es la distancia entre las Celdas c1 y c2.
	 * @throws NullPointerException si c1 o c2 son null.
	 */
	/*protected int distancia (Celda c1, Celda c2) throws NullPointerException
	{
		if (c1 == null || c2 == null)
			throw new NullPointerException ("ControlCentral.distancia()" + "\n" +
											"Imposible calcular distancia, alguna celda ess nulas.");
				
		int x = Math.abs(c1.getPosFila() - c2.getPosFila());
		int y = Math.abs(c1.getPosColumna() - c2.getPosColumna());		
		return (int) Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
	}*/
	
	/**
	 * Verifica si Mario se encuentra agachado.
	 * @return verdadero si Mario est� agachado, falso, en caso contrario.
	 */
	protected boolean agachado()
	{
		//return celdaGrande == mario.getCeldaActual();
		return celdaGrande == null;
	}
	
	/*METODOS REDEFINIDOS*/
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{			
		super.caer();		
		//if ( celdaGrande != null && mario.getCeldaActual() != celdaGrande.getInferior() )
			//moverseAcelda (celdaGrande.getInferior());		
	}
	
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
	 */
	public void saltar () throws AccionActorException
	{		
		if (condicionSaltar () && !this.agachado())
		{
			if (mario.miraIzq())
				mario.getSpriteManager().cambiarSprite(-saltando);
			else
				mario.getSpriteManager().cambiarSprite(saltando);
			if (/*celdaGrande != null &&*/ celdaGrande.haySuperior())
			{
				Celda celdaSuperior = celdaGrande.getSuperior();
				if (!celdaSuperior.isOcupada())
				{					
					moverseAcelda (celdaSuperior);
					//super.saltar();					
					
					mario.setPG(mario.getPG()+1);
					this.PS++;
					//mario.moverseAcelda(celdaGrande);
					this.actualizarCelda(celdaGrande);
				}
				else //Mario colisiona una Estructura desde abajo.
					mario.producirColisiones(celdaSuperior);
			}
		}
		else
			if ((mario.getPG() != -1) && (mario.getCeldaActual().getInferior().isOcupada()))
				PS = 0;		
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaGrande;
		try 
		{
			//if (celdaGrande == null)
				//throw new NullPointerException ("La celdaGrande del Actor es null.");
			
			if (celdaGrande.hayAnterior() && mario.getCeldaActual().hayAnterior() && !this.agachado())
			{				
				mario.mirarIzq(true);
				mario.getSpriteManager().cambiarSprite(-caminando);
				mario.getSpriteManager().setGif(cantSpritesCaminando());
				
				celdaAnterior = mario.getCeldaActual().getAnterior();
				if (!celdaGrande.getAnterior().isOcupada() && !celdaAnterior.isOcupada())
				{
					moverseAcelda (celdaGrande.getAnterior());
					mario.moverseAcelda(celdaAnterior);
					//super.moverseAizquierda();
					//if (mario.getCeldaActual() != celdaGrande.getInferior())
						//moverseAcelda (celdaAnterior);
				}
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("MarioGrande.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("MarioGrande.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la derecha.
	 * 
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{
		Celda celdaSiguiente = celdaGrande;
		try 
		{						
			if ( celdaGrande.haySiguiente() && mario.getCeldaActual().haySiguiente() && !this.agachado())
			{
				mario.mirarIzq(false);
				mario.getSpriteManager().cambiarSprite(caminando);
				mario.getSpriteManager().setGif(cantSpritesCaminando());
				
				celdaSiguiente = mario.getCeldaActual().getSiguiente();
				if (!celdaGrande.getSiguiente().isOcupada() && !celdaSiguiente.isOcupada())
				{
					moverseAcelda (celdaGrande.getSiguiente());
					mario.moverseAcelda(celdaSiguiente);					
					//super.moverseAderecha();
					//if (mario.getCeldaActual() != celdaGrande.getInferior())
						//this.moverseAcelda (celdaSiguiente);
				}
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("MarioGrande.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("MarioGrande.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Provoca las colisiones con los Actores en la Celda c, actualizando su ubicaci�n.
	 * Mueve Actor a la Celda c.
	 * Actualiza el SpriteManager.
	 * 
	 * @param c Celda a la que se mueve el Actor.
	 * @throws NullPointerException Si c es null.
	 */
	public void moverseAcelda (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("MarioGrande.moverseAcelda()" + "\n" +
                                            "Imposible moverse a la Celda c. c es null");
		
		mario.producirColisiones(c);
		//this.actualizarCelda(c);
		/*
		celdaGrande.sacarActor(mario);
		celdaGrande = c;
		celdaGrande.agregarActor(mario);
		*/
	}
	
	/**
	 * Modifica la Celda actual del actor por la Celda c.
	 * @param c es la nueva Celda para el Actor.
	 * @throws NullPointerException si c es null.
	 */
	protected void actualizarCelda (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Actor.ActualizarCelda()" + "\n" +
                                            "Imposible moverse a la Celda c. c es null");
		
		super.actualizarCelda(c);
		if (!this.agachado())
		{
			celdaGrande.sacarActor(mario);
			celdaGrande = c.getSuperior();
			celdaGrande.agregarActor(mario);
		}
		System.out.println("La celdaGrande de Mario es: " + celdaGrande.getPosFila() + " , "+ celdaGrande.getPosColumna() + " )");
		System.out.println("La celdaActual de Mario es: " + mario.getCeldaActual().getPosFila() + " , "+ mario.getCeldaActual().getPosColumna() + " )");
	}
	
}