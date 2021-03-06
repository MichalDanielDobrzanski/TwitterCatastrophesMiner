package org.sag.wedt.categorizer;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.util.Logger;
import org.sag.wedt.packets.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Michał Breiter.
 */
public class GetLocationsBehaviour extends OneShotBehaviour {
    private static final Logger logger = Logger.getJADELogger(GetLocationsBehaviour.class.getName());

    private final ArrayList<AID> storeAgents;
    private CategorizedTweet categorizedTweet;

    private GetLocations getLocations;

    public GetLocationsBehaviour(ArrayList<AID> storeAgents, CategorizedTweet categorizedTweet) {
        this.storeAgents = storeAgents;
        this.categorizedTweet = categorizedTweet;
    }

    @Override
    public void onStart() {
        // for why agent has model see comment in CategorizedAgent.getLocations
        CategorizerAgent categorizerAgent = (CategorizerAgent) getAgent();
        getLocations = categorizerAgent.getGetLocations();
    }

    @Override
    public void action() {
        String[] locations = getLocations.findLocations(categorizedTweet.getCrawled().getStatus().getText());
        categorizedTweet.setLocationNames(locations);

        GetLocationsBehaviour.this.getAgent().send(PacketBuilder.inform().
                to(storeAgents.toArray(new AID[storeAgents.size()])).withContent(categorizedTweet).build());
    }

}
