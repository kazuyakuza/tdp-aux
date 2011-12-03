package ProyectoX.Logica.Personajes.Enemigo;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Excepciones.IAexception;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.ActualizadorNivel;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.IA.IA;
import ProyectoX.Logica.Personajes.Enemigo.IA.IAKT;
import ProyectoX.Logica.Responsabilidades.Movible;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

public class KoopaTroopa extends Actor implements Enemigo, Movible, afectableXgravedad
{	
	//Atributos de Instancia
	protected CaracteristicaKT miCaracteristica;
	protected IAKT miIA;
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acci�n arriba.
                     //Si PG=0, el Actor no es afectado por la Gravedad (est� sobre un lugar s�lido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acci�n de caer.

	protected boolean mov; //Mejora del moviemiento.
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminaci�n acciones.
	
	//Prioridades para el UpNeeder
	//0 = morir
	//1 = da�ar PJ
	//3 = spriteManager.cambiarSprite(quieto)
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por par�metro.
	 * 
	 * @param c Caracteristica de KoopaTroopa con la que se inicializa.
	 */
	public KoopaTroopa (CaracteristicaKT c)
	{
		super (c.getNombresSprites());
		upNeeder = new UpNeeder (3);
		Updater.getUpdater().addUpNeeder(upNeeder);
		miCaracteristica = c;
		c.setKoopaTroopa(this);
		spriteManager.cambiarSprite(miCaracteristica.spriteQuieto());
		miIA = new IAKT (this);
		PG = 0;
		mov = true;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acci�n "izquierda".
	 */
	public void izquierda ()
	{
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * Especifica la acci�n "derecha".
	 */
	public void derecha ()
	{
		miCaracteristica.moverseAderecha();
	}
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		if (upNeeder.hayWorkerPrioridad(0))//KoopaTroopa se va a morir en la proxim� actualizaci�n.
			return;
		
		Celda celdaInferior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.hayInferior())
			{
				celdaInferior = celdaActual.getInferior();
				if (!celdaInferior.isOcupada())
					moverseAcelda(celdaInferior);
				else
					PG = 0;
			}		
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("KoopaTroopa.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("KoopaTroopa.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acci�n de morir (ser destruido) de KoopaTroopa.
	 */
	public void morir ()
	{
		miIA.meMori(this);
		
		ActualizadorNivel.act().eliminarCaible(this);		
		ActualizadorNivel.act().eliminarEnemigo(this);
		
		miCaracteristica.setKoopaTroopa(null);
		miCaracteristica = null;
		
		super.morir();
		
		upNeeder.notUpdate();
		upNeeder = null;
		miIA = null;
	}
	
	/**
	 * KoopaTroopa realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		miCaracteristica.moverseAizquierda();
	}
	
	/**
	 * KoopaTroopa realiza la acci�n de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		miCaracteristica.moverseAderecha();
	}
	
	/**
	 * KoopaTroopa realiza la acci�n de recuperarse.
	 * 
	 * Esto es, recuperar el estado KTNormal si est� en estado KTCaparaz�n.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al recuperarse.
	 */
	public void recuperarse () throws AccionActorException
	{
		miCaracteristica.recuperarse();
	}
	
	/**
	 * Setea la IA que controla al Enemigo con ia.
	 * 
	 * @param ia IA del Enemigo.
	 * @throws NullPointerException Si ia es null.
	 * @throws IAexception Si se ingresa una IA que no es una IAKT.
	 */
	public void setIA (IA ia) throws NullPointerException, IAexception
	{
		if (ia == null)
			throw new NullPointerException ("KoopaTroopa.setIA()" + "\n" +
                                            "Imposible asignar una IA null.");
		
		try
		{
			miIA = (IAKT) ia;
		}
		catch (ClassCastException e)
		{
			throw new IAexception ("KoopaTroopa.setIA()" + "\n" +
                                   "Imposible asignar la IA ingresada. No es del tipo esperado. (IAKT)");
		}
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto)
	{
		if (celdaActual.getInferior().isOcupada())
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
		
		miIA.cambieDeEstado(this);
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
	 * Devuelve el UpNeeder del Actor.
	 * 
	 * @return UpNeeder del Actor.
	 */
	public UpNeeder getUpNeeder ()
	{
		return upNeeder;
	}
	
	/**
	 * Verifica si la colisi�n con el Actor proviene desde arriba.
	 * @param mario Mario con el que se colisiona.
	 * @return Verdadero si Mario se encuentra arriba, falso, en caso contrario.
	 */
	protected boolean colisionArriba (Mario mario)
	{
		//Mario se encuentra arriba del KoopaTroopa si y solo si para Mario el vectorDistancia = (0,1).
		int [] vector = mario.vectorDistancia(this);
		return (vector[0] == 0 && vector[1] == -1);
	}
	
	/*M�todos en Ejecuci�n*/
	
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
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarPj (final PjSeleccionable pj) throws ColisionException, NullPointerException
	{		
		if (pj == null)
			throw new NullPointerException ("Goomba.colisionarPj()" + "/n" +
											"Imposible realizar colisi�n, actor nulo.");
		
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
	 * @throws ColisionException Si se produce alg�n error en la colisi�n.
	 */
	public void colisionarBola (final BolaFuego bola) throws ColisionException, NullPointerException
	{
		if (bola == null)
			throw new NullPointerException ("Goomba.colisionarPJ()" + "\n" +
					                        "Imposible efectuar colision. El Actor Jugador pasado por par�metro es null.");
		
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
			throw new NullPointerException ("KoopaTroopa.producirColisiones()" + "\n" +
					                        "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);
	}

}