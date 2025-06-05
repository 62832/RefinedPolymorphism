package gripe._90.refinedpolymorph;

import gripe._90.refinedpolymorph.mixin.AbstractCraftingGridContainerMenuAccessor;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class GridRecipeSelectPacket implements CustomPacketPayload {
    public static final GridRecipeSelectPacket INSTANCE = new GridRecipeSelectPacket();

    static final Type<GridRecipeSelectPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(RefinedPolymorphism.MODID, "select"));
    static final StreamCodec<ByteBuf, GridRecipeSelectPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private GridRecipeSelectPacket() {}

    void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            var sender = context.player();

            if (sender.containerMenu instanceof AbstractCraftingGridContainerMenuAccessor accessed) {
                RefinedPolymorphism.onSelect(accessed.getCraftingGrid().getCraftingMatrix());
            }
        });
    }

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
