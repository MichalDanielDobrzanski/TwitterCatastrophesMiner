package org.sag.wedt.categorizer;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import org.sag.wedt.common.ServiceTypes;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by breiker on 5/22/16.
 */
public class CategorizerAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(CategorizerAgent.class.getName());
    private ArrayList<AID> gathererAgents = new ArrayList<AID>();

    void findGathererAgents() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(ServiceTypes.GATHERER.toTypeString());
        template.addServices(sd);
        try {
            SearchConstraints searchConstraints = new SearchConstraints(); // only one returned
            // not specified in documentation but milliseconds
            final long timeout = 120 * 1000;
            DFAgentDescription[] result = DFService.searchUntilFound(this, this.getDefaultDF(), template, searchConstraints, timeout);
            gathererAgents.clear();
            for(DFAgentDescription agent : result) {
                gathererAgents.add(agent.getName());
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void setup() {
        logger.log(Level.INFO, "setup for categorizer agent");

    }
}
