package biblioteca;

import java.io.Serializable;

/**
 * Clase que representa a un autor.
 * Con los métodos implementados en biblioteca se pueden realizar consultas, agregaciones y eliminaciones.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */

public class Autoria implements Serializable {

    private Integer id;
    private String nombre;
    private String apellido;

    public Autoria(Integer id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " Nombre: " + nombre +
                " Apellido: " + apellido ;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
