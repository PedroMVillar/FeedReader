package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

import parser.ParseException;
/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */
public class SubscriptionParser extends GeneralParser<Subscription> {

    private String jsonPath;

    /** Abrir la “fuente” desde un fichero */
    @Override
    protected InputStream openSource(String sourcePath) throws IOException {
        this.jsonPath = sourcePath;
        return new FileInputStream(sourcePath);
    }

    /** Parseo real: de InputStream → Subscription */
    @Override
    protected Subscription doParse(InputStream in) throws ParseException {
        
    }

    /** Post‐proceso / normalización (opcional) */
    @Override
    protected Subscription normalize(Subscription parsed) {
        return parsed;
    }
}