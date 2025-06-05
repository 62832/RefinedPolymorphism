package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.mixin.AbstractCraftingGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.RecipeMatrixAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(RefinedPolymorphism.MODID)
public class RefinedPolymorphism {
    public static final String MODID = "refinedpolymorph";

    public RefinedPolymorphism(IEventBus eventBus) {
        PolymorphApi.getInstance().registerMenu(menu -> {
            if (menu instanceof AbstractCraftingGridContainerMenuAccessor craftingGrid
                    && craftingGrid.getCraftingGrid() instanceof BlockEntity be) {
                return be;
            }

            return null;
        });

        PolymorphApi.getInstance().registerBlockEntity(be -> {
            if (be instanceof CraftingGridBlockEntity craftingGrid) {
                return new CraftingGridRecipeData(craftingGrid);
            }

            return null;
        });

        eventBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1")
                .playToServer(
                        GridRecipeSelectPacket.TYPE,
                        GridRecipeSelectPacket.STREAM_CODEC,
                        GridRecipeSelectPacket::handle));
    }

    public static void onSelect(RecipeMatrixContainer container) {
        var matrix = (RecipeMatrixAccessor) ((MatrixAwareContainer) container).refpoly$getMatrix();

        if (matrix != null) {
            matrix.setCurrentRecipe(null);
            container.changed();
        }
    }
}
