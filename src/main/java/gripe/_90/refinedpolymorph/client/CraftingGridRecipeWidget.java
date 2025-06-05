package gripe._90.refinedpolymorph.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.client.base.PersistentRecipesWidget;
import com.refinedmods.refinedstorage.common.grid.screen.CraftingGridScreen;
import gripe._90.refinedpolymorph.GridRecipeSelectPacket;
import gripe._90.refinedpolymorph.mixin.AbstractCraftingGridContainerMenuAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

public class CraftingGridRecipeWidget extends PersistentRecipesWidget {
    private Slot outputSlot;

    public CraftingGridRecipeWidget(CraftingGridScreen screen) {
        super(screen);
        var menu = (AbstractCraftingGridContainerMenuAccessor) screen.getMenu();

        for (var slot : screen.getMenu().slots) {
            if (slot.container == menu.getCraftingGrid().getCraftingResult()) {
                outputSlot = slot;
                break;
            }
        }
    }

    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation recipeId) {
        super.selectRecipe(recipeId);
        var player = Minecraft.getInstance().player;

        if (player != null) {
            player.level().getRecipeManager().byKey(recipeId).ifPresent(recipe -> PolymorphApi.getInstance()
                    .getPlayerRecipeData(player)
                    .selectRecipe(recipe));
        }

        PolymorphApi.getInstance().getNetwork().sendPlayerRecipeSelectionC2S(recipeId);
        PacketDistributor.sendToServer(GridRecipeSelectPacket.INSTANCE);
    }

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }
}
