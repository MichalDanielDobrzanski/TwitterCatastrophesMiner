package org.sag.wedt.store;

import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;
import org.sag.wedt.packets.CategorizedTweet;
import org.sag.wedt.packets.PacketObjectReceiver;
import org.sag.wedt.packets.PacketReceiver;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 */
public class StoreBehaviour extends CyclicBehaviour implements PacketObjectReceiver {
    private static final Logger logger = Logger.getJADELogger(StoreBehaviour.class.getName());

    private final StoreAgent agent;

    private StoreTweets storeTweets = StoreTweets.getInstance();

    public StoreBehaviour(StoreAgent storeAgent) {
        this.agent = storeAgent;
    }

    public void action() {
        PacketReceiver.listen(getAgent(), this).forObject(this);
    }

    @Override
    public void onPacket(Serializable object) {

        CategorizedTweet categorizedTweet = (CategorizedTweet) object;
        // TODO actually store to persistent storage
        // TODO If enough update model
        logger.info("Agent " + getAgent().getAID().getLocalName() + " got tweet: \""
                + categorizedTweet.getCrawled().getStatus().getText() + "\"" + " locations: "
                );

        storeTweets.store(categorizedTweet);
    }
}
