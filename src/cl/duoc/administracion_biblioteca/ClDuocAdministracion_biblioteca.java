package cl.duoc.administracion_biblioteca;

import cl.duoc.administracion_biblioteca.libro.Libro;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroNoEncontradoExcepcion;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroPrestadoExcepcion;
import cl.duoc.administracion_biblioteca.usuario.Usuario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClDuocAdministracion_biblioteca {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        // Para agregar libros y usuarios
        biblioteca.agregarLibro(new Libro("1984", "George Orwell"));
        biblioteca.agregarLibro(new Libro("El principito", "Antoine de Saint-Exupéry"));
        biblioteca.agregarLibro(new Libro("Cien años de soledad", "Gabriel Garcia Marquez"));
        biblioteca.agregarLibro(new Libro("Orgullo y prejuicio", "Jane Austen"));
        biblioteca.agregarLibro(new Libro("Effective Java", "Joshua Bloch"));
        biblioteca.agregarLibro(new Libro("Heard First Java", "Kathy Sierra y Bert Bates"));
        biblioteca.agregarUsuario(new Usuario("Colomba", "UC001"));
        biblioteca.agregarUsuario(new Usuario("Nicolas", "UC002"));
        biblioteca.agregarUsuario(new Usuario("Liliana", "UC003"));

        System.out.println("======================================================================================");
        System.out.println("=========== BIENVENIDOS AL SISTEMA DE ADMINISTRACION DE BIBLIOTECA DUOC UC ===========");
        System.out.println("======================================================================================");
        System.out.println("                                                                                      ");

        boolean continuar = true;
        while (continuar) {

            System.out.print("Seleccione una opcion: ");
            System.out.println("                     ");
            System.out.println("1. Buscar libro");
            System.out.println("2. Prestar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Generar informe");
            System.out.println("5. Salir");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el titulo del libro a buscar:");
                        String tituloBuscar = scanner.nextLine().toLowerCase();
                        // Validación de entrada vacía
                        if (tituloBuscar.trim().isEmpty()){
                            System.out.println("Debe escribir un titulo para buscar el libro ");
                            break;
                        }
                        
                        try {
                            Libro libro = biblioteca.buscarLibro(tituloBuscar);
                            System.out.println("Libro encontrado: Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Prestado: " + (libro.esPrestado() ? "Si" : "No"));
                        } catch (LibroNoEncontradoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Ingrese el titulo del libro a prestar:");
                        String tituloPrestar = scanner.nextLine().toLowerCase();
                        
                       if (tituloPrestar.trim().isEmpty()) {
                           System.out.println("Debe escribir un titulo para que el libro sea prestado");
                       }
                    
                        try {
                            biblioteca.prestarLibro(tituloPrestar);
                            System.out.println("Libro prestado con exito.");
                        } catch (LibroNoEncontradoExcepcion | LibroPrestadoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Ingrese el titulo del libro a devolver:");
                        String tituloDevolver = scanner.nextLine().toLowerCase();
                        if (tituloDevolver.trim().isEmpty()){
                            System.out.println("Debe escribir un titulo para devolver el libro");
                        }
                        
                        try {
                            biblioteca.devolverLibro(tituloDevolver);
                            System.out.println("Libro devuelto con exito.");
                        } catch (LibroNoEncontradoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Generando informe...");
                        System.out.println("Lista de libros:");
                        for (Libro libro : biblioteca.getLibros()) {
                            System.out.println("Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Prestado: " + (libro.esPrestado() ? "Si" : "No"));
                        }
                        System.out.println();
                        System.out.println("Lista de usuarios:");
                        for (Usuario usuario : biblioteca.getUsuarios()) {
                            System.out.println("Nombre: " + usuario.getNombre() + ", ID: " + usuario.getId());
                        }
                        break;
                    case 5:
                        continuar = false;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente de nuevo.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next(); // Limpiar el buffer
            }
        }
        scanner.close();
    }
}
