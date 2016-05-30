package org.sag.wedt.store;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import org.sag.wedt.common.ServiceTypes;

import static java.util.logging.Level.INFO;

/**
 * Created by Michał Breiter.
 */
public class StoreAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(StoreAgent.class.getName());

    @Override
    public void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(ServiceTypes.GATHERER.toTypeString());
        sd.setName("store");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace(); // TODO take down
        }
        final String category = (String) this.getArguments()[0];
        addBehaviour(new StoreBehaviour(this));
    }

    @Override
    public void takeDown() {
        logger.log(INFO, "agent " + getAID().toString() + " take down");
        // remove ourselves from yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}

