package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.mixin.AbstractCraftingGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.RecipeMatrixAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.common.Mod;

@Mod(RefinedPolymorphism.MODID)
public class RefinedPolymorphism {
    public static final String MODID = "refinedpolymorph";

    public RefinedPolymorphism() {
        PolymorphApi.getInstance().registerMenu(menu -> {
            if (menu instanceof AbstractCraftingGridContainerMenuAccessor craftingGrid
                    && craftingGrid.getCraftingGrid() instanceof BlockEntity be) {
                return be;
            }

            return null;
        });

        PolymorphApi.getInstance().registerBlockEntity(be -> {
            if (be instanceof CraftingGridBlockEntity craftingGrid) {
                return new CraftingGridRecipeData(craftingGrid);
            }

            return null;
        });
    }

    public static void onSelect(RecipeMatrixContainer container) {
        var matrix = (RecipeMatrixAccessor) ((MatrixAwareContainer) container).refpoly$getMatrix();

        if (matrix != null) {
            matrix.setCurrentRecipe(null);
            container.changed();
        }
    }
}
