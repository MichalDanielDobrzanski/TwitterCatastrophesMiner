package com.sag.wedt;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Main {

    DoccatModel model;

    public static void main(String[] args) {

//        String content = "Document that needs to categorized goes here";
//
//        try {
//            new Main().DocumentCategorizer(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Main twitterCategorizer = new Main();
        //twitterCategorizer.trainModel();
        //twitterCategorizer.classifyNewTweet("Have a nice day!");

        DisasterMiner dm = new DisasterMiner();

    }

    public void DocumentCategorizer(String text) throws IOException {

        File test = new File("Path to your en-doccat.bin model file");
        String classificationModelFilePath = test.getAbsolutePath();
        DocumentCategorizerME classificationME = new DocumentCategorizerME(
                new DoccatModel(
                        new FileInputStream(classificationModelFilePath)));
        String documentContent = text;
        double[] classDistribution = classificationME.categorize(documentContent);
        String predictedCategory = classificationME.getBestCategory(classDistribution);

        System.out.println("Model prediction : " + predictedCategory);

    }

    public void trainModel() {
        InputStream dataIn = null;
        try {
            dataIn = new FileInputStream("input/sample_tweets.txt");
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
            // Specifies the minimum number of times a feature must be seen
            int cutoff = 2;
            int trainingIterations = 30;

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

    public void classifyNewTweet(String tweet) {
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(tweet);
        String category = myCategorizer.getBestCategory(outcomes);

        if (category.equalsIgnoreCase("1")) {
            System.out.println("The tweet is positive :) ");
        } else {
            System.out.println("The tweet is negative :( ");
        }
    }




}
