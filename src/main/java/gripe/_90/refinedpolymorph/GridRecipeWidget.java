package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.client.recipe.widget.PersistentRecipesWidget;
import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.screen.grid.GridScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class GridRecipeWidget extends PersistentRecipesWidget {
    private final Slot outputSlot;
    private final Player player;

    public GridRecipeWidget(GridScreen screen, Slot output) {
        super(screen);
        outputSlot = output;
        player = screen.getMenu().getPlayer();
    }

    @Override
    public Slot getOutputSlot() {
        return outputSlot;
    }

    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        player.level().getRecipeManager().byKey(id).ifPresent(recipe -> {
            PolymorphApi.common().getRecipeData(player).ifPresent(data -> data.selectRecipe(recipe));
            RS.NETWORK_HANDLER.sendToServer(new GridRecipeSelectMessage(id));
        });
        PolymorphApi.common().getPacketDistributor().sendPlayerRecipeSelectionC2S(id);
    }
}
