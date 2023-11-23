package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.refinedmods.refinedstorage.api.network.grid.IGrid;
import com.refinedmods.refinedstorage.blockentity.grid.GridBlockEntity;
import com.refinedmods.refinedstorage.container.GridContainerMenu;
import com.refinedmods.refinedstorage.screen.grid.GridScreen;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("refinedpolymorph")
public class RefinedPolymorphism {
    public RefinedPolymorphism() {
        var common = PolymorphApi.common();
        common.registerContainer2BlockEntity(
                menu -> menu instanceof GridContainerMenu grid ? grid.getBlockEntity() : null);
        common.registerBlockEntity2RecipeData(
                be -> be instanceof GridBlockEntity grid ? new GridBlockEntityRecipeData(grid) : null);

        if (FMLEnvironment.dist.isClient()) {
            Client.registerWidget();
        }
    }

    public static void onSelect(Recipe<?> recipe, IGrid grid) {
        if (recipe instanceof CraftingRecipe craftingRecipe) {
            ((CraftingGrid) grid).refinedpolymorph$setCurrentRecipe(craftingRecipe);
            grid.onCraftingMatrixChanged();
        }
    }

    private static class Client {
        private static void registerWidget() {
            var client = PolymorphApi.client();
            client.registerWidget(screen -> screen instanceof GridScreen grid
                    ? client.findCraftingResultSlot(grid)
                            .map(slot -> new GridRecipeWidget(grid, slot))
                            .orElse(null)
                    : null);
        }
    }
}
