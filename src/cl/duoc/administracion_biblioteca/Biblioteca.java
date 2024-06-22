package cl.duoc.administracion_biblioteca;

import cl.duoc.administracion_biblioteca.libro.Libro;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroNoEncontradoExcepcion;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroPrestadoExcepcion;
import cl.duoc.administracion_biblioteca.usuario.Usuario;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Biblioteca {

    // Para almacenar objetos de la clase Libro
    private List<Libro> libros;
    // Para gestionar usuarios con ID único
    private HashMap<Integer, Usuario> usuarios;
    // Para almacenar categorías de libros
    private HashSet<String> categorias;
    // Para mantener los títulos de los libros ordenados alfabéticamente 
    private TreeSet<String> titulosOrdenados;

    // Constructor que inicializa las colecciones
    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new HashMap<>();
        categorias = new HashSet<>();
        titulosOrdenados = new TreeSet<>();
    }

    // Método para agregar libro a la biblioteca
    public void agregarLibro(Libro libro) {
        libros.add(libro);
        titulosOrdenados.add(libro.getTitulo());
    }
    // Método para agregar usuario a la biblioteca

    public void agregarUsuario(int id, Usuario usuario) {
        usuarios.put(id, usuario);
    }
    // Método para  buscar un libro por su título 

    public Libro buscarLibro(String titulo) throws LibroNoEncontradoExcepcion {
        String tituloLower = titulo.toLowerCase();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().equals(tituloLower)) {
                return libro;
            }
        }
        throw new LibroNoEncontradoExcepcion("El libro no fue encontrado.");
    }
    // Método para  prestar un libro a un usuario 

    public void prestarLibro(String titulo, int usuarioId) throws LibroNoEncontradoExcepcion, LibroPrestadoExcepcion {
        String tituloLower = titulo.toLowerCase();
        Libro libro = buscarLibro(tituloLower);
        if (libro.esPrestado()) {
            throw new LibroPrestadoExcepcion("El libro ya está prestado.");
        }
        Usuario usuario = usuarios.get(usuarioId);
        if (usuario == null) {
            throw new LibroNoEncontradoExcepcion("Usuario no encontrado.");
        }
        libro.prestar(usuario);
    }

    // Método para  devolver  un libro prestado 
    public void devolverLibro(String titulo) throws LibroNoEncontradoExcepcion {
        String tituloLower = titulo.toLowerCase();
        Libro libro = buscarLibro(tituloLower);
        libro.devolver();
    }

    // Método para obtener la lista de libros
    public List<Libro> getLibros() {
        return libros;
    }

    // Método para obtener el mapa de usuarios
    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
    }
    // Método para agregar una categoría a la biblioteca

    public void agregarCategoria(String categoria) {
        categorias.add(categoria);
    }
    // Método para obtener las categorías de libros

    public HashSet<String> getCategorias() {
        return categorias;
    }
// Método para obtener los títulos de los libros ordenados alfabéticamente

    public TreeSet<String> getTitulosOrdenados() {
        return titulosOrdenados;
    }

// Método para guardar los usuarios en un archivo de texto
    public void guardarUsuariosEnArchivo() {
        try (FileWriter writer = new FileWriter("usuarios.txt")) {
            for (Usuario usuario : usuarios.values()) {
                writer.write("Nombre: " + usuario.getNombre() + ", ID: " + usuario.getId() + "\n");
            }
            System.out.println("Información de usuarios guardada en usuarios.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar la información de usuarios: " + e.getMessage());

        }

    }

}
