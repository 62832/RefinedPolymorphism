package gripe._90.refinedpolymorph.fabric.client;

import gripe._90.refinedpolymorph.client.RefinedPolymorphismClient;
import net.fabricmc.api.ClientModInitializer;

public class RefinedPolymorphismFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RefinedPolymorphismClient.registerWidgets();
    }
}
