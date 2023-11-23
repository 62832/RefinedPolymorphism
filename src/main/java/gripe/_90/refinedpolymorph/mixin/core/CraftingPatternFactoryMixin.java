package gripe._90.refinedpolymorph.mixin.core;

import com.refinedmods.refinedstorage.api.autocrafting.ICraftingPatternContainer;
import com.refinedmods.refinedstorage.apiimpl.autocrafting.CraftingPatternFactory;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CraftingPatternFactory.class)
public abstract class CraftingPatternFactoryMixin {
    @SuppressWarnings("unchecked")
    @Redirect(
            method = "create",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <C extends Container, R extends Recipe<C>> Optional<R> getRecipe(
            RecipeManager manager,
            RecipeType<R> type,
            C inv,
            Level level,
            Level unused,
            ICraftingPatternContainer container,
            ItemStack stack) {
        var id = new ResourceLocation(stack.getOrCreateTag().getString("polymorphRecipe"));
        var recipe = manager.byKey(id);
        return recipe.isPresent() ? (Optional<R>) recipe : manager.getRecipeFor(type, inv, level);
    }
}
