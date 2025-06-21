package gripe._90.refinedpolymorph.data;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternType;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.mixin.accessor.PatternGridBlockEntityAccessor;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

public class PatternGridRecipeData extends AbstractGridRecipeData<PatternGridBlockEntity> {
    private final Map<PatternType, RecipeHolder<?>> lastRecipeByType = new EnumMap<>(PatternType.class);

    public PatternGridRecipeData(PatternGridBlockEntity owner) {
        super(owner);
    }

    @Override
    public RecipeMatrixContainer getMatrix() {
        var accessed = (PatternGridBlockEntityAccessor) getOwner();
        return switch (accessed.getPatternType()) {
            case CRAFTING -> accessed.callGetCraftingMatrix();
            case SMITHING_TABLE -> accessed.callGetSmithingTableMatrix();
            default -> null;
        };
    }

    @Override
    public void selectRecipe(@NotNull RecipeHolder<?> recipe) {
        super.selectRecipe(recipe);
        lastRecipeByType.put(((PatternGridBlockEntityAccessor) getOwner()).getPatternType(), recipe);
    }

    public void switchPatternType(PatternType type) {
        selectRecipe(lastRecipeByType.get(type));
    }
}
