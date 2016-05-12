package com.sag.wedt;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.*;
import java.util.ArrayList;

/**
 * Klasa ucząca model
 */
public class TweetCategorizer {

    private static final String trainingDir = "openNLP/input_training/";

    private ArrayList<Category> categories;
    private DoccatModel model;

    public TweetCategorizer(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void trainModel(int cutoff, int trainingIterations) {
        InputStream dataIn = null;
        try {
            // dataIn = new FileInputStream(trainingDir + "05.09_23.44_tweets.txt");
            dataIn = new FileInputStream(trainingDir + "tweets.txt");
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);

            // Specifies the minimum number of times a feature must be seen

            TrainingParameters tp = new TrainingParameters();
            tp.put(TrainingParameters.CUTOFF_PARAM,Integer.toString(cutoff));
            tp.put(TrainingParameters.ITERATIONS_PARAM,Integer.toString(trainingIterations));

            model = DocumentCategorizerME.train("en", sampleStream, tp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataIn != null) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testModel() {


    }

    public void classifyTweet(String tweet) {
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(tweet);
        String category = myCategorizer.getBestCategory(outcomes);


    }

//    public void fixInputFile() {
//
//        File in = new File("openNLP/input/tweets.txt");
////        File out = new File("openNLP/input/tweets_copy.txt");
////        output = new BufferedWriter(new FileWriter(out, true));
////        // File
////
////        System.out.println(in.);
//
//    }

}
