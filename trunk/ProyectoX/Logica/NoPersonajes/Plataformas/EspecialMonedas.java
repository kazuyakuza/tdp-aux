package ProyectoX.Logica.NoPersonajes.Plataformas;

import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Grafico.Sprite.CargadorSprite;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Personajes.Mario;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.NoPersonajes.Moneda;

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
	 * 
	 * @param cargadorSprite Clase para cargar los sprites.
	 */
	public EspecialMonedas(int monedas,CargadorSprite cargadorSprite) 
	{
		super(cargadorSprite);
		spriteManager.cargarSprites(nombresSprites);
		spriteManager.rotarGif(cantFramesMovimiento);
		this.monedas = new ListaPositionSimple <Moneda> ();
		inicializar(monedas, cargadorSprite);
	}
	
	private void inicializar (int cantidad, CargadorSprite cargadorSprite)
	{
		for (int i=0; i < cantidad; i++)
			this.monedas.addLast(new Moneda (cargadorSprite));
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
			if ( (this.celdaActual.getBloque().hayInferior(this.celdaActual)) && (colisionAbajo(mario)) )
			{//Si la colisión de Mario es desde abajo, sacar moneda, sino, no hacer nada.			
				if (hayMoneda())
				{//Si hay monedas, sacar la primera y agregarsela al jugador, sino no hacer nada.
					//moneda = monedas.remove(monedas.first());
					monedas.remove(monedas.first());
					mario.getJugador().agregarMoneda();
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
	 * @param a Actor con el que se colisiona.
	 * @return Verdadero si el Actor a se encuentra abajo, falso, en caso contrario.
	 */
	protected boolean colisionAbajo (Actor a)
	{
		return this.celdaActual.getBloque().getInferior(this.celdaActual) == a.getCeldaActual();
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