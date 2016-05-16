package com.sag.wedt;

/**
 * Created by dobi on 16.05.16.
 */
public interface Directories {

    String trainingDir = "input_training/";
    String testingDir = "input_testing/";
    String dataDir = "data/";

    String outFile = "tweets.txt";
    String trainingFile = trainingDir + outFile;
    String modelFile = dataDir + "model.bin";
    String catFile = "categories.txt";
}
