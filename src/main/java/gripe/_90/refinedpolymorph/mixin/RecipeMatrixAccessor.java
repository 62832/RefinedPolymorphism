package gripe._90.refinedpolymorph.mixin;

import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeMatrix.class)
public interface RecipeMatrixAccessor {
    @Accessor
    void setCurrentRecipe(Recipe<?> recipe);
}
