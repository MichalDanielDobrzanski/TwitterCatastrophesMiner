package com.sag.wedt;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final String dir = "input/";
    private static final String catFile = "categories.txt";

    private static ArrayList<Category> categories;

    // czytanie kategorii z pliku
    private static void initCategories() {
        categories = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(dir + catFile));
            while (sc.hasNext()) {
                Category c = new Category(sc.next());
                c.addKeyWord(sc.next());
                c.addKeyWord(sc.next());
                categories.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initCategories();

//        String content = "Document that needs to categorized goes here";
//
//        try {
//            new Main().DocumentCategorizer(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Main twitterCategorizer = new Main();
        //twitterCategorizer.trainModel();
        //twitterCategorizer.classifyTweet("Have a nice day!");

        // Znajdowanie tweetow:
        //TweetFinder tf = new TweetFinder(categories);
        //tf.getTwitts();
        //tf.saveTwitts();


        // Uczenie modelu:
        TweetCategorizer tc = new TweetCategorizer(categories);
        tc.trainModel(2,30);

        // TODO: Usunac z plikow trenujacych puste linie oraz jakiekolwiek, ktore nie maja formatu:
        // <kategoria>" "<tresc>

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


}
