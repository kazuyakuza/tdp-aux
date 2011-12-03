package ProyectoX.Logica.Mapa;

import java.util.Random;

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
import ProyectoX.Logica.NoPersonajes.PowerUps.BombaNuclear;
import ProyectoX.Logica.NoPersonajes.PowerUps.Estrella;
import ProyectoX.Logica.NoPersonajes.PowerUps.HongoVerde;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.SuperHongo;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;
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
		
		//Agregación SubSuelo
		bloqueActual.setFilaOcupada(12, true);
		
		//Agregación Actores Enemigos.
		/*aux = 0;
		while (aux < 1)
		{
			Goomba goomba = new Goomba ();
			bloqueActual.ABC[10][10 + aux].agregarActor(goomba);
			goomba.setCeldaActual(bloqueActual.ABC[10][10 + aux]);
			actores.addFirst(goomba);
			enemigos.addFirst(goomba);
			caibles.addFirst(goomba);
			aux++;
		}*/
		
		Goomba goomba = new Goomba ();
		bloqueActual.ABC[10][10].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[10][10]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst(goomba);
		
		KoopaTroopa kt1 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[10][15].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[10][15]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);
		
		/*
		KoopaTroopa kt2 = new KoopaTroopa (new KTCaparazon());
		bloqueActual.ABC[4][4].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[4][4]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);
		*/
		
		//Agregacion de un power up.
		/*PowerUp hongo = new SuperHongo();
		bloqueActual.ABC[3][7].agregarActor(hongo);
		hongo.setCeldaActual(bloqueActual.ABC[3][7]);
		actores.addFirst(hongo);
		powerUps.addFirst(hongo);
		caibles.addFirst(hongo);
		
		PowerUp flor = new FlorFuego();
		bloqueActual.ABC[3][8].agregarActor(flor);
		flor.setCeldaActual(bloqueActual.ABC[3][8]);
		actores.addFirst(flor);
		powerUps.addFirst(flor);
		caibles.addFirst(flor);*/
		
		/*
		PowerUp bomba = new Estrella();
		bloqueActual.ABC[8][6].agregarActor(bomba);
		bomba.setCeldaActual(bloqueActual.ABC[8][6]);
		actores.addFirst(bomba);
		powerUps.addFirst(bomba);
		caibles.addFirst(bomba);
		*/
		
		Moneda moneda1 = new Moneda();
		bloqueActual.ABC[8][8].agregarActor(moneda1);
		moneda1.setCeldaActual(bloqueActual.ABC[8][8]);
		actores.addFirst(moneda1);	

		Moneda moneda2 = new Moneda();
		bloqueActual.ABC[10][8].agregarActor(moneda2);
		moneda2.setCeldaActual(bloqueActual.ABC[10][8]);	
		actores.addFirst(moneda2);
		
		
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
		
		EspecialPowerUp plataformaPUP3= new EspecialPowerUp (new HongoVerde(), cc, true);
		bloqueActual.ABC[7][15].setOcupada(true);
		bloqueActual.ABC[7][15].agregarEstructura(plataformaPUP3);
		plataformaPUP3.setCeldaActual(bloqueActual.ABC[7][15]);
		actores.addFirst(plataformaPUP3);
		plataformas.addFirst(plataformaPUP3);
		especialesPowerUp.addFirst(plataformaPUP3);
		
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
		/*
		/*Rompible rompible = new Rompible ();
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
		*/
		
		//Agregación Principio Mapa
		bloqueActual.setColumnaOcupada(0, true);
		
		//Agregación Final Mapa
		bloqueActual.setColumnaOcupada(39, true);
		
		//Agregación Actor del Jugador.
		bloqueActual.ABC[10][1].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[10][1]);
		actores.addFirst(actor);
		pjs.addFirst((PjSeleccionable) actor);
		caibles.addFirst ((afectableXgravedad)actor);
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
		bloqueActual = new Bloque(mapa, 0, 0, 15, 80);//Nivel de Piso default.
		bloqueActual.setNivelPiso(12);
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregación Principio Mapa
		bloqueActual.setColumnaOcupada(2, true);
		
		//Agregación SubSuelo
		bloqueActual.setFilaOcupada(14, true);
		
		//Agregación Actores no Personjes.
		
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
		
		//Vacio en el Piso.
		Vacio vacio1 = new Vacio ();
		bloqueActual.ABC[13][14].agregarActor(vacio1);
		vacio1.setCeldaActual(bloqueActual.ABC[13][14]);
		actores.addFirst(vacio1);
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
		
		//Vacio en el Piso.
		aux = 27;
		while (aux <= 30)
		{
			Vacio vacio2 = new Vacio ();
			bloqueActual.ABC[13][aux].agregarActor(vacio2);
			vacio2.setCeldaActual(bloqueActual.ABC[13][aux]);
			actores.addFirst(vacio2);
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
		Irrompible plataforma0 = new Irrompible ();
		bloqueActual.ABC[5][9].setOcupada(true);
		bloqueActual.ABC[5][9].agregarEstructura(plataforma0);
		plataforma0.setCeldaActual(bloqueActual.ABC[5][9]);
		actores.addFirst(plataforma0);
		plataformas.addFirst(plataforma0);
		
		//Irrompibles
		aux = 6; aux2 = 24;
		while ((aux <= 11) && (aux2 > 18))
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma);
			plataforma.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux2--;
			Irrompible plataforma2 = new Irrompible ();
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma2);
			plataforma2.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma2);
			plataformas.addFirst(plataforma2);
			aux++;
		}
		
		//Irrompibles
		aux = 26;
		while (aux <= 30)
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[6][aux].setOcupada(true);
			bloqueActual.ABC[6][aux].agregarEstructura(plataforma);
			plataforma.setCeldaActual(bloqueActual.ABC[6][aux]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux++;
		}
		
		//Irrompibles
		aux = 3;
		while (aux <= 11)
		{
			Irrompible plataforma = new Irrompible ();
			bloqueActual.ABC[aux][31].setOcupada(true);
			bloqueActual.ABC[aux][31].agregarEstructura(plataforma);
			plataforma.setCeldaActual(bloqueActual.ABC[aux][31]);
			actores.addFirst(plataforma);
			plataformas.addFirst(plataforma);
			aux++;
		}
		
		//Irrompibles
		Irrompible plataforma = new Irrompible ();
		bloqueActual.ABC[11][26].setOcupada(true);
		bloqueActual.ABC[11][26].agregarEstructura(plataforma);
		plataforma.setCeldaActual(bloqueActual.ABC[11][26]);
		actores.addFirst(plataforma);
		plataformas.addFirst(plataforma);
		
		//Irrompibles
		Irrompible plataforma2 = new Irrompible ();
		bloqueActual.ABC[6][37].setOcupada(true);
		bloqueActual.ABC[6][37].agregarEstructura(plataforma2);
		plataforma2.setCeldaActual(bloqueActual.ABC[6][37]);
		actores.addFirst(plataforma2);
		plataformas.addFirst(plataforma2);
		
		//EspecialPowerUp
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(), cc, true);
		bloqueActual.ABC[9][9].setOcupada(true);
		bloqueActual.ABC[9][9].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[9][9]);
		actores.addFirst(plataformaPUP);
		plataformas.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		 int cantMonedas = 0; 
		do cantMonedas = new Random ().nextInt(6); while (cantMonedas == 0);
		EspecialMonedas plataformaM0 = new EspecialMonedas (cantMonedas);
		bloqueActual.ABC[9][34].setOcupada(true);
		bloqueActual.ABC[9][34].agregarEstructura(plataformaM0);
		plataformaM0.setCeldaActual(bloqueActual.ABC[9][34]);
		actores.addFirst(plataformaM0);
		plataformas.addFirst(plataformaM0);
		
		aux = 35;PowerUp pw = null;
		while (aux <= 39)
		{
			if ((aux%2) == 0)
			{
				switch (aux)
				{
					case 34: pw = new SuperHongo(); break;
					case 36: pw = new BombaNuclear(cc); break;
					default: pw = new Estrella(); break;
				}
				EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (pw, cc, false);
				bloqueActual.ABC[9][aux].setOcupada(true);
				bloqueActual.ABC[9][aux].agregarEstructura(plataformaPUP2);
				plataformaPUP2.setCeldaActual(bloqueActual.ABC[9][aux]);
				actores.addFirst(plataformaPUP2);
				plataformas.addFirst(plataformaPUP2);
				especialesPowerUp.addFirst(plataformaPUP2);
			}
			else
			{
				do cantMonedas = new Random ().nextInt(6); while (cantMonedas == 0);
				EspecialMonedas plataformaM = new EspecialMonedas (cantMonedas);
				bloqueActual.ABC[9][aux].setOcupada(true);
				bloqueActual.ABC[9][aux].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[9][aux]);
				actores.addFirst(plataformaM);
				plataformas.addFirst(plataformaM);
			}
			aux++;
		}
		
		aux = 36;
		while (aux <= 38)
		{
			EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new HongoVerde(), cc, false);
			bloqueActual.ABC[3][aux].setOcupada(true);
			bloqueActual.ABC[3][aux].agregarEstructura(plataformaPUP2);
			plataformaPUP2.setCeldaActual(bloqueActual.ABC[3][aux]);
			actores.addFirst(plataformaPUP2);
			plataformas.addFirst(plataformaPUP2);
			especialesPowerUp.addFirst(plataformaPUP2);
			aux++;
		}
		
		aux = 46;
		while (aux <= 56)
		{
			if ((aux%2) == 0)
			{
				Rompible rompible = new Rompible ();
				bloqueActual.ABC[8][aux].setOcupada(true);
				bloqueActual.ABC[8][aux].agregarEstructura(rompible);
				rompible.setCeldaActual(bloqueActual.ABC[8][aux]);
				actores.addFirst(rompible);
				plataformas.addFirst(rompible);
			}
			else
			{
				do cantMonedas = new Random ().nextInt(6); while (cantMonedas == 0);
				EspecialMonedas plataformaM = new EspecialMonedas (cantMonedas);
				bloqueActual.ABC[8][aux].setOcupada(true);
				bloqueActual.ABC[8][aux].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[8][aux]);
				actores.addFirst(plataformaM);
				plataformas.addFirst(plataformaM);
			}
			aux++;
		}
		
		Rompible r4 = new Rompible ();
		bloqueActual.ABC[7][56].setOcupada(true);
		bloqueActual.ABC[7][56].agregarEstructura(r4);
		r4.setCeldaActual(bloqueActual.ABC[7][56]);
		actores.addFirst(r4);
		plataformas.addFirst(r4);
		
		Rompible r3 = new Rompible ();
		bloqueActual.ABC[7][46].setOcupada(true);
		bloqueActual.ABC[7][46].agregarEstructura(r3);
		r3.setCeldaActual(bloqueActual.ABC[7][46]);
		actores.addFirst(r3);
		plataformas.addFirst(r3);
		
		Rompible r5 = new Rompible ();
		bloqueActual.ABC[8][39].setOcupada(true);
		bloqueActual.ABC[8][39].agregarEstructura(r5);
		r5.setCeldaActual(bloqueActual.ABC[8][39]);
		actores.addFirst(r5);
		plataformas.addFirst(r5);
		
		Rompible r6 = new Rompible ();
		bloqueActual.ABC[8][34].setOcupada(true);
		bloqueActual.ABC[8][34].agregarEstructura(r6);
		r6.setCeldaActual(bloqueActual.ABC[8][34]);
		actores.addFirst(r6);
		plataformas.addFirst(r6);
		
		Rompible r2 = new Rompible ();
		bloqueActual.ABC[9][10].setOcupada(true);
		bloqueActual.ABC[9][10].agregarEstructura(r2);
		r2.setCeldaActual(bloqueActual.ABC[9][10]);
		actores.addFirst(r2);
		plataformas.addFirst(r2);
		
		Rompible r1 = new Rompible ();
		bloqueActual.ABC[9][8].setOcupada(true);
		bloqueActual.ABC[9][8].agregarEstructura(r1);
		r1.setCeldaActual(bloqueActual.ABC[9][8]);
		actores.addFirst(r1);
		plataformas.addFirst(r1);
		
		//Agregación Actores Enemigos.
		
		//Goombas
		aux = 48;
		while (aux <= 54)
		{
			Goomba goomba = new Goomba ();
			bloqueActual.ABC[7][aux].agregarActor(goomba);
			goomba.setCeldaActual(bloqueActual.ABC[7][aux]);
			actores.addFirst(goomba);
			enemigos.addFirst(goomba);
			caibles.addFirst(goomba);
			aux++;aux++;
		}
		
		//KoopaTroopas
		KoopaTroopa kt2 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[11][42].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[11][42]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);
		
		KoopaTroopa kt1 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[11][37].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[11][37]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);
		
		KoopaTroopa kt3 = new KoopaTroopa (new KTNormal());
		bloqueActual.ABC[8][37].agregarActor(kt3);
		kt3.setCeldaActual(bloqueActual.ABC[8][37]);
		actores.addFirst(kt3);		
		enemigos.addFirst(kt3);		
		caibles.addFirst(kt3);
		
		Goomba goomba = new Goomba ();
		bloqueActual.ABC[11][25].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[11][25]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst(goomba);
		
		//Goombas
		aux = 11;
		while (aux <= 13)
		{
			if (aux != 12)
			{
				Goomba goomba0 = new Goomba ();
				bloqueActual.ABC[11][aux].agregarActor(goomba0);
				goomba0.setCeldaActual(bloqueActual.ABC[11][aux]);
				actores.addFirst(goomba0);
				enemigos.addFirst(goomba0);
				caibles.addFirst(goomba0);
			}
			aux++;
		}
		
		//Agregación Monedas
		aux = 20;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[11][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[11][aux]);
			actores.addFirst(moneda1);
			aux++;
		}
		aux = 21;
		while (aux <= 30)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[10][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[10][aux]);
			actores.addFirst(moneda1);
			aux++;
		}
		aux = 22;
		while (aux <= 30)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[9][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[9][aux]);
			actores.addFirst(moneda1);
			aux++;
		}
		aux = 23;
		while (aux <= 30)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[8][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[8][aux]);
			actores.addFirst(moneda1);
			aux++;
		}
		aux = 24;
		while (aux <= 30)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[7][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[7][aux]);
			actores.addFirst(moneda1);
			aux++;
		}
		aux = 8;
		while (aux <= 10)
		{
			Moneda moneda1 = new Moneda();
			bloqueActual.ABC[8][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[8][aux]);
			actores.addFirst(moneda1);
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
		
		//Agregación Techo Mapa
		bloqueActual.setFilaOcupada(0, true);
		
		//Agregación Final Mapa
		bloqueActual.setColumnaOcupada(69, true);
		
		//Agregación Actor del Jugador.
		bloqueActual.ABC[11][5].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[11][5]);
		actores.addFirst(actor);
		pjs.addFirst((PjSeleccionable) actor);
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