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
public class StoreBehaviour extends CyclicBehaviour {
    private static final Logger logger = Logger.getJADELogger(StoreBehaviour.class.getName());
    private final StoreAgent agent;

    public StoreBehaviour(StoreAgent storeAgent) {
        this.agent = storeAgent;
    }

    public void action() {
        PacketReceiver.listen(getAgent(), this).forObject(new PacketObjectReceiver() {
            public void onPacket(Serializable object) {
                CategorizedTweet crawledTweet = (CategorizedTweet) object;
                // TODO actually store to persistent storage
                // TOD If enough update model
                logger.info(getAgent().getAID().getLocalName() + " got tweet:" + crawledTweet.getCrawled().getStatus().getText());
            }
        });
    }
}
