package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.BoundaryViolationException;
import ProyectoX.Excepciones.PosicionIncorrectaException;

/**
 * Representa una parte de la totalidad del Mapa del juego.
 * 
 * El Bloque est� representado por un Arreglo Bidimensional de Celdas o ABC.
 * 
 * Para el cliente, la posici�n en el extremo superior izquierdo del ABC es (0,0).
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Bloque
{
	
	//Variables de Instancia
	protected Celda[][] ABC; //Arreglo Bidimensional de Celdas
	protected Mapa mapa; //Mapa al que pertenece el Bloque.
	protected int fila, columna; //Posici�n (fila,columna) del Bloque en el Mapa.
	protected int nivelPiso; //Representa el nivel del Piso en el Bloque.
	                         //Si el Bloque tiene piso, entonces 0 <= nivelPiso < this.getFilas()
	                         //Si nivelPiso = -1, entonces el Bloque no tiene piso.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un Bloque de Filas = cantFilas y Columnas = cantColumnas.
	 * Inicia con posici�n del nivel del Piso con cantFilas-2.
	 * Crea una Celda para cada posici�n del ABC.
	 * 
	 * @param m Mapa al que pertenece el Bloque a crear.
	 * @param f Fila de la Posici�n del Bloque a crear en el Mapa.
	 * @param c Columna de la Posici�n del Bloque a crear en el Mapa.
	 * @param cantFilas Cantidad de filas del nuevo Bloque.
	 * @param cantColumnas Cantidad de columnas del nuevo Bloque.
	 * @throws NullPointerException Si el Mapa m es null.
	 * @throws BoundaryViolationException Si se ingrensa valores incorrectos del tama�o del Bloque.
	 * @throws PosicionIncorrectaException Si la posici�n (f,c) pasada por par�metro es incorrecta. Si hay un error al ingresar una Celda a una determinada posici�n.
	 */
	public Bloque (Mapa m, int f, int c, int cantFilas, int cantColumnas) throws NullPointerException, BoundaryViolationException, PosicionIncorrectaException
	{
		if (m == null)
			throw new NullPointerException ("Bloque." + "\n" +
                                            "Imposible crear Bloque. El Mapa pasado por par�metro es null.");
		if ((f < 0) || (c < 0))
			throw new PosicionIncorrectaException ("Bloque." + "\n" +
					                              "Imposible crear un Bloque en la posici�n (" + f + "," + c + ") del Mapa.");
		if ((cantFilas < 0) || (cantColumnas < 0))
			throw new BoundaryViolationException ("Bloque." + "\n" +
					                              "Imposible armar un Bloque de " + cantFilas + " filas X " + cantColumnas + " calumnas.");
		
		mapa = m;
		fila = f;
		columna = c;
		ABC = new Celda[cantFilas][cantColumnas];
		nivelPiso = cantFilas-2;
		crearCeldas();
	}
	
	/*COMANDOS*/
	
	/**
	 * Crea y asigna todas las Celdas al ABC.
	 * 
	 * @throws PosicionIncorrectaException Si hay un error al ingresar una Celda a una determinada posici�n.
	 */
	private void crearCeldas () throws PosicionIncorrectaException
	{
		for (int i=0; i < getFilas(); i++)
			for (int j=0; j < getColumnas(); j++)
				try
				{
					ABC[i][j] = new Celda (esPiso(i,j), i, j, this);
				}
				catch (Exception e)
				{
					throw new PosicionIncorrectaException ("Bloque.crearCeldas()" + "\n" +
                                                           "Error al crear/asignar la Celda de posici�n (" + i + "," + j + ")." + "\n" +
							                               "Detalles del error:" + "\n" +
							                               e.getMessage());
				}
	}
	
	/**
	 * Cambia el nivel del piso del bloque.
	 * 
	 * Si el Bloque tiene piso, entonces 0 <= nivelPiso < this.getFilas()
	 * Si nivelPiso = -1, entonces el Bloque no tiene piso.
	 * 
	 * @param nivel Nuevo nivel del piso.
	 * @throws BoundaryViolationException Si el nivel ingresado no existe en este Bloque.
	 */
	public void setNivelPiso (int nivel) throws BoundaryViolationException
	{
		if ((nivel < -1) || (nivel >= getFilas()))
			throw new BoundaryViolationException ("Bloque.setNivelPiso()" + "\n" +
                                                  "Imposible setear el piso del Bloque en " + nivel + ".");
		
		if (nivelPiso != nivel)
		{
			setFilaOcupada(nivelPiso, false);//Actualiza las Celdas poni�ndolas como no piso.
			nivelPiso = nivel;
			if (nivelPiso != -1)
				setFilaOcupada(nivelPiso, true);//Atualiza las Celdas poni�ndolas como piso.
		}
	}
	
	/**
	 * Pone a todas las Celdas de la Fila indicada como totalmenteOcupada = ocupada.
	 * 
	 * @param fila Fila de las Celdas a cambiar.
	 * @param ocupada Valor de totalmenteOcupada de las Celdas a cambiar.
	 * @throws BoundaryViolationException Si se ingresa una Fila que no existe en el Bloque. 
	 */
	public void setFilaOcupada (int fila, boolean ocupada) throws BoundaryViolationException
	{
		if ((fila < 0) || (fila >= getFilas()))
			throw new BoundaryViolationException ("Bloque.setFilaOcupada()" + "\n" +
                                                  "No exite fila en el Bloque igual a " + fila + ".");
		
		for (int j=0; j < getColumnas(); j++)
			ABC[fila][j].totalmenteOcupada = ocupada;
	}
	
	/**
	 * Pone a todas las Celdas de la Columna indicada como totalmenteOcupada = ocupada.
	 * 
	 * @param columna Columna de las Celdas a cambiar.
	 * @param ocupada Valor de totalmenteOcupada de las Celdas a cambiar.
	 * @throws BoundaryViolationException Si se ingresa una Columna que no existe en el Bloque.
	 */
	public void setColumnaOcupada (int columna, boolean ocupada) throws BoundaryViolationException
	{
		if ((columna < 0) || (columna >= getColumnas()))
			throw new BoundaryViolationException ("Bloque.setColumnaOcupada()" + "\n" +
                                                  "No exite columna en el Bloque igual a " + columna + ".");
		for (int i=0; i < getFilas(); i++)
			ABC[i][columna].totalmenteOcupada = ocupada;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la Posici�n del Bloque en el Mapa.
	 * 
	 * @return Arreglo de dos componentes con la Posici�n del Bloque en el Mapa. Donde arreglo[0] es la fila de la posici�n, y arreglo[1] es la columna de la posici�n.
	 */
	public int[] getPosEnMapa ()
	{
		return new int[] {fila, columna};
	}
	
	/**
	 * Devuelve la cantidad de Filas del ABC.
	 * Pero para utilizar los m�todos de esta clase, la fila m�xima es getFilas()-1.
	 * 
	 * @return Cantidad de Filas del ABC.
	 */
	public int getFilas ()
	{
		return ABC.length;
	}
	
	/**
	 * Devuelve la cantidad de Columnas del ABC.
	 * Pero para utilizar los m�todos de esta clase, la columna m�xima es getColumnas()-1.
	 * 
	 * @return Cantidad de Columnas del ABC.
	 */
	public int getColumnas ()
	{
		return ABC[0].length;
	}
	
	/**
	 * Devuelve el nivel del piso del Bloque actual.
	 * 
	 * @return Nivel del piso del Bloque actual.
	 */
	public int getNivelPiso ()
	{
		return nivelPiso;
	}
	
	/**
	 * Verifica si existe una Celda en la posici�n (f, c).
	 * 
	 * @param f Fila de la posici�n.
	 * @param c Columna de la posici�n.
	 * @return True:  existe una Celda en la posici�n (f,c).
	 *         False: demas casos.
	 */
	public boolean existeCelda (int f, int c)
	{
		return ((f >= 0) && (f < getFilas()) && (c >= 0) && (c < getColumnas()));
	}
	
	/**
	 * Verica si la pisici�n (x,y) pertence al piso del Bloque y devuelve el resultado.
	 * 
	 * @param fila Fila de la posici�n que se quiere verificar.
	 * @param columna Columna de la posici�n que se quiere verificar.
	 * @return True:  (x,y) pertence al piso del Bloque.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public boolean esPiso (int fila, int columna) throws PosicionIncorrectaException
	{
		verificarPosicion (fila, columna);
		return (fila == nivelPiso);
	}
	
	/**
	 * Verica si la pisici�n (x,y) pertence a una posici�n por debajo del piso del Bloque y devuelve el resultado.
	 * 
	 * @param posicion Arreglo de dos componentes donde posicion[0] = Fila de la posici�n que se quiere verificar y posicion[1] = Columna de la posici�n que se quiere verificar.
	 * @return True:  la posici�n est� por debajo del piso del Bloque.
	 *         False: caso contrario.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public boolean debajoDelPiso (int[] posicion) throws PosicionIncorrectaException
	{
		verificarPosicion (posicion[0], posicion[1]);
		return (posicion[0] > nivelPiso);
	}
	
	/**
	 * Verifica si la Celda pasada por par�metro est� en el L�mite del Bloque.
	 * Es decir, la posicion de la Celda coincide con la primera o �ltima fila, o la primera o �ltima columna.
	 * 
	 * @param celda Celda a verificar.
	 * @return True:  la Celda est� en el l�mite.
	 *         False: caso contrario.
	 * @throws NullPointerException Si la celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public boolean esLimite (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		verificarCelda (celda);
		return ((celda.posFila == 0) ||
				(celda.posFila == (getFilas()-1)) ||
				(celda.posColumna == 0) ||
				(celda.posColumna == (getColumnas()-1)));
	}
	
	/**
	 * Verica si la pisici�n (x,y) es correcta y pertence al piso del Bloque.
	 * 
	 * @param fila Fila de la posici�n a verificar.
	 * @param columna Columna de la posici�n a verificar.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	private void verificarPosicion (int fila, int columna) throws PosicionIncorrectaException
	{
		if ((fila < 0) || (fila >= getFilas()) || (columna < 0) || (columna >= getColumnas()))
			throw new PosicionIncorrectaException ("Bloque.verificarPosicion()" + "\n" +
                                                   "No existe la posici�n (" + fila + "," + columna + ") en el Bloque.");
	}
	
	/**
	 * Verifica si la Celda es correcta.
	 * 
	 * @param celda Celda a verificar.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se ingresa una Celda de una posici�n imposible o incorrecta para este Bloque.
	 */
	private void verificarCelda (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (celda == null)
			throw new NullPointerException ("Bloque.verificarCelda()" + "\n" +
                                            "La celda es null.");
		try
		{
			verificarPosicion(celda.posFila, celda.posColumna);
		}
		catch (PosicionIncorrectaException e)
		{
			throw new PosicionIncorrectaException ("Bloque.verificarCelda()" + "\n" +
                                                   "La posici�n de la Celda ingresada (" + celda.posFila + "," + celda.posColumna +") no corresponde con el Bloque actual.");
		}
	}
	
	/**
	 * Verifica si hay una Celda a izquierda de la Celda pasada por par�metro.
	 * 
	 * Hay una Celda a izquierda si:
	 * - hay una Celda a izquierda en el ABC
	 * - o hay una Celda a izquierda en el Bloque a izquierda del Bloque Actual.
	 * 
	 * @param celda Celda a la que verificarle si existe una Celda que est� a su izquierda.
	 * @return True:  existe una Celda a izquierda de la Celda pasada por par�metro.
	 *         False: demas casos.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean hayAnterior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueAnterior(getPosEnMapa()))
			{
				Bloque bAnt = mapa.getBloqueAnterior(getPosEnMapa());
				return bAnt.existeCelda(celda.posFila, bAnt.getColumnas() - 1);
			}
		return existeCelda(celda.posFila, celda.posColumna - 1);	
	}
	
	/**
	 * Verifica si hay una Celda a derecha de la Celda pasada por par�metro.
	 * 
	 * Hay una Celda a derecha si:
	 * - hay una Celda a derecha en el ABC
	 * - o hay una Celda a derecha en el Bloque a derecha del Bloque Actual.
	 * 
	 * @param celda Celda a la que verificarle si existe una Celda que est� a su derecha.
	 * @return True:  existe una Celda a derecha de la Celda pasada por par�metro.
	 *         False: demas casos.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean haySiguiente (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueSiguiente(getPosEnMapa()))
			{
				Bloque bSig = mapa.getBloqueSiguiente(getPosEnMapa());
				return bSig.existeCelda(celda.posFila, 0);
			}
		return existeCelda(celda.posFila, celda.posColumna + 1);			
	}
	
	/**
	 * Verifica si hay una Celda por encima de la Celda pasada por par�metro.
	 * 
	 * Hay una Celda por encima si:
	 * - hay una Celda por encima en el ABC
	 * - o hay una Celda por encima en el Bloque por encima del Bloque Actual.
	 * 
	 * @param celda Celda a la que verificarle si existe una Celda que est� por encima.
	 * @return True:  existe una Celda por encima de la Celda pasada por par�metro.
	 *         False: demas casos.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean haySuperior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueSuperior(getPosEnMapa()))
			{
				Bloque bSup = mapa.getBloqueSuperior(getPosEnMapa());
				return bSup.existeCelda(bSup.getFilas() - 1, celda.posColumna);
			}
		return existeCelda(celda.posFila - 1, celda.posColumna);	
	}
	
	/**
	 * Verifica si hay una Celda por debajo de la Celda pasada por par�metro.
	 * 
	 * Hay una Celda por debajo si:
	 * - hay una Celda por debajo en el ABC
	 * - o hay una Celda por debajo en el Bloque por debajo del Bloque Actual.
	 * 
	 * @param celda Celda a la que verificarle si existe una Celda que est� por debajo.
	 * @return True:  existe una Celda por debajo de la Celda pasada por par�metro.
	 *         False: demas casos.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public boolean hayInferior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueInferior(getPosEnMapa()))
			{
				Bloque bInf = mapa.getBloqueInferior(getPosEnMapa());
				return bInf.existeCelda(0, celda.posColumna);
			}
		return existeCelda(celda.posFila + 1, celda.posColumna);	
	}
	
	/**
	 * Devuelve la Celda a izquierda en el ABC de la Celda pasada por par�metro.
	 * 
	 * @param celda Celda a la que buscarle la Celda que est� a su izquierda.
	 * @return Celda a la izquierda en el ABC de la Celda pasada por par�metro.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getAnterior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueAnterior(getPosEnMapa()))
			{
				Bloque bAnt = mapa.getBloqueAnterior(getPosEnMapa());
				return bAnt.getCelda(celda.posFila, bAnt.getColumnas() - 1);
			}
			else
				throw new PosicionIncorrectaException ("Bloque.getAnterior()" + "\n" +
						                               "La primera celda de una fila no tiene anterior." + "\n" +
						                               "Y no existe un Bloque anterior al actual.");
		return ABC[celda.posFila][celda.posColumna - 1];	
	}
	
	/**
	 * Devuelve la Celda a derecha en el ABC de la Celda pasada por par�metro.
	 * 
	 * @param celda Celda a la que buscarle la Celda que est� a su derecha.
	 * @return Celda a la derecha en el ABC de la Celda pasada por par�metro.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getSiguiente (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueSiguiente(getPosEnMapa()))
			{
				Bloque bSig = mapa.getBloqueSiguiente(getPosEnMapa());
				return bSig.getCelda(celda.posFila, 0);
			}
			else
				throw new PosicionIncorrectaException ("Bloque.getSiguiente()" + "\n" +
                                                       "La �ltima celda de una fila no tiene siguiente." + "\n" +
                                                       "Y no existe un Bloque siguiente al actual.");
		return ABC[celda.posFila][celda.posColumna + 1];			
	}
	
	/**
	 * Devuelve la Celda por encima en el ABC de la Celda pasada por par�metro.
	 * 
	 * @param celda Celda a la que buscarle la Celda que est� por encima.
	 * @return Celda por encima en el ABC de la Celda pasada por par�metro.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getSuperior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueSuperior(getPosEnMapa()))
			{
				Bloque bSup = mapa.getBloqueSuperior(getPosEnMapa());
				return bSup.getCelda(bSup.getFilas() - 1, celda.posColumna);
			}
			else
				throw new BoundaryViolationException ("Bloque.getSuperior()" + "\n" +
                                                      "La primera celda de una columna no tiene superior." + "\n" +
                                                      "Y no existe un Bloque superior al actual.");
		return ABC[celda.posFila - 1][celda.posColumna];	
	}
	
	/**
	 * Devuelve la Celda por debajo en el ABC de la Celda pasada por par�metro.
	 * 
	 * @param celda Celda a la que buscarle la Celda que est� por debajo.
	 * @return Celda por debajo en el ABC de la Celda pasada por par�metro.
	 * @throws NullPointerException Si la Celda pasada por par�metro es null.
	 * @throws PosicionIncorrectaException Si se pide una Celda en una posici�n imposible o incorrecta.
	 */
	public Celda getInferior (Celda celda) throws NullPointerException, PosicionIncorrectaException
	{
		if (esLimite(celda))
			if (mapa.hayBloqueInferior(getPosEnMapa()))
			{
				Bloque bInf = mapa.getBloqueInferior(getPosEnMapa());
				return bInf.getCelda(0, celda.posColumna);
			}
			else
				throw new PosicionIncorrectaException ("Bloque.getInferior()" + "\n" +
                                                       "La ultima celda de una columna no tiene inferior." + "\n" +
                                                       "Y no existe un Bloque inferior al actual.");
		return ABC[celda.posFila + 1][celda.posColumna];	
	}
	
	/**
	 * Devuelve la Celda en la posici�n (fila,columna) en el ABC.
	 * 
	 * @param celda Celda a la que buscarle la Celda que est� a su izquierda.
	 * @return Celda a la izquierda en el ABC de la Celda pasada por par�metro.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public Celda getCelda (int fila, int columna) throws PosicionIncorrectaException
	{
		verificarPosicion (fila, columna);
		return ABC[fila][columna];
	}

}