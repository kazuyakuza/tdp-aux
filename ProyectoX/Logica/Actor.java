package ProyectoX.Logica;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Grafico.Sprite.SpriteManager;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Mario;

/**
 * Representa a todos los objetos virtuales que pueden desarrolar una "actuación" dentro del juego.
 * Por "actuación" se entiende a algún tipo de interacción entre el Juego en si, otro objecto del juego, o el Jugador del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class Actor
{
	
	//Variables de Instancia
	  //Grafica y Sonido
	protected SpriteManager spriteManager;
	//private SoundManager soundManager;
	  //Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminación acciones.
	  //Logica
	protected Celda celdaActual; 
	protected int PG;//Potencia de la Gravedad.
					 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acción arriba.
					 //Si PG=0, el Actor no es afectado por la Gravedad (está sobre un lugar sólido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acción de caer.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Recibe los nombres de los Sprites para el Actor actual, y crea el SpriteManager.
	 * El SpriteManager carga los sprites con el CargadorSprite pasado por parámetro.
	 * 
	 * @param nombresSprites Nombres de los archivos de las imagenes del Sprite para este Actor.
	 * @param cargadorSprite Clase para cargar los sprites.
	 * @throws NullPointerException Si nombresSprites es null, o cargadorSprite es null.
	 */
	protected Actor (String[] nombresSprites, CargadorSprite cargadorSprite)
	{
		if ((nombresSprites == null) || (nombresSprites.length == 0))
			throw new NullPointerException ("Actor." + "\n" +
					                        "Imposible crear Actor. nombresSprites es null.");
		if (cargadorSprite == null)
			throw new NullPointerException ("Actor." + "\n" +
                                            "Imposible crear Actor. cargadorSprite es null.");
		
		spriteManager = new SpriteManager (nombresSprites, cargadorSprite);
		upNeeder = new UpNeeder (5);
		celdaActual = null;
		PG = 0;
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
		
		producirColisiones(c);
		celdaActual.sacarActor(this);
		celdaActual = c;
		celdaActual.agregarActor(this);
		spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Realiza las colisiones del Actor actual con los Actores que se encuentran en la Celda c.
	 * 
	 * @param c Celda con los Actores a colisionar con el Actor actual. 
	 */
	protected abstract void producirColisiones (Celda c);
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (celdaActual.getBloque().getInferior(celdaActual).isOcupada())
			PG = 0;
		else
			if (!(PG < 0))
				PG -= efecto;
	}
	
	/*COMANDOS ABSTRACTOS*/
	
	/**
	 * Realiza la acción de colisionar con otro Actor a.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionar (Actor a) throws ColisionException;
	
	/**
	 * Realiza la acción de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionarPj (Actor actorJugador) throws ColisionException;
	
	/**
	 * Realiza la Acción caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce algún error al caer.
	 */
	public abstract void caer () throws AccionActorException;
	
	/**
	 * Realiza la acción de morir del Actor.
	 */
	public void morir (Actor a)
	{
		spriteManager.setEliminar();
		celdaActual.sacarActor(this);
		
		spriteManager = null;
		celdaActual = null;
		
		upNeeder.notUpdate();
		upNeeder = null;

		PG = 0;
	}
	
	/**
	 * Realiza un checkeo sobre actorJugador para verificar que no sea nulo y que sea un objeto Mario.
	 * 
	 * @param actorJugador Actor que se quiere comprobar si es un Mario.
	 * @return el objeto Mario.
	 * @throws ColisionException si actorJugador no es objeto Mario.
	 * @throws NullPointerException si actorJugador es null.
	 */
	protected Mario checkActorJugador (Actor actorJugador) throws ColisionException, NullPointerException
	{
		if (actorJugador == null)
			throw new NullPointerException ("Actor.checkActorJugador()" + "\n" +
					                        "Imposible colisionar con Actor nulo.");
		try
		{	
			Mario mario = (Mario) actorJugador;
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
	
    /**
	 * Devuelve la Potencia de la Gravedad sobre este Actor.
	 * 
	 * @return Potencia de la Gravedad sobre este Actor.
	 */
	public int getPG ()
	{
		return PG;
	}
	
	/**
	 * Devuelve el UpNeeder del Actor junto con el UpNeeder del SpriteManager.
	 * 
	 * @return UpNeeder del Actor junto con el UpNeeder del SpriteManager.
	 */
	public PositionList<UpNeeder> getUpNeeders ()
	{
		PositionList<UpNeeder> r = new ListaPositionSimple<UpNeeder> ();
		r.addFirst(upNeeder);
		r.addFirst(spriteManager.getUpNeeder());
		return r;
	}

}