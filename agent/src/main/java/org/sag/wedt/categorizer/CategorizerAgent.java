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
 * Created by Michał Breiter.
 */
public class CategorizerAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(CategorizerAgent.class.getName());
    private ArrayList<AID> storeAgents = new ArrayList<AID>();

    /** Find store agents.
     * TODO not used now, change hardcoded to discovery
     */
    void findStoreAgents() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(ServiceTypes.GATHERER.toTypeString());
        template.addServices(sd);
        try {
            SearchConstraints searchConstraints = new SearchConstraints(); // only one returned
            // not specified in documentation but milliseconds
            final long timeout = 120 * 1000;
            DFAgentDescription[] result = DFService.searchUntilFound(this, this.getDefaultDF(), template, searchConstraints, timeout);
            storeAgents.clear();
            for(DFAgentDescription agent : result) {
                storeAgents.add(agent.getName());
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void setup() {
        final String storeAgentName = (String) this.getArguments()[0];
        storeAgents.add(new AID(storeAgentName, AID.ISLOCALNAME));
        logger.log(Level.INFO, "setup for categorizer agent" + storeAgents + " all: " + storeAgents.size());
        addBehaviour(new CategorizeBehaviour(storeAgents));
    }
}
