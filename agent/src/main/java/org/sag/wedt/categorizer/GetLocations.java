package org.sag.wedt.categorizer;

import jade.util.Logger;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Created by Michał Breiter.
 * Singleton.
 */
public class GetLocations {
    private static final Logger logger = Logger.getJADELogger(GetLocations.class.getName());

    public static final String tokenModelFile = "/en-token.bin";
    public static final String locationModelFile = "/en-ner-location.bin";


    private NameFinderME locationFinder = null;
    private TokenizerME tokenizer = null;

    private static GetLocations getLocations;

    public static GetLocations getInstance() {
        if (getLocations == null) {
            getLocations = new GetLocations();
        }
        return getLocations;
    }

    GetLocations() {
        InputStream locationModelStream = this.getClass().getResourceAsStream(locationModelFile);
        InputStream tokenModelStream = this.getClass().getResourceAsStream(tokenModelFile);
        // TODO to chyba powinien byc inny model niz TokenNameFinderModel i NameFinderMe
        try {
            TokenNameFinderModel model = new TokenNameFinderModel(locationModelStream);
            locationFinder = new NameFinderME(model);

            TokenizerModel tokenizerModel = new TokenizerModel(tokenModelStream);
            tokenizer = new TokenizerME(tokenizerModel);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                locationModelStream.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "nie mozna zamknac pliku z modelem " + locationModelFile);
            }
        }
    }

    String[] findLocations(String input) {
//        String tokens[] = tokenizer.tokenize(input);
//        Span[] span = locationFinder.find(tokens);
//        locationFinder.clearAdaptiveData(); // albo nie
//        for (int i = 0; i < span.length; i++) {
//            logger.log(Level.INFO, "Span: " + span[i].toString());
//        }
        return new String[] {};
    }
}
