package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;

/**
 * Representa a Mario en estado MarioBlanco. (cuando Mario toma la Flor de Fuego) del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class MarioBlanco extends Caracteristica
{
	//Atributos de Clase
	private static final String dirRecursos = "Mario/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a MarioBlanco, la ubicación en los índices es:
	                                                {dirRecursos + "Mario-Dead.gif", 	  //0: Mario muerto
													 dirRecursos + "FieryMario.gif",      //1: Mario quieto		                                             
		                                             dirRecursos + "FieryMario-Walk1.gif",//2: Mario caminando1
		                                             dirRecursos + "FieryMario-Walk2.gif",//3: Mario caminando2
		                                             dirRecursos + "FieryMario-Walk3.gif",//4: Mario caminando3
		                                             dirRecursos + "FieryMario-Jump.gif", //5: Mario saltando
													 dirRecursos + "FieryMario-Duck.gif",//6: Mario agachado
													 dirRecursos + "FieryMario-Fireball.gif"}; //7: Mario
	
	protected static int agachado = 6;
	protected static int lanzando = 7;
	
	//Atribtuos de Instancia
	protected Celda celdaGrande;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Caracteristica para Mario, con estado MarioBlanco.
	 * 
	 * @param pj es el Mario vinculado a la Caracteristica. 
	 */	
	public MarioBlanco (Mario pj)
	{
		super(pj);
		celdaGrande = mario.getCeldaActual().getBloque().getSuperior(mario.getCeldaActual());
	}
	
	/*COMANDOS IMPLEMENTADOS*/
		
	/**
	 * Mario realiza la acción de agacharse.
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
	 * Mario realiza la acción A.
	 */
	public void accionA () throws AccionActorException
	{
		if (mario.izq)
		{
			mario.getSpriteManager().cambiarSprite(-lanzando);
			
			if (! mario.getUpNeeder().hayWorkerPrioridad(4))
				mario.getUpNeeder().addWorker(4, new Worker ()
				{
					public void work() throws Exception
					{
						mario.getSpriteManager().cambiarSprite(-mario.miCaracteristica.spriteQuieto());
					}
				});
		}
		else
		{
			mario.getSpriteManager().cambiarSprite(lanzando);
			
			if (! mario.getUpNeeder().hayWorkerPrioridad(4))
				mario.getUpNeeder().addWorker(4, new Worker ()
				{
					public void work() throws Exception
					{
						mario.getSpriteManager().cambiarSprite(mario.miCaracteristica.spriteQuieto());
					}
				});
		}
		disparar();
	}
		
	/**
	 * Mario realiza la acción B.
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
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		//Esta Caracteristica no hace nada.
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colisionó con Mario.
	 */
	public void serDañado (Actor a)
	{
		mario.setCaracteristica(new MarioGrande(mario));
		mario.setCaracteristica(new Invulnerable (mario.getCaracteristica(), 4000));
		((Invulnerable)mario.getCaracteristica()).empezar();		
		//mario = null;
	}
	
	/**
	 * Realiza la acción de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		if (brick == null)
			throw new NullPointerException ("MarioBlanco.golpearRompible()" + "\n" + 
											"Imposible golpear plataforma Rompible, brick es null.");
		brick.morir();
	}
	
	/**
	 * Realiza la acción de MarioBlanco de lanzar una Bola de Fuego.
	 */
	public void disparar ()
	{
		BolaFuego bola = new BolaFuego (mario);
		mario.getCeldaActual().agregarActor(bola);
		bola.setCeldaActual(mario.getCeldaActual());
		mario.getSpriteManager().printNextMe(bola.getSpriteManager());
		mario.getJugador().getControlCentral().agregarActor(bola);
		//mario.getJugador().getControlCentral().agregarAfectableXgravedad(bola);
		if (mario.izq)
			bola.moverseAizquierda();
		else
			bola.moverseAderecha();
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
	 * Retorna el multiplicador bonus que otorga ésta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus ()
	{
		return 10;
	}
	
/*METODOS REDEFINIDOS*/
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
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
	 * Mario realiza la acción de saltar.
	 * 
	 * @throws AccionActorException Si se produce algún error al saltar.
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
	 * Mario realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
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
	 * Mario realiza la acción de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
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