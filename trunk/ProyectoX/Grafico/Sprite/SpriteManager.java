package ProyectoX.Grafico.Sprite;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import ProyectoX.Excepciones.CargaRecursoException;
import ProyectoX.Excepciones.PosicionIncorrectaException;
import ProyectoX.Excepciones.SpriteException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Worker;

/**
 * Controla los Sprites cargados.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class SpriteManager implements ImageObserver
{
	
	//Variables de Instancia
	private UpNeeder upNeeder; //UpNeeder del SpriteManager para completar operaciones.
	private CargadorSprite cargadorSprite;
	private BufferedImage spriteActual;
	private BufferedImage[] sprites; //Guarda las imagenes posibles para este sprite, donde:
	                                 //[0] es la imagen intermedia. "mirando hacia fuera de la pantalla"
	                                 //[1] es la imagen hacia la derecha. "mirando hacia la derecha"
	                                 //para cargar la imagen a izquierda [-1], se invierte la imagen en [1]. "mirando hacia la izquierda"
	private boolean invertido; //True:  el sprite actual esta invertido deacuerdo a su imagen original.
	                           //False: caso contrario.
	private boolean eliminar; //True:  el sprite debe ser eliminado.
	                          //False: caso contrario.
	private double posX, posY; //Posición Actual del Sprite en el Escenario.
	                           //Si posX=-1 y posY=-1, entonces no se ha asignado una posición aún.
	private double difX, difY; //Diferencia entre la posición actual y la posición a la que debe moverse.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Carga todos los sprites de acuerdo a los nombre de los archivos de las imagenes pasado por parámetro,
	 * y establece como spriteActual al primer sprite [0].
	 * Inicializa la posición en (-1,-1)
	 * 
	 * @param nombreSprites Nombres de los archivos de las imagenes para este sprite.
	 * @param cargadorSprite Cargador de Sprite para cargar la imagen.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public SpriteManager (String[] nombresSprites, CargadorSprite cargadorSprite) throws CargaRecursoException
	{
		upNeeder = new UpNeeder(0);
		this.cargadorSprite = cargadorSprite;
		cargarSprites(nombresSprites);
		posX = posY = -1;
		difX = difY = 0;
		invertido = false;
		eliminar = false;
	}
	
	/*COMANDOS*/
	
	/**
	 *  Carga todos los sprites de acuerdo a los nombre de los archivos de las imagenes pasado por parámetro,
	 * y establece como spriteActual al primer sprite [0].
	 * 
	 * @param nombreSprites Nombres de los archivos de las imagenes para este sprite.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public void cargarSprites (String[] nombresSprites) throws CargaRecursoException
	{
		sprites = new BufferedImage[nombresSprites.length];
		for (int i=0; i<nombresSprites.length; i++)
			try
			{
				sprites[i] = cargadorSprite.obtenerSprite(nombresSprites[i], this);
			}
			catch (CargaRecursoException exception)
			{
				throw new CargaRecursoException ("SpriteManager." + "\n" +
						                         "Error al cargar el sprite de nombre " + nombresSprites[i] + "." + "\n" +
						                         "Detalles del Eror:" + "\n" +
						                         exception.getMessage());
			}
		spriteActual = sprites[0];
	}
	
	/**
	 * Cambia el sprite actual por el indicado en cambio.
	 * Cambio < 0 "mirando hacia la izquierda"
	 * Cambio = 0 "mirando hacia fuera de la pantalla"
	 * Cambio > 0 "mirando hacia la derecha"
	 * 
	 * @param cambio Numero del sprite a cambiar.
	 * @throws SpriteException Si se ingresa un valor erróneo de cambio de sprite.
	 */
	public void cambiarSprite (int cambio) throws SpriteException
	{
		if (Math.abs(cambio) >= sprites.length)
			throw new SpriteException("SpriteManager.cambiarSprite()" + "\n" +
					                  "Numero de Sprite a cargar incorrecto." + "\n" +
					                  "Ingresado: " + cambio + " | Máximo: -" + sprites.length + "|" + sprites.length + "+");
		
		if (invertido)
		{
			spriteActual = invertir(spriteActual);
			invertido = false;
		}
		
		spriteActual = sprites[Math.abs(cambio)];
		
		if (cambio < 0)
		{
			spriteActual = invertir(spriteActual);
			invertido = true;
		}
	}
	
	/**
	 * Invierte/refleja en el eje x la imagen image, y le devuelve.
	 * 
	 * @param image Imagen a invertir.
	 * @return Imagen invertida/reflejada.
	 * @throws SpriteException Si se produce un error al invertir la imagen.
	 */
	private BufferedImage invertir (BufferedImage image) throws SpriteException
	{
		int w = image.getWidth();
		int h = image.getHeight();
		GraphicsConfiguration gc = image.createGraphics().getDeviceConfiguration();
		BufferedImage imageInv = gc.createCompatibleImage(w, h, Transparency.BITMASK);
		Graphics g = imageInv.getGraphics();
        g.drawImage(image, 0, 0, w, h, w, 0, 0, h, this);
		g.dispose();
		return imageInv;
	}
	
	/**
	 * Actualiza la posición del sprite a la posición pasada por parámetro.
	 * 
	 * @param posicion Arreglo de dos componenete con posicion[0] = Nueva posición X, y posicion[1] = Nueva posición Y.
	 * @throws PosicionIncorrectaException Si se ingresa una posición incorrecta.
	 */
	public void actualizar (int[] posicion) throws PosicionIncorrectaException
	{
		actualizar(posicion[0], posicion[1]);
	}
	
	/**
	 * Actualiza la posición del sprite a la posición (X,Y).
	 * 
	 * @param X Nueva posición X.
	 * @param Y Nueva posición Y.
	 * @throws PosicionIncorrectaException Si se ingresa una posición incorrecta.
	 */
	public void actualizar (final int X, final int Y) throws PosicionIncorrectaException
	{
		if ((X < 0) || (Y < 0))
			throw new PosicionIncorrectaException ("Posición ingresada incorrecta." + "\n"
					                             + "No existe posición (" + X + "," + Y +").");
		
		if ((posX == -1) && (posY == -1))
		{//Posición Inicial.
			posX = X;
			posY = Y;
		}
		else
		{//Actualiza a una posición intermedia entre la actual y a la (X,Y).
			if ((posX % (int) posX) == 0.0)
			{
				if ((int) posX != X)
					if ((int) posX < X)
					{
						posX += 0.5;
						difX += 0.5;
					}
					else
					{
						posX -= 0.5;
						difX -= 0.5;
					}
			}
			if ((posY % (int) posY) == 0.0)
			{
				if ((int) posY != Y)
					if ((int) posY < Y)
					{
						posY += 0.5;
						difY += 0.5;
					}
					else
					{
						posY -= 0.5;
						difY -= 0.5;
					}
			}
			
			//Agregar Worker al UpNeeder para terminar actualización.
			if (! upNeeder.hayWorkerPrioridad(0))
				upNeeder.addWorker(0,
						new Worker ()
						{
							public void work() throws Exception
							{
								actualizacion();
							}
						});
		}
	}
	
	/**
	 * Actualiza la posición del sprite.
	 * 
	 * @throws PosicionIncorrectaException Si se ingresa una posición incorrecta.
	 */
	private void actualizacion () throws PosicionIncorrectaException
	{
		posX += difX;
		posY += difY;
		difX = 0;
		difY = 0;
	}
	
	/**
	 * Indica a este SpriteManager como "debe ser eliminado".
	 */
	public void setEliminar ()
	{
		eliminar = true;
	}
	
	/**
	 * Setea los nombres de sprites del SpriteManager.
	 * @param nombresSprites los nuevos nombres para los sprites del manejador.
	 */
	public void setSprites (String[] nombresSprites)
	{
		sprites = new BufferedImage[nombresSprites.length];
		CargadorSprite cargadorSprite = new CargadorSprite();
		for (int i=0; i<nombresSprites.length; i++)
			try
			{
				sprites[i] = cargadorSprite.obtenerSprite(nombresSprites[i], this);
			}
			catch (CargaRecursoException exception)
			{
				throw new CargaRecursoException (exception.getMessage() + "\n" +
						                         "Error al cargar el sprite de nombre " + nombresSprites[i] + ".");
			}
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el UpNeeder del SpriteManager.
	 * 
	 * @return UpNeeder del SpriteManager.
	 */
	public UpNeeder getUpNeeder ()
	{
		return upNeeder;
	}
	
	/**
	 * Devuelve el Sprite (imagen actual).
	 * 
	 * @return Sprite.
	 */
	public BufferedImage getSpriteActual ()
	{
		return spriteActual;
	}
	
	/**
	 * Devuelve la posición actual del Sprite.
	 * 
	 * @return (x,y):   Posición actual del Sprite.
	 *         (-1,-1): Cuando el Sprite no tiene una posición asignada.
	 * @throws PosicionIncorrectaException Si no se ha asignado una posición.
	 */
	public double[] posicion () throws PosicionIncorrectaException
	{
		if ((posX == -1) && (posY == -1))
			throw new PosicionIncorrectaException ("No se ha asignado posición." + "\n" +
					                               "Detalles del Error:" + "\n" +
					                               "Error al llamar SpriteManager.posicion(), donde posX y posY del SpriteManager son iguales a -1.");
		return new double[] {posX, posY};
	}
	
	/**
	 * Indica si el SpriteManager debe ser eliminado.
	 * 
	 * @return True:  el SpriteManger debe ser eliminado.
	 *         False: caso contrario.
	 */
	public boolean isEliminar ()
	{
		return eliminar;
	}
	
	/*Métodos en Ejecución*/
	
	/**
	 * Método de ImageObserver para Actualización del Sprite (imagen actual).
	 */
	public boolean imageUpdate (Image img, int infoflags,int x, int y, int w, int h)
	{
		return (infoflags & (ALLBITS|ABORT)) == 0;
    }

}