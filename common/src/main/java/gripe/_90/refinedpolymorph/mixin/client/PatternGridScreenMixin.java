package gripe._90.refinedpolymorph.mixin.client;

import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridScreen;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternType;
import gripe._90.refinedpolymorph.client.AbstractGridRecipeWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternGridScreen.class)
public abstract class PatternGridScreenMixin {
    @Inject(method = "patternTypeChanged", at = @At("RETURN"), remap = false)
    private void reinitWidget(PatternType newPatternType, CallbackInfo ci) {
        if (PolymorphWidgets.getInstance().getCurrentWidget() instanceof AbstractGridRecipeWidget<?, ?> widget) {
            widget.resetWidgetOffsets();
        }
    }
}
