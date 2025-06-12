package gripe._90.refinedpolymorph.mixin.duck;

import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.duck.MatrixAwareContainer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RecipeMatrixContainer.class)
public abstract class RecipeMatrixContainerMixin implements MatrixAwareContainer {
    @Unique
    private RecipeMatrix<?, ?> refpoly$matrix;

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T extends Recipe<I>, I extends RecipeInput> RecipeMatrix<T, I> refpoly$getMatrix() {
        return (RecipeMatrix<T, I>) refpoly$matrix;
    }

    @Override
    public <T extends Recipe<I>, I extends RecipeInput> void refpoly$setMatrix(RecipeMatrix<T, I> matrix) {
        refpoly$matrix = matrix;
    }
}
