package parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
//import java.text.ParseException;
import java.util.List;

import parser.ParseException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import feed.*;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser<Feed> {
    
    /** Abre la “fuente” tomando directamente el XML como String */
    @Override
    protected InputStream openSource(String rawXml) {
        return new ByteArrayInputStream(rawXml.getBytes(StandardCharsets.UTF_8));
    }

    /** Parseo real: de InputStream → Feed */
    @Override
    protected Feed doParse(InputStream in) throws ParseException {
        
    }

}
