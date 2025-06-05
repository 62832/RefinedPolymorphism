package gripe._90.refinedpolymorph.mixin.integration.refinedstorage_quartz_arsenal;

import com.refinedmods.refinedstorage.common.api.support.network.item.NetworkItemContext;
import com.refinedmods.refinedstorage.common.api.support.slotreference.SlotReference;
import com.refinedmods.refinedstorage.common.grid.CraftingGrid;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import gripe._90.refinedpolymorph.HostAwareMatrix;
import gripe._90.refinedpolymorph.PlayerAwareGrid;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"com.refinedmods.refinedstorage.quartzarsenal.common.wirelesscraftinggrid.WirelessCraftingGrid"})
public abstract class WirelessCraftingGridMixin implements PlayerAwareGrid {
    @Shadow
    @Final
    @Nullable
    private RecipeMatrix<CraftingRecipe, CraftingInput> craftingRecipe;

    @Unique
    private Player refpoly$player;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void attachMatrixHost(
            Player player, NetworkItemContext context, SlotReference slotReference, CallbackInfo ci) {
        if (craftingRecipe != null) {
            ((HostAwareMatrix) craftingRecipe).refpoly$setHost((CraftingGrid) this);
        }

        refpoly$player = player;
    }

    @Override
    public Player refpoly$getPlayer() {
        return refpoly$player;
    }
}
