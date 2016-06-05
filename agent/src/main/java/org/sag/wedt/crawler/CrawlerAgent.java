package org.sag.wedt.crawler;

import jade.core.Agent;
import jade.util.Logger;

/**
 * Created by Michał Breiter.
 */
public class CrawlerAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(CrawlerAgent.class.getName());

    @Override
    public void setup() {
        final String categorizerAgentName = (String) this.getArguments()[0];
        final String category = (String) this.getArguments()[1];
        final String keywords[] = new String[2];
        keywords[0] = (String) this.getArguments()[2];
        keywords[1] =  (String) this.getArguments()[3];
        logger.info("CrawlerAgent setup cat:" + category + " agent name: " + categorizerAgentName);
        addBehaviour(new CrawlTwitterBehaviour(this, category, categorizerAgentName, keywords));
    }

    @Override
    public void takeDown() {
    }
}
