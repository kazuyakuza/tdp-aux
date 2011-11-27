package ProyectoX.Logica.Personajes.Enemigo;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Excepciones.IAexception;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.BolaFuego;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.IA.IA;
import ProyectoX.Logica.Personajes.Enemigo.IA.IAGoomba;
import ProyectoX.Logica.Responsabilidades.Movible;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Representa al Enemigo Goomba del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Goomba extends Actor implements Enemigo, Movible //, afectableXgravedad
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Enemigos/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a Goomba, la ubicaci�n en los �ndices es:
	                                                {dirRecursos + "Goomba-1.png", //0: Goomba quieto
													 dirRecursos + "Goomba-2.png"};//1: Goomba movimiento
	//Atributos de Instancia
	protected IAGoomba miIA;
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acci�n arriba.
                     //Si PG=0, el Actor no es afectado por la Gravedad (est� sobre un lugar s�lido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acci�n de caer.

	//Numeros de los Sprites.
	protected static int quieto = 0;
	protected static int movimiento = 1;
	
	//Prioridades para el UpNeeder
	//0 = morir
	//1 = da�ar PJ
	//3 = spriteManager.cambiarSprite(quieto)
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por par�metro.
	 * 
	 * @param c Caracteristica de Mario con la que se inicializa.
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public Goomba (CargadorSprite cargadorSprite)
	{
		super (nombresSprites, cargadorSprite);
		miIA = new IAGoomba (this);
		PG = 0;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acci�n "izquierda".
	 */
	public synchronized void izquierda ()
	{
		moverseAizquierda();
	}
	
	/**
	 * Especifica la acci�n "derecha".
	 */
	public synchronized void derecha ()
	{
		moverseAderecha();
	}
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public synchronized void caer () throws AccionActorException
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
			throw new AccionActorException ("Goomba.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.caer()" + "\n" +
                                            "Imposible realizar la acci�n caer a/desde Celda de posici�n (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acci�n de morir (ser destruido) de Goomba.
	 */
	public void morir ()
	{
		miIA.meMori(this);
		celdaActual.getBloque().getMapa().getNivel().eliminarCaible(this);
		celdaActual.getBloque().getMapa().getNivel().eliminarActor(this);
		super.morir();
	}
	
	/**
	 * Goomba realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().hayAnterior(celdaActual))
			{
				spriteManager.cambiarSprite(-movimiento);
				celdaAnterior = celdaActual.getBloque().getAnterior(celdaActual);
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
				
				if (! upNeeder.hayWorkerPrioridad(3))
					upNeeder.addWorker(3, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		spriteManager.cambiarSprite(-quieto);
                    	}
                    });
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Goomba.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acci�n moverAizquierda a/desde Celda de posici�n (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Goomba realiza la acci�n de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.getBloque().haySiguiente(celdaActual))
			{
				spriteManager.cambiarSprite(movimiento);
				celdaSiguiente = celdaActual.getBloque().getSiguiente(celdaActual);
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
				
				if (! upNeeder.hayWorkerPrioridad(3))
                    upNeeder.addWorker(3, new Worker ()
                    {
                    	public void work() throws Exception
                    	{
                    		spriteManager.cambiarSprite(quieto);
                    	}
                    });
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Goomba.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acci�n moverAderecha a/desde Celda de posici�n (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Setea la IA que controla al Enemigo con ia.
	 * 
	 * @param ia IA del Enemigo.
	 * @throws NullPointerException Si ia es null.
	 * @throws IAexception Si se ingresa una IA que no es una IAGoomba.
	 */
	public void setIA (IA ia) throws NullPointerException, IAexception
	{
		if (ia == null)
			throw new NullPointerException ("Goomba.setIA()" + "\n" +
                                            "Imposible asignar una IA null.");
		
		try
		{
			miIA = (IAGoomba) ia;
		}
		catch (ClassCastException e)
		{
			throw new IAexception ("Goomba.setIA()" + "\n" +
                                   "Imposible asignar la IA ingresada. No es del tipo esperado. (IAGoomba)");
		}
	}
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
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
		return 60;
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
			
			if (celdaActual.getBloque().getSuperior(celdaActual) == mario.getCeldaActual())
			{
				pj.getJugador().asignarPuntos(60);
				
				if (! upNeeder.hayWorkerPrioridad(0))
					upNeeder.addWorker(0, new Worker ()
					{
						public void work() throws Exception
						{
							morir();
						}
					});
			}
			else
			{
				final Goomba gAux = this;
			
				if (! upNeeder.hayWorkerPrioridad(1))
					upNeeder.addWorker(1, new Worker ()
					{
						public void work() throws Exception
						{
							mario.serDa�ado(gAux);
						}
					});
			}
		}
		catch (Exception e)
		{
			throw new ColisionException ("Goomba.colisionarPj()" + "\n" +
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
			bola.getMario().getJugador().asignarPuntos(getPuntos(bola.getMario()));
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