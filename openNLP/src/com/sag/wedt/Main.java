package com.sag.wedt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main implements Directories {

    private static ArrayList<Category> categories;

    // czytanie kategorii z pliku
    private static void initCategories() {
        categories = new ArrayList<>();
//        File f = new File(dir + catFile);
//        System.out.println(f.getAbsolutePath());
        try {
            Scanner sc = new Scanner(new File(dataDir + catFile));
            while (sc.hasNext()) {
                String cat = sc.next();
                Category c = new Category(cat);
                c.addKeyWord(sc.next());
                if (!cat.equals("Other"))
                    c.addKeyWord(sc.next());
                categories.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initCategories();

        // Znajdowanie tweetow:
//        TweetFinder tf = new TweetFinder(categories);
//        tf.getTwitts();
//        tf.saveTwitts();

//        // Uczenie modelu:
        TweetCategorizer tc = new TweetCategorizer(categories);
        tc.trainModel(2,30,trainingFile);
        tc.saveModel();
//
//        // Testowanie modelu:
        tc.testModel("totally irrelevant crap");
        tc.testModel("how can i enter.");
        tc.testModel("love the title");
        tc.testModel("Big sad crash of two cars. Two people died");
        tc.testModel("Fire Fire Fire something else car disaster kaboom!");

        System.out.println("End");
    }


}
