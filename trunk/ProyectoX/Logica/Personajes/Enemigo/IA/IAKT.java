package ProyectoX.Logica.Personajes.Enemigo.IA;

import ProyectoX.Excepciones.IAexception;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.Personajes.Enemigo.Goomba;
import ProyectoX.Logica.Personajes.Enemigo.KTCaparazon;
import ProyectoX.Logica.Personajes.Enemigo.KoopaTroopa;

/**
 * Inteligancia Arificial que controla a los Actores Koopa Troopa del Juego.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class IAKT extends IA
{
	
	//Atributos de Instancia
	private Celda celdaActual; //Ultima Celda en la que está el KoopaTroopa marioneta.
	private boolean normal; //True = el KoopaTroopa está en estado KTNormal.
	                        //False = el KoopaTroopa está en estado KTCaparazon.
	private boolean izq; //True = el KoopaTroopa se está moviendo hacia la izquierda.
	                     //False = el KoopaTroopa se está moviendo hacia la derecha.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea una IA con su respectiva marioneta.
	 * 
	 * @param kt KoopaTroopa marioneta de la IA a crear.
	 */
	public IAKT (KoopaTroopa kt)
	{
		super(kt);
		setActuar();
		celdaActual = cast().getCeldaActual();
		izq = true;
		normal = true;
	}
	
	/*COMANDOS*/
	
	/**
	 * La marionata indica a la IA q cambió de estado..
	 * 
	 * @throws NullPointerException Si kt es null.
	 * @throws IAexception Si se ingresa un KoopaTroopa que no es la marioneta de la IA actual.
	 */
	public void cambieDeEstado (KoopaTroopa kt) throws NullPointerException, IAexception
	{
		if (kt == null)
			throw new NullPointerException ("IAKT.meMori()" + "\n" +
                                            "Imposible verificar muerte. El KoopaTroopa ingresado es null.");
		if (kt != cast())
			throw new IAexception ("IAKT.meMori()" + "\n" +
					               "Imposible verificar muerte. El KoopaTroopa ingresado no es la marioneta de la IA actual.");
		normal = !normal;
	}
	
	/**
	 * La marionata indica a la IA q se murió.
	 * 
	 * @throws NullPointerException Si kt es null.
	 * @throws IAexception Si se ingresa un KoopaTroopa que no es la marioneta de la IA actual.
	 */
	public void meMori (KoopaTroopa kt) throws NullPointerException, IAexception
	{
		if (kt == null)
			throw new NullPointerException ("IAKT.meMori()" + "\n" +
                                            "Imposible verificar muerte. El KoopaTroopa ingresado es null.");
		if (kt != cast())
			throw new IAexception ("IAKT.meMori()" + "\n" +
					               "Imposible verificar muerte. El KoopaTroopa ingresado no es la marioneta de la IA actual.");
		actuar = -1;
	}
	
	/**
	 * Actualiza el valor de actuar.
	 * 
	 * @param act Valor a actualizar.
	 */
	protected void setActuar ()
	{
		actuar = 4;
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
	private KoopaTroopa cast ()
	{
		return (KoopaTroopa) marioneta;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * La marioneta realiza la acción de moverse.
	 */
	protected void actuacion () throws IAexception
	{
		if (normal)
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
		else
			cast().recuperarse();
	}

}