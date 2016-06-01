package org.sag.wedt.store;

import jade.util.Logger;
import org.sag.wedt.packets.CategorizedTweet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;


/**
 * Created by dobi on 01.06.16.
 * Klasa obługująca pojedynczy plik, do którego są zapisywane kolejne Tweety.
 * Singleton.
 */
public class StoreTweets {
    private static final Logger logger = Logger.getJADELogger(StoreBehaviour.class.getName());

    public static final String outFile = "src/main/java/org/sag/wedt/common/result.txt";

    private static StoreTweets storeTweets;

    public static StoreTweets getInstance() {
        if (storeTweets == null)
            storeTweets = new StoreTweets();
        return storeTweets;
    }

    public void store(CategorizedTweet categorizedTweet) {

        // assigned category | initial category | tweet content
        String output = categorizedTweet.getCategory().getCategory() + " " +
                categorizedTweet.getCrawled().getCrawlerCategory() + " " +
                proceesTweetText(categorizedTweet.getCrawled().getStatus().getText()) + "\n";

        File file = new File(outFile);
        try {
            // using a thread-safe library for accessing a single file.
            // http://stackoverflow.com/questions/7366266/best-way-to-write-string-to-file-using-java-nio
            Files.write(Paths.get(file.toURI()),
                    output.getBytes("utf-8"),
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO,"Stored a Tweet.");
    }

    /**
     * Removing new line characters, userNames and https suffixes...
     */
    private String proceesTweetText(String tweetText) {
        // remove new lines
        tweetText = tweetText.replace("\n","").replace("\r","");
        // remove urls "https://...":
        tweetText = tweetText.replaceAll("https?://\\S+\\s?", "");
        // remove users "@...":
        tweetText = tweetText.replaceAll("@\\S+\\s?", "");
        return tweetText;
    }

}
