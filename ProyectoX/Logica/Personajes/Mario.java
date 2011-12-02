package ProyectoX.Logica.Personajes;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Jugador;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Responsabilidades.Movible;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Representa al tipo de Personaje Mario, personaje seleccionable del juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Mario extends Actor implements PjSeleccionable, Movible, afectableXgravedad
{				
	
	//Atributos de Instancia
	protected Caracteristica miCaracteristica;	//Representa al tipo de Mario, chico, grande o blanco.
	protected Jugador jugador;
	protected boolean izq = false;//Inidica si MarioBlanco está mirando hacia la izquierda.	
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acción arriba.
                     //Si PG=0, el Actor no es afectado por la Gravedad (está sobre un lugar sólido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acción de caer.
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminación acciones.
	
	//Prioridades para el UpNeeder
	//0 = spriteManager.cambiarSprite(quieto)
	//1 = PS = 0 en Característica.caer() y efectoGravedad.
	//4 = luego de disparar spriteManager.cambiarSprite(quieto)
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por parámetro.
	 * 
	 * @param c Caracteristica de Mario con la que se inicializa.
	 */
	public Mario (Caracteristica c)
	{
		super (c.getNombresSprites());
		upNeeder = new UpNeeder (5);
		Updater.getUpdater().addUpNeeder(upNeeder);
		miCaracteristica = c;
		c.setMario(this);
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
		PG = 0;		
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acción "arriba".
	 */
	public void arriba ()
	{
		miCaracteristica.saltar();
	}
	
	/**
	 * Especifica la acción "abajo".
	 */
	public void abajo ()
	{
		miCaracteristica.agacharse();
	}
	
	/**
	 * Especifica la acción "izquierda".
	 */
	public void izquierda ()
	{
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * Especifica la acción "derecha".
	 */
	public void derecha ()
	{
		miCaracteristica.moverseAderecha();
	}
	
	/**
	 * Especifica la acción "A".
	 */
	public void A ()
	{
		miCaracteristica.accionA();
	}
	
	/**
	 * Especifica la acción "B".
	 */
	public void B ()
	{
		miCaracteristica.accionB();
	}
	
	/**
	 * Especifica la no acción.
	 */
	public void quieto ()
	{
		cambiarSpriteQuieto();
	}
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		miCaracteristica.caer();
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de Mario.
	 * 
	 * @throws NullPointerException Si jugador es null.
	 * @throws AccionActorException Si se produce un error al morir.
	 */
	public void morir () throws NullPointerException, AccionActorException
	{				
		if (jugador == null)
			throw new NullPointerException ("Mario.morir()" + "\n" +
                                            "Imposible quitar vida al Jugador. Jugador es null.");
		
		spriteManager.cambiarSprite(miCaracteristica.spriteMuerto());
		ActualizadorNivel.act().eliminarCaible(this);
		ActualizadorNivel.act().eliminarPJ(this);
		
		super.morir();
		
		upNeeder.notUpdate();
		upNeeder = null;
		
		try
		{
			jugador.quitarVida();
		}
		catch (ClassCastException e)
		{	
			throw new AccionActorException("Mario.morir()" + "\n" +
										   "Imposible obtener los puntos del actor causante de la muerte.");
		}
	}
	
	/*COMANDOS*/
	
	/**
	 * Mario realiza la acción de saltar.
	 * 
	 * @throws AccionActorException Si se produce algún error al saltar.
	 */
	public void saltar () throws AccionActorException
	{
		miCaracteristica.saltar();
	}
	
	/**
	 * Mario realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{/* TODAVIA NO LO BORRES
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayAnterior(celdaActual))
			{
				izq = true;
				spriteManager.cambiarSprite(-miCaracteristica.spriteCaminando());
				spriteManager.setGif(3);
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Mario.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción moverAizquierda." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Mario.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
		*/
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * Mario realiza la acción de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{/*	TODAVIA NO LO BORRES
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().haySiguiente(celdaActual))
			{
				izq = false;
				spriteManager.cambiarSprite(miCaracteristica.spriteCaminando());
				spriteManager.setGif(3);
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Mario.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Mario.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
		*/
		miCaracteristica.moverseAderecha();
	}

	public void cambiarSpriteQuieto ()
	{
		if ((PG != -1) && (! upNeeder.hayWorkerPrioridad(0)))
            upNeeder.addWorker(0, new Worker ()
            {
            	public void work() throws Exception
            	{
            		if (izq)
						spriteManager.cambiarSprite(-miCaracteristica.spriteQuieto());
					else
						spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
            	}
            });
	}
	
	/**
	 * Setea al Jugador que controla a Mario con j.
	 * 
	 * @param j Jugador de Mario.
	 * @throws NullPointerException Si j es null.
	 */
	public void setJugador (Jugador j) throws NullPointerException
	{
		if (j == null)
			throw new NullPointerException ("Mario.setJugador()" + "\n" +
                                            "Imposible asignar un Jugador null.");
		
		jugador = j;
	}
		
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		miCaracteristica.crecerHongo();
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce algún error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		miCaracteristica.crecerFlor();
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
	}
	
	/**
	 * Realiza la acción de ser colisionado por un enemigo.
	 * @param a es el Actor (enemigo) que colisiona con Mario.
	 * @throws AccionActorException Si se produce algún error al ser dañado.
	 */
	public void serDañado (Actor a) throws AccionActorException
	{
		miCaracteristica.serDañado(a);
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
	}
	
	/**
	 * Realiza la acción de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		if (brick == null)
			throw new NullPointerException ("Mario.golpearRompible()" + "\n" + 
											"Imposible golpear plataforma, es null.");
		miCaracteristica.golpearRompible (brick);
	}
	
	/**
	 * Setea la Caracteristica de Mario con c.
	 * 
	 * @param c Caracteristica de Mario.
	 */
	public void setCaracteristica (Caracteristica c)
	{
		miCaracteristica = c;		
	}
		
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (celdaActual.getInferior().isOcupada())
			PG = 0;
		else
		{
			if (!(PG < 0))
				PG -= efecto;
		}
	}
	
	/**
	 * Setea si Mario mira hacia izquierda.	 
	 * @param iz boolean que indica si mario mira hacia izq.
	 */
	public void mirarIzq(boolean iz)
	{
		izq = iz;
	}
	
	/**
	 * Setea el PG de Mario.	 
	 * @param pg entero con el que se setea pg.
	 */
	public void setPG (int pg)
	{		
		PG = pg;
	}
			
	/*CONSULTAS*/
	
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
	 * Devuelve el Jugador que controla a Mario.
	 * 
	 * @return Jugador que controla a Mario.
	 */
	public Jugador getJugador ()
	{
		return jugador;
	}
	
	/**
	 * Retorna la Caracteristica asociada a Mario.
	 * @return la Caracteristica asociada a Mario.
	 */
	public Caracteristica getCaracteristica ()
	{
		return miCaracteristica;
	}
	
	/**
	 * Retorna el multiplicador bonus que aplica Mario sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus()
	{
		return miCaracteristica.multiplicadorBonus();
	}
	
	/**
	 * Verifica si Mario está mirando hacia la izquierda.
	 * @return Verdadero si Mario mira hacia la izquierda, falaso, en caso contrario.
	 */
	public boolean miraIzq()
	{
		return izq;
	}
	
	/**
	 * Calcula un vector que representa la distancia de Mario al Actor a en el eje cartesiano.
	 * El vector es de tamñano 2 (x,y). 
	 * En el índice 0 se ubica la distancia de Mario al Actor a en el eje x.
	 * En el índice 1 se ubica la distancia de Mario al Actor a en el eje y.
	 * 
	 * @param a Actor que se utiliza para calcular la distancia hacia Mario.
	 * @return un arreglo de dos componentes (x,y) que contiene la distancia de Mario hacia el Actor a en el eje cartesinano.
	 */
	public int[] vectorDistancia (Actor a)
	{
		return miCaracteristica.vectorDistancia(a);
	}
	
	/**
	 * Devuelve el UpNeeder del Actor.
	 * 
	 * @return UpNeeder del Actor.
	 */
	public UpNeeder getUpNeeder ()
	{
		return upNeeder;
	}
				
	/*Métodos en Ejecución*/
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si a es null.
	 * @throws ColisionException Si se produce algún error en la colisión. 
	 */
	public void colisionar (Actor a) throws ColisionException, NullPointerException
	{
		if (a == null)
			throw new NullPointerException ("Mario.colisionar()" + "\n" +
            								"Imposible colisionar con Actor nulo.");
		
		try
		{
			a.colisionarPj(this);
		}
		catch (Exception e)
		{
			throw new ColisionException ("Mario.colisionar()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 */
	public void colisionarPj (PjSeleccionable pj)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 */
	public void colisionarBola (BolaFuego bola)
	{
		//No le afecta.
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
			throw new NullPointerException ("Mario.producirColisiones()" + "\n" +
		      		                        "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionarPj(this);	
	}

}