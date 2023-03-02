package ejercicio4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Comparable<Cliente>, Serializable{

	private static final long serialVersionUID = 1L;
	int id;
	String nombre = "";
	String telefono;
	static String fichero_de_datos = "src\\ficheros\\Clientes.dat";
	
	//------------------------------------------CONSTRUCTOR---------------------------------------------------
	
	public Cliente(String nombre, Long telefono) throws Exception {
		setId();
		setTelefono(telefono);
		setNombre(nombre);
	}

	//----------------------------ASIGNACION DE ID AL CLIENTE---------------------------------------------------
	
	public void setId() {
		
		int ultimoID;
		
		if(Ejercicio4.clientes.isEmpty()) 
			this.id = 1;
		else {
			List<Cliente> l = new ArrayList<>(Ejercicio4.clientes);
			ultimoID = l.size()-1;
			
			this.id = (l.get(ultimoID).id) + 1;
		}		
	}
	
	//----------------------------CORRECCION Y ASIGNACION DEL NOMBRE (Y APELLIDOS) DEL CLIENTE---------------------

	public void setNombre(String nombre) {
		String[] titular = nombre.split(" ");
		String n;
		
		for (int i = 0; i < titular.length; i++) {
			
			if(!titular[i].isBlank()) {
				n = titular[i].trim();
				String primera_letra = String.valueOf(n.charAt(0));
				n = primera_letra.toUpperCase() + n.substring(1).toLowerCase();
				this.nombre += n + " ";
			}
		}
		
		this.nombre.trim();
	}

	//------------------------------COMPROBACION Y ASIGNACION DE TELEFONO DEL CLIENTE-------------------------------
	
	public void setTelefono(Long telefono) throws Exception {
		
		String tlf = "+34 ";
		String telCli = String.valueOf(telefono);
		boolean numerOcupado = false;
		
		if(telCli.length() == 9) {
			tlf += telCli;
			Iterator<Cliente> it = Ejercicio4.clientes.iterator();
			while(it.hasNext()) {
				if(it.next().telefono.equals(tlf)) 
					numerOcupado = true;
			}
			
			if(!numerOcupado)
				this.telefono = tlf;
			else
				throw new Exception("NUMERO DE TELEFONO YA ASIGNADO");		
		} else {
			throw new Exception("NUMERO DE TELEFONO NO VALIDO");
		}			
	}

	//----------------------------------COMPARABLE PARA TREESET "clientes"------------------------------------------
	
	@Override
	public int compareTo(Cliente o) {
		
		if(this.id > o.id)
			return 1;
		else if(this.id < o.id)
			return -1;
		else
			return 0;
	}
	
	//----------------------------------AÑADIR NUEVO CLIENTE---------------------------------------------------
	
	public static void nuevoCliente() throws Exception {
		
		System.out.print("INGRESE NOMBRE Y APELLIDOS DEL CLIENTE: ");
		String nom = new Scanner(System.in).nextLine();
		
		System.out.print("INGRESE TELEFONO DEL CLIENTE: ");
		Long telf = new Scanner(System.in).nextLong();
		
		Cliente cliente = new Cliente(nom, telf);
		
		Ejercicio4.clientes.add(cliente);
		
	}
	
	//----------------------------------MODIFICACION DE DATOS---------------------------------------------------
	
	public static void modificarDato(int n) throws Exception {
		
		Cliente client = null;
		
		if(Ejercicio4.clientes.isEmpty()) 
			throw new Exception("NO EXISTEN CLIENTES PARA MODIFICAR");
		else {
			List<Cliente> l = new ArrayList<>(Ejercicio4.clientes);
			
			for (Cliente c : l) {
				
				if(c.id == n)
					client = c;
			}				
		
			if(client == null) throw new Exception("NO HAY NINGUN CLIENTE CON EL ID ESPECIFICADO");
			else {
				System.out.println("DATO QUE DESEA MODIFICAR:\n1.- NOMBRE\n2.- TELEFONO\nCUALQUIER OTRA TECLA PARA SALIR");
				int op = new Scanner(System.in).nextInt();
				
				switch(op) {
				case 1:
					System.out.println("INGRESE NUEVO NOMBRE: ");
					try {
					String nuevo_nombre = new Scanner(System.in).nextLine();
					client.setNombre(nuevo_nombre);
					System.out.println("NOMBRE ACTUALIZADO");
					} catch(InputMismatchException e) {
						System.out.println(e.getMessage());
					}
					break;
					
				case 2:
					System.out.println("INGRESE NUEVO TELEFONO: ");
					try {
					Long nuevo_tlf = new Scanner(System.in).nextLong();
					client.setTelefono(nuevo_tlf);
					System.out.println("TELEFONO ACTUALIZADO");
					} catch(InputMismatchException e) {
						System.out.println(e.getMessage());
					}
					break;
					
				default:
					break;
				}
			}
		}
	}
	
	
	//---------------------------------DAR DE BAJA A UN CLIENTE----------------------------------------------------
	
	public static void baja(int n) throws Exception {
		
		Cliente client = null;
		
		if(Ejercicio4.clientes.isEmpty()) 
			throw new Exception("NO EXISTEN CLIENTES PARA ELIMINAR");
		else {
			List<Cliente> l = new ArrayList<>(Ejercicio4.clientes);
			
			for (Cliente c : l) {
				
				if(c.id == n)
					client = c;
			}				
		
			if(client == null) 
				throw new Exception("NO HAY NINGUN CLIENTE CON EL ID ESPECIFICADO");
			else {
				System.out.println("COMPROBANDO CREDENCIALES...");
				System.out.println("DANDO DE BAJA...");
				Ejercicio4.clientes.remove(client);
				System.out.println("COMPLETADO");
			}
		}
	}
	
	//-----------------------------------LISTAR CLIENTES [ metodo toString() ]---------------------------------------
	
	@Override
	public String toString() {
		return "\u001b[7m\u001b[1m\u001b[36;1m" + this.id + "\t" + this.nombre + "      " + this.telefono + "\u001b[0m\n";
	}
	
	//-----------------------------------RECUPERACION DE DATOS---------------------------------------
	
	public static void cargarDatos() {
		
		File datos = new File(fichero_de_datos);
		
		System.out.println("COMPROBANDO EXISTENCIA DE DATOS GUARDADOS...");
		
		if(datos.exists()) {
			
			try(ObjectInputStream rd = new ObjectInputStream(new FileInputStream(fichero_de_datos))) {
				
				Ejercicio4.clientes.addAll((Collection<Cliente>) rd.readObject());
				System.out.println("DATOS GUARDADOS RECUPERADOS...");
				System.out.println("INICIANDO APLICACION...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("NO HAY DATOS GUARDADOS\n PROCEDIENDO A ABRIR LA APLICACION...");
	}
	
	//-----------------------------------GUARDADO DE DATOS---------------------------------------
	
	public static void sobreescribirDatos() {
		
		System.out.println("SOBREESCRIBIENDO DATOS...");
		try(ObjectOutputStream wrt = new ObjectOutputStream(new FileOutputStream(fichero_de_datos))) {
			
			wrt.writeObject(Ejercicio4.clientes);
			System.out.println("NUEVOS DATOS GUARDADOS");
			System.out.println("SALIENDO DE LA APLICACION...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------MENU---------------------------------------
	
	public static void menu() {
		
		cargarDatos();
		Scanner sc = new Scanner(System.in);
		int opcion, id_cliente;
		
		System.out.println("ELIJA ALGUNA DE LAS SIGUIENTES OPCIONES:\n"
				+ "1.- DAR DE ALTA A UN CLIENTE\n"
				+ "2.- MODIFICAR DATOS DE UN CLIENTE\n"
				+ "3.- DAR DE BAJA A UN CLIENTE\n"
				+ "4.- LISTAR CLIENTES DE LA EMPRESA\n"
				+ "5.- SALIR");
		opcion = sc.nextInt();
		
		while(opcion != 5) {
			
			switch (opcion) {
			case 1:
				try {
					nuevoCliente();
				} catch (Exception e) {
					System.err.println(e);
				}
				break;
				
			case 2:
				System.out.print("INTRODUZCA ID DEL CLIENTE: ");
				id_cliente = sc.nextInt();
				try {
					modificarDato(id_cliente);
				} catch (Exception e) {
					System.err.println(e);
				}
				break;
				
			case 3:
				System.out.print("INTRODUZCA ID DEL CLIENTE: ");
				id_cliente = sc.nextInt();
				try {
					baja(id_cliente);
				} catch (Exception e) {
					System.err.println(e);
				}
				break;
				
			case 4:
				System.out.println("\u001b[7m\u001b[1mID\t\t   CLIENTE\t       TELEFONO    \u001b[0m");
				Iterator<Cliente> it = Ejercicio4.clientes.iterator();
				while(it.hasNext())
					System.out.println(it.next().toString());				
				break;
				
			default:
				System.out.println("ACCION NO VALIDA");
				break;
			}
			
			System.out.println("ELIJA ALGUNA DE LAS SIGUIENTES OPCIONES:\n"
					+ "1.- DAR DE ALTA A UN CLIENTE\n"
					+ "2.- MODIFICAR DATOS DE UN CLIENTE\n"
					+ "3.- DAR DE BAJA A UN CLIENTE\n"
					+ "4.- LISTAR CLIENTES DE LA EMPRESA\n"
					+ "5.- SALIR");
			opcion = sc.nextInt();
		}
		
		sobreescribirDatos();		
	}
}
