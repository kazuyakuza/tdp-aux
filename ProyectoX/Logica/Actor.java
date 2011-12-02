package ProyectoX.Logica;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.SpriteManager;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Responsabilidades.Posicionable;

/**
 * Representa a todos los objetos virtuales que pueden desarrolar una "actuación" dentro del juego.
 * Por "actuación" se entiende a algún tipo de interacción entre el Juego en si, otro objecto del juego, o el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class Actor implements Posicionable
{
	
	//Variables de Instancia
	  //Grafica y Sonido
	protected SpriteManager spriteManager;
	//private SoundManager soundManager;
	  //Logica
	protected Celda celdaActual;	
	
	/*CONSTRUCTOR*/
	
	/**
	 * Recibe los nombres de los Sprites para el Actor actual, y crea el SpriteManager.
	 * El SpriteManager carga los sprites.
	 * 
	 * @param nombresSprites Nombres de los archivos de las imagenes del Sprite para este Actor.
	 * @throws NullPointerException Si nombresSprites es null.
	 */
	protected Actor (String[] nombresSprites)
	{
		if ((nombresSprites == null) || (nombresSprites.length == 0))
			throw new NullPointerException ("Actor." + "\n" +
					                        "Imposible crear Actor. nombresSprites es null.");
		
		spriteManager = new SpriteManager (nombresSprites);
		celdaActual = null;		
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el SpriteManager actual por el nuevo sp.
	 * 
	 * @param sp Nuevo SpriteManager.
	 * @throws NullPointerException Si sp es null.
	 */
	public void setSpriteManager (SpriteManager sp) throws NullPointerException
	{
		if (sp == null)
			throw new NullPointerException ("Actor.setSpriteManager()" + "\n" +
                                            "El SpriteManager sp es null.");
		
		spriteManager = sp;
	}
	
	/**
	 * Cambia la Celda Actual por la Celda C.
	 * 
	 * @param c Nueva Celda Actual.
	 * @throws NullPointerException Si c es null.
	 */
	public void setCeldaActual (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Actor.setCeldaActual()" + "\n" +
                                            "Imposible asignar la Celda c. c es null");
		
		celdaActual = c;
		spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Provoca las colisiones con los Actores en la Celda c.
	 * Mueve Actor a la Celda c.
	 * Actualiza el SpriteManager.
	 * 
	 * @param c Celda a la que se mueve el Actor.
	 * @throws NullPointerException Si c es null.
	 */
	public void moverseAcelda (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Actor.moverseAcelda()" + "\n" +
                                            "Imposible moverse a la Celda c. c es null");
				
		this.producirColisiones(c);
		this.actualizarCelda(c);
		/*
		celdaActual.sacarActor(this);
		celdaActual = c;
		celdaActual.agregarActor(this);				
		*/	
		//spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Modifica la Celda actual del actor por la Celda c, actualizando su ubicación.
	 * @param c es la nueva Celda para el Actor.
	 * @throws NullPointerException si c es null.
	 */
	protected void actualizarCelda (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("Actor.ActualizarCelda()" + "\n" +
                                            "Imposible moverse a la Celda c. c es null");
		
		celdaActual.sacarActor(this);
		//celdaActual = c;
		this.setCeldaActual(c);
		celdaActual.agregarActor(this);	
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual.
	 * @throws NullPointerException Si c es null.
	 */
	protected abstract void producirColisiones (Celda c) throws NullPointerException;
	
	/*COMANDOS ABSTRACTOS*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si a es null.
	 * @throws ColisionException Si se produce algún error en la colisión.  
	 */
	public abstract void colisionar (Actor a) throws ColisionException, NullPointerException;
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException;
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionarBola (BolaFuego bola) throws ColisionException, NullPointerException;
	
	/**
	 * Realiza la acción de morir del Actor.
	 */
	public void morir ()
	{
		spriteManager.setEliminar();
		celdaActual.sacarActor(this);
		
		spriteManager = null;
		celdaActual = null;
	}
	
	/**
	 * Realiza un checkeo sobre actorJugador para verificar que no sea nulo y que sea un objeto Mario.
	 * 
	 * @param pj Actor que se quiere comprobar si es un Mario.
	 * @return el objeto Mario.
	 * @throws ColisionException si pj no es objeto Mario.
	 * @throws NullPointerException si pj es null.
	 */
	protected Mario checkActorJugador (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("Actor.checkActorJugador()" + "\n" +
					                        "Imposible colisionar con Actor nulo.");
		try
		{	
			Mario mario = (Mario) pj;
			return mario;
		}
		catch (ClassCastException e) 
		{
			throw new ColisionException ("Actor.checkActorJugador()" + "\n" +
                                         "Imposible realizar colisión, el actor no es un Mario.");
		}		
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el SpriteManager actual.
	 * 
	 * @return SpriteManager actual.
	 */
	public SpriteManager getSpriteManager ()
	{
		return spriteManager;
	}
	
	/**
	 * Devuelve la celda actual del Actor.
	 * 
	 * @return Celda actual del Actor.
	 */
	public Celda getCeldaActual ()
	{
		return celdaActual;
	}

}