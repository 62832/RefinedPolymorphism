package gripe._90.refinedpolymorph.mixin.pattern;

import com.refinedmods.refinedstorage.common.autocrafting.PatternResolver;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PatternResolver.ResolvedSmithingTablePattern.class)
public interface ResolvedSmithingTablePatternAccessor {
    @Invoker("<init>")
    static PatternResolver.ResolvedSmithingTablePattern create(
            UUID id, ItemResource template, ItemResource base, ItemResource addition, ItemResource output) {
        throw new AssertionError();
    }
}
