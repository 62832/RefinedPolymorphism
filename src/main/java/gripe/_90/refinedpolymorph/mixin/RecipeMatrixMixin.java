package gripe._90.refinedpolymorph.mixin;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.common.grid.AbstractGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrix;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.duck.HostAwareMatrix;
import gripe._90.refinedpolymorph.duck.MatrixAwareContainer;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RecipeMatrix.class, priority = 500)
public abstract class RecipeMatrixMixin<T extends Recipe<I>, I extends RecipeInput> implements HostAwareMatrix {
    @Unique
    private Object refpoly$host;

    @Shadow
    @Final
    private RecipeMatrixContainer matrix;

    @Shadow
    @Final
    private RecipeType<T> recipeType;

    @Shadow
    @Final
    private Function<RecipeMatrixContainer, I> inputProvider;

    @Override
    public void refpoly$setHost(Object host) {
        refpoly$host = host;
    }

    @Override
    public Object refpoly$getHost() {
        return refpoly$host;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void attachToContainer(
            Runnable listener,
            Supplier<?> levelSupplier,
            int width,
            int height,
            Function<?, ?> inputProvider,
            RecipeType<?> recipeType,
            CallbackInfo ci) {
        ((MatrixAwareContainer) matrix).refpoly$setMatrix((RecipeMatrix<?, ?>) (Object) this);
    }

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
        if (refpoly$host instanceof AbstractGridBlockEntity be) {
            return PolymorphApi.getInstance()
                    .getRecipeManager()
                    .getBlockEntityRecipe(recipeType, inputProvider.apply(matrix), level, be);
        }

        return instance;
    }
}
