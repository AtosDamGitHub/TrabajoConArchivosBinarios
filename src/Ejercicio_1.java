package ficheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Ejercicio_1 {

	public static void main(String[] args) {
		File archivo = new File("numeros.dat");
		ArrayList<Double> numeros = new ArrayList<>();

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {

			while (true) {
				try {
					Double num = (Double) in.readObject();
					numeros.add(num);

				} catch (ClassNotFoundException e) {
					System.out.println("No se pudo leer el objeto del archivo: " + e.getMessage());
				} catch (IOException e) {
					break; // fin del archivo
				}

			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}

		// Mostramos los números por pantalla
		for (Double num : numeros) {
			System.out.println(num);
		}
	}
}
