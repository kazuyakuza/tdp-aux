package ProyectoX.Logica.Personajes;

import ProyectoX.Excepciones.AccionActorException;
import ProyectoX.Logica.Actor;
import ProyectoX.Logica.Mapa.Celda;
import ProyectoX.Logica.NoPersonajes.Plataformas.Rompible;
import ProyectoX.Logica.Responsabilidades.Posicionable;

public abstract class DecoracionCaracteristica extends Caracteristica
{
	
	//Atributos de Instancia
	protected Caracteristica componente;
	
	/*CONSTRUCTORES*/
	
	/**
	 * Crea una decoraci�n de Caracteristica que modifica las responsabilidades de la misma en ejecuci�n.
	 * 
	 * @param comp es la Caracteristica a la cual se decora.
	 */
	protected DecoracionCaracteristica (Caracteristica comp)
	{		
		super();
		componente = comp;
		mario = comp.getMario();
		mario.getSpriteManager().cargarSprites(this.getNombresSprites());
		mario.getSpriteManager().cambiarSprite(quieto);
	}
	
	/**
	 * Mario realiza la acci�n de agacharse.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al agacharse.
	 */
	public void agacharse () throws AccionActorException
	{
		componente.agacharse();
	}
	
	/**
	 * Realiza la acci�n de pararse.
	 */
	public void pararse ()
	{
		componente.pararse();
	}
	
	/**
	 * Mario realiza la acci�n A.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n A.
	 */
	public void accionA () throws AccionActorException
	{
		componente.accionA();
	}
		
	/**
	 * Mario realiza la acci�n B.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al realizar la acci�n B.
	 */
	public void accionB () throws AccionActorException
	{
		componente.accionB();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por un Super Hongo.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerHongo () throws AccionActorException
	{
		componente.crecerHongo();
	}
	
	/**
	 * Realiza el efecto de crecer sobre Mario producido por una Flor de Fuego.
	 * Dicho efecto evoluciona a Mario.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al crecer.
	 */
	public void crecerFlor () throws AccionActorException
	{
		componente.crecerFlor();
	}
	
	/**
	 * Realiza la acci�n de ser colisionado por un enemigo (Actor).
	 * @param a es el Actor (enemigo) que colision� con Mario.
	 */
	public void serDa�ado(Actor a)
	{
		componente.serDa�ado(a);
	}
	
	/**
	 * Realiza la acci�n de golpear una plataforma Rompible.
	 * @param estructura es la plataforma Rompible que Mario golpea.
	 * @throws NullPointerException si brick es null.
	 */
	public void golpearRompible (Rompible brick) throws NullPointerException
	{
		componente.golpearRompible(brick);
	}
	
	/**
	 * Retorna el multiplicador bonus que otorga �sta Caracteristica sobre los puntos.
	 * @return un entero que es el multiplicador bonus sobre los puntos.
	 */
	public int multiplicadorBonus()
	{
		return componente.multiplicadorBonus();
	}
	
	/**
	 * Retorna los nombres de sprites correspondientes a la Caracteristica.
	 * @retun un arreglo de Strings que contiene los nombres de sprites.
	 */
	public String[] getNombresSprites()
	{		
		return componente.getNombresSprites();
	}
	
	/*COMANDOS*/
	
	/**
	 * Realiza la Acci�n Caer, producida por el efecto de la Gravedad.
	 * 
	 * @throws AccionActorException Si se produce un error al caer.
	 */
	public void caer () throws AccionActorException
	{
		componente.caer();
	}	
	
	/**
	 * Mario realiza la acci�n de saltar.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al saltar.
	 */
	public void saltar () throws AccionActorException
	{		
		componente.saltar();
	}
	
	/**
	 * Mario realiza la acci�n de moverse hacia la izquierda.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a izquierda.
	 */
	public void moverseAizquierda () throws AccionActorException
	{
		componente.moverseAizquierda();
	}
	
	/**
	 * Mario realiza la acci�n de moverse hace la derecha.
	 * 
	 * @throws AccionActorException Si se produce alg�n error al moverse a derecha.
	 */
	public void moverseAderecha () throws AccionActorException
	{		
		componente.moverseAderecha();
	}
	
	/**
	 * Setea al Mario al que la Caracteristica representa con pj.
	 * @param pj es el Mario con el que se setea a la Caracteristica.
	 */
	public void setMario (Mario pj)
	{
		mario = pj;
	}
	
	/**
	 * Modifica la Celda actual del actor por la Celda c, actualizando su ubicaci�n.
	 * @param c es la nueva Celda para el Actor.
	 * @throws NullPointerException si c es null.
	 */
	protected void actualizarCelda (Celda c) throws NullPointerException
	{
		componente.actualizarCelda(c);
	}
	
	/*CONSULTAS*/
	
	/**
	 * Retorna el Mario asociado a la Caracteristica.
	 * @return el Mario asociado a la Caracteristica.
	 */
	public Mario getMario()
	{
		return mario;
	}
			
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado muerto de Mario.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario muerto.
	 */
	public int spriteMuerto()
	{
		return componente.spriteMuerto();
	}
		
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado de Mario caminando.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario caminando.
	 */
	public int spriteCaminando()
	{
		return componente.spriteCaminando();
	}
	
	/**
	 * Retorna la cantidad de sprites que representan a Mario caminando.
	 * @return entero equivalente a la cantidad de sprites que animan a Mario caminando.
	 */
	public int cantSpritesCaminando()
	{
		return componente.cantSpritesCaminando();
	}
	
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado de Mario saltando.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario saltando.
	 */
	public int spriteSaltando()
	{
		return componente.spriteSaltando();
	}
	
	/**
	 * Retorna el �ndice del arreglo donde se encuentra el sprite que representa el estado quieto de Mario.
	 * @return un entero que es el �ndice del arreglo de sprite donde est� el de Mario quieto.
	 */
	public int spriteQuieto()
	{
		return componente.spriteQuieto();
	}
	
	/**
	 * Calcula un vector que representa la distancia m�s cercana de Mario al Actor a en el eje cartesiano.
	 * El vector es de tam�ano 2 (x,y). 
	 * En el �ndice 0 se ubica la distancia de Mario al Actor a en el eje x.
	 * En el �ndice 1 se ubica la distancia de Mario al Actor a en el eje y.
	 * 
	 * @param a Actor Posicionable que se utiliza para calcular la distancia hacia Mario.
	 * @return un arreglo de dos componentes (x,y) que contiene la distancia m�s cercana de Mario hacia el Actor a en el eje cartesinano.
	 */
	public int[] vectorDistancia (Posicionable a)
	{
		return componente.vectorDistancia(a);
	}
	
	/**
	 * Verifica que se cumpla la condic�n para saltar.
	 * @return verdadero si se cumple la condici�n para saltar, falso, en caso contrario.
	 */
	protected boolean condicionSaltar ()
	{		
		return componente.condicionSaltar();
	}
	
}
