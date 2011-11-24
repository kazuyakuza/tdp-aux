package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.InicioNivelException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.NoPersonajes.Piso;
import ProyectoX.Logica.NoPersonajes.Plataformas.Irrompible;

import ProyectoX.Logica.NoPersonajes.PowerUps.*;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.NoPersonajes.BolaFuego;

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
	}
	
	/*COMANDOS*/
	
	/**
	 * Inicializa el Nivel creado.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @param cargadorSprite Clase encargada de cargar las imagenes de los Actores.
	 * @return Lista de Actores del Nivel creado.
	 * @throws InicioNivelException Si se produce un error al Iniciar el Nivel.
	 */
	public PositionList<Actor> inicializarNivel (Actor actorJugador, ControlCentral cc, CargadorSprite cargadorSprite) throws InicioNivelException
	{
		PositionList<Actor> listaActores = null;
		try
		{
			switch (id)
			{
				case 1:
					   {
						   listaActores = nivel1(actorJugador, cc, cargadorSprite);
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
		return listaActores;
	}
	
	/**
	 * Crea el Nivel 1.
	 * 
	 * @param actorJugador Actor del Jugador del Juego.
	 * @param cc ControlCentral del Juego para los Actores que lo requieran.
	 * @param cargadorSprite Clase encargada de cargar las imagenes de los Actores.
	 * @return Lista de Actores del Nivel creado.
	 */
	private PositionList<Actor> nivel1 (Actor actor, ControlCentral cc, CargadorSprite cargadorSprite)
	{
		fondo = "Fondo2.png";
		
		mapa = new Mapa (1, 1);
		
		//Creaci�n de la lista de Actores que estar�n en el Nivel.
		PositionList<Actor> listaActores = new ListaPositionSimple<Actor> ();
		
		//Creaci�n Bloque 1 del Nivel. Que es el Bloque de inicio del Nivel.
		bloqueActual = new Bloque(mapa, 0, 0, 15/*8*/, 20/*10*/);//Nivel de Piso default.
		
		mapa.setBloque(0, 0, bloqueActual);
		
		//Agregaci�n Piso
		int aux = 0;
		while (aux < 20)
		{
			/*if (aux != 6)
			{*/
				Piso piso = new Piso(cargadorSprite);
				bloqueActual.ABC[13][aux].agregarEstructura(piso);
				piso.setCeldaActual(bloqueActual.ABC[13][aux]);
				listaActores.addFirst(piso);
			//}
			aux++;
		}
		
		//Agregaci�n Actor del Jugador.
		bloqueActual.ABC[12][1].agregarActor(actor);
		actor.setCeldaActual(bloqueActual.ABC[12][1]);
		listaActores.addFirst(actor);
		
		//Agregacion de un power up.
		PowerUp powerUp = new SuperHongo(cargadorSprite);
		bloqueActual.ABC[5][7].agregarActor(powerUp);
		powerUp.setCeldaActual(bloqueActual.ABC[5][7]);
		listaActores.addLast(powerUp);
		
		PowerUp flor = new FlorFuego(cargadorSprite);
		bloqueActual.ABC[5][8].agregarActor(flor);
		flor.setCeldaActual(bloqueActual.ABC[5][8]);
		listaActores.addLast(flor);
		
		PowerUp bomba = new Estrella(cargadorSprite);
		bloqueActual.ABC[10][6].agregarActor(bomba);
		bomba.setCeldaActual(bloqueActual.ABC[10][6]);
		listaActores.addLast(bomba);
				
		BolaFuego bola = new BolaFuego((Mario)actor,cargadorSprite);
		bloqueActual.ABC[6][10].agregarActor(bola);
		bola.setCeldaActual(bloqueActual.ABC[6][10]);
		listaActores.addLast(bola);
		
		//Agregaci�n Actores no Personjes.
		aux = 6;
		while (aux < 12)
		{
			Irrompible plataforma = new Irrompible (cargadorSprite);
			bloqueActual.ABC[9][aux].setOcupada(true);
			bloqueActual.ABC[9][aux].agregarEstructura(plataforma);//Plataforma irrompible
			plataforma.setCeldaActual(bloqueActual.ABC[9][aux]);
			listaActores.addFirst(plataforma);
			aux++;
		}
		
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
		
		return listaActores;
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

}