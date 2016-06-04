package org.sag.wedt.categorizer;

import jade.util.Logger;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

/**
 * Created by Michał Breiter.
 * Singleton.
 */
public class GetLocations {
    private static final Logger logger = Logger.getJADELogger(GetLocations.class.getName());

    public static final String tokenModelFile = "/en-token.bin";
    public static final String sentenceModelFile = "/en-sent.bin";
    public static final String locationModelFile = "/en-ner-location.bin";

    private NameFinderME locationFinder = null;
    private TokenizerME tokenizer = null;
    private SentenceDetectorME sentenceDetector = null;

    GetLocations() throws IOException {
        InputStream locationModelStream = this.getClass().getResourceAsStream(locationModelFile);
        InputStream sentenceModelStream = this.getClass().getResourceAsStream(sentenceModelFile);
        InputStream tokenModelStream = this.getClass().getResourceAsStream(tokenModelFile);
        /** Uzywamy tych modeli i tokenizerow, bo chyba takie zostaly uzyte do tworzenia originalnego modelu
         *  en-ner-location
         */
        try {
            TokenNameFinderModel model = new TokenNameFinderModel(locationModelStream);
            locationFinder = new NameFinderME(model);

            SentenceModel sentenceModel = new SentenceModel(sentenceModelStream);
            sentenceDetector = new SentenceDetectorME(sentenceModel);

            TokenizerModel tokenizerModel = new TokenizerModel(tokenModelStream);
            tokenizer = new TokenizerME(tokenizerModel);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                locationModelStream.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "nie mozna zamknac pliku z modelem " + locationModelFile);
            }
        }
    }

    String[] findLocations(String input) {
        String sentences[] = sentenceDetector.sentDetect(input);
        ArrayList<String> loc = new ArrayList<>();
        for(String sentence: sentences) {
            String[] tokens = tokenizer.tokenize(sentence);
            Span[] spans = locationFinder.find(tokens);
            String[] foundLocations = Span.spansToStrings(spans, tokens);
            Collections.addAll(loc, foundLocations);
        }

        String result[] = new String[loc.size()];
        result = loc.toArray(result);

        locationFinder.clearAdaptiveData(); // Tak
        return result;
    }
}
