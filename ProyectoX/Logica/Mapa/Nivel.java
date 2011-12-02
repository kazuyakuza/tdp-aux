package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.InicioNivelException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.NoPersonajes.Moneda;
import ProyectoX.Logica.NoPersonajes.Piso;
import ProyectoX.Logica.NoPersonajes.Especiales.Llegada;
import ProyectoX.Logica.NoPersonajes.Especiales.Vacio;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialMonedas;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialPowerUp;
import ProyectoX.Logica.NoPersonajes.Plataformas.Irrompible;
import ProyectoX.Logica.NoPersonajes.Plataformas.Plataforma;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.NoPersonajes.PowerUps.Estrella;
import ProyectoX.Logica.NoPersonajes.PowerUps.FlorFuego;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.SuperHongo;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;
import ProyectoX.Logica.Personajes.Enemigo.KTCaparazon;
import ProyectoX.Logica.Personajes.Enemigo.KTNormal;
import ProyectoX.Logica.Personajes.Enemigo.KoopaTroopa;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

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
	protected Bloque bloqueActual; //Bloque en el que está el Jugador.
	protected int id; //Identificación del Nivel (1,2,3,4...)
	protected String fondo;
	private PositionList<Actor> actores; //Lista de todos los Actores en el Nivel actual.
	private PositionList<PjSeleccionable> pjs;  //Lista de todos los Actores PjSeleccionable en el Nivel actual.
	private PositionList<Enemigo> enemigos; //Lista de todos los Actores Enemigo en el Nivel actual.
	private PositionList<Plataforma> plataformas; //Lista de todos los Actores Plataforma en el Nivel actual.
	private PositionList<EspecialPowerUp> especialesPowerUp; //Lista de todos los Actores Plataforma EspecialPowerUp en el Nivel actual.
	private PositionList<PowerUp> powerUps; //Lista de todos los Actores PowerUp en el Nivel actual.
	private PositionList<afectableXgravedad> caibles; //Lista de todos los Actores afectables por la Gravedad.
	
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
		pjs = new ListaPositionSimple<PjSeleccionable> ();
		enemigos = new ListaPositionSimple<Enemigo> ();
		plataformas = new ListaPositionSimple<Plataforma> ();
		especialesPowerUp = new ListaPositionSimple<EspecialPowerUp> ();
		powerUps = new ListaPositionSimple<PowerUp> ();
		caibles = new ListaPositionSimple<afectableXgravedad> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa el Nivel creado.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @throws InicioNivelException Si se produce un error al Iniciar el Nivel.
	 */
	public void inicializarNivel (Actor actorJugador, ControlCentral cc) throws InicioNivelException
	{
		try
		{
			switch (id)
			{
				case 0:
           			   {
           				   nivelTest(actorJugador, cc);
           				   break;
					   }
				case 1:
					   {
						   nivel1(actorJugador, cc);
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
	 * Crea el Nivel Test.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 */
	private void nivelTest (Actor actor, ControlCentral cc)
	{
		fondo = "Fondo2.png";
		
		mapa = new Mapa (this, 1, 1);
		
		//Creación Bloque 1 del Nivel. Que es el Bloque de inicio del Nivel.
		bloqueActual = new Bloque(mapa, 0, 0, 13, 40);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregación Piso
		int aux = 0;
		while (aux < 40)
		{
			Piso piso = new Piso();
			bloqueActual.ABC[11][aux].agregarEstructura(piso);
			piso.setCeldaActual(bloqueActual.ABC[11][aux]);
			actores.addFirst(piso);
			aux++;
		}
		
		//Agregación Actores Enemigos.
		/*aux = 0;
		while (aux < 8)
		{
			Goomba goomba = new Goomba ();
			bloqueActual.ABC[10][10 + aux].agregarActor(goomba);
			goomba.setCeldaActual(bloqueActual.ABC[10][10 + aux]);
			actores.addFirst(goomba);
			enemigos.addFirst(goomba);
			caibles.addFirst((afectableXgravedad) goomba);
			aux++;
		}*/
		
		/*Goomba goomba = new Goomba ();
		bloqueActual.ABC[10][10].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[10][10]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst((afectableXgravedad) goomba)*/
		
		/*KoopaTroopa kt1 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[10][10].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[10][10]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);*/
		
		/*KoopaTroopa kt2 = new KoopaTroopa (new KTCaparazon());
		bloqueActual.ABC[4][4].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[4][4]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);*/
		
		//Agregacion de un power up.
		PowerUp powerUp = new SuperHongo();
		bloqueActual.ABC[3][7].agregarActor(powerUp);
		powerUp.setCeldaActual(bloqueActual.ABC[3][7]);
		actores.addLast(powerUp);
		powerUps.addLast(powerUp);
		caibles.addLast((afectableXgravedad) powerUp);
		
		PowerUp flor = new FlorFuego();
		bloqueActual.ABC[3][8].agregarActor(flor);
		flor.setCeldaActual(bloqueActual.ABC[3][8]);
		actores.addLast(flor);
		powerUps.addLast(flor);
		caibles.addLast((afectableXgravedad) flor);
		
		PowerUp bomba = new Estrella();
		bloqueActual.ABC[8][6].agregarActor(bomba);
		bomba.setCeldaActual(bloqueActual.ABC[8][6]);
		actores.addLast(bomba);
		powerUps.addLast(bomba);
		caibles.addLast((afectableXgravedad) bomba);
		
		Moneda moneda1 = new Moneda();
		bloqueActual.ABC[8][8].agregarActor(moneda1);
		moneda1.setCeldaActual(bloqueActual.ABC[8][8]);
		actores.addLast(moneda1);
		
		Moneda moneda2 = new Moneda();
		bloqueActual.ABC[10][8].agregarActor(moneda2);
		moneda2.setCeldaActual(bloqueActual.ABC[10][8]);		
		actores.addLast(moneda2);
		
		//Agregación Actores no Personjes.
		aux = 6;
		while (aux < 12)
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[7][aux].setOcupada(true);
			bloqueActual.ABC[7][aux].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[7][aux]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux++;
		}
		
		/*Irrompible plataforma = new Irrompible ();
		bloqueActual.ABC[10][9].setOcupada(true);
		bloqueActual.ABC[10][9].agregarEstructura(plataforma);//Plataforma irrompible
		plataforma.setCeldaActual(bloqueActual.ABC[10][9]);
		actores.addFirst(plataforma);
		plataformas.addFirst(plataforma);
		Irrompible plataforma2 = new Irrompible ();
		bloqueActual.ABC[10][19].setOcupada(true);
		bloqueActual.ABC[10][19].agregarEstructura(plataforma2);//Plataforma irrompible
		plataforma2.setCeldaActual(bloqueActual.ABC[10][19]);
		actores.addFirst(plataforma2);
		plataformas.addFirst(plataforma2);*/
		
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(), cc, true);
		bloqueActual.ABC[7][5].setOcupada(true);
		bloqueActual.ABC[7][5].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[7][5]);
		actores.addFirst(plataformaPUP);
		plataformas.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		EspecialMonedas plataformaM = new EspecialMonedas (3);
		bloqueActual.ABC[7][13].setOcupada(true);
		bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
		plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
		actores.addFirst(plataformaM);
		plataformas.addFirst(plataformaM);
		
		EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new Estrella(), cc, false);
		bloqueActual.ABC[7][14].setOcupada(true);
		bloqueActual.ABC[7][14].agregarEstructura(plataformaPUP2);
		plataformaPUP2.setCeldaActual(bloqueActual.ABC[7][14]);
		actores.addFirst(plataformaPUP2);
		plataformas.addFirst(plataformaPUP2);
		especialesPowerUp.addFirst(plataformaPUP2);
		
		Rompible rompible = new Rompible ();
		bloqueActual.ABC[7][12].setOcupada(true);
		bloqueActual.ABC[7][12].agregarEstructura(rompible);
		rompible.setCeldaActual(bloqueActual.ABC[7][12]);
		actores.addFirst(rompible);
		plataformas.addFirst(rompible);
		
		Rompible r1 = new Rompible ();
		bloqueActual.ABC[7][2].setOcupada(true);
		bloqueActual.ABC[7][2].agregarEstructura(r1);
		r1.setCeldaActual(bloqueActual.ABC[7][2]);
		actores.addFirst(r1);
		plataformas.addFirst(r1);
		
		Rompible r2 = new Rompible ();
		bloqueActual.ABC[7][3].setOcupada(true);
		bloqueActual.ABC[7][3].agregarEstructura(r2);
		r2.setCeldaActual(bloqueActual.ABC[7][3]);
		actores.addFirst(r2);
		plataformas.addFirst(r2);
		
		
		//Agregación Principio Mapa
		bloqueActual.setColumnaOcupada(0, true);
		
		//Agregación Final Mapa
		bloqueActual.setColumnaOcupada(39, true);
		
		//Agregación Actor del Jugador.
		bloqueActual.ABC[10][1].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[10][1]);
		actores.addFirst(actor);
		pjs.addFirst((PjSeleccionable) actor);
		caibles.addFirst((afectableXgravedad) actor);
	}
	
	/**
	 * Crea el Nivel 1.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 */
	private void nivel1 (Actor actor, ControlCentral cc)
	{
		fondo = "Fondo2.png";
		
		mapa = new Mapa (this, 1, 1);
		
		//Creación Bloque 1 del Nivel. Que es el Bloque de inicio del Nivel.
		bloqueActual = new Bloque(mapa, 0, 0, 14, 80);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregación Principio Mapa
		bloqueActual.setColumnaOcupada(2, true);
		
		//Agregación Piso
		int aux = 0;
		int aux2 = 0;
		while (aux < 80)
		{
			if ((aux != 14) && ((aux < 27) || (aux > 30)))
			{
				Piso piso = new Piso();
				bloqueActual.ABC[12][aux].setOcupada(true);
				bloqueActual.ABC[12][aux].agregarEstructura(piso);
				piso.setCeldaActual(bloqueActual.ABC[12][aux]);
				actores.addFirst(piso);
			}
			else
				bloqueActual.ABC[12][aux].setOcupada(false);
			aux++;
		}
		
		//Agregación Actores no Personjes.
		
		//Vacio en el Piso.
		Vacio vacio1 = new Vacio ();
		bloqueActual.ABC[13][14].agregarActor(vacio1);
		vacio1.setCeldaActual(bloqueActual.ABC[13][14]);
		actores.addLast(vacio1);
		//Piso al costado del Vacio.
		Piso piso1 = new Piso();
		bloqueActual.ABC[13][13].setOcupada(true);
		bloqueActual.ABC[13][13].agregarEstructura(piso1);
		piso1.setCeldaActual(bloqueActual.ABC[13][13]);
		actores.addFirst(piso1);
		Piso piso2 = new Piso();
		bloqueActual.ABC[13][15].setOcupada(true);
		bloqueActual.ABC[13][15].agregarEstructura(piso2);
		piso2.setCeldaActual(bloqueActual.ABC[13][15]);
		actores.addFirst(piso2);
		
		aux = 27;
		while (aux <= 30)
		{
			Vacio vacio2 = new Vacio ();
			bloqueActual.ABC[13][aux].agregarActor(vacio2);
			vacio2.setCeldaActual(bloqueActual.ABC[13][aux]);
			actores.addLast(vacio2);
			aux++;
		}
		//Piso al costado del Vacio.
		Piso p1 = new Piso();
		bloqueActual.ABC[13][26].setOcupada(true);
		bloqueActual.ABC[13][26].agregarEstructura(p1);
		p1.setCeldaActual(bloqueActual.ABC[13][26]);
		actores.addFirst(p1);
		Piso p2 = new Piso();
		bloqueActual.ABC[13][31].setOcupada(true);
		bloqueActual.ABC[13][31].agregarEstructura(p2);
		p2.setCeldaActual(bloqueActual.ABC[13][31]);
		actores.addFirst(p2);
		
		//Irrompibles
		aux = 6; aux2 = 24;
		while ((aux <= 11) && (aux2 >= 18))
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux2--;
			Irrompible plataforma2 = new Irrompible ();
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma2);//Plataforma irrompible
			plataforma2.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma2);
			plataformas.addFirst(plataforma2);
			aux2--;
			aux++;
		}
		aux = 26;
		while (aux <= 30)
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[6][aux].setOcupada(true);
			bloqueActual.ABC[6][aux].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[6][aux]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux++;
		}
		aux = 6;
		while (aux <= 11)
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[aux][31].setOcupada(true);
			bloqueActual.ABC[aux][31].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[aux][31]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux++;
		}
		Irrompible plataforma = new Irrompible ();
		bloqueActual.ABC[5][9].setOcupada(true);
		bloqueActual.ABC[5][9].agregarEstructura(plataforma);//Plataforma irrompible
		plataforma.setCeldaActual(bloqueActual.ABC[5][9]);
		actores.addFirst(plataforma);
		plataformas.addFirst(plataforma);
		
		Rompible r1 = new Rompible ();
		bloqueActual.ABC[9][8].setOcupada(true);
		bloqueActual.ABC[9][8].agregarEstructura(r1);
		r1.setCeldaActual(bloqueActual.ABC[9][8]);
		actores.addFirst(r1);
		plataformas.addFirst(r1);
		
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(), cc, true);
		bloqueActual.ABC[9][9].setOcupada(true);
		bloqueActual.ABC[9][9].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[9][9]);
		actores.addFirst(plataformaPUP);
		plataformas.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		Rompible r2 = new Rompible ();
		bloqueActual.ABC[9][10].setOcupada(true);
		bloqueActual.ABC[9][10].agregarEstructura(r2);
		r2.setCeldaActual(bloqueActual.ABC[9][10]);
		actores.addFirst(r2);
		plataformas.addFirst(r2);
		
		aux = 34;
		while (aux <= 39)
		{
			if ((aux%2) == 0)
			{
				EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new Estrella(), cc, false);
				bloqueActual.ABC[8][14].setOcupada(true);
				bloqueActual.ABC[8][14].agregarEstructura(plataformaPUP2);
				plataformaPUP2.setCeldaActual(bloqueActual.ABC[8][14]);
				actores.addFirst(plataformaPUP2);
				plataformas.addFirst(plataformaPUP2);
				especialesPowerUp.addFirst(plataformaPUP2);
			}
			else
			{
				EspecialMonedas plataformaM = new EspecialMonedas (3);
				bloqueActual.ABC[7][13].setOcupada(true);
				bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
				actores.addFirst(plataformaM);
				plataformas.addFirst(plataformaM);
			}
			aux++;
		}
		
		aux = 46;
		while (aux <= 56)
		{
			if ((aux%2) == 0)
			{
				Rompible rompible = new Rompible ();
				bloqueActual.ABC[8][12].setOcupada(true);
				bloqueActual.ABC[8][12].agregarEstructura(rompible);
				rompible.setCeldaActual(bloqueActual.ABC[8][12]);
				actores.addFirst(rompible);
				plataformas.addFirst(rompible);
			}
			else
			{
				EspecialMonedas plataformaM = new EspecialMonedas (3);
				bloqueActual.ABC[7][13].setOcupada(true);
				bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
				actores.addFirst(plataformaM);
				plataformas.addFirst(plataformaM);
			}
			aux++;
		}
		
		//Agregación Actores Enemigos.
		
		aux = 11;
		while (aux <= 13)
		{
			if (aux != 12)
			{
				Goomba goomba = new Goomba ();
				bloqueActual.ABC[11][aux].agregarActor(goomba);
				goomba.setCeldaActual(bloqueActual.ABC[11][aux]);
				actores.addFirst(goomba);
				enemigos.addFirst(goomba);
				caibles.addFirst((afectableXgravedad) goomba);
			}
			aux++;
		}
		
		Goomba goomba = new Goomba ();
		bloqueActual.ABC[11][25].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[11][25]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst((afectableXgravedad) goomba);
		
		KoopaTroopa kt1 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[11][37].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[11][37]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);
		
		KoopaTroopa kt2 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[11][42].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[11][42]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);
		
		//Agregación Monedas
		aux = 20;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[11][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[11][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		aux = 21;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[10][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[10][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		aux = 22;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[9][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[9][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		
		//Agregación Llegada
		aux = 0;
		while (aux <= 11)
		{
			Actor llegada = new Llegada(cc);
			bloqueActual.ABC[aux][65].agregarActor(llegada);
			llegada.setCeldaActual(bloqueActual.ABC[aux][65]);
			actores.addFirst(llegada);
			aux++;
		}
		
		//Agregación Final Mapa
		bloqueActual.setColumnaOcupada(69, true);
		
		//Agregación Actor del Jugador.
		bloqueActual.ABC[11][5].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[11][5]);
		actores.addFirst(actor);
		caibles.addFirst((afectableXgravedad) actor);
	}
	
	/**
	 * Cambia el Blque Actual por el Bloque pasadon por parámetro.
	 * 
	 * @param bloque Nuevo Bloque Actual.
	 */
	public void setBloqueActual (Bloque bloque)
	{
		bloqueActual = bloque;
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
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de Actores.
	 */
	public PositionList<Actor> getActores (ControlCentral cc)
	{
		return actores;
	}
	
	/**
	 * Devuelve la Lista de PersonajesSeleccionables.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de PersonajesSeleccionables.
	 */
	public PositionList<PjSeleccionable> getPJs (ControlCentral cc)
	{
		return pjs;
	}
	
	/**
	 * Devuelve la Lista de Enemigos.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de Enemigos.
	 */
	public PositionList<Enemigo> getEnemigos (ControlCentral cc)
	{
		return enemigos;
	}
	
	/**
	 * Devuelve la Lista de Plataformas.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de Plataformas.
	 */
	public PositionList<Plataforma> getPlataformas (ControlCentral cc)
	{
		return plataformas;
	}
	
	/**
	 * Devuelve la Lista de EspecialesPowerUp.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de EspecialesPowerUp.
	 */
	public PositionList<EspecialPowerUp> getEspecialesPowerUp (ControlCentral cc)
	{
		return especialesPowerUp;
	}
	
	/**
	 * Devuelve la Lista de PowerUps.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de PowerUps.
	 */
	public PositionList<PowerUp> getPowerUps (ControlCentral cc)
	{
		return powerUps;
	}
	
	/**
	 * Devuelve la Lista de Caibles.
	 * 
	 * @param cc Verificación de que es solicitado por un ControlCentral.
	 * @return Lista de Caibles.
	 */
	public PositionList<afectableXgravedad> getCaibles (ControlCentral cc)
	{
		return caibles;
	}

}