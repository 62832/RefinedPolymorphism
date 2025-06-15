package gripe._90.refinedpolymorph.mixin.pattern;

import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import gripe._90.refinedpolymorph.ExtendedPatternComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatternGridBlockEntity.class)
public abstract class PatternGridBlockEntityMixin {
    @Shadow
    @Final
    private RecipeMatrix<CraftingRecipe, CraftingInput> craftingRecipe;

    @Shadow
    @Final
    private RecipeMatrix<SmithingRecipe, SmithingRecipeInput> smithingTableRecipe;

    @Inject(method = "createCraftingPattern", at = @At("TAIL"))
    private void addSelectedCraftingOutput(CallbackInfoReturnable<ItemStack> cir, @Local ItemStack result) {
        var output = craftingRecipe.getResult().getItem(0);
        result.set(
                ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get(),
                new ResourceAmount(ItemResource.ofItemStack(output), output.getCount()));
    }

    @Inject(method = "createSmithingTablePattern", at = @At("TAIL"))
    private void addSelectedSmithingTableOutput(CallbackInfoReturnable<ItemStack> cir, @Local ItemStack result) {
        var output = smithingTableRecipe.getResult().getItem(0);
        result.set(
                ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get(),
                new ResourceAmount(ItemResource.ofItemStack(output), output.getCount()));
    }
}
