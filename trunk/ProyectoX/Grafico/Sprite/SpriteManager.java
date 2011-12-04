package ProyectoX.Grafico.Sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import ProyectoX.Excepciones.CargaRecursoException;
import ProyectoX.Excepciones.PosicionIncorrectaException;
import ProyectoX.Excepciones.SpriteException;
import ProyectoX.Grafico.BloqueGrafico;
import ProyectoX.Librerias.Threads.EmptyUpNeederException;
import ProyectoX.Librerias.Threads.UpNeeder;
import ProyectoX.Librerias.Threads.Updater;
import ProyectoX.Librerias.Threads.Worker;
import ProyectoX.Logica.ControlCentral;

/**
 * Controla los Sprites cargados.
 * 
 * Para simular un gif, cargar cada frame como una imagen distinta.
 * Llamar a setGif() con cantidad de frames del Gif.
 * Cada vez que se pide el spriteActual, se devolver� un frame distinto.
 * Siempre partiendo desde el spriteActual cargado con cambiarSprite().
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class SpriteManager implements ImageObserver
{
	
	//Variables de Clase
	private final int medidaPixelCelda = 32;//Medida de un lado en pixeles de una celda en el Escenario.
                                            //Celda = si se dividiera el Escenario usando una matriz, cada celda tendr�a una medida de lado en pixeles.
	
	//Variables de Instancia
	protected BloqueGrafico bloqueGrafico; //BloqueGrafico al que pertenece el SpriteManager actual.
	private UpNeeder upNeeder; //UpNeeder del SpriteManager para completar operaciones.
	private BufferedImage spriteActual;
	private BufferedImage[] sprites; //Guarda las imagenes posibles para este sprite, donde:
	                                 //[0] es la imagen intermedia. "mirando hacia fuera de la pantalla"
	                                 //[1] es la imagen hacia la derecha. "mirando hacia la derecha"
	                                 //para cargar la imagen a izquierda [-1], se invierte la imagen en [1]. "mirando hacia la izquierda"
	private boolean invertido; //True:  el sprite actual esta invertido deacuerdo a su imagen original.
	                           //False: caso contrario.
	private boolean eliminar; //True:  el sprite debe ser eliminado.
	                          //False: caso contrario.
	private boolean isAgif; //Inidica si el Sprite cargado es un gif con movimiento.
	private int frameGifActual, framesGif; //FrameActual y Cantidad m�xima de Frames del gif.
	private double posX, posY; //Posici�n Actual del Sprite en el Escenario.
	                           //Si posX=-1 y posY=-1, entonces no se ha asignado una posici�n a�n.
	private double difX, difY; //Diferencia entre la posici�n actual y la posici�n a la que debe moverse.
	
	//Prioridades UpNeeder
	//0 -> Terminar Actualizaci�n de posici�n.
	//1 -> Rotaci�n de Gif.
	
	/*CONSTRUCTOR*/
	
	/**
	 * Carga todos los sprites de acuerdo a los nombre de los archivos de las imagenes pasado por par�metro,
	 * y establece como spriteActual al primer sprite [0].
	 * Inicializa la posici�n en (-1,-1)
	 * 
	 * @param nombreSprites Nombres de los archivos de las imagenes para este sprite.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public SpriteManager (String[] nombresSprites) throws CargaRecursoException
	{
		bloqueGrafico = null;
		upNeeder = new UpNeeder(1);
		Updater.getUpdater().addUpNeeder(upNeeder);
		cargarSprites(nombresSprites);
		posX = posY = -1;
		difX = difY = 0;
		eliminar = false;
	}
	
	/*COMANDOS*/
	
	/**
	 *  Carga todos los sprites de acuerdo a los nombre de los archivos de las imagenes pasado por par�metro,
	 * y establece como spriteActual al primer sprite [0].
	 * 
	 * @param nombreSprites Nombres de los archivos de las imagenes para este sprite.
	 * @throws NullPointerException Su nombreSprites es null.
	 * @throws CargaRecursoException Error al cargar el Sprite.
	 */
	public void cargarSprites (String[] nombresSprites) throws NullPointerException, CargaRecursoException
	{
		if (nombresSprites == null)
			throw new NullPointerException ("SpriteManager.cargarSprites()" + "\n" +
					                        "Imposible cargar sprite. Nombre sprites es null.");
		
		sprites = new BufferedImage[nombresSprites.length];
		for (int i=0; i<nombresSprites.length; i++)
			try
			{
				sprites[i] = CargadorSprite.getCargador().obtenerSprite(nombresSprites[i], this);
			}
			catch (CargaRecursoException exception)
			{
				throw new CargaRecursoException ("SpriteManager." + "\n" +
						                         "Error al cargar el sprite de nombre " + nombresSprites[i] + "." + "\n" +
						                         "Detalles del Eror:" + "\n" +
						                         exception.getMessage());
			}
			
		spriteActual = sprites[0];

		invertido = false;
		frameGifActual = 0;
		framesGif = 0;
		setNotGif();
	}
	
	/**
	 * Cambia el sprite actual por el indicado en cambio.
	 * Cambio < 0 "mirando hacia la izquierda"
	 * Cambio = 0 "mirando hacia fuera de la pantalla"
	 * Cambio > 0 "mirando hacia la derecha"
	 * 
	 * Setea al sprite como no gif.
	 * 
	 * @param cambio Numero del sprite a cambiar.
	 * @throws SpriteException Si se ingresa un valor err�neo de cambio de sprite.
	 */
	public void cambiarSprite (int cambio) throws SpriteException
	{
		setNotGif();
		cambioSprite(cambio);
	}
	
	/**
	 * Cambia el sprite actual por el indicado en cambio.
	 * Cambio < 0 "mirando hacia la izquierda"
	 * Cambio = 0 "mirando hacia fuera de la pantalla"
	 * Cambio > 0 "mirando hacia la derecha"
	 * 
	 * @param cambio Numero del sprite a cambiar.
	 * @throws SpriteException Si se ingresa un valor err�neo de cambio de sprite.
	 */
	private void cambioSprite (int cambio) throws SpriteException
	{
		if (Math.abs(cambio) >= sprites.length)
			throw new SpriteException("SpriteManager.cambioSprite()" + "\n" +
					                  "Numero de Sprite a cargar incorrecto." + "\n" +
					                  "Ingresado: " + cambio + " | M�ximo: -" + (sprites.length - 1) + "|" + (sprites.length - 1) + "+");
		
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
		
		frameGifActual = cambio;
	}
	
	/**
	 * A cada pixel del SpriteActual lo pinta con su color contrario.
	 * 
	 * Para hacer un flasheo continuo se debe llamar reiteradas veces a este m�todo.
	 * 
	 * Al terminar el flashe se debe volver a cargar los sprite con el m�todo cargarSprites().
	 */
	public void flashear ()
	{
		Color color = null;
		int r,g,b;
		for (int i = 0; i < spriteActual.getWidth(); i++)
			for (int j = 0; j < spriteActual.getHeight(); j++)
				if (spriteActual.getRGB(i, j) != 0)
				{
					//se obtiene el color del pixel
					color = new Color(spriteActual.getRGB(i, j));
					//se extraen los valores RGB
					r = color.getRed();
					g = color.getGreen();
					b = color.getBlue();
					//se coloca en la nueva imagen con los valores invertidos
					spriteActual.setRGB(i, j, new Color(255 - r, 255 - g, 255 - b).getRGB());
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
	 * Actualiza la posici�n del sprite a la posici�n pasada por par�metro.
	 * 
	 * @param posicion Arreglo de dos componenete con posicion[0] = Nueva posici�n X, y posicion[1] = Nueva posici�n Y.
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
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
	 * @throws EmptyUpNeederException 
	 */
	public void actualizar (final int X, final int Y) throws EmptyUpNeederException
	{
		if ((X < 0) || (Y < 0))
			throw new PosicionIncorrectaException ("SpriteManager.actualizar()" + "\n" +
                                                   "Posici�n ingresada incorrecta." + "\n" +
					                               "No existe posici�n (" + X + "," + Y +").");
		
		if ((posX == -1) && (posY == -1))
		{//Posici�n Inicial.
			posX = X;
			posY = Y;
		}
		else
		{//Actualiza a una posici�n intermedia entre la actual y a la (X,Y).
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
			
			//Agregar Worker al UpNeeder para terminar actualizaci�n.
			if (! upNeeder.hayWorkerPrioridad(0))
				upNeeder.addWorker(0,
						new Worker ()
						{
							public void work() throws Exception
							{
								actualizacion();
								
								if ((! upNeeder.isEmpty()) && (upNeeder.hayWorkerPrioridad(1)) && (upNeeder.prioridadNextWorker() == 1))
									upNeeder.getNextWorker().work();
							}
						});
		}
	}
	
	/**
	 * Actualiza la posici�n del sprite.
	 * 
	 * @throws PosicionIncorrectaException Si se ingresa una posici�n incorrecta.
	 */
	private void actualizacion () throws PosicionIncorrectaException
	{
		posX += difX;
		posY += difY;
		difX = 0;
		difY = 0;
	}
	
	/**
	 * Setea el BloqueGrafico del SpriteManager.
	 * 
	 * @param cc ControlCentral que le est� agregando el BloqueGrafico.
	 * @param bG Nuevo BloqueGr�fico para el SpriteManager.
	 */
	public void setBloqueGrafico (ControlCentral cc, BloqueGrafico bG)
	{
		bloqueGrafico = bG;
	}
	
	/**
	 * Indica a este SpriteManager como "debe ser eliminado".
	 */
	public void setEliminar ()
	{
		eliminar = true;
	}
	
	/**
	 * Inidica que se debe tomar al spriteActual como un gif,
	 * donde su primer frame es el spriteActual, y sus siguientes
	 * frames son los ubicados en el arreglo sprites en las posiciones
	 * frameGifActual a (frameGifActual + fGif).
	 * 
	 * Se realizan rotaciones infinitas del gif, que es ir desde el primer hasta el �ltimo frame cada vez.
	 * 
	 * Es necesario llamar a setNotGif() para detener las rotaciones.
	 * 
	 * @param fGif Cantidad de frames del gif.
	 */
	public void rotarGif (int fGif)
	{
		isAgif = true;
		rotarGifSoporte(fGif);
	}
	
	/**
	 * M�todo soporta para rotarGif.
	 */
	private void rotarGifSoporte (final int fGif)
	{
		if (isAgif)
		{
			if (framesGif == 0)
			{
				if (frameGifActual > 0)
					frameGifActual -= (fGif - 1);
				else
					if (frameGifActual < 0)
						frameGifActual += (fGif - 1);
				framesGif = (fGif - 1);
			}
			upNeeder.addWorker(1,
					new Worker ()
					{
						public void work() throws Exception
						{
							rotarGifSoporte(fGif);
						}
					});
		}
	}
	
	/**
	 * Inidica que se debe tomar al spriteActual como un gif,
	 * donde su primer frame es el spriteActual, y sus siguientes
	 * frames son los ubicados en el arreglo sprites en las posiciones
	 * frameGifActual a (frameGifActual + (fGif - 1)).
	 * 
	 * Se realiza una �nica rotaci�n del gif, que es ir desde el primer hasta el �ltimo frame 1 sola vez.
	 * Para hacer rotaciones usar el m�todo rotarGif().
	 * 
	 * Para este m�todo no en necesario llamar a setNotGif().
	 * 
	 * @param fGif Cantidad de frames del gif.
	 */
	public void setGif (int fGif)
	{
		framesGif = (fGif - 1);
		isAgif = true;
	}
	
	/**
	 * Indica que el spriteActual no es un gif.
	 */
	public void setNotGif ()
	{
		isAgif = false;
	}
	
	/**
	 * Limpia el SpriteManager.
	 */
	public void limpiar ()
	{
		bloqueGrafico = null;
		spriteActual = null;
		sprites = null;
		upNeeder.notUpdate();
		upNeeder = null;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el Sprite Actual (imagen actual).
	 * 
	 * Si el spriteActual esta siendo tomado como una gif, entonces con cada llamada a este m�todo,
	 * se cambia el spriteActual por el siguiente del que corresponda en el gif.
	 * Pero siempre se devuelve el sprite anterior al cambio.
	 * 
	 * @return Sprite Actual.
	 * @throws SpriteException Si se produce un error al obtener el spriteActual cuando est� siendo tratado como un gif.
	 */
	public BufferedImage getSpriteActual () throws SpriteException
	{
		BufferedImage r = spriteActual;
		if (isAgif)
		{
			if (framesGif > 0)
			{
				if (frameGifActual >= 0)
					frameGifActual++;
				else
					frameGifActual--;
				framesGif--;
				
				if (Math.abs(frameGifActual) < sprites.length)
				{
					try
					{
						cambioSprite(frameGifActual);
					}
					catch (SpriteException e)
					{
						throw new SpriteException ("SpriteManager.getSpriteActual()" + "\n" +
								                   "Error al obtener un Sprite." + "\n" +
								                   "Detalles del error:" + "\n" +
								                   e.getMessage());
					}
				}
			}
		}
		return r;
	}
	
	/**
	 * Devuelve la posici�n actual del Sprite.
	 * 
	 * @return (x,y):   Posici�n actual del Sprite.
	 *         (-1,-1): Cuando el Sprite no tiene una posici�n asignada.
	 * @throws PosicionIncorrectaException Si no se ha asignado una posici�n.
	 */
	public double[] posicion () throws PosicionIncorrectaException
	{
		if ((posX == -1) && (posY == -1))
			throw new PosicionIncorrectaException ("SpriteManager.posicion()" + "\n" +
                                                   "No se ha asignado posici�n." + "\n" +
					                               "Detalles del Error:" + "\n" +
					                               "Error al llamar SpriteManager.posicion(), donde posX y posY del SpriteManager son iguales a -1.");
		
		double X = posY; //Intercambio entre posY Columnas y posici�n en eje X
		double Y = posX; //Intercambio entre posX filas y posici�n en eje Y
		
		//Si el Srite es mayor a medidaPixelCelda X medidaPixelCelda, entonces se lo ubica medidaPixelCelda pixeles mas arriba.
		if (spriteActual.getWidth() > medidaPixelCelda)
		{
			X -= ((spriteActual.getWidth()/(double) medidaPixelCelda) - 1);
			if ((X % (int) X) <= 0.25)
				X = (int) X;
		}
		if (spriteActual.getHeight() > medidaPixelCelda)
		{
			Y -= ((spriteActual.getHeight()/(double) medidaPixelCelda) - 1);
			if ((Y % (int) Y) <= 0.25)
				Y = (int) Y;
		}
		
		return new double[] {X, Y};
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
	 * Agrega al SpriteManager sp al BloqueGrafico al que pertenece el SpriteManager actual.
	 * 
	 * @param sp SpriteManager a agregar al BloqueGr�fico.
	 */
	public void printNextMe (SpriteManager sp)
	{
		sp.bloqueGrafico = bloqueGrafico;
		bloqueGrafico.agregarSprite(sp);
	}
	
	/*Metodos sin Uso*/
	
	/**
	 * M�todo de ImageObserver para Actualizaci�n del Sprite (imagen actual).
	 */
	public boolean imageUpdate (Image img, int infoflags,int x, int y, int w, int h)
	{
		return isAgif;
    }

}