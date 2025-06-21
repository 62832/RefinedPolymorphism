package gripe._90.refinedpolymorph.mixin.integration.refinedstorage_quartz_arsenal;

import com.refinedmods.refinedstorage.common.api.support.slotreference.SlotReference;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import gripe._90.refinedpolymorph.duck.HostAwareMatrix;
import gripe._90.refinedpolymorph.duck.PlayerAwareGrid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"com.refinedmods.refinedstorage.quartzarsenal.common.wirelesscraftinggrid.WirelessCraftingGrid"})
public abstract class WirelessCraftingGridMixin implements PlayerAwareGrid {
    @Unique
    private Player refpoly$player;

    @Shadow
    private static void setMatrixSlots(ItemStack stack, RecipeMatrix<CraftingRecipe, CraftingInput> recipe) {}

    // spotless:off
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/quartzarsenal/common/wirelesscraftinggrid/WirelessCraftingGrid;createMatrix(Lnet/minecraft/world/entity/player/Player;Ljava/lang/Runnable;Lcom/refinedmods/refinedstorage/common/api/support/slotreference/SlotReference;)Lcom/refinedmods/refinedstorage/common/support/RecipeMatrix;"))
    // spotless:on
    private RecipeMatrix<CraftingRecipe, CraftingInput> attachMatrixHost(
            Player player, Runnable listener, SlotReference slotReference) {
        return refpoly$createMatrix(player, listener, slotReference);
    }

    @Unique
    private RecipeMatrix<CraftingRecipe, CraftingInput> refpoly$createMatrix(
            Player player, Runnable listener, SlotReference slotReference) {
        refpoly$player = player;
        var matrix = RecipeMatrix.crafting(listener, player::level);
        ((HostAwareMatrix) matrix).refpoly$setHost(this);
        slotReference.resolve(player).ifPresent(stack -> setMatrixSlots(stack, matrix));
        return matrix;
    }

    @Override
    public Player refpoly$getPlayer() {
        return refpoly$player;
    }
}
