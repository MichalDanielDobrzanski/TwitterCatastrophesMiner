package org.sag.wedt.categorizer;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;
import org.sag.wedt.packets.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michał Breiter.
 */
public class CategorizeBehaviour extends CyclicBehaviour implements PacketObjectReceiver {
    private static final Logger logger = Logger.getJADELogger(CategorizeBehaviour.class.getName());

    private final ArrayList<AID> storeAgents;

    private GetCategories getCategories = GetCategories.getInstance();

    public CategorizeBehaviour(ArrayList<AID> storeAgents) {
        this.storeAgents = storeAgents;
    }

    @Override
    public void action() {
        PacketReceiver.listen(getAgent(), this).forObject(this);
    }

    public void onPacket(Serializable object) {
        CrawledTweet crawledTweet = (CrawledTweet) object;

        // classify
        CategorizedTweet categorizedTweet = getCategories.categorize(crawledTweet);

        logger.info("Categorize got tweet. Text: \"" + crawledTweet.getStatus().getText() + "\". Category: " + categorizedTweet.getCategory().getCategory());
        // send to store
        //CategorizeBehaviour.this.getAgent().send(PacketBuilder.inform().to(storeAgents.toArray(new AID[storeAgents.size()])).withContent(categorizedTweet).build());
        CategorizeBehaviour.this.getAgent().addBehaviour(new GetLocationsBehaviour(storeAgents, categorizedTweet));
    }
}
