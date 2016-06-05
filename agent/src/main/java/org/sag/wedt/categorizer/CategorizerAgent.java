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

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Michał Breiter.
 */
public class CategorizerAgent extends Agent {
    private static final Logger logger = Logger.getJADELogger(CategorizerAgent.class.getName());
    private ArrayList<AID> storeAgents = new ArrayList<AID>();


    /** OpenNLP finder and tokenizer cannot be shared between threads. If we are starting more than one agent in
     * same container we risk some problems. GetLocationBehaviour is created on demand so we have to keep cached instance
     * in agent (or some thread local variable)
     */
    private GetLocations getLocations = new GetLocations();

    public CategorizerAgent() throws IOException {
    }

    public GetLocations getGetLocations() {
        return getLocations;
    }


    @Override
    protected void setup() {
        final String storeAgentName = (String) this.getArguments()[0];
        storeAgents.add(new AID(storeAgentName, AID.ISLOCALNAME));
        logger.log(Level.INFO, "setup for categorizer agent" + storeAgents + " all: " + storeAgents.size());

        addBehaviour(new CategorizeBehaviour(storeAgents));
    }

}
