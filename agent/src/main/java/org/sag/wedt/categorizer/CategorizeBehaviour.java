package org.sag.wedt.categorizer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;
import org.sag.wedt.common.TweetCategories;
import org.sag.wedt.packets.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michał Breiter.
 */
public class CategorizeBehaviour extends CyclicBehaviour implements PacketObjectReceiver {
    private static final Logger logger = Logger.getJADELogger(CategorizeBehaviour.class.getName());

    private final ArrayList<AID> gathererAgents;

    public CategorizeBehaviour(ArrayList<AID> gathererAgents) {
        this.gathererAgents = gathererAgents;
    }

    @Override
    public void action() {
        PacketReceiver.listen(getAgent(), this).forObject(this);
    }

    public void onPacket(Serializable object) {
        CrawledTweet crawledTweet = (CrawledTweet) object;
        // classify

        // TODO CLASSIFY

        TweetCategories classifiedCategory = TweetCategories.OTHER;
        CategorizedTweet categorizedTweet = new CategorizedTweet(crawledTweet, classifiedCategory);
        logger.info("categorize got tweet" + crawledTweet.getStatus().getText() + " category" + classifiedCategory);
        // send to store
        CategorizeBehaviour.this.getAgent().send(PacketBuilder.inform().to(gathererAgents.toArray(new AID[gathererAgents.size()])).withContent(categorizedTweet).build());
    }
}
