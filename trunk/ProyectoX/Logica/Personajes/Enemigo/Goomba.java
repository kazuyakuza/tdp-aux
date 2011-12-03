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
public class Goomba extends Actor implements Enemigo, Movible, afectableXgravedad
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Enemigos/";
	private static final String [] nombresSprites = //En este arreglo se encuentran todas las rutas a las imagenes correspondientes a Goomba, la ubicación en los índices es:
	                                                {dirRecursos + "Goomba-1.png", //0: Goomba quieto
													 dirRecursos + "Goomba-2.png"};//1: Goomba movimiento
	//Atributos de Instancia
	protected IAGoomba miIA;
	protected int PG;//Potencia de la Gravedad.
	                 //Si PG>0, el Actor se esta "elevando". Generalmente realizando la acción arriba.
                     //Si PG=0, el Actor no es afectado por la Gravedad (está sobre un lugar sólido).
                     //Si PG<0, el Actor es afectado por la Gravedad, y se produce la acción de caer.

	//Numeros de los Sprites.
	protected static int quieto = 0;
	protected static int movimiento = 1;
	protected boolean mov; //Mejora del moviemiento.
	
	//Actualizador
	protected UpNeeder upNeeder; //UpNeeder para terminación acciones.
	
	//Prioridades para el UpNeeder
	//0 = morir
	//1 = dañar PJ
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Personaje Seleccionable Mario con la Caracteristica pasada por parámetro.
	 */
	public Goomba ()
	{
		super (nombresSprites);
		upNeeder = new UpNeeder (1);
		Updater.getUpdater().addUpNeeder(upNeeder);
		miIA = new IAGoomba (this);
		PG = 0;
		mov = true;
	}
	
	/*COMANDOS IMPLEMENTADOS*/
	
	/**
	 * Especifica la acción "izquierda".
	 */
	public void izquierda ()
	{
		moverseAizquierda();
	}
	
	/**
	 * Especifica la acción "derecha".
	 */
	public void derecha ()
	{
		moverseAderecha();
	}
	
	/**
	 * Realiza la Acción Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		if (upNeeder.hayWorkerPrioridad(0))//Goomba se va a morir en la proximá actualización.
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
			throw new AccionActorException ("Goomba.caer()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.caer()" + "\n" +
                                            "Imposible realizar la acción caer a/desde Celda de posición (" + celdaInferior.getPosFila() + "," + celdaInferior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Realiza la acción de morir (ser destruido) de Goomba.
	 */
	public void morir ()
	{
		miIA.meMori(this);
		
		ActualizadorNivel.act().eliminarCaible(this);
		ActualizadorNivel.act().eliminarEnemigo(this);
		
		super.morir();
		
		upNeeder.notUpdate();
		upNeeder = null;
		miIA = null;
	}
	
	/**
	 * Goomba realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		Celda celdaAnterior = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.hayAnterior())
			{
				if (mov)
					spriteManager.cambiarSprite(movimiento);
				else
					spriteManager.cambiarSprite(quieto);
				mov = !mov;
				celdaAnterior = celdaActual.getAnterior();
				if (!celdaAnterior.isOcupada())
					moverseAcelda(celdaAnterior);
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Goomba.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.moverseAizquierda()" + "\n" +
                                            "Imposible realizar la acción moverAizquierda a/desde Celda de posición (" + celdaAnterior.getPosFila() + "," + celdaAnterior.getPosColumna() + ")." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e2.getMessage());
		}
	}
	
	/**
	 * Goomba realiza la acción de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		Celda celdaSiguiente = celdaActual;
		try 
		{
			if (celdaActual == null)
				throw new NullPointerException ("La celdaActual del Actor es null.");
			
			if (celdaActual.haySiguiente())
			{
				if (mov)
					spriteManager.cambiarSprite(movimiento);
				else
					spriteManager.cambiarSprite(quieto);
				mov = !mov;
				celdaSiguiente = celdaActual.getSiguiente();
				if (!celdaSiguiente.isOcupada())
					moverseAcelda(celdaSiguiente);
			}
		}
		catch (NullPointerException e1)
		{
			throw new AccionActorException ("Goomba.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción caer." + "\n" +
					                        "Detalles del error:" + "\n" +
					                        e1.getMessage());
		}
		catch (Exception e2)
		{
			throw new AccionActorException ("Goomba.moverseAderecha()" + "\n" +
                                            "Imposible realizar la acción moverAderecha a/desde Celda de posición (" + celdaSiguiente.getPosFila() + "," + celdaSiguiente.getPosColumna() + ")." + "\n" +
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
	 * Si la Gravedad afecta a este Actor, entonces llamará a este método para afectarlo.
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
	 * Verifica si la colisión con el Actor proviene desde arriba.
	 * @param mario Mario con el que se colisiona.
	 * @return Verdadero si Mario se encuentra arriba, falso, en caso contrario.
	 */
	protected boolean colisionArriba (Mario mario)
	{
		//Mario se encuentra arriba del Goomba si y solo si para Mario el vectorDistancia = (0,1).
		int [] vector = mario.vectorDistancia(this);
		return (vector[0] == 0 && vector[1] == -1);
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
			
			//if (celdaActual.getSuperior() == mario.getCeldaActual())
			if (colisionArriba(mario))
			{
				pj.getJugador().asignarPuntos(this.getPuntos(mario));
				
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
							mario.serDañado(gAux);
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
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarBola (final BolaFuego bola) throws ColisionException, NullPointerException
	{
		if (bola == null)
			throw new NullPointerException ("Goomba.colisionarPJ()" + "\n" +
					                        "Imposible efectuar colision. El Actor Jugador pasado por parámetro es null.");
		
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
			throw new NullPointerException ("Goomba.producirColisiones()" + "\n" +
					                        "Imposible realizar colisiones. La celda indicada es null.");
		
		Iterator <Actor> actores = c.getActores();
		while (actores.hasNext())
			actores.next().colisionar(this);
	}
	
}