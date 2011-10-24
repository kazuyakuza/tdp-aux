package Proyecto0.Hola_Mundo;

/**
 * Proyecto 0 "Hola Mundo"
 * Clase Alumno que representa a los alumnos de la comisión del proyecto.
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Alumno
{
	
	//Atributos
	
	private String nombres, apellidos;
	private int LU;
	
	/*Constructor*/
	
	public Alumno (String Anombres, String Aapellidos, int numLU)
	{
		nombres = Anombres;
		apellidos = Aapellidos;
		LU = numLU;
	}
	
	/*Consultas*/
	
	public String getNombres ()
	{
		return nombres;
	}
	
	public String getApellidos ()
	{
		return apellidos;
	}
	
	public int getLU ()
	{
		return LU;
	}
	
	public String getApellidosNombres ()
	{
		return (apellidos + "," + nombres);
	}

}