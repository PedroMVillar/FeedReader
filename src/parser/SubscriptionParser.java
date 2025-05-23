package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

import org.json.*;

import parser.ParseException;
/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */
public class SubscriptionParser extends GeneralParser<Subscription> {
    public SubscriptionParser(String jsonPath) { super(jsonPath); }

    @Override
    public Subscription parse() throws Exception {
        JSONArray array = readJsonArray();                     // 1. leer archivo
        return mapToSubscription(array);                       // 2. iterar y mapear
    }

    /** Abre el archivo y devuelve el JSONArray completo */
    private JSONArray readJsonArray() throws IOException, JSONException {
        try (FileReader fr = new FileReader(sourcePath)) {
            JSONTokener tokener = new JSONTokener(fr);
            return new JSONArray(tokener);
        }
    }

    /** Construye el objeto Subscription a partir del JSONArray */
    private Subscription mapToSubscription(JSONArray array) {
        Subscription subs = new Subscription(sourcePath);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            subs.addSingleSubscription(mapSingle(obj));
        }
        return subs;
    }

    /** Mapea un solo JSONObject a SingleSubscription */
    private SingleSubscription mapSingle(JSONObject obj) {
        String url      = obj.getString("url");
        String type     = obj.getString("urlType");
        JSONArray params = obj.optJSONArray("urlParams");
        SingleSubscription s = new SingleSubscription(url, null, type);
        if (params != null) {
            for (int j = 0; j < params.length(); j++) {
                s.setUlrParams(params.getString(j));
            }
        }
        return s;
    }

}