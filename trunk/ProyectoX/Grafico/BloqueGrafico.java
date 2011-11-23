package ProyectoX.Grafico;

import ProyectoX.Excepciones.PosicionIncorrectaException;
import ProyectoX.Excepciones.SpriteException;
import ProyectoX.Grafico.Sprite.SpriteManager;
import ProyectoX.Librerias.TDALista.ListaPositionSimple;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;

/**
 * Representación un bloque gráfico de sprites.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class BloqueGrafico
{
	
	//Variables de Instancia
	protected PositionList<SpriteManager> sprites;
	protected int maxX, maxY, nivelPiso;
	//maxX = máxima posición posible en el eje X.
	//maxY = máxima posición posible en el eje Y.
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea un BloqueGráfico de dimensiones mX y mY.
	 * 
	 * @param mX Máximo X (Largo).
	 * @param mY Máximo Y (Alto).
	 */
	public BloqueGrafico (int mX, int mY)
	{
		maxX = mX;
		maxY = mY;
		nivelPiso = maxY;
		sprites = new ListaPositionSimple<SpriteManager> ();
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega un Sprite al Bloque.
	 * 
	 * @param sp Sprite a agregar.
	 * @throws NullPointerException Si sp es null.
	 * @throws PosicionIncorrectaException Si la posición del SpriteManager a ingresar no corresponde con las posiciones posibles de este BloqueGráfico.
	 */
	public void agregarSprite (SpriteManager sp) throws NullPointerException, PosicionIncorrectaException
	{
		if (sp == null)
			throw new NullPointerException ("BloqueGrafico.agregarSprite()" + "\n" +
                                            "El SpriteManager que está intentado ingresar es null.");
		if ((sp.posicion()[0] > maxX) || (sp.posicion()[1] > maxY))
			throw new PosicionIncorrectaException ("BloqueGrafico.agregarSprite()" + "\n" +
					                               "Posición ingresada incorrecta." + "\n" +
                                                   "No existe posición (" + sp.posicion()[0] + "," + sp.posicion()[1] +")." + "\n" +
                                                   "maxX = " + maxX + " maxY = " + maxY);
		sprites.addLast(sp);
	}
	
	/**
	 * Agrega los Sprites al BloqueGráfico.
	 * 
	 * @param sps Sprites a agregar.
	 * @throws NullPointerException Si sps es null.
	 * @throws PosicionIncorrectaException Si la posición del SpriteManager a ingresar no corresponde con las posiciones posibles de este BloqueGráfico.
	 */
	public void agregarSprites (PositionList<SpriteManager> sps) throws NullPointerException, PosicionIncorrectaException
	{
		if (sps == null)
			throw new NullPointerException ("BloqueGrafico.agregarSprites()" + "\n" +
                                            "La lista de SpriteManagers que está intentado ingresar es null.");
		for (SpriteManager sp: sps)
			agregarSprite(sp);
	}
	
	/**
	 * Elimina un Sprite del BloqueGráfico.
	 * 
	 * @param sp Sprite a Eliminar.
	 * @throws NullPointerException Si sp es null.
	 * @throws SpriteException Si se ingresa un SpriteManager que no pertenece al BloqueGrafico.
	 */
	public void eliminarSprite (SpriteManager sp) throws NullPointerException, SpriteException
	{
		if (sp == null)
			throw new NullPointerException ("BloqueGrafico.eliminarSprite()" + "\n" +
                                            "El SpriteManager que está intentado ingresar es null.");
		
		Position<SpriteManager> aux = sprites.first();
		while ((aux != sprites.last()) && (aux.element() != sp))
			aux = sprites.next(aux);
		if (aux.element() != sp)
			throw new SpriteException ("BloqueGrafico.eliminarSprite()" + "\n" +
                                       "El SpriteManager que se intenta eliminar no pertenece al BloqueGrafico actual.");
		sprites.remove(aux);
	}
	
	/**
	 * Actualiza el nivel del piso del bloque.
	 * 
	 * @param nPiso Nuevo nivel del piso.
	 */
	public void setNivelPiso (int nPiso)
	{
		nivelPiso = nPiso;
	}
	
	/**
	 * Elimina todos los sprites del bloque.
	 */
	public void limpiar ()
	{
		while (! sprites.isEmpty())
			sprites.remove(sprites.first());
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve maxX.
	 * 
	 * @return maxX.
	 */
	public int getMaxX ()
	{
		return maxX;
	}
	
	/**
	 * Devuelve maxY.
	 * 
	 * @return maxY.
	 */
	public int getMaxY ()
	{
		return maxY;
	}
	
	/**
	 * Devuelve el nivel del Piso.
	 * 
	 * @return Nivel del Piso.
	 */
	public int getNivelPiso ()
	{
		return nivelPiso;
	}

}