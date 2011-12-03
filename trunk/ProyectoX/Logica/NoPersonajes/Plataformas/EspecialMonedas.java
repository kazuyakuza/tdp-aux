package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.NoPersonajes.Moneda;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;

/**
 * Representa a una Plataforma Especial Monedas en el Juego.
 * Es una plataforma Irrompible que contiene varias Monedas, las cuales aparecen cuando la plataforma 
 * es golpeada desde abajo por Mario.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class EspecialMonedas extends Irrompible
{
	//Atributos de Clase
	private static final String dirRecursos = "Estructuras/Plataformas/";
	private static final String [] nombresSprites = {dirRecursos + "QuestionBlock-1.png",//0 = Plataforma con monedas.
													 dirRecursos + "QuestionBlock-2.png",
													 dirRecursos + "QuestionBlock-3.png",
													 dirRecursos + "Empty-Block.gif"};	 //3 = Plataforma sin monedas.
	
	private static int cantFramesMovimiento = 3;
	private static int vacio = 3;
	
	//Atributos de Instancia
	protected PositionList <Moneda> monedas;

	/**
	 * Crea una Plataforma EspecialMonedas.
	 */
	public EspecialMonedas(int monedas) 
	{
		super();
		spriteManager.cargarSprites(nombresSprites);
		spriteManager.rotarGif(cantFramesMovimiento);
		this.monedas = new ListaPositionSimple <Moneda> ();
		inicializar(monedas);
	}
	
	/**
	 * Inicializa al objeto, agregando a la lista de monedas, la cantidad especificada.
	 * @param cantidad monedas a crear y agregar a la plataforma.
	 */
	private void inicializar (int cantidad)
	{
		Moneda m = null;
		for (int i=0; i < cantidad; i++)
		{
			m = new Moneda ();
			this.monedas.addLast(m);
			m.getSpriteManager().setNotGif();
		}
	}
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Le da Monedas al PjSeleccionable pj.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public void colisionarPj (PjSeleccionable pj) throws ColisionException, NullPointerException
	{
		if (pj == null)
			throw new NullPointerException ("EspecialMonedas.colisionarPj()" + "/n" +
											"Imposible realizar colisión, actor nulo.");
		
		try
		{
			Mario mario = checkActorJugador (pj);
			Moneda moneda;
			if (colisionAbajo(mario))
			{//Si la colisión de Mario es desde abajo, sacar moneda, sino, no hacer nada.			
				if (hayMoneda())
				{//Si hay monedas, sacar la primera y agregarsela al jugador, sino no hacer nada.
					moneda = monedas.remove(monedas.first());
					
					moneda.getSpriteManager().actualizar(celdaActual.getSuperior().getPosicion());
					moneda.getSpriteManager().rotarGif(moneda.getCantFramesMovimiento());
					spriteManager.printNextMe(moneda.getSpriteManager());
					mario.getJugador().asignarPuntos(moneda.getPuntos(mario));
					mario.getJugador().agregarMoneda();
					moneda.matate(this);
					
					if (!hayMoneda())
						spriteManager.cambiarSprite(vacio);					
				}			
			}
		}
		catch (Exception e)
		{
			throw new ColisionException ("EspecialMonedas.colisionarPj()" + "\n" +
					                     "Detalles del Error:" + "\n" +
					                     e.getMessage());
		}
	}
	
	/**
	 * Verifica si la colisión con el Actor proviene desde abajo.
	 * @param mario Mario con el que se colisiona.
	 * @return Verdadero si Mario a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Mario mario)
	{
		//Mario se encuentra debajo de la plataforma si y solo si para Mario el vectorDistancia = (0,1).
		int [] vector = mario.vectorDistancia(this);		
		return (vector[0] == 0 && vector[1] == 1);
	}
	
	/**
	 * Verifica si la plataforma contiene moneda.	 
	 * @return Verdadero si hay monedas en la plataforma, falso, en caso contrario.
	 */
	protected boolean hayMoneda()
	{
		return !monedas.isEmpty();
	}

}