package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que encapsula todos los métodos que permiten la escritura y lectura de ficheros y binarios.
 * En la clase biblioteca se realizan llamadas a estos métodos para realizar diferentes acciones de gestión de datos.
 *
 * ---------------------------------------------------Nota------------------------------------------------------
 * Si ejecutas el programa en linux es probable que puedas encontrarte errores de que no lea bien los ficheros,
 * ya que ha sido desarrollado en windows y en el ordenador de clase con ubuntu he tenido algunos conflictos
 * con los ficheros al probar el programa.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */

public class GestionaFicheros implements Serializable{
    /**
     *Ya que en los métodos de escritura y lectura de fichero binario no se pide archivo File
     *Se declara en la clase una variable estática File con la ruta definida del bin.
     */
    private static final File  ficheroBin = new File("./files/biblioteca.bin");

    /**
     * Este método permite exportar datos a un fichero recorriendo los HashMaps y escribiéndolos en el fichero de texto seleccionado,
     * mediante un PrintWriter y dos bucles para recorrer y escribir en uno los autores y en otro los libros.
     *
     * @param f Ruta donde se creara el fichero.
     * @param autores   Se necesita crear una variable del objeto para poder guardar ahi los datos depositados en el HashMap
     * @param libros    y posteriormente hacer get a sus atributos en un PrintWriter para realizar la escritura,
     * @throws IOException Puede lanzar un error de entrada/salida.
     *
     * Ejemplo de como se ordena el fichero:
     *  ***AUT***
     *  1       (Id)
     *  manuel  (Nombre)
     *  gonzalez (Apellido)
     *  ***LIB***   Separación de libros con autores.
     *  89797456    (Isbn)
     *  lazarillo   (Título)
     *  1   (Id de autor)
     */
    public static void exportarFichero(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException{
        Autoria a = null;
        Libro libroCopia = null;
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            pw.println("***AUT***");             //Escritura de autores en el fichero
            for (int i = 1; i <= autores.size(); ++i) {
                a = autores.get(i);
                pw.println(a.getId());
                pw.println(a.getNombre());
                pw.println(a.getApellido());
            }
            pw.println("***LIB***");            //Escritura de libros en el fichero
            for (Map.Entry<String, Libro> l: libros.entrySet()) {
                libroCopia = l.getValue();
                pw.println(libroCopia.getIsbn());
                pw.println(libroCopia.getTitulo());
                a = libroCopia.getAutoria();
                pw.println(a.getId());
            }
            pw.close();
    }

    /**
     * Este método permite importar ficheros de una ruta definida mediante el archivo file a los Hashmap de autores y libros,
     * mediante un Buffered Reader y dos bucles while que tienen en cuenta la separación de autores y libros añadiendo primero los autores
     * y después los libros.
     *
     * @param f Ruta donde se creara el fichero.
     * @param autores Se piden los dos HashMap al añadir el método,
     * @param libros  para realizar las integraciones (put) correctamente
     * @throws IOException  Puede lanzar un error de entrada/salida.
     */
    public static void importarFicheros(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException {
        String id = null;
        String nombre = null;
        String apellido = null;
        Autoria var = null;
        BufferedReader br = new BufferedReader(new FileReader(f));
            id = br.readLine();  //Asi evitamos conflictos con ***AUT***
            while (!(id = br.readLine()).equals("***LIB***")) {
            nombre = br.readLine();
            apellido = br.readLine();
                autores.put(Integer.valueOf(id),new Autoria(Integer.valueOf(id),nombre,apellido));
            }
            while ((nombre = br.readLine()) != null) {       //Leemos libros y también reutilizamos variables para evitar consumo de procesos abundante.
                apellido = br.readLine();    //(Titulo)
                id = br.readLine();         //(Id del autor)
                    var = autores.get(Integer.valueOf(id));
                        libros.put(nombre,(new Libro(nombre,apellido,var)));
            }
            br.close();
    }

    /**
     *Este método permite guardas los datos del programa en un fichero binario mediante la ruta definida en la clase, ya que siempre se va a guardar en la misma posición
     * mediante un ObjectOutputStream se escribe primero el HashMap de autores y después el de libros.
     *
     * @param autores HashMap que almacena todas las variables de los autores.
     * @param libros  HashMap que almacena todas las variables de los libros.
     * @throws IOException Puede lanzar un error de entrada/salida.
     */
    public static void guardarBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroBin));
            oos.writeObject(autores);
            oos.writeObject(libros);
            oos.close();
    }

    /**
     * Este método se encarga de leer el fichero binario, depositado en la ruta marcada por la función File de la clase mediante un
     * ObjectInputStream se lee el fichero binario, y primero se guarda en una variable auxiliar de los HashMap realizando un casting
     * del mismo para posteriormente mediante el método (putAll) añadir a los HashMap del programa todas las variables obtenidas en los
     * HashMap auxiliares.
     *
     * @param autores   HashMap que almacena todas las variables de los autores.
     * @param libros    HashMap que almacena todas las variables de los libros.
     * @throws IOException Puede lanzar un error de entrada/salida.
     * @throws ClassNotFoundException Puede lanzar un error de clase no encontrada.
     */
    public static void leerBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroBin));
        HashMap<Integer, Autoria> autoresAUX = (HashMap<Integer, Autoria>) ois.readObject();
            autores.putAll(autoresAUX);
        HashMap<String, Libro> librosAux = (HashMap<String, Libro>) ois.readObject();
            libros.putAll(librosAux);
        ois.close();
    }
}
