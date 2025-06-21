package gripe._90.refinedpolymorph.fabric;

import gripe._90.refinedpolymorph.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {
    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
