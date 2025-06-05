package gripe._90.refinedpolymorph;

import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.Nullable;

public interface MatrixAwareContainer {
    @Nullable
    <T extends Recipe<I>, I extends RecipeInput> RecipeMatrix<T, I> refpoly$getMatrix();

    <T extends Recipe<I>, I extends RecipeInput> void refpoly$setMatrix(RecipeMatrix<T, I> matrix);
}
