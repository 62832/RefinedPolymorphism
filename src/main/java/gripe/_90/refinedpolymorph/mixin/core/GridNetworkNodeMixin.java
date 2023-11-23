package gripe._90.refinedpolymorph.mixin.core;

import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import com.refinedmods.refinedstorage.apiimpl.network.node.GridNetworkNode;
import com.refinedmods.refinedstorage.apiimpl.network.node.NetworkNode;
import com.refinedmods.refinedstorage.blockentity.grid.GridBlockEntity;
import com.refinedmods.refinedstorage.inventory.item.BaseItemHandler;
import gripe._90.refinedpolymorph.CraftingGrid;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GridNetworkNode.class, remap = false)
public abstract class GridNetworkNodeMixin extends NetworkNode implements CraftingGrid {
    @Shadow
    private CraftingRecipe currentRecipe;

    @Shadow
    private boolean exactPattern;

    @Shadow
    @Final
    private CraftingContainer matrix;

    @Shadow
    @Final
    private BaseItemHandler patterns;

    protected GridNetworkNodeMixin(Level level, BlockPos pos) {
        super(level, pos);
    }

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
        return pos != null && ticks > 1
                ? RecipeSelection.getBlockEntityRecipe(type, inv, level, level.getBlockEntity(pos))
                : manager.getRecipeFor(type, inv, level);
    }

    @Inject(method = "onCreatePattern", at = @At("TAIL"))
    private void appendPattern(CallbackInfo ci) {
        if (exactPattern) {
            var be = level.getBlockEntity(pos);

            if (be instanceof GridBlockEntity) {
                RecipeSelection.getBlockEntityRecipe(RecipeType.CRAFTING, matrix, level, be)
                        .ifPresent(recipe -> patterns.getStackInSlot(1)
                                .getOrCreateTag()
                                .putString("polymorphRecipe", recipe.getId().toString()));
            }
        }
    }

    @Override
    public void refinedpolymorph$setCurrentRecipe(CraftingRecipe recipe) {
        currentRecipe = recipe;
    }
}
