import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import feed.Feed;
import httpRequest.httpRequester;
import parser.RedditParser;
import parser.RssParser;
import parser.SubscriptionParser;
//import subscription.SingleSubscription;
import subscription.*;

public class FeedReaderMain {
	/**
	 * Prints command usage help.
	 */
	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}

	/**
     * Dado un objeto Subscription (con su lista de SingleSubscription),
     * recorre todas las suscripciones y descarga su feed.
     * Devuelve un Map<SingleSubscription, String> con la respuesta cruda.
     */
	private static Map<SingleSubscription, String> fetchAllFeeds(Subscription subs, httpRequester requester){
		Map<SingleSubscription, String> feeds = new LinkedHashMap<>();
		try {
			for (SingleSubscription s : subs.getSubscriptionsList()) {
				for (int i = 0; i < s.getUlrParamsSize(); i++) {
					String feedUrl = s.getFeedToRequest(i);
					String raw;
					// según el tipo de URL, uso un método u otro
					if ("rss".equalsIgnoreCase(s.getUrlType())) {
						raw = requester.getFeedRss(feedUrl);
					} else if ("reddit".equalsIgnoreCase(s.getUrlType())) {
						raw = requester.getFeedReedit(feedUrl);
					} else {
						throw new IllegalArgumentException("Tipo desconocido: " + s.getUrlType());
					}
					feeds.put(s, raw);
				}
			}
		} catch (Exception e) {
            System.err.println("Error al parsear suscripciones: " + e.getMessage());
            e.printStackTrace();
        }
		return feeds;	
	}	


	public static void main(String[] args) {
		System.out.println("************* FeedReader version 1.0 *************");
		if (args.length == 0) {
			try {
				/* -------------------- Leer el archivo de suscription por defecto; --------------------- */
				// Instancio el parser para Suscription
				SubscriptionParser parser = new SubscriptionParser("config/subscriptions.json");
				/* -------------------------------------------------------------------------------------- */

				/* -------------- Llamar al httpRequester para obtenr el feed del servidor -------------- */
        		Subscription subs = parser.parse();
				httpRequester requester = new httpRequester();
				/* Recibo mis resultados de las peticiones en un map < Petición , Respuesta >  */
				Map<SingleSubscription,String> feeds = fetchAllFeeds(subs, requester);
				/* -------------------------------------------------------------------------------------- */

				/* -- Llamar al Parser especifico para extrar los datos necesarios por la aplicacion  --- */
				for (Map.Entry<SingleSubscription,String> entry : feeds.entrySet()) {
    				SingleSubscription sub = entry.getKey();
    				String rawBody          = entry.getValue();
					
					if ("rss".equalsIgnoreCase(sub.getUrlType())) {
						// Creo el parser pasándole el XML en rawBody
						RssParser rssParser = new RssParser(rawBody);
						// Parseo a Feed
						Feed feed = rssParser.parse();
						// Usamos el Feed ----------- VER ACÁ QUE ONDA --------------
						feed.prettyPrint();
					}
					else if ("reedit".equalsIgnoreCase(sub.getUrlType())) {
						// Creo el parser pasándole el JSON en rawBody
						RedditParser redditParser = new RedditParser(rawBody);
						// Parseo a Feed
						Feed feed = redditParser.parse();
						// Usamos el Feed ----------- VER ACÁ QUE ONDA --------------
						feed.prettyPrint();
					}
				}
				/* -------------------------------------------------------------------------------------- */

			} catch (Exception e) {
				// TODO: handle exception
			}
			/*
			Leer el archivo de suscription por defecto;
			Llamar al httpRequester para obtenr el feed del servidor
			Llamar al Parser especifico para extrar los datos necesarios por la aplicacion 
			Llamar al constructor de Feed
			LLamar al prettyPrint del Feed para ver los articulos del feed en forma legible y amigable para el usuario
			*/
			
		} else if (args.length == 1){
			
			/*
			Leer el archivo de suscription por defecto;
			Llamar al httpRequester para obtenr el feed del servidor
			Llamar al Parser especifico para extrar los datos necesarios por la aplicacion 
			Llamar al constructor de Feed
			Llamar a la heuristica para que compute las entidades nombradas de cada articulos del feed
			LLamar al prettyPrint de la tabla de entidades nombradas del feed.
			 */
			
		}else {
			printHelp();
		}
	}

}
