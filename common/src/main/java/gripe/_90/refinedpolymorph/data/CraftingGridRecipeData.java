package gripe._90.refinedpolymorph.data;

import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;

public class CraftingGridRecipeData extends AbstractGridRecipeData<CraftingGridBlockEntity> {
    public CraftingGridRecipeData(CraftingGridBlockEntity owner) {
        super(owner);
    }

    @Override
    protected RecipeMatrixContainer getMatrix() {
        return getOwner().getCraftingMatrix();
    }
}
