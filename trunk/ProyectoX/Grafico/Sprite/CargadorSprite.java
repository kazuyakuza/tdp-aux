package ProyectoX.Grafico.Sprite;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.imageio.ImageIO;

import ProyectoX.Excepciones.CargaRecursoException;

/**
 * Clase utilizada para cargar Sprites (im�genes).
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class CargadorSprite extends CargadorRecurso
{
	
	//Variables de Clase
	private static String dirSprites = "Imagenes/";
	
	//Mi Instancia
	private static CargadorSprite cargadorSprite = new CargadorSprite ();
	
	/**
	 * Redefici�n de Constructor Default.
	 */
	private CargadorSprite ()
	{
		super();
	}
	
	/*COMANDOS*/

	/**
	 * Carga un Sprite y lo devuelve.
	 * 
	 * @param nombre Nombre del archivo del Sprite.
	 * @param io ImageObserver para el Sprite a cargar.
	 * @return Sprite cargado.
	 * @throws CargaRecursoException Si se produce un error al cargar el Sprite solicitado.
	 */
	public BufferedImage obtenerSprite (String nombre, ImageObserver io) throws CargaRecursoException
	{
		BufferedImage imagenCargada = (BufferedImage) obtenerRecurso(nombre);
		BufferedImage combatible = crearCombatible(imagenCargada.getWidth(), imagenCargada.getHeight(), Transparency.BITMASK);
		Graphics g = combatible.getGraphics();
        g.drawImage(imagenCargada, 0, 0, io);
        return combatible;
	}
	
	/**
	 * Devuelve el recurso de nombre nom.
	 * 
	 * @param nom Nombre del recurso a devolver.
	 * @return Recurso de nombre nom.
	 * @throws CargaRecursoException Si se produce un error al cargar el recurso solicitado.
	 */
	public Object obtenerRecurso (String nom) throws CargaRecursoException
	{
		Object res = super.obtenerRecurso(dirSprites + nom);
		return res;
    }
	
	/**
	 * Lee la url del recurso a cargar, y lo devuelve.
	 * 
	 * @param url Direcci�n del recurso a cargar.
	 * @return Recurso de direcci�n url.
	 * @throws CargaRecursoException Si hay un error al leer la URL.
	 */
	public Object cargarRecurso (URL url) throws CargaRecursoException
	{
		try
		{
			return ImageIO.read(url);
		}
		catch (Exception e)
		{
			throw new CargaRecursoException ("CargadorSprite.cargadorRecurso()" + "\n" +
					                         "Error al Cargar el Recurso Imagen: " + url.getFile() + "\n" +
					                         "Detalles del error:" + "\n" +
					                         e.getMessage());
		}
	}
	
	/**
	 * Devuelve una imagen combatible con modo de v�deo nativo que se est� mostrando.
	 * 
	 * @param ancho Ancho de la imagen.
	 * @param alto Alto de la imagen.
	 * @param transparency Transpariencia de la imagen.
	 * @return Imagen combatible.
	 */
	public BufferedImage crearCombatible (int ancho, int alto, int transparency)
	{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage combatible = gc.createCompatibleImage(ancho, alto, transparency);
		return combatible;
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el CargadorSprite.
	 * 
	 * @return CargadorSprite.
	 */
	public static CargadorSprite getCargador ()
	{
		return cargadorSprite;
	}

}