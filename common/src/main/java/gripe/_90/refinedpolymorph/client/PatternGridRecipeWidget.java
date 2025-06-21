package gripe._90.refinedpolymorph.client;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridScreen;
import gripe._90.refinedpolymorph.mixin.accessor.PatternGridContainerMenuAccessor;
import net.minecraft.world.inventory.ResultContainer;

public class PatternGridRecipeWidget extends AbstractGridRecipeWidget<PatternGridContainerMenu, PatternGridScreen> {
    public PatternGridRecipeWidget(PatternGridScreen screen) {
        super(screen);
    }

    @Override
    protected ResultContainer getResultContainer() {
        var accessed = (PatternGridContainerMenuAccessor) menu;
        return switch (accessed.callGetPatternType()) {
            case CRAFTING -> (ResultContainer) accessed.getCraftingResult();
            case SMITHING_TABLE -> (ResultContainer) accessed.getSmithingTableResult();
            default -> null;
        };
    }

    @Override
    public int getXPos() {
        return getOutputSlot() == null ? 0 : super.getXPos();
    }

    @Override
    public int getYPos() {
        return getOutputSlot() == null ? 0 : super.getYPos();
    }
}
