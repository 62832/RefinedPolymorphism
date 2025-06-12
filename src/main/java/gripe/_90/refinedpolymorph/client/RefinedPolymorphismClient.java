package gripe._90.refinedpolymorph.client;

import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridScreen;
import com.refinedmods.refinedstorage.common.grid.screen.CraftingGridScreen;
import gripe._90.refinedpolymorph.RefinedPolymorphism;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = RefinedPolymorphism.MODID, dist = Dist.CLIENT)
public class RefinedPolymorphismClient {
    public RefinedPolymorphismClient() {
        PolymorphWidgets.getInstance().registerWidget(screen -> {
            if (screen instanceof CraftingGridScreen craftingGrid) {
                return new CraftingGridRecipeWidget(craftingGrid);
            }

            if (screen instanceof PatternGridScreen patternGrid) {
                return new PatternGridRecipeWidget(patternGrid);
            }

            return null;
        });
    }
}
