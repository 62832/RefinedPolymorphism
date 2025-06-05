package gripe._90.refinedpolymorph.client;

import com.illusivesoulworks.polymorph.api.client.base.PersistentRecipesWidget;
import com.refinedmods.refinedstorage.common.grid.screen.CraftingGridScreen;
import gripe._90.refinedpolymorph.mixin.AbstractCraftingGridContainerMenuAccessor;
import net.minecraft.world.inventory.Slot;

public class CraftingGridRecipeWidget extends PersistentRecipesWidget {
    private Slot outputSlot;

    public CraftingGridRecipeWidget(CraftingGridScreen gridScreen) {
        super(gridScreen);
        var menu = gridScreen.getMenu();

        for (var slot : menu.slots) {
            if (slot.container
                    == ((AbstractCraftingGridContainerMenuAccessor) menu)
                            .getCraftingGrid()
                            .getCraftingResult()) {
                outputSlot = slot;
                break;
            }
        }
    }

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }
}
