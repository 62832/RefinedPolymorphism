package gripe._90.refinedpolymorph.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.client.base.PersistentRecipesWidget;
import com.refinedmods.refinedstorage.common.grid.AbstractGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import gripe._90.refinedpolymorph.GridRecipeSelectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;

public abstract class AbstractGridRecipeWidget<M extends AbstractGridContainerMenu, S extends AbstractGridScreen<M>>
        extends PersistentRecipesWidget {
    protected final M menu;
    private Slot outputSlot;

    public AbstractGridRecipeWidget(S screen) {
        super(screen);
        menu = screen.getMenu();
        initOutputSlot();
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

        if (Minecraft.getInstance().getConnection() != null) {
            Minecraft.getInstance()
                    .getConnection()
                    .send(new ServerboundCustomPayloadPacket(GridRecipeSelectPacket.INSTANCE));
        }
    }

    protected abstract ResultContainer getResultContainer();

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }

    @Override
    public void resetWidgetOffsets() {
        initOutputSlot();
        super.resetWidgetOffsets();
    }

    private void initOutputSlot() {
        for (var slot : menu.slots) {
            if (slot.container == getResultContainer()) {
                outputSlot = slot;
                break;
            }
        }
    }
}
