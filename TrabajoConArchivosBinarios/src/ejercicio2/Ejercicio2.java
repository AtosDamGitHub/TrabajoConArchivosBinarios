package ejercicio2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Ejercicio2 {

	public static void main(String[] args) {
		
		File archivo = new File("textoFinal.dat");
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo,true))){
			String linea = " ";
			String textoFinal = "";
			while (linea!="") {
				System.out.println("Introduce una linea de texto: ");
				linea = new Scanner(System.in).nextLine();
				textoFinal += linea+"\n";
			}
			salida.writeObject(textoFinal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))){
			System.out.println("Texto guardado en el archivo: ");
			while (true) {
				System.out.println(entrada.readObject());
			}
		} catch (IOException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		archivo.deleteOnExit();
		
	}
	
}
