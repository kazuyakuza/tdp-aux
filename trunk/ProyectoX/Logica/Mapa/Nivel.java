package ProyectoX.Logica.Mapa;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.InicioNivelException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.NoPersonajes.Estructura;
import ProyectoX.Logica.NoPersonajes.Moneda;
import ProyectoX.Logica.NoPersonajes.Piso;
import ProyectoX.Logica.NoPersonajes.Especiales.Llegada;
import ProyectoX.Logica.NoPersonajes.Especiales.Vacio;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialMonedas;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialPowerUp;
import ProyectoX.Logica.NoPersonajes.Plataformas.Irrompible;
import ProyectoX.Logica.NoPersonajes.PowerUps.Estrella;
import ProyectoX.Logica.NoPersonajes.PowerUps.FlorFuego;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.SuperHongo;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;

/**
 * Representa un Nivel del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Nivel
{
	
	//Atributos de Clase
	private static final String dirRecursos = "Fondos/";
	
	//Atributos de Instancia
	protected Mapa mapa;
	protected Bloque bloqueActual; //Bloque en el que est� el Jugador.
	protected int id; //Identificaci�n del Nivel (1,2,3,4...)
	protected String fondo;
	private PositionList<Actor> actores; //Lista de todos los Actores en el Nivel actual.
	private PositionList<Llegada> llegadas; //Lista de todos los Actores Llegada en el Nivel actual.
	private PositionList<Vacio> vacios; //Lista de todos los Actores Vacio en el Nivel actual.
	private PositionList<Estructura> estructuras; //Lista de todos los Actores Estructura en el Nivel actual.
	private PositionList<EspecialPowerUp> especialesPowerUp; //Lista de todos los Actores EspecialPowerUp en el Nivel actual.
	private PositionList<PowerUp> powerUps; //Lista de todos los Actores PowerUp en el Nivel actual.
	private PositionList<Enemigo> enemigos; //Lista de todos los Actores Enemigo en el Nivel actual.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea el Nivel i.
	 * 
	 * @param i Nivel a crear.
	 */
	public Nivel (int i)
	{
		id = i;
		bloqueActual = null;
		mapa = null;
		fondo = null;
		
		actores = new ListaPositionSimple<Actor> ();
		llegadas = new ListaPositionSimple<Llegada> ();
		vacios = new ListaPositionSimple<Vacio> ();
		estructuras = new ListaPositionSimple<Estructura> ();
		especialesPowerUp = new ListaPositionSimple<EspecialPowerUp> ();
		powerUps = new ListaPositionSimple<PowerUp> ();
		enemigos = new ListaPositionSimple<Enemigo> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa el Nivel creado.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @param cargadorSprite Clase encargada de cargar las imagenes de los Actores.
	 * @throws InicioNivelException Si se produce un error al Iniciar el Nivel.
	 */
	public void inicializarNivel (Actor actorJugador, ControlCentral cc, CargadorSprite cargadorSprite) throws InicioNivelException
	{
		try
		{
			switch (id)
			{
				case 1:
					   {
						   nivel1(actorJugador, cc, cargadorSprite);
						   break;
					   }
				default:
				{
					throw new InicioNivelException ("El Nivel indicado no existe.");
				}
			}
		}
		catch (Exception e)
		{
			throw new InicioNivelException ("Nivel.inicializarNivel()" + "\n" +
                                            "Error al Inicializar el Nivel " + id + "." + "\n" +
					                        "Detalles del Error: " + "\n" +
					                        e.getMessage());
		}
	}
	
	/**
	 * Crea el Nivel 1.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @param cargadorSprite Clase encargada de cargar las imagenes de los Actores.
	 */
	private void nivel1 (Actor actor, ControlCentral cc, CargadorSprite cargadorSprite)
	{
		fondo = "Fondo2.png";
		
		mapa = new Mapa (this, 1, 1);
		
		//Creaci�n Bloque 1 del Nivel. Que es el Bloque de inicio del Nivel.
		bloqueActual = new Bloque(mapa, 0, 0, 13, 20);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregaci�n Piso
		int aux = 0;
		while (aux < 20)
		{
			/*if (aux != 6)
			{*/
				Piso piso = new Piso(cargadorSprite);
				bloqueActual.ABC[11][aux].agregarEstructura(piso);
				piso.setCeldaActual(bloqueActual.ABC[11][aux]);
				actores.addFirst(piso);
				estructuras.addFirst(piso);
			//}
			aux++;
		}
		
		//Agregaci�n Actor del Jugador.
		bloqueActual.ABC[10][1].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[10][1]);
		actores.addFirst(actor);
		
		//Agregaci�n Actores Enemigos.
		/*
		aux = 0;
		while (aux < 9)
		{
			Goomba goomba = new Goomba (cargadorSprite);
			bloqueActual.ABC[1][10 + aux].agregarActor(goomba);
			goomba.setCeldaActual(bloqueActual.ABC[1][10 + aux]);
			actores.addFirst(goomba);
			enemigos.addFirst(goomba);
			aux++;
		}
		*/
		
		//Agregacion de un power up.
		PowerUp powerUp = new SuperHongo(cargadorSprite);
		bloqueActual.ABC[3][7].agregarActor(powerUp);
		powerUp.setCeldaActual(bloqueActual.ABC[3][7]);
		actores.addLast(powerUp);
		powerUps.addLast(powerUp);
		
		PowerUp flor = new FlorFuego(cargadorSprite);
		bloqueActual.ABC[3][8].agregarActor(flor);
		flor.setCeldaActual(bloqueActual.ABC[3][8]);
		actores.addLast(flor);
		powerUps.addLast(flor);
		
		PowerUp bomba = new Estrella(cargadorSprite);
		bloqueActual.ABC[8][6].agregarActor(bomba);
		bomba.setCeldaActual(bloqueActual.ABC[8][6]);
		actores.addLast(bomba);
		powerUps.addLast(bomba);
		
		Moneda moneda1 = new Moneda(cargadorSprite);
		bloqueActual.ABC[8][8].agregarActor(moneda1);
		moneda1.setCeldaActual(bloqueActual.ABC[8][8]);
		actores.addLast(moneda1);
		
		Moneda moneda2 = new Moneda(cargadorSprite);
		bloqueActual.ABC[10][8].agregarActor(moneda2);
		moneda2.setCeldaActual(bloqueActual.ABC[10][8]);		
		actores.addLast(moneda2);
		
		
		//Agregaci�n Actores no Personjes.
		aux = 6;
		while (aux < 12)
		{
			Irrompible plataforma = new Irrompible (cargadorSprite);
			bloqueActual.ABC[7][aux].setOcupada(true);
			bloqueActual.ABC[7][aux].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[7][aux]);
			actores.addFirst(plataforma);
			estructuras.addFirst(plataforma);
			aux++;
		}
		
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(cargadorSprite),true, cargadorSprite);
		bloqueActual.ABC[7][5].setOcupada(true);
		bloqueActual.ABC[7][5].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[7][5]);
		actores.addFirst(plataformaPUP);
		//estructuras.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		EspecialMonedas plataformaM = new EspecialMonedas (3, cargadorSprite);
		bloqueActual.ABC[7][13].setOcupada(true);
		bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
		plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
		actores.addFirst(plataformaM);
		estructuras.addFirst(plataformaM);
		
		EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new Estrella(cargadorSprite),false, cargadorSprite);
		bloqueActual.ABC[7][14].setOcupada(true);
		bloqueActual.ABC[7][14].agregarEstructura(plataformaPUP2);
		plataformaPUP2.setCeldaActual(bloqueActual.ABC[7][14]);
		actores.addFirst(plataformaPUP2);
		//estructuras.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP2);
		
		
		//Vacio en el Piso.
		//bloqueActual.ABC[6][6].setOcupada(false);
		
		//Piso al costado del Vacio.
		/*bloqueActual.ABC[7][5].setOcupada(true);
		Piso piso = new Piso(cargadorSprite);
		bloqueActual.ABC[7][5].agregarEstructura(piso);
		piso.setCeldaActual(bloqueActual.ABC[7][5]);
		listaActores.addFirst(piso);*/
		
		//Piso al costado del Vacio.
		/*bloqueActual.ABC[7][7].setOcupada(true);
		Piso piso2 = new Piso(cargadorSprite);
		bloqueActual.ABC[7][7].agregarEstructura(piso2);
		piso2.setCeldaActual(bloqueActual.ABC[7][7]);
		listaActores.addFirst(piso2);*/
		
		//Agregaci�n Principio Mapa
		bloqueActual.setColumnaOcupada(0, true);
		
		//Agregaci�n Final Mapa
		bloqueActual.setColumnaOcupada(19, true);
		
		//Agregaci�n Llegada
		/*aux = 0;
		while (aux <= 5)
		{
			Actor llegada = new Llegada(cc, cargadorSprite);
			bloqueActual.ABC[aux][9].agregarActor(llegada);
			llegada.setCeldaActual(bloqueActual.ABC[aux][9]);
			listaActores.addFirst(llegada);
			aux++;
		}*/
	}
	
	/**
	 * Cambia el Blque Actual por el Bloque pasadon por par�metro.
	 * 
	 * @param bloque Nuevo Bloque Actual.
	 */
	public void setBloqueActual (Bloque bloque)
	{
		bloqueActual = bloque;
	}
	
	/**
	 * Elimina el Actor actor del Nivel.
	 * 
	 * @param actor Actor a eliminar.
	 * @throws NullPointerException Si actor es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un Actor que no pertenece al Nivel.
	 */
	public void eliminarActores (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("Nivel.eliminarActores()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != actor))
			p = actores.next(p);
		if (p.element() != actor)
			throw new AccionActorException ("Nivel.eliminarActores()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		actores.remove(p);
	}
	
	/**
	 * Elimina la Estructura estructura del Nivel.
	 * 
	 * @param estructura Estructura a eliminar.
	 * @throws NullPointerException Si estructura es igual a null.
	 * @throws AccionActorException Si se intenta eliminar una Estructura que no pertenece al Nivel.
	 */
	public void eliminarEstructuras (Estructura estructura) throws NullPointerException, AccionActorException
	{
		if (estructura == null)
			throw new NullPointerException ("Nivel.eliminarEstructuras()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		Position<Estructura> p = estructuras.first();
		while ((p != estructuras.last()) && (p.element() != estructura))
			p = estructuras.next(p);
		if (p.element() != estructura)
			throw new AccionActorException ("Nivel.eliminarEstructuras()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		estructuras.remove(p);
		eliminarActores((Actor) estructura);
	}
	
	/**
	 * Elimina el EspecialPowerUp especialPowerUp del Nivel.
	 * 
	 * @param especialPowerUp EspecialPowerUp a eliminar.
	 * @throws NullPointerException Si especialPowerUp es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un especialPowerUp que no pertenece al Nivel.
	 */
	public void eliminarEspecialPowerUp (EspecialPowerUp especialPowerUp) throws NullPointerException, AccionActorException
	{
		if (especialPowerUp == null)
			throw new NullPointerException ("Nivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		Position<EspecialPowerUp> p = especialesPowerUp.first();
		while ((p != especialesPowerUp.last()) && (p.element() != especialPowerUp))
			p = especialesPowerUp.next(p);
		if (p.element() != especialPowerUp)
			throw new AccionActorException ("Nivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		especialesPowerUp.remove(p);
		eliminarEstructuras((Estructura) especialPowerUp);
	}
	
	/**
	 * Elimina el PowerUp powerUp del Nivel.
	 * 
	 * @param powerUp PowerUp a eliminar.
	 * @throws NullPointerException Si powerUp es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un PowerUp que no pertenece al Nivel.
	 */
	public void eliminarPowerUps (PowerUp powerUp) throws NullPointerException, AccionActorException
	{
		if (powerUp == null)
			throw new NullPointerException ("Nivel.eliminarPowerUps()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		Position<PowerUp> p = powerUps.first();
		while ((p != powerUps.last()) && (p.element() != powerUp))
			p = powerUps.next(p);
		if (p.element() != powerUp)
			throw new AccionActorException ("Nivel.eliminarPowerUps()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		powerUps.remove(p);
		eliminarActores(powerUp);
	}
	
	/**
	 * Elimina el Enemigo enemigo del Nivel.
	 * 
	 * @param enemigo Enemigo a eliminar.
	 * @throws NullPointerException Si enemigo es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un Enemigo que no pertenece al Nivel.
	 */
	public void eliminarEnemigo (Enemigo enemigo) throws NullPointerException, AccionActorException
	{
		if (enemigo == null)
			throw new NullPointerException ("Nivel.eliminarPowerUps()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		Position<Enemigo> p = enemigos.first();
		while ((p != enemigos.last()) && (p.element() != enemigo))
			p = enemigos.next(p);
		if (p.element() != enemigo)
			throw new AccionActorException ("Nivel.eliminarPowerUps()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		enemigos.remove(p);
		eliminarActores((Actor) enemigo);
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Mapa correspondiente al Nivel.
	 * 
	 * @return Mapa del Nivel.
	 */
	public Mapa getMapa ()
	{
		return mapa;
	}
	
	/**
	 * Devuelve el Bloque Actual del Nivel.
	 * 
	 * @return Bloque Actual del Nivel. 
	 */
	public Bloque getBloqueActual ()
	{
		return bloqueActual;
	}
	
	/**
	 * Devuelve el fondo actual del Nivel.
	 * 
	 * @return Fondo del Nivel.
	 */
	public String fondo ()
	{
		return dirRecursos + fondo;
	}
	
	/**
	 * Devuelve la Lista de Actores.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Actores.
	 */
	public PositionList<Actor> getActores (ControlCentral cc)
	{
		return actores;
	}
	
	/**
	 * Devuelve la Lista de Llegadas.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Llegadas.
	 */
	public PositionList<Llegada> getLlegadas (ControlCentral cc)
	{
		return llegadas;
	}
	
	/**
	 * Devuelve la Lista de Vacios.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Vacios.
	 */
	public PositionList<Vacio> getVacios (ControlCentral cc)
	{
		return vacios;
	}
	
	/**
	 * Devuelve la Lista de Estructuras.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Estructuras.
	 */
	public PositionList<Estructura> getEstructuras (ControlCentral cc)
	{
		return estructuras;
	}
	
	/**
	 * Devuelve la Lista de EspecialesPowerUp.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de EspecialesPowerUp.
	 */
	public PositionList<EspecialPowerUp> getEspecialesPowerUp (ControlCentral cc)
	{
		return especialesPowerUp;
	}
	
	/**
	 * Devuelve la Lista de PowerUps.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de PowerUps.
	 */
	public PositionList<PowerUp> getPowerUps (ControlCentral cc)
	{
		return powerUps;
	}
	
	/**
	 * Devuelve la Lista de Enemigos.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Enemigos.
	 */
	public PositionList<Enemigo> getEnemigos (ControlCentral cc)
	{
		return enemigos;
	}

}