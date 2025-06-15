package gripe._90.refinedpolymorph;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.support.resource.ResourceCodecs;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExtendedPatternComponents {
    public static final DeferredRegister<DataComponentType<?>> DR =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, RefinedPolymorphism.MODID);

    public static final Supplier<DataComponentType<ResourceAmount>> SELECTED_PATTERN_OUTPUT =
            DR.register("selected_pattern_output", () -> DataComponentType.<ResourceAmount>builder()
                    .persistent(ResourceCodecs.AMOUNT_CODEC)
                    .networkSynchronized(ResourceCodecs.AMOUNT_STREAM_CODEC)
                    .build());
}
