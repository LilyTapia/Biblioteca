package cl.duoc.administracion_biblioteca;

import cl.duoc.administracion_biblioteca.libro.Libro;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroNoEncontradoExcepcion;
import cl.duoc.administracion_biblioteca.libro.excepcion.LibroPrestadoExcepcion;
import cl.duoc.administracion_biblioteca.usuario.Usuario;
import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Libro buscarLibro(String titulo) throws LibroNoEncontradoExcepcion {
        //Titulo minusculas
        String tituloLower = titulo.toLowerCase();
        //Recorrer la lista de los libros para buscar el libro por titulo
        for (Libro libro : libros) {
           //convertir el título del libro actual a minúsculas para la comparación 
            if (libro.getTitulo().toLowerCase().equals(tituloLower)) {
                return libro;
            }
            //Si no se encuentra libro, se lanza una excepcion
        }
        throw new LibroNoEncontradoExcepcion("El libro no fue encontrado.");
    }

    public void prestarLibro(String titulo) throws LibroNoEncontradoExcepcion, LibroPrestadoExcepcion {
        String tituloLower=titulo.toLowerCase();
        
        Libro libro = buscarLibro(tituloLower);
        if (libro.esPrestado()) {
            throw new LibroPrestadoExcepcion("El libro ya está prestado.");
        }
        libro.prestar();
    }

    public void devolverLibro(String titulo) throws LibroNoEncontradoExcepcion {
        String tituloLower=titulo.toLowerCase();
        Libro libro = buscarLibro(tituloLower);
        libro.devolver();
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
