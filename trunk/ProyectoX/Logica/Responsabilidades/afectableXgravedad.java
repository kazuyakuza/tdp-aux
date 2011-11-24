package ProyectoX.Logica.Responsabilidades;

import ProyectoX.Excepciones.AccionActorException;

/**
 * Indica los m�todos necesarios para un Actor Afectable por la Gravedad..
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public interface afectableXgravedad
{
	
	/*COMANDOS*/
	
	/**
	 * Si la Gravedad afecta a este Actor, entonces llamar� a este m�todo para afectarlo.
	 * 
	 * @param efecto Efecto de la Gravedad sobre este Actor.
	 */
	public void efectoGravedad (int efecto);
	
	/**
	 * Realiza la Acci�n caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al caer.
	 */
	public void caer () throws AccionActorException;
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve la Potencia de la Gravedad sobre este Actor.
	 * 
	 * @return Potencia de la Gravedad sobre este Actor.
	 */
	public int getPG ();

}