package ProyectoX.Logica.Mapa;

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
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.NoPersonajes.PowerUps.Estrella;
import ProyectoX.Logica.NoPersonajes.PowerUps.FlorFuego;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.NoPersonajes.PowerUps.SuperHongo;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;
import ProyectoX.Logica.Personajes.Enemigo.KoopaTroopa;
import ProyectoX.Logica.Personajes.Enemigo.KTNormal;
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
		llegadas = new ListaPositionSimple<Llegada> ();
		vacios = new ListaPositionSimple<Vacio> ();
		estructuras = new ListaPositionSimple<Estructura> ();
		especialesPowerUp = new ListaPositionSimple<EspecialPowerUp> ();
		powerUps = new ListaPositionSimple<PowerUp> ();
		enemigos = new ListaPositionSimple<Enemigo> ();
		caibles = new ListaPositionSimple<afectableXgravedad> ();
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
				case 0:
           			   {
           				   nivelTest(actorJugador, cc, cargadorSprite);
           				   break;
					   }
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
	 * Crea el Nivel Test.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @param cargadorSprite Clase encargada de cargar las imagenes de los Actores.
	 */
	private void nivelTest (Actor actor, ControlCentral cc, CargadorSprite cargadorSprite)
	{
		fondo = "Fondo2.png";
		
		mapa = new Mapa (this, 1, 1);
		
		//Creaci�n Bloque 1 del Nivel. Que es el Bloque de inicio del Nivel.
		bloqueActual = new Bloque(mapa, 0, 0, 13, 40);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregaci�n Piso
		int aux = 0;
		while (aux < 40)
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
		caibles.addFirst((afectableXgravedad) actor);
		
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
			caibles.addFirst((afectableXgravedad) goomba);
			aux++;
		}*/
		
		/*Goomba goomba = new Goomba (cargadorSprite);
		bloqueActual.ABC[1][10].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[1][10]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst((afectableXgravedad) goomba);
		
		KoopaTroopa kt1 = new KoopaTroopa (new KTNormal(), cargadorSprite);
		bloqueActual.ABC[3][3].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[3][3]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);*/
		
		/*
		KoopaTroopa kt2 = new KoopaTroopa (new KTCaparazon(), cargadorSprite);
		bloqueActual.ABC[4][4].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[4][4]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);
		*/
		
		//Agregacion de un power up.
		PowerUp powerUp = new SuperHongo(cargadorSprite);
		bloqueActual.ABC[3][7].agregarActor(powerUp);
		powerUp.setCeldaActual(bloqueActual.ABC[3][7]);
		actores.addLast(powerUp);
		powerUps.addLast(powerUp);
		caibles.addLast((afectableXgravedad) powerUp);
		
		PowerUp flor = new FlorFuego(cargadorSprite);
		bloqueActual.ABC[3][8].agregarActor(flor);
		flor.setCeldaActual(bloqueActual.ABC[3][8]);
		actores.addLast(flor);
		powerUps.addLast(flor);
		caibles.addLast((afectableXgravedad) flor);
		
		/*PowerUp bomba = new Estrella(cargadorSprite);
		bloqueActual.ABC[8][6].agregarActor(bomba);
		bomba.setCeldaActual(bloqueActual.ABC[8][6]);
		actores.addLast(bomba);
		powerUps.addLast(bomba);
		caibles.addLast((afectableXgravedad) bomba);*/
		
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
		
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(cargadorSprite), cc, true, cargadorSprite);
		bloqueActual.ABC[7][5].setOcupada(true);
		bloqueActual.ABC[7][5].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[7][5]);
		actores.addFirst(plataformaPUP);
		estructuras.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		EspecialMonedas plataformaM = new EspecialMonedas (3, cargadorSprite);
		bloqueActual.ABC[7][13].setOcupada(true);
		bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
		plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
		actores.addFirst(plataformaM);
		estructuras.addFirst(plataformaM);
		
		EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new Estrella(cargadorSprite), cc, false, cargadorSprite);
		bloqueActual.ABC[8][14].setOcupada(true);
		bloqueActual.ABC[8][14].agregarEstructura(plataformaPUP2);
		plataformaPUP2.setCeldaActual(bloqueActual.ABC[8][14]);
		actores.addFirst(plataformaPUP2);
		estructuras.addFirst(plataformaPUP2);
		especialesPowerUp.addFirst(plataformaPUP2);
		
		Rompible rompible = new Rompible (cargadorSprite);
		bloqueActual.ABC[8][12].setOcupada(true);
		bloqueActual.ABC[8][12].agregarEstructura(rompible);
		rompible.setCeldaActual(bloqueActual.ABC[8][12]);
		actores.addFirst(rompible);
		estructuras.addFirst(rompible);
		
		Rompible r1 = new Rompible (cargadorSprite);
		bloqueActual.ABC[8][2].setOcupada(true);
		bloqueActual.ABC[8][2].agregarEstructura(r1);
		r1.setCeldaActual(bloqueActual.ABC[8][2]);
		actores.addFirst(r1);
		estructuras.addFirst(r1);
		
		Rompible r2 = new Rompible (cargadorSprite);
		bloqueActual.ABC[8][3].setOcupada(true);
		bloqueActual.ABC[8][3].agregarEstructura(r2);
		r2.setCeldaActual(bloqueActual.ABC[8][3]);
		actores.addFirst(r2);
		estructuras.addFirst(r2);
		
		
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
		bloqueActual.setColumnaOcupada(39, true);
		
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
		bloqueActual = new Bloque(mapa, 0, 0, 14, 80);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregaci�n Principio Mapa
		bloqueActual.setColumnaOcupada(2, true);
		
		//Agregaci�n Actor del Jugador.
		bloqueActual.ABC[11][5].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[11][5]);
		actores.addFirst(actor);
		caibles.addFirst((afectableXgravedad) actor);
		
		//Agregaci�n Piso
		int aux = 0;
		int aux2 = 0;
		while (aux < 80)
		{
			if ((aux != 14) && ((aux < 27) || (aux > 30)))
			{
				Piso piso = new Piso(cargadorSprite);
				bloqueActual.ABC[12][aux].setOcupada(true);
				bloqueActual.ABC[12][aux].agregarEstructura(piso);
				piso.setCeldaActual(bloqueActual.ABC[12][aux]);
				actores.addFirst(piso);
				estructuras.addFirst(piso);
			}
			else
				bloqueActual.ABC[12][aux].setOcupada(false);
			aux++;
		}
		
		//Agregaci�n Actores no Personjes.
		
		//Vacio en el Piso.
		Vacio vacio1 = new Vacio (cargadorSprite);
		bloqueActual.ABC[13][14].agregarActor(vacio1);
		vacio1.setCeldaActual(bloqueActual.ABC[13][14]);
		actores.addLast(vacio1);
		//Piso al costado del Vacio.
		Piso piso1 = new Piso(cargadorSprite);
		bloqueActual.ABC[13][13].setOcupada(true);
		bloqueActual.ABC[13][13].agregarEstructura(piso1);
		piso1.setCeldaActual(bloqueActual.ABC[13][13]);
		actores.addFirst(piso1);
		estructuras.addFirst(piso1);
		Piso piso2 = new Piso(cargadorSprite);
		bloqueActual.ABC[13][15].setOcupada(true);
		bloqueActual.ABC[13][15].agregarEstructura(piso2);
		piso2.setCeldaActual(bloqueActual.ABC[13][15]);
		actores.addFirst(piso2);
		estructuras.addFirst(piso2);
		
		aux = 27;
		while (aux <= 30)
		{
			Vacio vacio2 = new Vacio (cargadorSprite);
			bloqueActual.ABC[13][aux].agregarActor(vacio2);
			vacio2.setCeldaActual(bloqueActual.ABC[13][aux]);
			actores.addLast(vacio2);
			aux++;
		}
		//Piso al costado del Vacio.
		Piso p1 = new Piso(cargadorSprite);
		bloqueActual.ABC[13][26].setOcupada(true);
		bloqueActual.ABC[13][26].agregarEstructura(p1);
		p1.setCeldaActual(bloqueActual.ABC[13][26]);
		actores.addFirst(p1);
		estructuras.addFirst(p1);
		Piso p2 = new Piso(cargadorSprite);
		bloqueActual.ABC[13][31].setOcupada(true);
		bloqueActual.ABC[13][31].agregarEstructura(p2);
		p2.setCeldaActual(bloqueActual.ABC[13][31]);
		actores.addFirst(p2);
		estructuras.addFirst(p2);
		
		//Irrompibles
		aux = 6; aux2 = 24;
		while ((aux <= 11) && (aux2 >= 18))
		{
			Irrompible plataforma = new Irrompible (cargadorSprite);
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma);
			estructuras.addFirst(plataforma);
			aux2--;
			Irrompible plataforma2 = new Irrompible (cargadorSprite);
			bloqueActual.ABC[aux][aux2].setOcupada(true);
			bloqueActual.ABC[aux][aux2].agregarEstructura(plataforma2);//Plataforma irrompible
			plataforma2.setCeldaActual(bloqueActual.ABC[aux][aux2]);
			actores.addFirst(plataforma2);
			estructuras.addFirst(plataforma2);
			aux2--;
			aux++;
		}
		aux = 26;
		while (aux <= 30)
		{
			Irrompible plataforma = new Irrompible (cargadorSprite);
			bloqueActual.ABC[6][aux].setOcupada(true);
			bloqueActual.ABC[6][aux].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[6][aux]);
			actores.addFirst(plataforma);
			estructuras.addFirst(plataforma);
			aux++;
		}
		aux = 6;
		while (aux <= 11)
		{
			Irrompible plataforma = new Irrompible (cargadorSprite);
			bloqueActual.ABC[aux][31].setOcupada(true);
			bloqueActual.ABC[aux][31].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[aux][31]);
			actores.addFirst(plataforma);
			estructuras.addFirst(plataforma);
			aux++;
		}
		Irrompible plataforma = new Irrompible (cargadorSprite);
		bloqueActual.ABC[5][9].setOcupada(true);
		bloqueActual.ABC[5][9].agregarEstructura(plataforma);//Plataforma irrompible
		plataforma.setCeldaActual(bloqueActual.ABC[5][9]);
		actores.addFirst(plataforma);
		estructuras.addFirst(plataforma);
		
		Rompible r1 = new Rompible (cargadorSprite);
		bloqueActual.ABC[9][8].setOcupada(true);
		bloqueActual.ABC[9][8].agregarEstructura(r1);
		r1.setCeldaActual(bloqueActual.ABC[9][8]);
		actores.addFirst(r1);
		estructuras.addFirst(r1);
		
		EspecialPowerUp plataformaPUP = new EspecialPowerUp (new SuperHongo(cargadorSprite), cc, true, cargadorSprite);
		bloqueActual.ABC[9][9].setOcupada(true);
		bloqueActual.ABC[9][9].agregarEstructura(plataformaPUP);
		plataformaPUP.setCeldaActual(bloqueActual.ABC[9][9]);
		actores.addFirst(plataformaPUP);
		estructuras.addFirst(plataformaPUP);
		especialesPowerUp.addFirst(plataformaPUP);
		
		Rompible r2 = new Rompible (cargadorSprite);
		bloqueActual.ABC[9][10].setOcupada(true);
		bloqueActual.ABC[9][10].agregarEstructura(r2);
		r2.setCeldaActual(bloqueActual.ABC[9][10]);
		actores.addFirst(r2);
		estructuras.addFirst(r2);
		
		aux = 34;
		while (aux <= 39)
		{
			if ((aux%2) == 0)
			{
				EspecialPowerUp plataformaPUP2 = new EspecialPowerUp (new Estrella(cargadorSprite), cc, false, cargadorSprite);
				bloqueActual.ABC[8][14].setOcupada(true);
				bloqueActual.ABC[8][14].agregarEstructura(plataformaPUP2);
				plataformaPUP2.setCeldaActual(bloqueActual.ABC[8][14]);
				actores.addFirst(plataformaPUP2);
				estructuras.addFirst(plataformaPUP2);
				especialesPowerUp.addFirst(plataformaPUP2);
			}
			else
			{
				EspecialMonedas plataformaM = new EspecialMonedas (3, cargadorSprite);
				bloqueActual.ABC[7][13].setOcupada(true);
				bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
				actores.addFirst(plataformaM);
				estructuras.addFirst(plataformaM);
			}
			aux++;
		}
		
		aux = 46;
		while (aux <= 56)
		{
			if ((aux%2) == 0)
			{
				Rompible rompible = new Rompible (cargadorSprite);
				bloqueActual.ABC[8][12].setOcupada(true);
				bloqueActual.ABC[8][12].agregarEstructura(rompible);
				rompible.setCeldaActual(bloqueActual.ABC[8][12]);
				actores.addFirst(rompible);
				estructuras.addFirst(rompible);
			}
			else
			{
				EspecialMonedas plataformaM = new EspecialMonedas (3, cargadorSprite);
				bloqueActual.ABC[7][13].setOcupada(true);
				bloqueActual.ABC[7][13].agregarEstructura(plataformaM);
				plataformaM.setCeldaActual(bloqueActual.ABC[7][13]);
				actores.addFirst(plataformaM);
				estructuras.addFirst(plataformaM);
			}
			aux++;
		}
		
		//Agregaci�n Actores Enemigos.
		
		aux = 11;
		while (aux <= 13)
		{
			if (aux != 12)
			{
				Goomba goomba = new Goomba (cargadorSprite);
				bloqueActual.ABC[11][aux].agregarActor(goomba);
				goomba.setCeldaActual(bloqueActual.ABC[11][aux]);
				actores.addFirst(goomba);
				enemigos.addFirst(goomba);
				caibles.addFirst((afectableXgravedad) goomba);
			}
			aux++;
		}
		
		Goomba goomba = new Goomba (cargadorSprite);
		bloqueActual.ABC[11][25].agregarActor(goomba);
		goomba.setCeldaActual(bloqueActual.ABC[11][25]);
		actores.addFirst(goomba);
		enemigos.addFirst(goomba);
		caibles.addFirst((afectableXgravedad) goomba);
		
		KoopaTroopa kt1 = new KoopaTroopa (new KTNormal(), cargadorSprite);
		bloqueActual.ABC[11][37].agregarActor(kt1);
		kt1.setCeldaActual(bloqueActual.ABC[11][37]);
		actores.addFirst(kt1);		
		enemigos.addFirst(kt1);		
		caibles.addFirst(kt1);
		
		KoopaTroopa kt2 = new KoopaTroopa (new KTNormal(), cargadorSprite);
		bloqueActual.ABC[11][42].agregarActor(kt2);
		kt2.setCeldaActual(bloqueActual.ABC[11][42]);
		actores.addFirst(kt2);
		enemigos.addFirst(kt2);
		caibles.addFirst(kt2);
		
		//Agregaci�n Monedas
		aux = 20;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda(cargadorSprite);
			bloqueActual.ABC[11][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[11][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		aux = 21;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda(cargadorSprite);
			bloqueActual.ABC[10][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[10][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		aux = 22;
		while (aux <= 24)
		{
			Moneda moneda1 = new Moneda(cargadorSprite);
			bloqueActual.ABC[9][aux].agregarActor(moneda1);
			moneda1.setCeldaActual(bloqueActual.ABC[9][aux]);
			actores.addLast(moneda1);
			aux++;
		}
		
		//Agregaci�n Llegada
		aux = 0;
		while (aux <= 11)
		{
			Actor llegada = new Llegada(cc, cargadorSprite);
			bloqueActual.ABC[aux][65].agregarActor(llegada);
			llegada.setCeldaActual(bloqueActual.ABC[aux][65]);
			actores.addFirst(llegada);
			aux++;
		}
		
		//Agregaci�n Final Mapa
		bloqueActual.setColumnaOcupada(69, true);
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
	 * Agrega PowerUp al nivel.
	 * 
	 * @param pwUp PowerUp a agregar.
	 * @throws NullPointerException Si se ingresa un PowerUp igual a null.
	 * @throws AccionActorException Si se intenta agregar un PowerUp a una Celda totalmente ocupada.
	 */
	public void agregarPowerUp (PowerUp pwUp) throws NullPointerException, AccionActorException
	{
		if (pwUp == null)
			throw new NullPointerException ("Celda.agregarActor()" + "\n" +
                                            "El Actor que est� intentando agregar a la Celda es null.");
		
		actores.addLast(pwUp);
		powerUps.addLast(pwUp);
		caibles.addLast(pwUp);
	}
	
	/**
	 * Elimina el Actor actor del Nivel.
	 * 
	 * Si el actor que se quiere eliminar es:
	 * Estructura => llamar a eliminarEstructura, y no a este m�todo.
	 * EspecialPowerUp => llamar a eliminarEspecialPowerUp, y no a este m�todo.
	 * PowerUp => llamar a eliminarPowerUp, y no a este m�todo.
	 * Enemigo => llamar a eliminarEnemigo, y no a este m�todo.
	 * 
	 * Si adem�s el actor es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a alguno de los antes mencionados.
	 * 
	 * @param actor Actor a eliminar.
	 * @throws NullPointerException Si actor es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un Actor que no pertenece al Nivel.
	 */
	public void eliminarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("Nivel.eliminarActor()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		if (actores.isEmpty())
			throw new AccionActorException ("Nivel.eliminarActor()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != actor))
			p = actores.next(p);
		if (p.element() != actor)
			throw new AccionActorException ("Nivel.eliminarActor()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		actores.remove(p);
	}
	
	/**
	 * Elimina la Estructura estructura del Nivel.
	 * 
	 * Se elimina autom�ticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * @param estructura Estructura a eliminar.
	 * @throws NullPointerException Si estructura es igual a null.
	 * @throws AccionActorException Si se intenta eliminar una Estructura que no pertenece al Nivel.
	 */
	public void eliminarEstructura (Estructura estructura) throws NullPointerException, AccionActorException
	{
		if (estructura == null)
			throw new NullPointerException ("Nivel.eliminarEstructura()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		if (estructuras.isEmpty())
			throw new AccionActorException ("Nivel.eliminarEstructura()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Estructura> p = estructuras.first();
		while ((p != estructuras.last()) && (p.element() != estructura))
			p = estructuras.next(p);
		if (p.element() != estructura)
			throw new AccionActorException ("Nivel.eliminarEstructura()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		estructuras.remove(p);
		eliminarActor((Actor) estructura);
	}
	
	/**
	 * Elimina el EspecialPowerUp especialPowerUp del Nivel.
	 * 
	 * Se elimina autom�ticamente de la lista de estructuras.
	 * NO LLAMAR eliminarEstructura.
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
		if (especialesPowerUp.isEmpty())
			throw new AccionActorException ("Nivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<EspecialPowerUp> p = especialesPowerUp.first();
		while ((p != especialesPowerUp.last()) && (p.element() != especialPowerUp))
			p = especialesPowerUp.next(p);
		if (p.element() != especialPowerUp)
			throw new AccionActorException ("Nivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		especialesPowerUp.remove(p);
		eliminarEstructura((Estructura) especialPowerUp);
	}
	
	/**
	 * Elimina el PowerUp powerUp del Nivel.
	 * 
	 * Se elimina autom�ticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * @param powerUp PowerUp a eliminar.
	 * @throws NullPointerException Si powerUp es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un PowerUp que no pertenece al Nivel.
	 */
	public void eliminarPowerUp (PowerUp powerUp) throws NullPointerException, AccionActorException
	{
		if (powerUp == null)
			throw new NullPointerException ("Nivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		if (powerUps.isEmpty())
			throw new AccionActorException ("Nivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<PowerUp> p = powerUps.first();
		while ((p != powerUps.last()) && (p.element() != powerUp))
			p = powerUps.next(p);
		if (p.element() != powerUp)
			throw new AccionActorException ("Nivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		powerUps.remove(p);
		eliminarActor(powerUp);
	}
	
	/**
	 * Elimina el Enemigo enemigo del Nivel.
	 * 
	 * Se elimina autom�ticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * @param enemigo Enemigo a eliminar.
	 * @throws NullPointerException Si enemigo es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un Enemigo que no pertenece al Nivel.
	 */
	public void eliminarEnemigo (Enemigo enemigo) throws NullPointerException, AccionActorException
	{
		if (enemigo == null)
			throw new NullPointerException ("Nivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		if (enemigos.isEmpty())
			throw new AccionActorException ("Nivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Enemigo> p = enemigos.first();
		while ((p != enemigos.last()) && (p.element() != enemigo))
			p = enemigos.next(p);
		if (p.element() != enemigo)
			throw new AccionActorException ("Nivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		enemigos.remove(p);
		eliminarActor((Actor) enemigo);
	}
	
	/**
	 * Elimina el Actor Caible caible del Nivel.
	 * 
	 * Se debe tambi�n llamar al Eliminar especifico para ese Actor. (eliminarPowerUp(), eliminarEnemigo(), etc.)
	 * 
	 * @param caible Actor Caible a eliminar.
	 * @throws NullPointerException Si caible es igual a null.
	 * @throws AccionActorException Si se intenta eliminar un Actor Caible que no pertenece al Nivel.
	 */
	public void eliminarCaible (afectableXgravedad caible) throws NullPointerException, AccionActorException
	{
		if (caible == null)
			throw new NullPointerException ("Nivel.eliminarCaible()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " es null.");
		if (caibles.isEmpty())
			throw new AccionActorException ("Nivel.eliminarCaible()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<afectableXgravedad> p = caibles.first();
		while ((p != caibles.last()) && (p.element() != caible))
			p = caibles.next(p);
		if (p.element() != caible)
			throw new AccionActorException ("Nivel.eliminarCaible()" + "\n" +
                                            "El Actor que est� intentando eliminar del Nivel " + id + " no pertenece a al mismo.");
		caibles.remove(p);
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
	
	/**
	 * Devuelve la Lista de Caibles.
	 * 
	 * @param cc Verificaci�n de que es solicitado por un ControlCentral.
	 * @return Lista de Caibles.
	 */
	public PositionList<afectableXgravedad> getCaibles (ControlCentral cc)
	{
		return caibles;
	}

}