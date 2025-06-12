package gripe._90.refinedpolymorph;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.mixin.accessor.PatternGridBlockEntityAccessor;

public class PatternGridRecipeData extends AbstractGridRecipeData<PatternGridBlockEntity> {
    public PatternGridRecipeData(PatternGridBlockEntity owner) {
        super(owner);
    }

    @Override
    protected RecipeMatrixContainer getMatrix() {
        var accessed = (PatternGridBlockEntityAccessor) getOwner();
        return switch (accessed.getPatternType()) {
            case CRAFTING -> accessed.callGetCraftingMatrix();
            case SMITHING_TABLE -> accessed.callGetSmithingTableMatrix();
            default -> null;
        };
    }
}
