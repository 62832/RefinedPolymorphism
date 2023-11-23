package gripe._90.refinedpolymorph.mixin.addons;

import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import com.refinedmods.refinedstorage.api.network.grid.ICraftingGridListener;
import com.refinedmods.refinedstorage.container.GridContainerMenu;
import com.ultramega.universalgrid.item.WirelessUniversalGrid;
import gripe._90.refinedpolymorph.CraftingGrid;
import java.util.Optional;
import java.util.Set;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WirelessUniversalGrid.class, remap = false)
public abstract class WirelessUniversalGridMixin implements CraftingGrid {
    @Shadow
    private Set<ICraftingGridListener> listeners;

    @Shadow
    private CraftingRecipe currentRecipe;

    @Redirect(
            method = "onCraftingMatrixChanged",
            remap = true,
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <C extends Container, R extends Recipe<C>> Optional<R> getRecipe(
            RecipeManager manager, RecipeType<R> type, C inv, Level level) {
        for (var listener : listeners) {
            if (listener instanceof GridContainerMenu menu) {
                return RecipeSelection.getPlayerRecipe(menu, type, inv, level, menu.getPlayer());
            }
        }

        return Optional.empty();
    }

    @Override
    public void refinedpolymorph$setCurrentRecipe(CraftingRecipe recipe) {
        currentRecipe = recipe;
    }
}
