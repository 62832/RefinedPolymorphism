package gripe._90.refinedpolymorph;

import com.google.common.base.Suppliers;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import com.refinedmods.refinedstorage.common.support.resource.ResourceCodecs;
import gripe._90.refinedpolymorph.data.CraftingGridRecipeData;
import gripe._90.refinedpolymorph.data.PatternGridRecipeData;
import gripe._90.refinedpolymorph.duck.MatrixAwareContainer;
import gripe._90.refinedpolymorph.mixin.accessor.AbstractCraftingGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.accessor.PatternGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.accessor.RecipeMatrixAccessor;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class RefinedPolymorphism {
    public static final String MODID = "refinedpolymorph";

    public static final Supplier<DataComponentType<ResourceAmount>> SELECTED_PATTERN_OUTPUT =
            Suppliers.memoize(() -> DataComponentType.<ResourceAmount>builder()
                    .persistent(ResourceCodecs.AMOUNT_CODEC)
                    .networkSynchronized(ResourceCodecs.AMOUNT_STREAM_CODEC)
                    .build());

    public static void registerRecipeData() {
        PolymorphApi.getInstance().registerMenu(menu -> {
            if (menu instanceof AbstractCraftingGridContainerMenuAccessor craftingGrid
                    && craftingGrid.getCraftingGrid() instanceof BlockEntity be) {
                return be;
            }

            if (menu instanceof PatternGridContainerMenuAccessor patternGrid
                    && patternGrid.getPatternGrid() instanceof BlockEntity be) {
                return be;
            }

            return null;
        });

        PolymorphApi.getInstance().registerBlockEntity(be -> {
            if (be instanceof CraftingGridBlockEntity craftingGrid) {
                return new CraftingGridRecipeData(craftingGrid);
            }

            if (be instanceof PatternGridBlockEntity patternGrid) {
                return new PatternGridRecipeData(patternGrid);
            }

            return null;
        });
    }

    public static void onSelect(@Nullable RecipeMatrixContainer container) {
        if (container == null) {
            return;
        }

        var matrix = (RecipeMatrixAccessor) ((MatrixAwareContainer) container).refpoly$getMatrix();

        if (matrix != null) {
            matrix.setCurrentRecipe(null);
            container.changed();
        }
    }
}
