package gripe._90.refinedpolymorph.neoforge.client;

import gripe._90.refinedpolymorph.RefinedPolymorphism;
import gripe._90.refinedpolymorph.client.RefinedPolymorphismClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = RefinedPolymorphism.MODID, dist = Dist.CLIENT)
public class RefinedPolymorphismNeoForgeClient {
    public RefinedPolymorphismNeoForgeClient() {
        RefinedPolymorphismClient.registerWidgets();
    }
}
