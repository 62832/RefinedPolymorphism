package gripe._90.refinedpolymorph.mixin.client;

import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.refinedmods.refinedstorage.common.support.stretching.AbstractStretchingScreen;
import gripe._90.refinedpolymorph.client.AbstractGridRecipeWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractStretchingScreen.class)
public abstract class AbstractStretchingScreenMixin {
    @Inject(method = "init()V", at = @At("RETURN"))
    private void moveWidget(CallbackInfo ci) {
        if (PolymorphWidgets.getInstance().getCurrentWidget() instanceof AbstractGridRecipeWidget<?, ?> widget) {
            widget.resetWidgetOffsets();
        }
    }
}
