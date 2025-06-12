package gripe._90.refinedpolymorph.mixin.accessor;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternType;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PatternGridBlockEntity.class)
public interface PatternGridBlockEntityAccessor {
    @Invoker
    RecipeMatrixContainer callGetCraftingMatrix();

    @Invoker
    RecipeMatrixContainer callGetSmithingTableMatrix();

    @Accessor
    PatternType getPatternType();
}
