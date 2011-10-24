package ProyectoX.Grafico.Sprite;

import java.net.URL;

import ProyectoX.Excepciones.CargaRecursoException;

/**
 * Clase utilizada para cargar Recursos.
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public abstract class CargadorRecurso
{
	
	//Variables de Clase
	private String dirRecursos = "ProyectoX/Recursos/";
	
	/*COMANDOS*/
	
	/**
	 * Devuelve el recurso de nombre nom.
	 * 
	 * @param nom Nombre del recurso a devolver.
	 * @return Recurso de nombre nom.
	 * @exception CargaRecursoException Si se produce un error al cargar el recurso solicitado.
	 */
	public Object obtenerRecurso (String nom) throws CargaRecursoException
	{
		Object res = cargarRecurso(dirRecursos + nom);
		return res;
	}
	
	/**
	 * Genera la URL necesaria para cargar el recurso de direccion dir.
	 * 
	 * @param dir Direcci�n del recurso a cargar.
	 * @return Recurso que est� en la direcci�n dir.
	 * @exception CargaRecursoException Si se produce un error al cargar el recurso solicitado. O si la direcci�n ingresada es incorrecta y genera una URL null.
	 */
	private Object cargarRecurso (String dir) throws CargaRecursoException
	{
		URL url = null;
		url = getClass().getClassLoader().getResource(dir);
		
		if (url == null)
			throw new CargaRecursoException ("Error al Cargar el Recurso de Direcci�n: " + dir + "\n" +
                                             "Detalles del error:" + "\n" +
                                             "La direcci�n ingresada genera una una URL null.");
		
		return cargarRecurso(url);
	}
	
	/**
	 * Lee la url del recurso a cargar, y lo devuelve.
	 * 
	 * @param url Direcci�n del recurso a cargar.
	 * @return Recurso de direcci�n url.
	 * @exception CargaRecursoException Si hay un error al leer la URL.
	 */
	public abstract Object cargarRecurso (URL url) throws CargaRecursoException;

}