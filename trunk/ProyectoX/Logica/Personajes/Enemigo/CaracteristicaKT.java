package ProyectoX.Logica.Personajes.Enemigo;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Excepciones.ColisionException;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Mario;

public abstract class CaracteristicaKT 
{
	//Atributos de Clase
	//Numeros de los Sprites.
	protected static int quieto = 0;
	protected static int movimiento = 1;
	
	//Atributos de Instancia
	protected KoopaTroopa koopa;
	
	/*CONSTRUCTORES*/
	
	/**
	 * 	Crea una CaracteristicaKT para KoopaTropa, cada CaracteristicaKT tiene sus propios nombres de sprites.
	 *  Queda sin vincular con un KoopaTropa.
	 */
	protected CaracteristicaKT ()
	{
		
	}
	
	/**
	 * Crea una CaracteristicaKT para KoopaTropa, cada Caracteristica tiene sus propios nombres de sprites.
	 * Setea al KoopaTropa vinculado con kt.
	 * @param kt es el KoopaTropa vinculado a la CaracteristicaKT. 
	 */	
	protected CaracteristicaKT (KoopaTroopa kt)
	{
		koopa = kt;
		koopa.getSpriteManager().cargarSprites(this.getNombresSprites());
	}
	
	/*METODOS ABSTRACTOS*/
	
	/**
	 * Realiza la acción de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a izquierda.
	 */
	public abstract void moverseAizquierda () throws AccionActorException;
	
	/**
	 * Realiza la acción de moverse hacia la derecha.
	 * 
	 * @throws AccionActorException Si se produce algún error al moverse a derecha.
	 */
	public abstract void moverseAderecha () throws AccionActorException;
	
	/**
	 * Efecto provocado por el Personaje Seleccionable pj que colisiona con el Actor actual.
	 * 
	 * Muere el KoopaTroopa actual.
	 * 
	 * @param pj Actor que colisiona al Actor actual.
	 * @throws NullPointerException Si pj es null.
	 * @throws ColisionException Si se produce algún error en la colisión.
	 */
	public abstract void colisionarPj (final Mario mario) throws ColisionException, NullPointerException;
	
	/**
	 * Retorna los nombres de sprites correspondientes a la CaracteristicaKT.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public abstract String[] getNombresSprites();
	
	/*COMANDOS*/
	
	/**
	 * Setea al KoopaTroopa al que la CaracteristicaKT representa con kt.
	 * @param kt es el KoopaTroopa con el que se setea a la CaracteristicaKT.
	 */
	public void setKoopaTroopa (KoopaTroopa kt)
	{
		koopa = kt;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Retorna el KoopaTroopa asociado a la CaracteristicaKT.
	 * @return el KoopaTroopa asociado a la CaracteristicaKT.
	 */
	public KoopaTroopa getKoopaTroopa()
	{
		return koopa;
	}
	
	public int spriteQuieto()
	{
		return quieto;
	}
	
	public int spriteMovimiento()
	{
		return movimiento;
	}
	
	
}
