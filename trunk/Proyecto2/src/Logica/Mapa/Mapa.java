package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.BoundaryViolationException;

/**
 * Representa la parte Jugable del Juego.
 * El Mapa esta conformado por uno o mas bloques.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Mapa
{
	
	//Variables de Instancia
	protected Bloque[] bloques;
	protected int cant;
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Mapa con los Bloques ingresados.
	 * 
	 * @param bs Bloques para el nuevo Mapa.
	 */
	public Mapa (Bloque[] bs)
	{
		bloques = bs;
		cant = bloques.length;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el Bloque i por el nuevo Bloque pasado por parámetro.
	 * 
	 * @param bloque Nuevo Bloque para la posición i.
	 * @exception BoundaryViolationException Si la posición ingresada no pertenece al Mapa.
	 */
	public void setBloque (int i, Bloque bloque) throws BoundaryViolationException
	{
		verificarPosicion(i);
		bloques[i] = bloque;
	}
	
	/**
	 * Verifica si la posicion i es correcta y pertenece al Mapa.
	 * 
	 * @param i Posición a verificar.
	 * @exception BoundaryViolationException Si la posición ingresada no pertenece al Mapa.
	 */
	private void verificarPosicion (int i) throws BoundaryViolationException
	{
		if ((i < 0) || (i >= cantBloques()))
			throw new BoundaryViolationException ("No existe Bloque " + i + " en el Mapa.");
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la cantidad de Bloques del Mapa.
	 * 
	 * @return Cantidad de Bloques del Mapa.
	 */
	public int cantBloques ()
	{
		return cant;
	}
	
	/**
	 * Devuelve el Bloque i del Mapa.
	 * 
	 * @param i Posición del Bloque a devolver.
	 * @return Bloque en la posición i.
	 * @exception BoundaryViolationException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloque (int i) throws BoundaryViolationException
	{
		verificarPosicion(i);
		return bloques[i];
	}
	
	/**
	 * Devuelve el Bloque anterior al Bloque i del Mapa.
	 * 
	 * @param i Posición del Bloque siguiente al Bloque a devolver.
	 * @return Bloque en la posición i-1.
	 * @exception BoundaryViolationException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueAnterior (int i) throws BoundaryViolationException
	{
		verificarPosicion(i);
		if (i == 0)
			throw new BoundaryViolationException ("No existe Bloque anterior al primero.");
		return bloques[i-1];
	}
	
	/**
	 * Devuelve el Bloque siguiente al Bloque i del Mapa.
	 * 
	 * @param i Posición del Bloque anterior al Bloque a devolver.
	 * @return Bloque en la posición i+1.
	 * @exception BoundaryViolationException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueSiguiente (int i) throws BoundaryViolationException
	{
		verificarPosicion(i);
		if (i == cantBloques()-1)
			throw new BoundaryViolationException ("No existe Bloque siguiente al último.");
		return bloques[i+1];
	}
	
}