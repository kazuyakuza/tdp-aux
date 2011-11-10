package ProyectoX.Logica;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Grafico.Sprite.SpriteManager;
import ProyectoX.Logica.Mapa.Celda;

/**
 * Representa a todos los objetos virtuales que pueden desarrolar una "actuaci�n" dentro del juego.
 * Por "actuaci�n" se entiende a alg�n tipo de interacci�n entre el Juego en si, otro objecto del juego, o el Jugador del Juego.
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
	  //Logica
	protected Celda celdaActual; 
	protected int PG;//Potencia de la Gravedad.
					 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acci�n arriba.
					 //Si PG=0, el Actor no es afectado por la Gravedad (est� sobre un lugar s�lido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acci�n de caer.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Recibe los nombres de los Sprites para el Actor actual, y crea el SpriteManager.
	 * El SpriteManager carga los sprites con el CargadorSprite pasado por par�metro.
	 * 
	 * @param nombresSprites Nombres de los archivos de las imagenes del Sprite para este Actor.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	protected Actor (String[] nombresSprites, CargadorSprite cargadorSprite)
	{
		spriteManager = new SpriteManager (nombresSprites, cargadorSprite);
		celdaActual = null;
		PG = 0;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el SpriteManager actual por el nuevo sp.
	 * 
	 * @param sp Nuevo SpriteManager.
	 */
	public void setSpriteManager (SpriteManager sp)
	{
		spriteManager = sp;
	}
	
	/**
	 * Cambia la Celda Actual por la Celda C.
	 * 
	 * @param c Nueva Celda Actual.
	 */
	public void setCeldaActual (Celda c)
	{
		celdaActual = c;
		spriteManager.actualizar(celdaActual.getPosicion());
	}
	
	/**
	 * Provoca las colisiones con los Actores en la Celda c.
	 * Mueve Actor a la Celda c.
	 * Actualiza el SpriteManager.
	 * 
	 * @param c Celda a la que se mueve el Actor.
	 */
	public void moverseAcelda (Celda c)
	{
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
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (!(PG < 0))
			PG -= efecto;
	}
	
	/*COMANDOS ABSTRACTOS*/
	
	/**
	 * Realiza la acci�n de colisionar con otro Actor a.
	 * 
	 * @param a Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public abstract void colisionar (Actor a) throws ColisionException;
	
	/**
	 * Realiza la acci�n de colisionar con un Personaje Seleccionable de un Jugador.
	 * 
	 * @param actorJugador Actor con el que se va a colisionar.
	 * @exception ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public abstract void colisionarPj (Actor actorJugador) throws ColisionException;
	
	/**
	 * Realiza la Acci�n caer, producida por el efecto de la Gravedad.
	 * 
	 * @exception AccionActorException Si se produce alg�n error al caer.
	 */
	public abstract void caer () throws AccionActorException;
	
	/**
	 * Realiza la acci�n de morir del Actor.
	 */
	public void morir()
	{
		/*spriteManager.setEliminar();
		celdaActual.sacarActor(this);
		
		spriteManager = null;
		celdaActual = null;
		PG = 0;*/
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

}