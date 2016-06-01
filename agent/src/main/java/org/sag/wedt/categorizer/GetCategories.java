package org.sag.wedt.categorizer;

import jade.util.Logger;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.sag.wedt.common.TweetCategory;
import org.sag.wedt.packets.CategorizedTweet;
import org.sag.wedt.packets.CrawledTweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by dobi on 01.06.16.
 * Klasa posiadająca dostęp do modelu dokonująca klasyfikacji.
 * Singleton.
 */
public class GetCategories {
    private static final Logger logger = Logger.getJADELogger(GetCategories.class.getName());

    public static final String modelFile = "src/main/java/org/sag/wedt/common/model.bin";

    private DocumentCategorizerME documentCategorizer = null;

    private static GetCategories getCategories;

    public static GetCategories getInstance() {
        if (getCategories == null)
            getCategories = new GetCategories();
        return getCategories;
    }

    private GetCategories() {
        File test = new File(modelFile);
        String classificationModelFilePath = test.getAbsolutePath();
        DocumentCategorizerME classificationME = null;
        try {
            documentCategorizer = new DocumentCategorizerME(
                    new DoccatModel(
                            new FileInputStream(classificationModelFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CategorizedTweet categorize(CrawledTweet crawledTweet) {

        String tweetContent = crawledTweet.getStatus().getText();

        double[] classDistribution = documentCategorizer.categorize(tweetContent);

        String predictedCategory = documentCategorizer.getBestCategory(classDistribution);

        logger.log(Level.INFO,"Predicted category: " + predictedCategory + ". Actual category: " + crawledTweet.getCrawlerCategory());

        return new CategorizedTweet(crawledTweet,new TweetCategory(predictedCategory));
    }

}
