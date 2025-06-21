package gripe._90.refinedpolymorph.client;

import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridScreen;
import com.refinedmods.refinedstorage.common.grid.screen.CraftingGridScreen;

public class RefinedPolymorphismClient {
    public static void registerWidgets() {
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
