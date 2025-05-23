package parser;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import feed.Article;
import feed.Feed;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 * */

public class RedditParser extends GeneralParser<Feed> {
    /**
     * Constructor: recibe el JSON del feed como String.
     */
    public RedditParser(String jsonBody) {
        super(jsonBody);
    }

    /**
     * Parsea el JSON de Reddit y devuelve un Feed con sus Articles.
     */
    @Override
    public Feed parse() throws Exception {
        // 1) Parseo inicial del JSON
        JSONObject root = new JSONObject(sourcePath);
        JSONObject data = root.getJSONObject("data");
        JSONArray children = data.getJSONArray("children");

        // 2) Obtener nombre del subreddit (si existe)
        String subreddit = "reddit";
        if (children.length() > 0) {
            JSONObject first = children.getJSONObject(0).getJSONObject("data");
            subreddit = first.optString("subreddit", subreddit);
        }
        Feed feed = new Feed(subreddit);

        // 3) Iterar cada post y mapear a Article
        for (int i = 0; i < children.length(); i++) {
            JSONObject postData = children.getJSONObject(i).getJSONObject("data");

            String title = postData.optString("title");
            String description = postData.optString("selftext");

            long createdUtc = postData.optLong("created_utc", 0L);
            Date pubDate = new Date(createdUtc * 1000);

            // Usar permalink para link al post, si está disponible
            String permalink = postData.optString("permalink", null);
            String link;
            if (permalink != null && !permalink.isEmpty()) {
                link = "https://reddit.com" + permalink;
            } else {
                link = postData.optString("url", null);
            }

            Article article = new Article(title, description, pubDate, link);
            feed.addArticle(article);
        }

        return feed;
    }
}
