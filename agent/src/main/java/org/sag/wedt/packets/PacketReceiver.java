package org.sag.wedt.packets;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * Created by Michał Breiter.
 * based on http://ideaheap.com/2015/05/jade-setup-for-beginners/
 */
public class PacketReceiver {
    private final Agent agent;
    private final Behaviour behavior;

    private PacketReceiver(Agent agent, Behaviour behavior) {
        this.agent = agent;
        this.behavior = behavior;
    }

    public static PacketReceiver listen(Agent agent, Behaviour behavior) {
        return new PacketReceiver(agent, behavior);
    }

    public void forObject(PacketObjectReceiver contentReceiver) {
        ACLMessage message = agent.receive();
        if (message != null) {
            try {
                contentReceiver.onPacket(message.getContentObject());
            } catch (UnreadableException e) {
                e.printStackTrace();
                behavior.block();
            }
        } else {
            behavior.block();
        }
    }
}
