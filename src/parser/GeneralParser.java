package parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/*Esta clase modela los atributos y metodos comunes a todos los distintos tipos de parser existentes en la aplicacion*/
public abstract class GeneralParser<T> {
    
    /** Ruta (o identificador) de la fuente a leer */
    protected final String sourcePath;
    
    /**
     * Constructor: recibe la ruta del recurso a parsear.
     * Todas las subclases heredarán y almacenarán esa ruta.
     */
    public GeneralParser(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    
    /**
     * Método principal que el cliente invoca.
     * @return instancia de T construida a partir del contenido
     * @throws Exception para propagar errores
     */
    public abstract T parse() throws Exception;
}
