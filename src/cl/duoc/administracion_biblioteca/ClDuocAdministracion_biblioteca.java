acion_biblioteca.libro.excepcion.LibroPrestadoExcepcion;
import cl.duoc.administracion_biblioteca.usuario.Usuario;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ClDuocAdministracion_biblioteca {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        // Para agregar libros a la biblioteca
        biblioteca.agregarLibro(new Libro("1984", "George Orwell", "Ciencia Ficción"));
        biblioteca.agregarLibro(new Libro("El principito", "Antoine de Saint-Exupéry", "Fantasía"));
        biblioteca.agregarLibro(new Libro("Cien años de soledad", "Gabriel Garcia Marquez", "Fantasía"));
        biblioteca.agregarLibro(new Libro("Effective Java", "Joshua Bloch", "Educativo"));
        biblioteca.agregarLibro(new Libro("Head First Java", "Kathy Sierra y Bert Bates", "Educativo"));

        // Agregar usuarios con ID único
        biblioteca.agregarUsuario(1, new Usuario("Colomba", "UC001"));
        biblioteca.agregarUsuario(2, new Usuario("Nicolas", "UC002"));
        biblioteca.agregarUsuario(3, new Usuario("Liliana", "UC003"));
        biblioteca.agregarUsuario(4, new Usuario("Armando", "UC004"));
        
        // Guardar la información de los usuarios en un archivo de texto
        biblioteca.guardarUsuariosEnArchivo();
        

        // Agregar categorías de libros
        biblioteca.agregarCategoria("Ciencia Ficción");
        biblioteca.agregarCategoria("Fantasía");
        biblioteca.agregarCategoria("Educativo");
        // Mensaje de bienvenida
        System.out.println("======================================================================================");
        System.out.println("=========== BIENVENIDOS AL SISTEMA DE ADMINISTRACION DE BIBLIOTECA DUOC UC ===========");
        System.out.println("======================================================================================");
        System.out.println("                                                                                      ");

        boolean continuar = true;
        while (continuar) {
            // Mostrar opciones del menú
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
                        // Opción para buscar un libro
                        System.out.println("Ingrese el titulo del libro a buscar:");
                        String tituloBuscar = scanner.nextLine().toLowerCase();
                        if (tituloBuscar.trim().isEmpty()) {
                            System.out.println("Debe escribir un titulo para buscar el libro ");
                            break;
                        }

                        try {
                            Libro libro = biblioteca.buscarLibro(tituloBuscar);
                            System.out.println("Libro encontrado: Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Categoría: " + libro.getCategoria() + ", Prestado: " + (libro.esPrestado() ? "Si" : "No"));
                        } catch (LibroNoEncontradoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        // opción para prestar un libro
                        System.out.println("Ingrese el titulo del libro a prestar:");
                        String tituloPrestar = scanner.nextLine().toLowerCase();
                        if (tituloPrestar.trim().isEmpty()) {
                            System.out.println("Debe escribir un titulo para que el libro sea prestado");
                            break;
                        }

                        System.out.println("Ingrese el ID del usuario que va a prestar el libro:");
                        int idUsuario = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer

                        try {
                            biblioteca.prestarLibro(tituloPrestar, idUsuario);
                            System.out.println("Libro prestado con exito.");
                        } catch (LibroNoEncontradoExcepcion | LibroPrestadoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        // opción para devolver un libro
                        System.out.println("Ingrese el titulo del libro a devolver:");
                        String tituloDevolver = scanner.nextLine().toLowerCase();
                        if (tituloDevolver.trim().isEmpty()) {
                            System.out.println("Debe escribir un titulo para devolver el libro");
                            break;
                        }

                        try {
                            biblioteca.devolverLibro(tituloDevolver);
                            System.out.println("Libro devuelto con exito.");
                        } catch (LibroNoEncontradoExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        //opción para generar informe
                        System.out.println("Generando informe...");

                        //  Mostrar todos los libros
                        System.out.println("Lista de todos los libros:");
                        for (Libro libro : biblioteca.getLibros()) {
                            String prestadoInfo = libro.esPrestado() ? "Si, Usuario: " + libro.getPrestadoA().getNombre() + " (ID: " + libro.getPrestadoA().getId() + ")" : "No";
                            System.out.println("Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Categoría: " + libro.getCategoria() + ", Prestado: " + prestadoInfo);
                        }

                        // Mostrar libros disponibles ordenados alfabéticamente
                        System.out.println("\nLista de libros disponibles (ordenados alfabéticamente):");
                        List<Libro> librosDisponibles = biblioteca.getLibros().stream()
                                .filter(libro -> !libro.esPrestado())
                                .sorted((libro1, libro2) -> libro1.getTitulo().compareToIgnoreCase(libro2.getTitulo()))
                                .collect(Collectors.toList());

                        for (Libro libro : librosDisponibles) {
                            System.out.println("Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Categoría: " + libro.getCategoria());
                        }

                        //  Mostrar cantidad de libros disponibles por categoría
                        System.out.println("\nCantidad de libros disponibles por categoría:");
                        Map<String, Long> librosPorCategoria = librosDisponibles.stream()
                                .collect(Collectors.groupingBy(Libro::getCategoria, Collectors.counting()));

                        for (Map.Entry<String, Long> entry : librosPorCategoria.entrySet()) {
                            System.out.println("Categoría: " + entry.getKey() + ", Cantidad: " + entry.getValue());
                        }
                        //Mostrar la lista de usuarios
                        System.out.println("\nLista de usuarios:");
                        for (Usuario usuario : biblioteca.getUsuarios().values()) {
                            System.out.println("Nombre: " + usuario.getNombre() + ", ID: " + usuario.getId());
                        }

                        break;
                    case 5:
                        // Opción para salir del programa
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
