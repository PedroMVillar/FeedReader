package parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/*Esta clase modela los atributos y metodos comunes a todos los distintos tipos de parser existentes en la aplicacion*/
public abstract class GeneralParser<T> {
    /**
     * Read or recive the input
     * @param path
     * @return
     * @throws IOException
     */
    protected abstract InputStream openSource(String path) throws IOException;
    
    /**
     * Convert bytes into T object
     * @param in
     * @return
     * @throws ParseException
     */
    protected abstract T doParse(InputStream in) throws ParseException;

    /**
     * Normalize type T 
     * @param parsed
     * @return
     */
    protected T normalize(T parsed) {
        return parsed;
    }

    /**
     * Parse de input to anything
     * @param sourcePath
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public final T parse(String sourcePath) throws IOException, ParseException {   
        try (InputStream in = openSource(sourcePath)) {
            in.reset();
            T parsed = doParse(in);
            return normalize(parsed);
        }
    }
}
