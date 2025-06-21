package gripe._90.refinedpolymorph.neoforge;

import gripe._90.refinedpolymorph.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforgespi.language.IModInfo;

public class NeoForgePlatform implements Platform {
    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get() == null
                ? LoadingModList.get().getMods().stream()
                        .map(IModInfo::getModId)
                        .anyMatch(modId::equals)
                : ModList.get().isLoaded(modId);
    }
}
