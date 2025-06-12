package gripe._90.refinedpolymorph.mixin.integration.refinedstorage_quartz_arsenal;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.duck.HostAwareMatrix;
import gripe._90.refinedpolymorph.duck.PlayerAwareGrid;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RecipeMatrix.class)
public abstract class RecipeMatrixMixin<T extends Recipe<I>, I extends RecipeInput> implements HostAwareMatrix {
    @Shadow
    @Final
    private RecipeType<T> recipeType;

    @Shadow
    @Final
    private Function<RecipeMatrixContainer, I> inputProvider;

    @Shadow
    @Final
    private RecipeMatrixContainer matrix;

    // spotless:off
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyReceiver(
            method = "loadRecipe",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;"))
    // spotless:on
    private Optional<RecipeHolder<T>> switchRecipeManager(
            Optional<RecipeHolder<T>> instance,
            Function<RecipeHolder<T>, T> mapper,
            @Local(argsOnly = true) Level level) {
        if (refpoly$getHost() instanceof PlayerAwareGrid wirelessGrid) {
            var player = wirelessGrid.refpoly$getPlayer();

            if (player != null) {
                return PolymorphApi.getInstance()
                        .getRecipeManager()
                        .getPlayerRecipe(player.containerMenu, recipeType, inputProvider.apply(matrix), level, player);
            }
        }

        return instance;
    }
}
