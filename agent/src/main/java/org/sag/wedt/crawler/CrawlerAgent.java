package org.sag.wedt.crawler;

import jade.core.Agent;
import jade.util.Logger;

/**
 * Created by breiker on 5/22/16.
 */
public class CrawlerAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(CrawlerAgent.class.getName());

    @Override
    public void setup() {
        final String category = (String) this.getArguments()[0];
        addBehaviour(new CrawlTwitterBehaviour(this, category));
        // TODO whitepage
    }

    @Override
    public void takeDown() {
    }
}
