package gripe._90.refinedpolymorph.mixin.core;

import com.refinedmods.refinedstorage.network.NetworkHandler;
import gripe._90.refinedpolymorph.GridRecipeSelectMessage;
import net.minecraftforge.network.simple.SimpleChannel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = NetworkHandler.class, remap = false)
public abstract class NetworkHandlerMixin {
    @Shadow
    @Final
    private SimpleChannel handler;

    @SuppressWarnings("UnusedAssignment")
    @Inject(method = "register", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void registerRecipeSelectMessage(CallbackInfo ci, int id) {
        handler.registerMessage(
                id++,
                GridRecipeSelectMessage.class,
                GridRecipeSelectMessage::encode,
                GridRecipeSelectMessage::decode,
                GridRecipeSelectMessage::handle);
    }
}
