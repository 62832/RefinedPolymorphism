package gripe._90.refinedpolymorph.mixin;

import java.util.List;
import java.util.Set;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class Plugin implements IMixinConfigPlugin {
    private boolean isModLoaded(String modId) {
        return ModList.get() == null
                ? LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(modId::equals)
                : ModList.get().isLoaded(modId);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("addons.WirelessCraftingGridMixin")) {
            return isModLoaded("refinedstorageaddons");
        }

        if (mixinClassName.contains("addons.RebornWirelessCraftingGridMixin")) {
            return isModLoaded("rebornstorage");
        }

        if (mixinClassName.contains("addons.WirelessUniversalGridMixin")) {
            return isModLoaded("universalgrid");
        }

        return true;
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
