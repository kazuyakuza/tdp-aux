package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Mapa.Celda;

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
		celdaGrande = mario.getCeldaActual().getBloque().getSuperior(mario.getCeldaActual());
		
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
	
	/*METODOS REDEFINIDOS*/
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{		
		super.caer();
		if ( celdaGrande != null && mario.getCeldaActual() != celdaGrande.getBloque().getInferior(celdaGrande) )
			moverseAcelda (celdaGrande.getBloque().getInferior(celdaGrande));				
	}
	
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
	 */
	public void saltar () throws AccionActorException
	{		
		if (celdaGrande != null && celdaGrande.getBloque().haySuperior(celdaGrande))
		{
			Celda celdaSuperior = celdaGrande.getBloque().getSuperior(celdaGrande);
			if (!celdaSuperior.isOcupada())
			{
				super.saltar();
				moverseAcelda (celdaSuperior);
			}
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		if (celdaGrande != null && celdaGrande.getBloque().hayAnterior(celdaGrande))
		{
			Celda celdaAnterior = celdaGrande.getBloque().getAnterior(celdaGrande);
			if (!celdaAnterior.isOcupada())
			{
				super.moverseAizquierda();
				if (mario.getCeldaActual() != celdaGrande.getBloque().getInferior(celdaGrande))
					moverseAcelda (celdaAnterior);
			}
		}
	}
	
	/**
	 * Mario realiza la acci�n de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		if (celdaGrande != null && celdaGrande.getBloque().haySiguiente(celdaGrande))
		{
			Celda celdaSiguiente = celdaGrande.getBloque().getSiguiente(celdaGrande);
			if (!celdaSiguiente.isOcupada())
			{
				super.moverseAizquierda();
				if (mario.getCeldaActual() != celdaGrande.getBloque().getInferior(celdaGrande))
					moverseAcelda (celdaSiguiente);
			}
		}
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
		
		mario.producirColisiones(c);
		celdaGrande.sacarActor(mario);
		celdaGrande = c;
		celdaGrande.agregarActor(mario);
		//mario.getSpriteManager().actualizar(celdaGrande.getPosicion());
	}
	
}