package parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
//import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import parser.ParseException;
import subscription.Subscription;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import feed.*;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser<Feed> {

    private static final SimpleDateFormat RFC822_DATE =
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public RssParser(String jsonPath) { super(jsonPath); }


    /**
     * Parsea el contenido XML y genera el Feed.
     */
    public Feed parse() throws Exception {

        // 1. Construir el DOM a partir del String
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(sourcePath));
        Document doc = db.parse(is);
        doc.getDocumentElement().normalize();

        // 2. Extraer <channel> y su <title> como nombre del sitio
        Element channel = (Element) doc.getElementsByTagName("channel").item(0);
        String siteTitle = getTextContent(channel, "title");
        Feed feed = new Feed(siteTitle);

        NodeList items = channel.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);

            String title       = getTextContent(item, "title");
            String description = getTextContent(item, "description");
            String link        = getTextContent(item, "link");
            String pubDateStr  = getTextContent(item, "pubDate");

            // 4. Parsear la fecha
            Date pubDate = null;
            if (pubDateStr != null) {
                pubDate = RFC822_DATE.parse(pubDateStr);
            }

            // 5. Crear el artículo y añadirlo al Feed
            Article art = new Article(title, description, pubDate, link);
            feed.addArticle(art);
        }

        return feed;
    }

    /** Helper para leer el texto de la primera ocurrencia de una etiqueta */
    private String getTextContent(Element parent, String tagName) {
        NodeList nl = parent.getElementsByTagName(tagName);
        if (nl.getLength() > 0 && nl.item(0).getFirstChild() != null) {
            return nl.item(0).getTextContent().trim();
        }
        return null;
    }

}
