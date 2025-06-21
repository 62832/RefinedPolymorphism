package gripe._90.refinedpolymorph;

import gripe._90.refinedpolymorph.mixin.accessor.AbstractCraftingGridContainerMenuAccessor;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class GridRecipeSelectPacket implements CustomPacketPayload {
    public static final GridRecipeSelectPacket INSTANCE = new GridRecipeSelectPacket();

    public static final Type<GridRecipeSelectPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(RefinedPolymorphism.MODID, "select"));
    public static final StreamCodec<ByteBuf, GridRecipeSelectPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private GridRecipeSelectPacket() {}

    public void handle(Player player) {
        if (player.containerMenu instanceof AbstractCraftingGridContainerMenuAccessor accessed) {
            RefinedPolymorphism.onSelect(accessed.getCraftingGrid().getCraftingMatrix());
        }
    }

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
