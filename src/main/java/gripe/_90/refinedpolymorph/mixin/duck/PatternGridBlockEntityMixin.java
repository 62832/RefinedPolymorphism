package gripe._90.refinedpolymorph.mixin.duck;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import gripe._90.refinedpolymorph.duck.HostAwareMatrix;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternGridBlockEntity.class)
public abstract class PatternGridBlockEntityMixin {
    @Shadow
    @Final
    private RecipeMatrix<CraftingRecipe, CraftingInput> craftingRecipe;

    @Shadow
    @Final
    private RecipeMatrix<SmithingRecipe, SmithingRecipeInput> smithingTableRecipe;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void attachMatrixHost(BlockPos pos, BlockState state, CallbackInfo ci) {
        ((HostAwareMatrix) craftingRecipe).refpoly$setHost(this);
        ((HostAwareMatrix) smithingTableRecipe).refpoly$setHost(this);
    }
}
