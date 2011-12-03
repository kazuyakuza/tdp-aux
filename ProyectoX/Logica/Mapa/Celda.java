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
 * Un espacio es una porci�n del Mapa que esta totalmende delimitada, y es �nica en el Mapa.
 * La celda puede contener actores, o estar totalmente ocupada.
 * Si una Celda est� totalmente ocupada, ning�n otro Actor puede ingresar a la Celda.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Celda
{
	
	//Variables de Instancia
	protected boolean totalmenteOcupada; //True = ning�n otro Actor puede ingresar a la Celda.
	protected int posFila, posColumna; //Posici�n en su Bloque.
	protected PositionList<Actor> actores; //Actores actuales en la Celda.
	protected Bloque bloque; //Bloque al que pertenece la Celda.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una Celda con los datos ingresados.
	 * 
	 * @param ocupada Si est� totalmente ocupada o no la celda.
	 * @param file Fila de la celda en el Mapa.
	 * @param columna Columna de la celda en el Mapa.
	 * @param b Bloque al que pertenece la Celda.
	 * @exception PosicionIncorrectaException Si se intenta asignar una posici�n incorrecta a la nueva Celda.
	 * @throws NullPointerException Si el Bloque b es null.
	 */
	public Celda (boolean ocupada, int fila, int columna, Bloque b) throws PosicionIncorrectaException
	{
		if ((fila < 0) || (columna < 0))
			throw new PosicionIncorrectaException ("Celda." + "\n" +
                                                   "Imposible asignar la posici�n (" + fila + "," + columna + ") a la nueva Celda.");
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
                                            "El Actor que est� intentando agregar a la Celda es null.");
		if (totalmenteOcupada)
			throw new AccionActorException("Celda.agregarActor()" + "\n" +
                                           "Imposible agregar Actor a la celda de posici�n (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda est� totalmente ocupada.");
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
                                            "El Actor que est� intentando agregar a la Celda es null.");
		if (!totalmenteOcupada)
			throw new AccionActorException("Celda.agregarEstructura()" + "\n" +
                                           "Imposible agregar Piso a la celda de posici�n (" + posFila + "," + posColumna + ")." + "\n" +
					                       "La celda no est� totalmente ocupada.");
		actores.addLast((Actor) estructura);
	}
	
	/**
	 * Saca el Actor pasado por par�metro.
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
                                            "El Actor que est� intentando sacar de la Celda (" + posFila + "," + posColumna + ") es null.");
		if (actores.isEmpty())
			throw new SpriteException ("Celda.sacarActor()" + "\n" +
                                       "El Actor que est� intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma." + "\n" +
                                       "La Celda no tiene Actores.");
		
		for (Actor a: actores)
			if (a == actor)
				return eliminarActor(a);
		
		throw new AccionActorException ("Celda.sacarActor()" + "\n" +
                "El Actor que est� intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma.");
	}
	
	/**
	 * Saca la Estructura pasado por par�metro.
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
                                            "El Actor que est� intentando sacar de la Celda es null.");
		if (!totalmenteOcupada)
			throw new AccionActorException("Celda.sacarEstructura()" + "\n" +
                                           "Imposible sacar una Estructura de una Celda que no est� totalmente ocupada.");
		
		for (Actor a: actores)
			if (a == estructura)
				return eliminarActor(a);
		
		throw new AccionActorException ("Celda.sacarEstructura()" + "\n" +
                "La Estructura que est� intentando sacar de la Celda (" + posFila + "," + posColumna + ") no pertenece a la misma.");
	}
	
	/**
	 * Saca el Actor pasado por par�metro.
	 * 
	 * @param actor Actor a sacar de la Celda.
	 * @return Actor sacado de la Celda.
	 */
	private Actor eliminarActor (Actor actor)
	{
		Position<Actor> p = actores.first();
		while ((p != actores.last()) && (p.element() != actor))
			p = actores.next(p);
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
	 * @return True:  si la Celda est� totalmente ocupada.
	 *         False: demas casos.
	 */
	public boolean isOcupada ()
	{
		return totalmenteOcupada;
	}
	
	/**
	 * Devuelve un arreglo de dos componentes con la posici�n de la Celda.
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
	 * Devuelve la posici�n horizontal (eje x) de la Celda.
	 * 
	 * @return La posici�n horizontal de la Celda.
	 */
	public int getPosFila ()
	{
		return posFila;
	}
	
	/**
	 * Devuelve la posici�n vertical (eje y) de la Celda.
	 * 
	 * @return La posici�n vertical de la Celda.
	 */
	public int getPosColumna ()
	{
		return posColumna;
	}
	
	/**
	 * Verifica si hay una Celda a izquierda de �sta.
	 * 
	 * Hay una Celda a izquierda si:
	 * - hay una Celda a izquierda en su bloque.
	 * - o hay una Celda a izquierda en el Bloque a izquierda del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda a izquierda de �sta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean hayAnterior () throws PosicionIncorrectaException
	{		
		return bloque.hayAnterior(this);	
	}
	
	/**
	 * Verifica si hay una Celda a derecha de �sta.
	 * 
	 * Hay una Celda a derecha si:
	 * - hay una Celda a derecha en su bloque.
	 * - o hay una Celda a derecha en el Bloque a derecha del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda a derecha de �sta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean haySiguiente () throws PosicionIncorrectaException
	{		
		return bloque.haySiguiente(this);			
	}
	
	/**
	 * Verifica si hay una Celda por encima de �sta.
	 * 
	 * Hay una Celda por encima si:
	 * - hay una Celda por encima en su bloque.
	 * - o hay una Celda por encima en el Bloque por encima del Bloque Actual.
	 * 	
	 * @return True:  existe una Celda por encima de �sta.
	 *         False: demas casos.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean haySuperior () throws PosicionIncorrectaException
	{		
		return bloque.haySuperior(this);	
	}
	
	/**
	 * Verifica si hay una Celda por debajo de �sta.
	 * 
	 * Hay una Celda por debajo si:
	 * - hay una Celda por debajo en su bloque.
	 * - o hay una Celda por debajo en el Bloque por debajo del Bloque Actual.
	 * 	 
	 * @return True:  existe una Celda por debajo de �sta.
	 *         False: demas casos.	
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean hayInferior () throws PosicionIncorrectaException
	{		
		return bloque.hayInferior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina anterior.
	 *
	 * @return Celda vecina que se encuentra a la izquierda de �sta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getAnterior () throws PosicionIncorrectaException
	{		
		return bloque.getAnterior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina siguiente.
	 * 	
	 * @return Celda vecina que se ubica a la derecha de �sta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getSiguiente () throws PosicionIncorrectaException
	{
		
		return bloque.getSiguiente(this);			
	}
	
	/**
	 * Devuelve su Celda vecina superior.
	 * 	 
	 * @return Celda vecina que se encuentra por encima de �sta.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getSuperior () throws PosicionIncorrectaException
	{
		
		return bloque.getSuperior(this);	
	}
	
	/**
	 * Devuelve su Celda vecina inferior.
	 *	 
	 * @return Celda vecina que se ubica por debajo de �sta.	 
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getInferior () throws PosicionIncorrectaException
	{		
		return bloque.getInferior(this);	
	}
	
	/**
	 * Calcula la distancia que hay entre las Celdas.
	 * @param c Celda con la que se desea calcular la distancia a �sta.	 
	 * @return entero que es la distancia entre las Celdas c y �sta.
	 * @throws NullPointerException si c es null.
	 */
	public int distancia (Celda c) throws NullPointerException
	{
		if (c == null)
			throw new NullPointerException ("ControlCentral.distancia()" + "\n" +
											"Imposible calcular distancia, alguna celda ess nulas.");
				
		int x = Math.abs(this.getPosFila() - c.getPosFila());
		int y = Math.abs(this.getPosColumna() - c.getPosColumna());		
		return (int) Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
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