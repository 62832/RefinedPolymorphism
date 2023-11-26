package gripe._90.refinedpolymorph;

import com.refinedmods.refinedstorage.container.GridContainerMenu;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public record GridRecipeSelectMessage(ResourceLocation recipeId) {
    public static void encode(GridRecipeSelectMessage message, FriendlyByteBuf buf) {
        buf.writeResourceLocation(message.recipeId());
    }

    public static GridRecipeSelectMessage decode(FriendlyByteBuf buf) {
        return new GridRecipeSelectMessage(buf.readResourceLocation());
    }

    public static void handle(GridRecipeSelectMessage message, Supplier<NetworkEvent.Context> ctx) {
        var context = ctx.get();
        var player = context.getSender();

        if (player != null) {
            context.enqueueWork(() -> {
                if (player.containerMenu instanceof GridContainerMenu grid) {
                    player.getLevel()
                            .getRecipeManager()
                            .byKey(message.recipeId())
                            .ifPresent(recipe -> RefinedPolymorphism.onSelect(recipe, grid.getGrid()));
                }
            });
        }

        context.setPacketHandled(true);
    }
}
