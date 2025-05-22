package parser;

/**
 * Excepción que señalan errores durante el parseo en tus parsers.
 */
public class ParseException extends Exception {

    /** Constructor que recibe un mensaje de error. */
    public ParseException(String message) {
        super(message);
    }

    /** Constructor que recibe un mensaje de error y la causa original. */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}