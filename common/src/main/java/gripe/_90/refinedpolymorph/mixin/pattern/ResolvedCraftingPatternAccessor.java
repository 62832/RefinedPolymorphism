package gripe._90.refinedpolymorph.mixin.pattern;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.api.resource.ResourceKey;
import com.refinedmods.refinedstorage.common.autocrafting.PatternResolver;
import java.util.List;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PatternResolver.ResolvedCraftingPattern.class)
public interface ResolvedCraftingPatternAccessor {
    @Invoker("<init>")
    static PatternResolver.ResolvedCraftingPattern create(
            UUID id, List<List<ResourceKey>> inputs, ResourceAmount output, List<ResourceAmount> byproducts) {
        throw new AssertionError();
    }
}
