package parser;

import java.io.IOException;
import java.io.InputStream;

import feed.*;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser<Feed> {

    @Override
    protected InputStream openSource(String path) throws IOException {

    }

    
}
