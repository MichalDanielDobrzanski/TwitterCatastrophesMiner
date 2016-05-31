package org.sag.wedt.crawler;

import jade.core.behaviours.OneShotBehaviour;
import jade.util.Logger;
import org.sag.wedt.packets.CrawledTweet;
import org.sag.wedt.packets.PacketBuilder;

/**
 * Created by Michał Breiter.
 */
public class SendTwittsBehaviour extends OneShotBehaviour {
    private static final Logger logger = Logger.getJADELogger(SendTwittsBehaviour.class.getName());

    private final CrawledTweet crawledTweet;
    private final String categorizerAgentName;

    public SendTwittsBehaviour(CrawledTweet crawledTweet, String categorizerAgentName) {
        this.crawledTweet = crawledTweet;
        this.categorizerAgentName = categorizerAgentName;
    }

    public void action() {
        logger.info("sending tweet from " + crawledTweet.getStatus().getUser().getName() + " to " + categorizerAgentName);
        getAgent().send(PacketBuilder.inform().toLocal(categorizerAgentName).withContent(crawledTweet).build());
    }
}
