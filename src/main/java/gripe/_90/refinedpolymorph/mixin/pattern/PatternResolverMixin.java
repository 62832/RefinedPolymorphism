package gripe._90.refinedpolymorph.mixin.pattern;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.api.resource.ResourceKey;
import com.refinedmods.refinedstorage.common.autocrafting.CraftingPatternState;
import com.refinedmods.refinedstorage.common.autocrafting.PatternResolver;
import com.refinedmods.refinedstorage.common.autocrafting.PatternState;
import com.refinedmods.refinedstorage.common.autocrafting.SmithingTablePatternState;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import gripe._90.refinedpolymorph.ExtendedPatternComponents;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PatternResolver.class)
public abstract class PatternResolverMixin {
    @Shadow
    protected abstract RecipeMatrixContainer getFilledCraftingMatrix(CraftingPatternState state);

    @Shadow
    protected abstract PatternResolver.ResolvedCraftingPattern toCraftingPattern(
            Level level,
            CraftingRecipe recipe,
            CraftingInput craftingInput,
            CraftingPatternState state,
            PatternState patternState);

    @Shadow
    protected abstract List<List<ResourceKey>> getInputs(CraftingRecipe recipe, CraftingPatternState state);

    @Shadow
    protected abstract List<ResourceAmount> getByproducts(CraftingRecipe recipe, CraftingInput craftingInput);

    // spotless:off
    @WrapOperation(
            method = "getCraftingPattern(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lcom/refinedmods/refinedstorage/common/autocrafting/PatternState;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/common/autocrafting/PatternResolver;getCraftingPattern(Lnet/minecraft/world/level/Level;Lcom/refinedmods/refinedstorage/common/autocrafting/PatternState;Lcom/refinedmods/refinedstorage/common/autocrafting/CraftingPatternState;)Ljava/util/Optional;"))
    // spotless:on
    private Optional<PatternResolver.ResolvedCraftingPattern> getExpectedCraftingPattern(
            PatternResolver instance,
            Level level,
            PatternState patternState,
            CraftingPatternState state,
            Operation<Optional<PatternResolver.ResolvedCraftingPattern>> original,
            @Local(argsOnly = true) ItemStack stack) {
        var selectedOutput = stack.get(ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get());
        return selectedOutput != null
                ? refpoly$getCraftingPattern(level, patternState, state, selectedOutput, stack)
                : original.call(instance, level, patternState, state);
    }

    // spotless:off
    @WrapOperation(
            method = "getSmithingTablePattern(Lcom/refinedmods/refinedstorage/common/autocrafting/PatternState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/common/autocrafting/PatternResolver;getSmithingTablePattern(Lnet/minecraft/world/level/Level;Lcom/refinedmods/refinedstorage/common/autocrafting/PatternState;Lcom/refinedmods/refinedstorage/common/autocrafting/SmithingTablePatternState;)Ljava/util/Optional;"))
    // spotless:on
    private Optional<PatternResolver.ResolvedSmithingTablePattern> getExpectedSmithingTablePattern(
            PatternResolver instance,
            Level level,
            PatternState patternState,
            SmithingTablePatternState state,
            Operation<Optional<PatternResolver.ResolvedSmithingTablePattern>> original,
            @Local(argsOnly = true) ItemStack stack) {
        var selectedRecipe = stack.get(ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get());
        return selectedRecipe != null
                ? refpoly$getSmithingTablePattern(level, patternState, state, selectedRecipe, stack)
                : original.call(instance, level, patternState, state);
    }

    @Unique
    private Optional<PatternResolver.ResolvedCraftingPattern> refpoly$getCraftingPattern(
            Level level,
            PatternState patternState,
            CraftingPatternState state,
            ResourceAmount selectedOutput,
            ItemStack patternStack) {
        var craftingMatrix = getFilledCraftingMatrix(state);
        var craftingInput = craftingMatrix.asPositionedCraftInput().input();
        var recipes = level.getRecipeManager().getRecipesFor(RecipeType.CRAFTING, craftingInput, level);

        if (recipes.isEmpty() || !(selectedOutput.resource() instanceof ItemResource itemOutput)) {
            return Optional.empty();
        }

        for (var recipe : recipes) {
            if (ItemStack.isSameItemSameComponents(
                    recipe.value().getResultItem(level.registryAccess()), itemOutput.toItemStack())) {
                return Optional.of(ResolvedCraftingPatternAccessor.create(
                        patternState.id(),
                        getInputs(recipe.value(), state),
                        selectedOutput,
                        getByproducts(recipe.value(), craftingInput)));
            }
        }

        patternStack.remove(ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get());
        return Optional.of(toCraftingPattern(level, recipes.getFirst().value(), craftingInput, state, patternState));
    }

    @Unique
    private Optional<PatternResolver.ResolvedSmithingTablePattern> refpoly$getSmithingTablePattern(
            Level level,
            PatternState patternState,
            SmithingTablePatternState state,
            ResourceAmount selectedOutput,
            ItemStack patternStack) {
        var input = new SmithingRecipeInput(
                state.template().toItemStack(),
                state.base().toItemStack(),
                state.addition().toItemStack());
        var recipes = level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, input, level);

        if (recipes.isEmpty() || !(selectedOutput.resource() instanceof ItemResource itemOutput)) {
            return Optional.empty();
        }

        SmithingRecipe foundRecipe = null;

        for (var recipe : recipes) {
            if (ItemStack.isSameItemSameComponents(
                    recipe.value().getResultItem(level.registryAccess()), itemOutput.toItemStack())) {
                foundRecipe = recipe.value();
            }
        }

        if (foundRecipe == null) {
            patternStack.remove(ExtendedPatternComponents.SELECTED_PATTERN_OUTPUT.get());
            foundRecipe = recipes.getFirst().value();
        }

        return Optional.of(ResolvedSmithingTablePatternAccessor.create(
                patternState.id(),
                state.template(),
                state.base(),
                state.addition(),
                ItemResource.ofItemStack(foundRecipe.assemble(input, level.registryAccess()))));
    }
}
