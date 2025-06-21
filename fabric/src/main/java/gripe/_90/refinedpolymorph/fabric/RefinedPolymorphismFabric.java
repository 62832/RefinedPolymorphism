package gripe._90.refinedpolymorph.fabric;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.fabric.api.RefinedStoragePlugin;
import gripe._90.refinedpolymorph.GridRecipeSelectPacket;
import gripe._90.refinedpolymorph.RefinedPolymorphism;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RefinedPolymorphismFabric implements RefinedStoragePlugin, ModInitializer {
    @Override
    public void onApiAvailable(@NotNull RefinedStorageApi api) {
        Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceLocation.fromNamespaceAndPath(RefinedPolymorphism.MODID, "selected_pattern_output"),
                RefinedPolymorphism.SELECTED_PATTERN_OUTPUT.get());
    }

    @Override
    public void onInitialize() {
        RefinedPolymorphism.registerRecipeData();

        PayloadTypeRegistry.playC2S().register(GridRecipeSelectPacket.TYPE, GridRecipeSelectPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(
                GridRecipeSelectPacket.TYPE, (pkt, ctx) -> pkt.handle(ctx.player()));
    }
}
