package gripe._90.refinedpolymorph.neoforge;

import gripe._90.refinedpolymorph.GridRecipeSelectPacket;
import gripe._90.refinedpolymorph.RefinedPolymorphism;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(RefinedPolymorphism.MODID)
public class RefinedPolymorphismNeoForge {
    public RefinedPolymorphismNeoForge(IEventBus eventBus) {
        RefinedPolymorphism.registerRecipeData();

        eventBus.addListener(RegisterEvent.class, event -> {
            if (event.getRegistryKey() == Registries.DATA_COMPONENT_TYPE) {
                event.register(
                        Registries.DATA_COMPONENT_TYPE,
                        ResourceLocation.fromNamespaceAndPath(RefinedPolymorphism.MODID, "selected_pattern_output"),
                        RefinedPolymorphism.SELECTED_PATTERN_OUTPUT::get);
            }
        });

        eventBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1")
                .playToServer(
                        GridRecipeSelectPacket.TYPE,
                        GridRecipeSelectPacket.STREAM_CODEC,
                        (pkt, ctx) -> ctx.enqueueWork(() -> pkt.handle(ctx.player()))));
    }
}
