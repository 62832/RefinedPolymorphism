package gripe._90.refinedpolymorph.client;

import com.refinedmods.refinedstorage.common.grid.AbstractCraftingGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.screen.CraftingGridScreen;
import gripe._90.refinedpolymorph.mixin.accessor.AbstractCraftingGridContainerMenuAccessor;
import net.minecraft.world.inventory.ResultContainer;

public class CraftingGridRecipeWidget
        extends AbstractGridRecipeWidget<AbstractCraftingGridContainerMenu, CraftingGridScreen> {
    public CraftingGridRecipeWidget(CraftingGridScreen screen) {
        super(screen);
    }

    @Override
    protected ResultContainer getResultContainer() {
        return ((AbstractCraftingGridContainerMenuAccessor) menu)
                .getCraftingGrid()
                .getCraftingResult();
    }
}
