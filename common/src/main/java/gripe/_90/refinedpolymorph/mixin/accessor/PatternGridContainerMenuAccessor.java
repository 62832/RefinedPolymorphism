package gripe._90.refinedpolymorph.mixin.accessor;

import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternType;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = PatternGridContainerMenu.class, remap = false)
public interface PatternGridContainerMenuAccessor {
    @Accessor
    Container getCraftingResult();

    @Accessor
    Container getSmithingTableResult();

    @Invoker
    PatternType callGetPatternType();

    @Accessor
    PatternGridBlockEntity getPatternGrid();
}
