package gripe._90.refinedpolymorph.mixin.accessor;

import com.refinedmods.refinedstorage.common.grid.AbstractCraftingGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.CraftingGrid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractCraftingGridContainerMenu.class)
public interface AbstractCraftingGridContainerMenuAccessor {
    @Accessor
    CraftingGrid getCraftingGrid();
}
