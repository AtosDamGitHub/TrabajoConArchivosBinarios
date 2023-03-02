package ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* 3. Se desea controlar el número de llamadas recibidas en una oficina. 
 * Para ello, al terminar cada jornada laboral, se guarda dicho número 
 * al final de un archivo binario. Implementar una aplicación con un 
 * menú, que nos permita añadir el número correspondiente cada día y
 * ver la lista completa en cualquier momento. 
 */
public class Ejercicio3 {
	int numero;
	LocalDate fecha;
	
	/*Constructor de la clase */
	public Ejercicio3(int numero) {
		this.numero = numero; //Número de teléfono 
		this.fecha = LocalDate.now(); //Fecha actual del sistema
	}
	
	/* Guarda los datos cuando se cree un nuevo número  */
	public static List<Ejercicio3> numeros = new ArrayList<Ejercicio3>(); 
	
	
	/*
	 * Metodo que genera el menú. 
	 * Cuenta con las opciones:
	 * 1.Registrar número de teléfono: 
	 * Pide al usuario el número de teléfono a guardar y por defecto también se guarda la fecha del sistema e
	 * introduce los datos en la lista numeros. Llama al método agregarFichero() para guardar la información 
	 * introducida en el fichero.
	 * 2.Lista de números:
	 * LLama al método leerFichero() para mostrar los números de teléfonos y la fecha en la que se guardaron
	 * en el fichero.
	 * 3. Salir :
	 * Termina el programa, muestra un mensaje de despedida.
	 * Repite el menú mientras que la opción sea distinta a 3.
	 * Si la opción que indica el usuario esta fuera del rango (1-3) lo indica.
	 */
	public static void menu() {
		Scanner teclado = new Scanner(System.in); 
		int opcion = 0; //Opcion que indica el usuario 
		
		do {//Lo realiza mientras la opcion sea distinta a 4
			System.out.println("-- MENÚ --");
			System.out.println("Indica la opción que quieras realizar. Pulsa 3 para salir");
			System.out.println("1.Registrar número de teléfono \n2.Lista de números \n3.Salir");
			opcion = teclado.nextInt(); 
			
			//Segun la opcion que indique el usuario sera un caso u otro 
			switch (opcion) {
			case 1: 
				System.out.println("Introduce el número de teléfono: "); 
				int numero = teclado.nextInt();
				numeros.add(new Ejercicio3(numero)); /*Añade un nuevo número a la lista */
				agregarFichero();
				break;
			case 2: 
				System.out.println("Datos del fichero: "); 
				leerFichero();
				break;
			case 3:
				System.out.println("Hasta pronto");
				break;
			default:
				//Si la opcion esta fuera del rango(1-3)
				System.out.println("Opción no valida");
				break;
			}
			System.out.println();
		}while (opcion!=3);
		
		teclado.close();
	}
	
	
	/* Metodo toString para mostrar los datos */
	@Override
	public String toString() {
		return "Número de teléfono:"+ this.numero+"   Día:"+this.fecha+"\n";
	}
	
	
	/* Método agregarFichero : 
	 * Guarda como String los datos de la lista numeros para guardarlo en el fichero.
	 * Comprueba si existe el fichero, si no existe lo crea y si existe le agrega la informacion. 
	 */
	public static void agregarFichero() {
		String fichero="ficheros\\ejercicio3.dat"; /*Ruta con el nombre que tendra el fichero */
	
		BufferedWriter bw = null;
	    FileWriter fw = null;
	    
	    try {
	        String data = Ejercicio3.numeros.toString();
	        File file = new File(fichero);
	        if (!file.exists()) { // Si no existe
	            file.createNewFile();//Crea el fichero
	        }
	        fw = new FileWriter(file.getAbsoluteFile(), true); // Si existe se pone en true 
	        //Agrega la informacion al fichero
	        bw = new BufferedWriter(fw);
	        bw.write(data); // Escribe el string de numeros
	        System.out.println("Información agregada en el fichero"); //Indica por pantalla que se agregado datos
	    }catch (IOException e) {
	        e.printStackTrace();
	    }finally {//Cierra instancias de FileWriter y BufferedWriter
	        try {
	            if (bw != null)
	                bw.close();
	            if (fw != null)
	                fw.close();
	        }catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	}
	
	
	 /* Método leerFichero():
	  * Abre el fichero para leer la información que contiene (muestra su información por pantalla) 
	  */
	public static void leerFichero() {
		String fichero="ficheros\\ejercicio3.dat";  /*Ruta con el nombre que tendra el fichero */
		try(BufferedReader in = new BufferedReader(new FileReader(fichero))){//Abre el fichero
			String linea = in.readLine(); //Lee la primera línea 
			
			while(linea!= null) { //Mientras no sea el final del fichero 
				System.out.println(linea); //Impime la línea 		
				linea = in.readLine(); //Vuelve a leer la línea 
			}
			
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	/*METODO MAIN desde donde llama al menú para realizar pruebas */
	public static void main(String[] args) {
	
		Ejercicio3.menu();
	}

}
