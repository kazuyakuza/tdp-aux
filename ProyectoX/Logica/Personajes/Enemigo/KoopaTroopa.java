package ProyectoX.Logica.Personajes.Enemigo;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.IA.IA;
import ProyectoX.Logica.Responsabilidades.Movible;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

public class KoopaTroopa extends Actor implements Enemigo, Movible, afectableXgravedad
{	
	//Atributos de Instancia
	protected CaracteristicaKT miCaracteristica;
	protected IA miIA;
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acción arriba.
                     //Si PG=0, el Actor no es afectado por la Gravedad (está sobre un lugar sólido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acción de caer.

	
	
	//Prioridades para el UpNeeder
	//0 = morir
	//1 = dañar PJ
	//3 = spriteManager.cambiarSprite(quieto)
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por parámetro.
	 * 
	 * @param c Caracteristica de Mario con la que se inicializa.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public KoopaTroopa (CaracteristicaKT c, CargadorSprite cargadorSprite)
	{
		super (c.getNombresSprites(), cargadorSprite);
		miCaracteristica = c;
		c.setKoopaTroopa(this);
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());		
		PG = 0;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acción "izquierda".
	 */
	public synchronized void izquierda ()
	{
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * Especifica la acción "derecha".
	 */
	public synchronized void derecha ()
	{
		miCaracteristica.moverseAderecha();
	}
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		Celda celdaInferior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayInferior(celdaActual))
			{
				celdaInferior = celdaActual.getBloque().getInferior(celdaActual);
				if (!celdaInferior.isOcupada())
					moverseAcelda(celdaInferior);
				else
					PG = 0;
			}		
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("KoopaTroopa.caer()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("KoopaTroopa.caer()" + "\n" +
                                            "Imposible realizar la acción caer a/desde Celda de posición (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
		
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de KoopaTroopa.
	 */
	public void morir ()
	{
		celdaActual.getBloque().getMapa().getNivel().eliminarCaible(this);		
		celdaActual.getBloque().getMapa().getNivel().eliminarActor(this);
		//celdaActual.getBloque().getMapa().getNivel().eliminarEnemigo(this);		
		miCaracteristica.setKoopaTroopa(null);
		miCaracteristica = null;
		super.morir();
	}
	
	/**
	 * KoopaTroopa realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * KoopaTroopa realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		miCaracteristica.moverseAderecha();
	}
	
	/**
	 * Setea la IA que controla al Enemigo con ia.
	 * 
	 * @param j IA del Enemigo.
	 * @throws NullPointerException Si ia es null.
	 */
	public void setIA (IA ia) throws NullPointerException
	{
		if (ia == null)
			throw new NullPointerException ("Goomba.setIA()" + "\n" +
                                            "Imposible asignar un Jugador null.");
		
		miIA = ia;
	}
	
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
	
	/**
	 * Setea a la CaracteristicaKT del KoopaTroopa con c.
	 *  
	 * @param c CaracteristicaKT con la que se setea al KoopaTroopa.
	 * @throws NullPointerException si c es null.
	 */
	public void setCaracteristicaKT (CaracteristicaKT c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("KoopaTroopa.setCaracteristicaKT()" + "\n" + 
											"Imposible asignar CaracteristicaKT, la misma es nula.");
		miCaracteristica = c;
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
	 * Devuelve la IA que controla a Goomba.
	 * 
	 * @return IA que controla a Goomba.
	 */
	public IA getIA ()
	{
		return miIA;
	}
	
	/**
	 * Retorna los puntos que un Punteable otorga.
	 * 
	 * @param mario Mario al que se le calculan los puntos a otorgar.
	 * @return los puntos que otorga.
	 */
	public int getPuntos (Mario mario)
	{
		return 90;
	}
	
	/**
	 * Efecto provocado por el Actor a que colisiona con el Actor actual.
	 * 
	 * @param a Actor que colisiona al Actor actual.
	 */
	public void colisionar (Actor a)
	{
		//No le afecta.
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Muere el Goomba actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (final PjSeleccionable pj) throws ColisionException, NullPointerException
	{		
		if (pj == null)
			throw new NullPointerException ("Goomba.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			final Mario mario = checkActorJugador(pj);
			miCaracteristica.colisionarPj(mario);
		}
		catch (Exception e)
		{
			throw new ColisionException ("KoopaTroopa.colisionarPj()" + "\n" +
						                 "Detalles del Error:" + "\n" +
						                 e.getMessage());
		}		
	}
	
	/**
	 * Efecto provocado por la Bola de Fuego bola que colisiona con el Actor actual.
	 * 
	 * @param bola Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (final BolaFuego bola) throws ColisionException, NullPointerException
	{
		if (bola == null)
			throw new NullPointerException ("Goomba.colisionarPJ()" + "\n" +
					                        "Imposible efectuar colision. El Actor Jugador pasado por parámetro es null.");
		
		try
		{
			bola.getMario().getJugador().asignarPuntos(this.getPuntos(bola.getMario()));
			bola.explotar();
			if (! upNeeder.hayWorkerPrioridad(0))
	            upNeeder.addWorker(0, new Worker ()
	            {
	            	public void work() throws Exception
	            	{
	            		morir();
	            	}
	            });
		}
		catch (Exception e)
		{
			throw new ColisionException ("Vacio.colisionarBola()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
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
			throw new NullPointerException ("BolaFuego.producirColisiones()" + "\n" +
					                        "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);
	}

}