package org.duh102.duhbot.consumer_example;

import org.duh102.duhbot.functions.ListeningPlugin;
import org.duh102.duhbot.functions.ServiceConsumerPlugin;
import org.duh102.duhbot.functions.ServiceMediator;
import org.duh102.duhbot.functions.ServiceResponse;
import org.duh102.duhbot.service_example.*;

import java.util.*;

public class ServiceConsumerTest implements ServiceConsumerPlugin {
    public static final String CONSUMER_NAME = "service-consumer-test";
    ServiceMediator mediator = null;

    @Override
    public void setInteraactionMediator(ServiceMediator serviceMediator) {
        this.mediator = serviceMediator;
    }

    @Override
    public String getPluginName() {
        return CONSUMER_NAME;
    }

    public Map<String, Integer> getInventory(String username) throws InventoryProblem {
        if( mediator == null) {
            return null;
        }
        try {
            ServiceResponse response =
                    mediator.interact(ServiceProviderExample.SERVICE_ENDPOINT,
                            InventoryViewEndpoint.PATH,
                            new InventoryViewRequest(username),
                            InventoryResponse.class);
            InventoryResponse inventoryResponse =
                    (InventoryResponse) response.getResponse();
            return inventoryResponse.get();
        } catch( InventoryProblem ip ) {
            throw ip;
        } catch( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Integer> modifyInventory(String username,
                                                String itemName,
                                                int countDiff) throws InventoryProblem {
        if( mediator == null) {
            return null;
        }
        try {
            ServiceResponse response =
                    mediator.interact(ServiceProviderExample.SERVICE_ENDPOINT,
                            InventoryChangeEndpoint.PATH,
                            new InventoryChangeRequest(username, itemName,
                                    countDiff),
                            InventoryResponse.class);
            InventoryResponse inventoryResponse =
                    (InventoryResponse)response.getResponse();
            return inventoryResponse.get();
        } catch( InventoryProblem ip ) {
            throw ip;
        } catch( Exception e){
            e.printStackTrace();;
            return null;
        }
    }
}
