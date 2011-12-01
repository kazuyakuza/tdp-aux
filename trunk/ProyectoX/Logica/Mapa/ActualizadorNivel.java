package ProyectoX.Logica.Mapa;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Librerias.TDALista.Position;
import ProyectoX.Librerias.TDALista.PositionList;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.ControlCentral;
import ProyectoX.Logica.NoPersonajes.Plataformas.EspecialPowerUp;
import ProyectoX.Logica.NoPersonajes.Plataformas.Plataforma;
import ProyectoX.Logica.NoPersonajes.PowerUps.PowerUp;
import ProyectoX.Logica.Personajes.PjSeleccionable;
import ProyectoX.Logica.Personajes.Enemigo.Enemigo;
import ProyectoX.Logica.Responsabilidades.afectableXgravedad;

/**
 * Brinda métodos de agregación y eliminación de Actores del Nivel referenciado.
 * 
 * Actores actuales existentes, y a las listas a las cuales pertenecen.
 * + actores (todos)
 * + pjs (Mario)
 * + enemigos (Goomba, KoopaTroopa)
 * + plataformas (Rompible, Irrompible, EspecialMonedas, EspecialPowerUp)
 * + especialesPowerUp (todos los EspecialPowerUp)
 * + PowerUps (todos los PowerUps)
 * + caibles (Mario, Goomba, KoopaTroopa, BolaFuego, todos los PowerUps)
 * 
 * Proyecto X
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class ActualizadorNivel
{
	
	//Variables de Instancia
	private ControlCentral controlCentral;
	private Nivel nivel;
	private static boolean listo;
	
	//Mi Instancia
	private static ActualizadorNivel actualizadorNivel = new ActualizadorNivel ();
	
	
	/*CONSTRUCTOR*/
	
	/**
	 * Crea un ActualizadorNivel.
	 * 
	 * Se deben agregar el ControlCentral y el Nivel.
	 */
	private ActualizadorNivel ()
	{
		controlCentral = null;
		nivel = null;
		listo = false;
	}
	
	/*COMANDOS*/
	
	/**
	 * Agrega el ControlCentral.
	 * 
	 * @param cc ControlCentral a agregar.
	 */
	public void agregarControl (ControlCentral cc)
	{
		controlCentral = cc;
		if (nivel != null)
			listo = true;
	}
	
	/**
	 * Agrega el Nivel.
	 * 
	 * @param n Nivel a agregar.
	 */
	public void agregarNivel (Nivel n)
	{
		nivel = n;
		if (controlCentral != null)
			listo = true;
	}
	
	/*Obtención Listas en Nivel*/
	
	/**
	 * Devuelve la Lista de Actores en el Nivel.
	 * 
	 * @return Lista de Actores en el Nivel.
	 */
	private PositionList<Actor> actores ()
	{
		return nivel.getActores(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de PersonajesSeleccionables en el Nivel.
	 * 
	 * @return Lista de PersonajesSeleccionables en el Nivel.
	 */
	private PositionList<PjSeleccionable> pjs ()
	{
		return nivel.getPJs(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de Enemigos en el Nivel.
	 * 
	 * @return Lista de Enemigos en el Nivel.
	 */
	private PositionList<Enemigo> enemigos ()
	{
		return nivel.getEnemigos(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de Plataformas en el Nivel.
	 * 
	 * @return Lista de Plataformas en el Nivel.
	 */
	private PositionList<Plataforma> plataformas ()
	{
		return nivel.getPlataformas(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de EspecialesPowerUp en el Nivel.
	 * 
	 * @return Lista de EspecialesPowerUp en el Nivel.
	 */
	private PositionList<EspecialPowerUp> especialesPowerUp ()
	{
		return nivel.getEspecialesPowerUp(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de PowerUps en el Nivel.
	 * 
	 * @return Lista de PowerUps en el Nivel.
	 */
	private PositionList<PowerUp> powerUps ()
	{
		return nivel.getPowerUps(controlCentral);
	}
	
	/**
	 * Devuelve la Lista de Caibles en el Nivel.
	 * 
	 * @return Lista de Caibles en el Nivel.
	 */
	private PositionList<afectableXgravedad> caibles ()
	{
		return nivel.getCaibles(controlCentral);
	}
	
	/*AGREGACIÓN*/
	
	/**
	 * Agregar el Actor actor al Nivel.
	 * 
	 * Si el actor que se quiere agregar es:
	 * PjSeleccionable => llamar a agregarPJ, y no a este método.
	 * Enemigo => llamar a agregarEnemigo, y no a este método.
	 * Plataforma => llamar a agregarPlataforma, y no a este método.
	 * EspecialPowerUp => llamar a agregarEspecialPowerUp, y no a este método.
	 * PowerUp => llamar a agregarPowerUp, y no a este método.
	 * 
	 * Si el actor a agregar es afectableXgravedad, entonces llamar a agregarCaible, después de llamar a este método o a alguno de los antes mencionados.
	 * 
	 * @param actor Actor a agregar.
	 * @throws NullPointerException Si actor es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarActor (Actor actor) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (actor == null)
			throw new NullPointerException ("ActualizadorNivel.agregarActor()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");
		
		actores().addLast(actor);
	}
	
	/**
	 * Agrega el PjSeleccionable pj del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR agregarActor.
	 * 
	 * Si el actor a agregar es afectableXgravedad, entonces llamar a agregarCaible, después de llamar a este método.
	 * 
	 * @param pj PjSeleccionable a agregar.
	 * @throws NullPointerException Si pj es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarPJ (PjSeleccionable pj) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (pj == null)
			throw new NullPointerException ("ActualizadorNivel.agregarPJ()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");

		pjs().addLast(pj);
		agregarActor((Actor) pj);
	}
	
	/**
	 * Agrega el Enemigo enemigo del Nivel.
	 * 
	 * Se agrega automáticamente de la lista de actores.
	 * NO LLAMAR agregarActor.
	 * 
	 * Si el actor a agregar es afectableXgravedad, entonces llamar a agregarCaible, después de llamar a este método.
	 * 
	 * @param enemigo Enemigo a agregar.
	 * @throws NullPointerException Si enemigo es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarEnemigo (Enemigo enemigo) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (enemigo == null)
			throw new NullPointerException ("ActualizadorNivel.agregarEnemigo()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");
		
		enemigos().addLast(enemigo);
		agregarActor((Actor) enemigo);
	}
	
	/**
	 * Agrega la Plataforma plataforma del Nivel.
	 * 
	 * Se agrega automáticamente de la lista de actores.
	 * NO LLAMAR agregarActor.
	 * 
	 * Si el actor a agregar es afectableXgravedad, entonces llamar a agregarCaible, después de llamar a este método.
	 * 
	 * @param plataforma Plataforma a agregar.
	 * @throws NullPointerException Si plataforma es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarPlataforma (Plataforma plataforma) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (plataforma == null)
			throw new NullPointerException ("ActualizadorNivel.agregarPlataforma()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");

		plataformas().addLast(plataforma);
		agregarActor((Actor) plataforma);
	}
	
	/**
	 * Agrega el EspecialPowerUp especialPowerUp del Nivel.
	 * 
	 * Se agrega automáticamente de la lista de actores.
	 * NO LLAMAR agregarActor.
	 * Se agrega automáticamente de la lista de plataformas.
	 * NO LLAMAR agregarPlataforma.
	 * 
	 * Si el actor a agregar es afectableXgravedad, entonces llamar a agregarCaible, después de llamar a este método.
	 * 
	 * @param especialPowerUp EspecialPowerUp a agregar.
	 * @throws NullPointerException Si especialPowerUp es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarEspecialPowerUp (EspecialPowerUp especialPowerUp) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (especialPowerUp == null)
			throw new NullPointerException ("ActualizadorNivel.agregarEspecialPowerUp()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");

		especialesPowerUp().addLast(especialPowerUp);
		agregarPlataforma(especialPowerUp);
	}
	
	/**
	 * Agrega el PowerUp powerUp del Nivel.
	 * 
	 * Se agrega automáticamente de la lista de actores.
	 * NO LLAMAR agregarActor.
	 * Se agrega automáticamente de la lista de actores caible.
	 * NO LLAMAR agregarCaible.
	 * 
	 * @param powerUp PowerUp a agregar.
	 * @throws NullPointerException Si powerUp es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarPowerUp (PowerUp powerUp) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (powerUp == null)
			throw new NullPointerException ("ActualizadorNivel.agregarPowerUp()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");

		powerUps().addLast(powerUp);
		agregarActor(powerUp);
		agregarCaible(powerUp);
	}
	
	/**
	 * Agrega el Actor Caible caible del Nivel.
	 * 
	 * Se debe también llamar al agregar especifico para ese Actor. (agregarEnemigo(), agregarActor(), etc.)
	 * Ejemplo: si se quiere agregar una BolaFuego, entonces las llamadas deben ser: + agregarActor(bola) + agregarCaible(bola)
	 * 
	 * @param caible Actor Caible a agregar.
	 * @throws NullPointerException Si caible es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 */
	public void agregarCaible (afectableXgravedad caible) throws NullPointerException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (caible == null)
			throw new NullPointerException ("ActualizadorNivel.agregarCaible()" + "\n" +
                                            "El Actor que está intentando agregar del Nivel " + nivel.id + " es null.");

		caibles().addLast(caible);
	}
	
	/*ELIMINACIÓN*/
	
	/**
	 * agrega el Actor actor del Nivel.
	 * 
	 * Si el actor que se quiere eliminar es:
	 * PjSeleccionable => llamar a eliminarPJ, y no a este método.
	 * Enemigo => llamar a eliminarEnemigo, y no a este método.
	 * Plataforma => llamar a eliminarPlataforma, y no a este método.
	 * EspecialPowerUp => llamar a eliminarEspecialPowerUp, y no a este método.
	 * PowerUp => llamar a eliminarPowerUp, y no a este método.
	 * 
	 * Si el actor a eliminar es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a este método o a alguno de los antes mencionados.
	 * 
	 * @param actor Actor a eliminar.
	 * @throws NullPointerException Si actor es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un Actor que no pertenece al Nivel.
	 */
	public void eliminarActor (Actor actor) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (actor == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarActor()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (actores().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarActor()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Actor> p = actores().first();
		while ((p != actores().last()) && (p.element() != actor))
			p = actores().next(p);
		if (p.element() != actor)
			throw new AccionActorException ("ActualizadorNivel.eliminarActor()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		actores().remove(p);
	}
	
	/**
	 * Elimina el PjSeleccionable pj del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * Si el actor a eliminar es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a este método.
	 * 
	 * @param pj PjSeleccionable a eliminar.
	 * @throws NullPointerException Si pj es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un PjSeleccionable que no pertenece al Nivel.
	 */
	public void eliminarPJ (PjSeleccionable pj) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (pj == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarPJ()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (pjs().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarPJ()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<PjSeleccionable> p = pjs().first();
		while ((p != pjs().last()) && (p.element() != pj))
			p = pjs().next(p);
		if (p.element() != pj)
			throw new AccionActorException ("ActualizadorNivel.eliminarPJ()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		pjs().remove(p);
		eliminarActor((Actor) pj);
	}
	
	/**
	 * Elimina el Enemigo enemigo del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * Si el actor a eliminar es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a este método.
	 * 
	 * @param enemigo Enemigo a eliminar.
	 * @throws NullPointerException Si enemigo es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un Enemigo que no pertenece al Nivel.
	 */
	public void eliminarEnemigo (Enemigo enemigo) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (enemigo == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (enemigos().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Enemigo> p = enemigos().first();
		while ((p != enemigos().last()) && (p.element() != enemigo))
			p = enemigos().next(p);
		if (p.element() != enemigo)
			throw new AccionActorException ("ActualizadorNivel.eliminarEnemigo()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		enemigos().remove(p);
		eliminarActor((Actor) enemigo);
	}
	
	/**
	 * Elimina la Plataforma plataforma del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * 
	 * Si el actor a eliminar es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a este método.
	 * 
	 * @param plataforma Plataforma a eliminar.
	 * @throws NullPointerException Si plataforma es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar una Plataforma que no pertenece al Nivel.
	 */
	public void eliminarPlataforma (Plataforma plataforma) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (plataforma == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarPlataforma()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (plataformas().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarPlataforma()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<Plataforma> p = plataformas().first();
		while ((p != plataformas().last()) && (p.element() != plataforma))
			p = plataformas().next(p);
		if (p.element() != plataforma)
			throw new AccionActorException ("ActualizadorNivel.eliminarPlataforma()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		plataformas().remove(p);
		eliminarActor((Actor) plataforma);
	}
	
	/**
	 * Elimina el EspecialPowerUp especialPowerUp del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * Se elimina automáticamente de la lista de plataformas.
	 * NO LLAMAR eliminarPlataforma.
	 * 
	 * Si el actor a eliminar es afectableXgravedad, entonces llamar a eliminarCaible, antes de llamar a este método.
	 * 
	 * @param especialPowerUp EspecialPowerUp a eliminar.
	 * @throws NullPointerException Si especialPowerUp es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un especialPowerUp que no pertenece al Nivel.
	 */
	public void eliminarEspecialPowerUp (EspecialPowerUp especialPowerUp) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (especialPowerUp == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (especialesPowerUp().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<EspecialPowerUp> p = especialesPowerUp().first();
		while ((p != especialesPowerUp().last()) && (p.element() != especialPowerUp))
			p = especialesPowerUp().next(p);
		if (p.element() != especialPowerUp)
			throw new AccionActorException ("ActualizadorNivel.eliminarEspecialPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		especialesPowerUp().remove(p);
		eliminarPlataforma(especialPowerUp);
	}
	
	/**
	 * Elimina el PowerUp powerUp del Nivel.
	 * 
	 * Se elimina automáticamente de la lista de actores.
	 * NO LLAMAR eliminarActor.
	 * Se elimina automáticamente de la lista de actores caible.
	 * NO LLAMAR eliminarCaible.
	 * 
	 * @param powerUp PowerUp a eliminar.
	 * @throws NullPointerException Si powerUp es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un PowerUp que no pertenece al Nivel.
	 */
	public void eliminarPowerUp (PowerUp powerUp) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (powerUp == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (powerUps().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<PowerUp> p = powerUps().first();
		while ((p != powerUps().last()) && (p.element() != powerUp))
			p = powerUps().next(p);
		if (p.element() != powerUp)
			throw new AccionActorException ("ActualizadorNivel.eliminarPowerUp()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		eliminarCaible(powerUp);
		powerUps().remove(p);
		eliminarActor(powerUp);
	}
	
	/**
	 * Elimina el Actor Caible caible del Nivel.
	 * 
	 * Se debe también llamar al Eliminar especifico para ese Actor. (eliminarEnemigo(), eliminarActor(), etc.)
	 * Ejemplo: si se quiere eliminar una BolaFuego, entonces las llamadas deben ser: + eliminarCaible(bola) + eliminarActor(bola)
	 * 
	 * @param caible Actor Caible a eliminar.
	 * @throws NullPointerException Si caible es igual a null. O si el ActualizadorNivel no ha sido inicializado (ControlCentral = null y/o Nivel = null)
	 * @throws AccionActorException Si se intenta eliminar un Actor Caible que no pertenece al Nivel.
	 */
	public void eliminarCaible (afectableXgravedad caible) throws NullPointerException, AccionActorException
	{
		if (! listo)
			throw new NullPointerException ("ActualizadorNivel.act()" + "\n" +
					                        "Imposible utilizar el ActualizadorNivel. Faltan agregar ControlCentral y/o Nivel.");
		if (caible == null)
			throw new NullPointerException ("ActualizadorNivel.eliminarCaible()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " es null.");
		if (caibles().isEmpty())
			throw new AccionActorException ("ActualizadorNivel.eliminarCaible()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo." + "\n" +
                                            "El nivel no tiene actores.");
		
		Position<afectableXgravedad> p = caibles().first();
		while ((p != caibles().last()) && (p.element() != caible))
			p = caibles().next(p);
		if (p.element() != caible)
			throw new AccionActorException ("ActualizadorNivel.eliminarCaible()" + "\n" +
                                            "El Actor que está intentando eliminar del Nivel " + nivel.id + " no pertenece a al mismo.");
		caibles().remove(p);
	}
	
	/*CONSULTAS*/
	
	/**
	 * Devuelve el ActualizadorNivel.
	 * 
	 * @return ActualizadorNivel.
	 */
	public static ActualizadorNivel act ()
	{
		return actualizadorNivel;
	}

}