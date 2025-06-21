package gripe._90.refinedpolymorph.data;

import com.illusivesoulworks.polymorph.common.capability.AbstractBlockEntityRecipeData;
import com.refinedmods.refinedstorage.common.grid.AbstractGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.RefinedPolymorphism;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGridRecipeData<G extends AbstractGridBlockEntity>
        extends AbstractBlockEntityRecipeData<G> {
    public AbstractGridRecipeData(BlockEntity owner) {
        super(owner);
    }

    @Nullable
    protected abstract RecipeMatrixContainer getMatrix();

    @Override
    protected NonNullList<ItemStack> getInput() {
        if (getOwner().hasLevel()) {
            var matrix = getMatrix();

            if (matrix != null) {
                var stacks = NonNullList.withSize(matrix.getContainerSize(), ItemStack.EMPTY);

                for (var i = 0; i < matrix.getContainerSize(); i++) {
                    stacks.set(i, matrix.getItem(i));
                }

                return stacks;
            }
        }

        return NonNullList.create();
    }

    @Override
    public void selectRecipe(@NotNull RecipeHolder<?> recipe) {
        super.selectRecipe(recipe);
        RefinedPolymorphism.onSelect(getMatrix());
    }
}
