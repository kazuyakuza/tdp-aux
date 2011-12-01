package ProyectoX.Logica.Mapa;

import java.util.Iterator;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.PosicionIncorrectaException;
import ProyectoX.Excepciones.SpriteException;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.NoPersonajes.Estructura;

/**
 * Representa un espacio en el mapa del Juego.
 * Un espacio es una porción del Mapa que esta totalmende delimitada, y es única en el Mapa.
 * La celda puede contener actores, o estar totalmente ocupada.
 * Si una Celda está totalmente ocupada, ningún otro Actor puede ingresar a la Celda.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Celda
{
	
	//Variables de Instancia
	protected boolean totalmenteOcupada; //True = ningún otro Actor puede ingresar a la Celda.
	protected int posFila, posColumna; //Posición en su Bloque.
	protected PositionList<Actor> actores; //Actores actuales en la Celda.
	protected Bloque bloque; //Bloque al que pertenece la Celda.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Celda con los datos ingresados.
	 * 
	 * @param ocupada Si está totalmente ocupada o no la celda.
	 * @param file Fila de la celda en el Mapa.
	 * @param columna Columna de la celda en el Mapa.
	 * @param b Bloque al que pertenece la Celda.
	 * @exception PosicionIncorrectaException Si se intenta asignar una posición incorrecta a la nueva Celda.
	 * @throws NullPointerException Si el Bloque b es null.
	 */
	public Celda (boolean ocupada, int fila, int columna, Bloque b) throws PosicionIncorrectaException
	{
		if ((fila < 0) || (columna < 0))
			throw new PosicionIncorrectaException ("Celda." + "\n" +
                                                   "Imposible asignar la posición (" + fila + "," + columna + ") a la nueva Celda.");
		if (b == null)
			throw new NullPointerException ("Celda." + "\n" +
                                            "Imposible crear una Celda con bloque null.");
		
		totalmenteOcupada = ocupada;
		posFila = fila;
		posColumna = columna;
		bloque = b;
		actores = new ListaPositionSimple<Actor> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega un Actor a la Celda.
	 * 
	 * @param actor Actor a agregar.
	 * @throws NullPointerException Si se ingresa un Actor igual a null.
	 * @throws AccionActorException Si se intenta agregar un Actor a una Celda totalmente ocupada.
	 */
	public void agregarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("Celda.agregarActor()" + "\n" +
                                            "El Actor que está intentando agregar a la Celda es null.");
		if (totalmenteOcupada)
			throw new AccionActorException("Celda.agregarActor()" + "\n" +
                                           "Imposible agregar Actor a la celda de posición (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda está totalmente ocupada.");
		actores.addLast(actor);
	}
	
	/**
	 * Agrega Estructura a la Celda.
	 * 
	 * @param estructura Estructura a agregar.
	 * @throws NullPointerException Si se ingresa una Estructura igual a null.
	 * @throws AccionActorException Si se intenta agregar una Estructura a una Celda no totalmente ocupada.
	 */
	public void agregarEstructura (Estructura estructura) throws NullPointerException, AccionActorException
	{
		if (estructura == null)
			throw new NullPointerException ("Celda.agregarEstructura()" + "\n" +
                                            "El Actor que está intentando agregar a la Celda es null.");
		if (!totalmenteOcupada)
			throw new AccionActorException("Celda.agregarEstructura()" + "\n" +
                                           "Imposible agregar Piso a la celda de posición (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda no está totalmente ocupada.");
		actores.addLast((Actor) estructura);
	}
	
	/**
	 * Saca el Actor pasado por parámetro.
	 * 
	 * @param actor Actor a sacar de la Celda.
	 * @return Actor sacado de la Celda.
	 * @throws NullPointerException Si actor es igual a null.
	 * @throws AccionActorException Si se intenta sacar un Actor que no pertenece a la Celda.
	 */
	public Actor sacarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (actor == null)
			throw new NullPointerException ("Celda.sacarActor()" + "\n" +
                                            "El Actor que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") es null.");
		if (actores.isEmpty())
			throw new SpriteException ("Celda.sacarActor()" + "\n" +
                                       "El Actor que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma." + "\n" +
                                       "La Celda no tiene Actores.");
		
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != actor))
			p = actores.next(p);
		if (p.element() != actor)
			throw new AccionActorException ("Celda.sacarActor()" + "\n" +
                                            "El Actor que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma.");
		return actores.remove(p);
	}
	
	/**
	 * Saca la Estructura pasado por parámetro.
	 * 
	 * @param actor Estructura a sacar de la Celda.
	 * @return Estructura sacada de la Celda.
	 * @throws NullPointerException Si actor es igual a null.
	 * @throws AccionActorException Si se intenta sacar un Actor que no pertenece a la Celda.
	 */
	public Actor sacarEstructura (Estructura estructura) throws NullPointerException, AccionActorException
	{
		if (estructura == null)
			throw new NullPointerException ("Celda.sacarEstructura()" + "\n" +
                                            "El Actor que está intentando sacar de la Celda es null.");
		if (!totalmenteOcupada)
			throw new AccionActorException("Celda.sacarEstructura()" + "\n" +
                                           "Imposible sacar una Estructura de una Celda que no está totalmente ocupada.");
		
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != estructura))
			p = actores.next(p);
		if (p.element() != estructura)
			throw new AccionActorException ("Celda.sacarEstructura()" + "\n" +
                                            "La Estructura que está intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma.");
		return actores.remove(p);
	}
	
	/**
	 * Setea si la Celda esta totalmenteOcupada o no.
	 * 
	 * @param ocupada True: la celda esta totalmente ocupada.
	 */
	public void setOcupada (boolean ocupada)
	{
		totalmenteOcupada = ocupada;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve un Iterador con los Actores de la Celda.
	 * 
	 * @return Iterador con los Actores de la Celda.
	 */
	public Iterator<Actor> getActores ()
	{
		return actores.iterator();
	}
	
	/**
	 * Devuelve el estado de la Celda.
	 * 
	 * @return True:  si la Celda está totalmente ocupada.
	 *         False: demas casos.
	 */
	public boolean isOcupada ()
	{
		return totalmenteOcupada;
	}
	
	/**
	 * Devuelve un arreglo de dos componentes con la posición de la Celda.
	 * Componente [0] = posFila.
	 * Componente [1] = posColumna.
	 * 
	 * @return Arreglo de dos componentes con {posFila, posColumna};
	 */
	public int[] getPosicion ()
	{
		return new int[] {posFila, posColumna};
	}
	
	/**
	 * Devuelve la posición horizontal (eje x) de la Celda.
	 * 
	 * @return La posición horizontal de la Celda.
	 */
	public int getPosFila ()
	{
		return posFila;
	}
	
	/**
	 * Devuelve la posición vertical (eje y) de la Celda.
	 * 
	 * @return La posición vertical de la Celda.
	 */
	public int getPosColumna ()
	{
		return posColumna;
	}
	
	/**
	 * Verifica si hay una Celda a izquierda de ésta.
	 * 
	 * Hay una Celda a izquierda si:
	 * - hay una Celda a izquierda en su bloque.
	 * - o hay una Celda a izquierda en el Bloque a izquierda del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda a izquierda de ésta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public boolean hayAnterior () throws PosicionIncorrectaException
	{		
		return bloque.hayAnterior(this);	
	}
	
	/**
	 * Verifica si hay una Celda a derecha de ésta.
	 * 
	 * Hay una Celda a derecha si:
	 * - hay una Celda a derecha en su bloque.
	 * - o hay una Celda a derecha en el Bloque a derecha del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda a derecha de ésta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public boolean haySiguiente () throws PosicionIncorrectaException
	{		
		return bloque.haySiguiente(this);			
	}
	
	/**
	 * Verifica si hay una Celda por encima de ésta.
	 * 
	 * Hay una Celda por encima si:
	 * - hay una Celda por encima en su bloque.
	 * - o hay una Celda por encima en el Bloque por encima del Bloque Actual.
	 * 	
	 * @return True:  existe una Celda por encima de ésta.
	 *         False: demas casos.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public boolean haySuperior () throws PosicionIncorrectaException
	{		
		return bloque.haySuperior(this);	
	}
	
	/**
	 * Verifica si hay una Celda por debajo de ésta.
	 * 
	 * Hay una Celda por debajo si:
	 * - hay una Celda por debajo en su bloque.
	 * - o hay una Celda por debajo en el Bloque por debajo del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda por debajo de ésta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public boolean hayInferior () throws PosicionIncorrectaException
	{		
		return bloque.hayInferior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina anterior.
	 *
	 * @return Celda vecina que se encuentra a la izquierda de ésta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public Celda getAnterior () throws PosicionIncorrectaException
	{		
		return bloque.getAnterior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina siguiente.
	 * 	
	 * @return Celda vecina que se ubica a la derecha de ésta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public Celda getSiguiente () throws PosicionIncorrectaException
	{
		
		return bloque.getSiguiente(this);			
	}
	
	/**
	 * Devuelve su Celda vecina superior.
	 * 	 
	 * @return Celda vecina que se encuentra por encima de ésta.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public Celda getSuperior () throws PosicionIncorrectaException
	{
		
		return bloque.getSuperior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina inferior.
	 *	 
	 * @return Celda vecina que se ubica por debajo de ésta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posición imposible o incorrecta.
	 */
	public Celda getInferior () throws PosicionIncorrectaException
	{		
		return bloque.getInferior(this);	
	}
	
	/**
	 * Devuelve el Bloque al que pertenece la Celda.
	 * 
	 * @return Bloque al que pertenece la Celda.
	 */
	public Bloque getBloque ()
	{
		return bloque;
	}
	
}