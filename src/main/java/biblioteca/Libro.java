package biblioteca;

import java.io.Serializable;

/**
 * Clase que representa a un libro.
 * Con los métodos implementados en biblioteca se pueden realizar consultas, agregaciones y eliminaciones.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */

public class Libro implements Serializable {
    private String isbn;
    private String titulo;
    private Autoria autoria;

    public Libro(String isbn, String titulo, Autoria autoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autoria = autoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autoria getAutoria() {
        return autoria;
    }

    @Override
    public String toString() {
        return "Isbn: " + isbn  +
                " Titulo: " + titulo  +
                " Autoria: " + autoria ;
    }
}
