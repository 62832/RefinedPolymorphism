package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.common.capability.AbstractBlockEntityRecipeData;
import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

public class CraftingGridRecipeData extends AbstractBlockEntityRecipeData<CraftingGridBlockEntity> {
    public CraftingGridRecipeData(CraftingGridBlockEntity owner) {
        super(owner);
    }

    @Override
    protected NonNullList<ItemStack> getInput() {
        if (getOwner().hasLevel()) {
            var matrix = getOwner().getCraftingMatrix();
            var stacks = NonNullList.withSize(matrix.getContainerSize(), ItemStack.EMPTY);

            for (var i = 0; i < matrix.getContainerSize(); i++) {
                stacks.set(i, matrix.getItem(i));
            }

            return stacks;
        }

        return NonNullList.create();
    }

    @Override
    public void selectRecipe(@NotNull RecipeHolder<?> recipe) {
        super.selectRecipe(recipe);
        RefinedPolymorphism.onSelect(getOwner().getCraftingMatrix());
    }
}
