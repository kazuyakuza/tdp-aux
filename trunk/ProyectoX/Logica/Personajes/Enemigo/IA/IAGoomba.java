package ProyectoX.Logica.Personajes.Enemigo.IA;

import ProyectoX.Excepciones.IAexception;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;

/**
 * Inteligancia Arificial que controla a los Actores Goomba del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class IAGoomba extends IA
{
	
	//Atributos de Instancia
	private Celda celdaActual; //Ultima Celda en la que está el Goomba marioneta.
	private boolean izq; //True = el Goomba se está moviendo hacia la izquierda.
	                     //False = el Goomba se está moviendo hacia la derecha.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea una IA con su respectiva marioneta.
	 * 
	 * @param g Goomba marioneta de la IA a crear.
	 */
	public IAGoomba (Goomba g)
	{
		super(g);
		setActuar();
		celdaActual = cast().getCeldaActual();
		izq = true;
	}
	
	/*COMANDOS*/
	
	/**
	 * La marionata indica a la IA q se murió.
	 * 
	 * @throws NullPointerException Si g es null.
	 * @throws IAexception Si se ingresa un Goomba que no es la marioneta de la IA actual.
	 */
	public void meMori (Goomba g) throws NullPointerException, IAexception
	{
		if (g == null)
			throw new NullPointerException ("IAGoomba.meMori()" + "\n" +
                                            "Imposible verificar muerte. El Goomba ingresado es null.");
		if (g != cast())
			throw new IAexception ("IAGoomba.meMori()" + "\n" +
					                        "Imposible verificar muerte. El Goomba ingresado no es la marioneta de la IA actual.");
		actuar = -1;
	}
	
	/**
	 * Actualiza el valor de actuar.
	 * 
	 * @param act Valor a actualizar.
	 */
	protected void setActuar ()
	{
		actuar = 3;
	}
	
	/**
	 * Limpia la IA actual.
	 */
	public void limpiar ()
	{
		celdaActual = null;
		super.limpiar();
	}
	
	/*CONSULTAS*/
	
	/**
	 * Realiza el cast necesario para obtener los métodos de Goomba.
	 */
	private Goomba cast ()
	{
		return (Goomba) marioneta;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * La marioneta realiza la acción de moverse.
	 */
	protected void actuacion () throws IAexception
	{
		if (izq)
			marioneta.izquierda();
		else
			marioneta.derecha();
		if (celdaActual == cast().getCeldaActual())
			izq = !izq;//Cambio dirección.
		else
			celdaActual = cast().getCeldaActual();
	}

}