package org.sag.wedt.packets;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Michał Breiter.
 * based on http://ideaheap.com/2015/05/jade-setup-for-beginners/
 */
public class PacketBuilder {

    private final ACLMessage message;

    private PacketBuilder(int performative) {
        this.message = new ACLMessage(performative);
    }

    public static PacketBuilder inform() {
        return new PacketBuilder(ACLMessage.INFORM);
    }

    public PacketBuilder to(AID... recievers) {
        for (AID reciever : recievers) {
            message.addReceiver(reciever);
        }
        return this;
    }

    public PacketBuilder toLocal(String... otherAgentNames) {
        for (String agentName : otherAgentNames) {
            AID address = new AID(agentName, AID.ISLOCALNAME);
            message.addReceiver(address);
        }
        return this;
    }

    public PacketBuilder withContent(String content) {
        message.setContent(content);
        return this;
    }

    public PacketBuilder withContent(int i) {
        return withContent(Integer.toString(i));
    }

    public PacketBuilder withContent(Serializable o) {
        try {
            message.setContentObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ACLMessage build() {
        return message;
    }

}
