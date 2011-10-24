package Proyecto0.Hola_Mundo;

/**
 * Proyecto 0 "Hola Mundo"
 * Clase Información.
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Informacion
{
	
	//Atributos
	
	private Alumno a1, a2;
	
	/*Constructor*/
	
	public Informacion (Alumno Na1, Alumno Na2)
	{
		a1 = Na1;
		a2 = Na2;
	}
	
	/*Consultas*/
	
	public String getHolaMundo ()
	{
		return "Hola Mundo!";
	}
	
	public String LUs ()
	{
		return ("LU1:" + a1.getLU() + " LU2:" + a2.getLU());
	}
	
	public Alumno getAlumno1 ()
	{
		return a1;
	}
	
	public Alumno getAlumno2 ()
	{
		return a2;
	}

}