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
public class TweetCategorizer implements Directories {

    private ArrayList<Category> categories;
    private DoccatModel model;

    public TweetCategorizer(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void trainModel(int cutoff, int trainingIterations, String trainingFile) {
        InputStream dataIn = null;
        try {
            // dataIn = new FileInputStream(trainingDir + "05.09_23.44_tweets.txt");

            dataIn = new FileInputStream(trainingFile);

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
            System.out.println("Model trained.");
        }
    }

    public void saveModel() {
        OutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(modelFile));
            model.serialize(modelOut);
        } catch (IOException e) {
            // Failed to save model
            e.printStackTrace();
        } finally {
            if (modelOut != null) {
                try {
                    modelOut.close();
                } catch (IOException e) {
                    // Failed to correctly save model.
                    // Written model might be invalid.
                    e.printStackTrace();
                }
            }
            System.out.println("Model saved.");
        }
    }

    public void testModel(String tweet) {

        File test = new File(modelFile);
        String classificationModelFilePath = test.getAbsolutePath();
        DocumentCategorizerME classificationME = null;
        try {
            classificationME = new DocumentCategorizerME(
                    new DoccatModel(
                            new FileInputStream(classificationModelFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String documentContent = tweet;

        // caterorize tweet:
        double[] classDistribution = classificationME.categorize(documentContent);
        String predictedCategory = classificationME.getBestCategory(classDistribution);

        System.out.println("Model prediction : " + predictedCategory);

    }


}
