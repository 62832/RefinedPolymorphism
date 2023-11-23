package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.common.capability.AbstractBlockEntityRecipeData;
import com.refinedmods.refinedstorage.blockentity.grid.GridBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

public class GridBlockEntityRecipeData extends AbstractBlockEntityRecipeData<GridBlockEntity> {
    public GridBlockEntityRecipeData(GridBlockEntity owner) {
        super(owner);
    }

    @Override
    protected NonNullList<ItemStack> getInput() {
        if (getOwner().hasLevel()) {
            var matrix = getOwner().getNode().getCraftingMatrix();

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
    public void selectRecipe(@NotNull Recipe<?> recipe) {
        super.selectRecipe(recipe);
        RefinedPolymorphism.onSelect(recipe, getOwner().getNode());
    }
}
