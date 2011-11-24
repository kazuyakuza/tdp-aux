package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.PosicionIncorrectaException;

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
	protected Bloque[][] ABB; //Arreglo Bidimensional de Bloques
	protected Nivel nivel; //Nivel al que pertenece el Mapa.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Mapa con los Bloques ingresados.
	 * 
	 * @param n Nivel al que pertenece el Mapa.
	 * @param cantFilas Cantidad de Filas de Bloques del Mapa.
	 * @param cantColumnas Cantidad de Columnas de Bloques del Mapa.
	 * @throws NullPointerException Si bs es null.
	 */
	public Mapa (Nivel n, int cantFilas, int cantColumnas) throws NullPointerException
	{
		if (n == null)
			throw new NullPointerException ("Mapa." + "\n" +
                                            "Imposible crear un Mapa con Nivel null.");
		if ((cantFilas <= 0) || (cantColumnas == 0))
			throw new NullPointerException ("Mapa." + "\n" +
                                            "Imposible crear un Mapa con cantFilas " + cantFilas + "y cantColumnas " + cantColumnas + ".");
		
		nivel = n;
		ABB = new Bloque[cantFilas][cantColumnas];
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el Bloque de posición (f,c) por el nuevo Bloque pasado por parámetro.
	 * 
	 * @param f Fila del Bloque a cambiar.
	 * @param c Columna del Bloque a cambiar.
	 * @param bloque Nuevo Bloque para la posición (f,c).
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 * @throws NullPointerException Si bloque es null.
	 */
	public void setBloque (int f, int c, Bloque bloque) throws PosicionIncorrectaException, NullPointerException
	{
		verificarPosicion(f, c);
		if (bloque == null)
			throw new NullPointerException ("Mapa.setBloque()" + "\n" +
            								"Imposible setear un Bloque null.");
		
		ABB[f][c] = bloque;
	}
	
	/**
	 * Verifica si la posicion (f,c) es correcta y pertenece al Mapa.
	 * 
	 * @param f Fila de la Posición a verificar. (0 <= f < catFilas())
	 * @param c Columna de la Posición a verificar. (0 <= c < catColumnas())
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	private void verificarPosicion (int f, int c) throws PosicionIncorrectaException
	{
		if ((f < 0) || (f >= cantFilas()))
			throw new PosicionIncorrectaException ("Mapa.verificarPosicion()" + "\n" +
					                              "No existe posición (" + f + "," + c + ") en el Mapa.");
		if ((c < 0) || (c >= cantColumnas()))
			throw new PosicionIncorrectaException ("Mapa.verificarPosicion()" + "\n" +
					                              "No existe posición (" + f + "," + c + ") en el Mapa.");
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Nivel al que pertenece el Mapa.
	 * 
	 * @return Nivel al que pertenece el Mapa.
	 */
	public Nivel getNivel ()
	{
		return nivel;
	}
	
	/**
	 * Devuelve la cantidad de Filas del ABB.
	 * 
	 * @return Cantidad de Filas del ABB.
	 */
	public int cantFilas ()
	{
		return ABB.length;
	}
	
	/**
	 * Devuelve la cantidad de Columnas del ABB.
	 * 
	 * @return Cantidad de Columnas del ABB.
	 */
	public int cantColumnas ()
	{
		return ABB[0].length;
	}
	
	/**
	 * Verifica si existe un Bloque en la posición (f,c), y devuelve el resultado.
	 * 
	 * @param f Fila de la Posición a verificar. (0 <= f < catFilas())
	 * @param c Columna de la Posición a verificar. (0 <= c < catColumnas())
	 * @return True:  hay un Bloque en la posición (f,c).
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloque (int f, int c) throws PosicionIncorrectaException
	{
		verificarPosicion(f, c);
		
		return (ABB[f][c] != null);
	}
	
	/**
	 * Verifica si existe un Bloque en la posición pasada por parámetro, y devuelve el resultado.
	 * 
	 * @param posición Arreglo de dos componentes donde: posición[0] es la fila de la posición a verificar.
	 *                                                   posición[1] es la columna de la posición a verificar.
	 * @return True:  hay un Bloque en la posición pasada por parámetro.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloque (int[] posicion) throws PosicionIncorrectaException
	{
		return hayBloque (posicion[0], posicion[1]);
	}
	
	/**
	 * Verifica si existe un Bloque a izquierda de la posición pasada por parámetro, y devuelve el resultado.
	 * 
	 * @param posición Arreglo de dos componentes donde: posición[0] es la fila de la posición a verificar.
	 *                                                   posición[1] es la columna de la posición a verificar.
	 * @return True:  hay un Bloque a izquierda de la posición pasada por parámetro.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloqueAnterior (int[] posicion) throws PosicionIncorrectaException
	{
		return hayBloque (posicion[0], posicion[1] - 1);
	}
	
	/**
	 * Verifica si existe un Bloque siguiente a la posición pasada por parámetro, y devuelve el resultado.
	 * 
	 * @param posición Arreglo de dos componentes donde: posición[0] es la fila de la posición a verificar.
	 *                                                   posición[1] es la columna de la posición a verificar.
	 * @return True:  hay un Bloque siguiente a la posición pasada por parámetro.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloqueSiguiente (int[] posicion) throws PosicionIncorrectaException
	{
		return hayBloque (posicion[0], posicion[1] + 1);
	}
	
	/**
	 * Verifica si existe un Bloque superior a la posición pasada por parámetro, y devuelve el resultado.
	 * 
	 * @param posición Arreglo de dos componentes donde: posición[0] es la fila de la posición a verificar.
	 *                                                   posición[1] es la columna de la posición a verificar.
	 * @return True:  hay un Bloque superior a la posición pasada por parámetro.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloqueSuperior (int[] posicion) throws PosicionIncorrectaException
	{
		return hayBloque (posicion[0] - 1, posicion[1]);
	}
	
	/**
	 * Verifica si existe un Bloque inferior a la posición pasada por parámetro, y devuelve el resultado.
	 * 
	 * @param posición Arreglo de dos componentes donde: posición[0] es la fila de la posición a verificar.
	 *                                                   posición[1] es la columna de la posición a verificar.
	 * @return True:  hay un Bloque inferior a la posición pasada por parámetro.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public boolean hayBloqueInferior (int[] posicion) throws PosicionIncorrectaException
	{
		return hayBloque (posicion[0] + 1, posicion[1]);
	}
	
	/**
	 * Devuelve el Bloque de posición (f,c) del Mapa.
	 * 
	 * @param f Fila del Bloque a devolver. (0 <= f < catFilas())
	 * @param c Columna del Bloque a devolver. (0 <= c < catColumnas())
	 * @return Bloque en la posición (f,c).
	 * @throws NullPointerException Si no existe un Bloque en la posición (f,c) solicitada.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloque (int f, int c) throws NullPointerException, PosicionIncorrectaException
	{
		verificarPosicion(f, c);
		
		if (ABB[f][c] == null)
			throw new NullPointerException ("Mapa.getBloque()" + "\n" +
					                        "No existe un Bloque en la posición (" + f + "," + c + ") solicitada.");
		
		return ABB[f][c];
	}
	
	/**
	 * Devuelve el Bloque de posición (f,c) del Mapa.
	 * 
	 * @param posicion Arreglo de dos componente donde: posicion[0] es la Fila del Bloque a devolver.
	 *   												posicion[1] es la Columna del Bloque a devolver.
	 * @return Bloque en la posición pasada por parámetro.
	 * @throws NullPointerException Si no existe un Bloque en la posición (f,c) solicitada.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloque (int[] posicion) throws NullPointerException, PosicionIncorrectaException
	{
		return getBloque (posicion[0], posicion[1]);
	}
	
	/**
	 * Devuelve el Bloque a izquierda de la posición pasada por parámetro.
	 * 
	 * @param posicion Arreglo de dos componentes donde posicion[0] es la Fila del Bloque siguiente al Bloque a devolver.
	 *                                                  posicion[1] es la Columna del Bloque siguiente al Bloque a devolver.
	 * @return Bloque a izquierda de la posición pasada por parámetro.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueAnterior (int[] posicion) throws PosicionIncorrectaException
	{
		return getBloque (posicion[0], posicion[1] - 1);
	}
	
	/**
	 * Devuelve el Bloque a derecha de la posición pasada por parámetro.
	 * 
	 * @param posicion Arreglo de dos componentes donde posicion[0] es la Fila del Bloque anterior al Bloque a devolver.
	 *                                                  posicion[1] es la Columna del Bloque anterior al Bloque a devolver.
	 * @return Bloque a derecha de la posición pasada por parámetro.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueSiguiente (int[] posicion) throws PosicionIncorrectaException
	{
		return getBloque (posicion[0], posicion[1] + 1);
	}
	
	/**
	 * Devuelve el Bloque por encima de la posición pasada por parámetro.
	 * 
	 * @param posicion Arreglo de dos componentes donde posicion[0] es la Fila del Bloque inferior al Bloque a devolver.
	 *                                                  posicion[1] es la Columna del Bloque inferior al Bloque a devolver.
	 * @return Bloque por encima de la posición pasada por parámetro.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueSuperior (int[] posicion) throws PosicionIncorrectaException
	{
		return getBloque (posicion[0] - 1, posicion[1]);
	}
	
	/**
	 * Devuelve el Bloque por debajo de la posición pasada por parámetro.
	 * 
	 * @param posicion Arreglo de dos componentes donde posicion[0] es la Fila del Bloque superior al Bloque a devolver.
	 *                                                  posicion[1] es la Columna del Bloque superior al Bloque a devolver.
	 * @return Bloque por debajo de la posición pasada por parámetro.
	 * @throws PosicionIncorrectaException Si la posición ingresada no pertenece al Mapa.
	 */
	public Bloque getBloqueInferior (int[] posicion) throws PosicionIncorrectaException
	{
		return getBloque (posicion[0] + 1, posicion[1]);
	}
	
}