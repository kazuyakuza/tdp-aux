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
	private BufferedImage spriteActual;
	private BufferedImage[] sprites; //Guarda las imagenes posibles para este sprite, donde:
	                                 //[0] es la imagen intermedia. "mirando hacia fuera de la pantalla"
	                                 //[1] es la imagen hacia la derecha. "mirando hacia la derecha"
	                                 //para cargar la imagen a izquierda [-1], se invierte la imagen en [1]. "mirando hacia la izquierda"
	private boolean invertido; //True:  el sprite actual esta invertido deacuerdo a su imagen original.
	                           //False: caso contrario.
	private boolean eliminar; //True:  el sprite debe ser eliminado.
	                          //False: caso contrario.
	private double posX, posY; //Posici�n Actual del Sprite en el Escenario.
	                           //Si posX=-1 y posY=-1, entonces no se ha asignado una posici�n a�n.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Carga todos los sprites deacuerdo a los nombre de los archivos de las imagenes pasado por par�metro,
	 * y establece como spriteActual al primer sprite [0].
	 * Inicializa la posici�n en (-1,-1)
	 * 
	 * @param nombreSprites Nombres de los archivos de las imagenes para este sprite.
	 * @param cargadorSprite Cargador de Sprite para cargar la imagen.
	 * @exception CargaRecursoException Error al cargar el Sprite.
	 */
	public SpriteManager (String[] nombresSprites, CargadorSprite cargadorSprite) throws CargaRecursoException
	{
		sprites = new BufferedImage[nombresSprites.length];
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
		spriteActual = sprites[0];
		posX = posY = -1;
		invertido = false;
		eliminar = false;
	}
	
	/*COMANDOS*/
	
	/**
	 * Cambia el sprite actual por el indicado en cambio.
	 * Cambio < 0 "mirando hacia la izquierda"
	 * Cambio = 0 "mirando hacia fuera de la pantalla"
	 * Cambio > 0 "mirando hacia la derecha"
	 * 
	 * @param cambio Numero del sprite a cambiar.
	 * @exception SpriteException Si se ingresa un valor err�neo de cambio de sprite.
	 */
	public void cambiarSprite (int cambio) throws SpriteException
	{
		if (Math.abs(cambio) >= sprites.length)
			throw new SpriteException("Numero de Sprite a cargar incorrecto." + "\n"
					                + "Ingresado: " + cambio + " | M�ximo: -" + sprites.length + "|" + sprites.length + "+");
		
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
	 * @exception SpriteException Si se produce un error al invertir la imagen.
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
	 * Actualiza la posici�n del sprite a la posici�n pasada por par�metro.
	 * 
	 * @param posicion Arreglo de dos componenete con posicion[0] = Nueva posici�n X, y posicion[1] = Nueva posici�n Y.
	 * @exception PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public void actualizar (int[] posicion) throws PosicionIncorrectaException
	{
		actualizar(posicion[0], posicion[1]);
	}
	
	/**
	 * Actualiza la posici�n del sprite a la posici�n (X,Y).
	 * 
	 * @param X Nueva posici�n X.
	 * @param Y Nueva posici�n Y.
	 * @exception PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	public void actualizar (int X, int Y) throws PosicionIncorrectaException
	{
		if ((X < 0) || (Y < 0))
			throw new PosicionIncorrectaException ("Posici�n ingresada incorrecta." + "\n"
					                             + "No existe posici�n (" + X + "," + Y +").");
		if ((posX == -1) && (posY == -1))
		{//Posici�n Inicial.
			posX = X;
			posY = Y;
		}
		else
		{
			if (((posX % (int) posX) != 0.0) || ((posY % (int) posY) != 0.0))
			{//Si alguna de los posiciones tiene decimal. Que significa que est� en medio de un movimiento,
				posX = X;
				posY = Y;
			}
			else
			{
				if (posX != X)
					if (posX < X)
						posX += 0.5;
					else
						posX -= 0.5;
				if (posY != Y)
					if (posY < Y)
						posY += 0.5;
					else
						posY -= 0.5;
				try
				{
					Thread.sleep(200);
				}
				catch (InterruptedException e)
				{
					throw new SpriteException(e.getMessage());
				}
				
				actualizar(X,Y);
			}
		}
	}
	
	/**
	 * Indica a este SpriteManager como "debe ser eliminado".
	 */
	public void setEliminar ()
	{
		eliminar = true;
	}
	
	/*CONSULTAS*/
	
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
	 * Devuelve la posici�n actual del Sprite.
	 * 
	 * @return (x,y):   Posici�n actual del Sprite.
	 *         (-1,-1): Cuando el Sprite no tiene una posici�n asignada.
	 * @exception PosicionIncorrectaException Si no se ha asignado una posici�n.
	 */
	public double[] posicion () throws PosicionIncorrectaException
	{
		if ((posX == -1) && (posY == -1))
			throw new PosicionIncorrectaException ("No se ha asignado posici�n.");
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
	
	/*M�todos en Ejecuci�n*/
	
	/**
	 * M�todo de ImageObserver para Actualizaci�n del Sprite (imagen actual).
	 */
	public boolean imageUpdate (Image img, int infoflags,int x, int y, int w, int h)
	{
		return (infoflags & (ALLBITS|ABORT)) == 0;
    }

}