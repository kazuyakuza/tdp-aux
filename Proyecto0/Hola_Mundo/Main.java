package Proyecto0.Hola_Mundo;

/**
 * Proyecto 0 "Hola Mundo"
 * Clase Información.
 * 
 * @author Javier Eduardo Barrocal LU:87158
 * @author Pablo Isaias Chacar LU:67704
 */
public class Main
{

	/**
	 * Inicializador Proyecto 0 "Hola Mundo".
	 */
	public static void main(String[] args)
	{
		
		Informacion info = new Informacion(new Alumno("Javier Eduardo","Barrocal",87158),new Alumno("Pablo Isaias","Chacar",67704));
		System.out.println(info.getHolaMundo());
		System.out.println("Alumno 1: " + info.getAlumno1().getApellidosNombres() + " " + info.getAlumno1().getLU());
		System.out.println("Alumno 2: " + info.getAlumno2().getApellidosNombres() + " " + info.getAlumno2().getLU());
		
	}

}