package gripe._90.refinedpolymorph;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridBlockEntity;
import com.refinedmods.refinedstorage.common.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage.common.support.RecipeMatrixContainer;
import gripe._90.refinedpolymorph.duck.MatrixAwareContainer;
import gripe._90.refinedpolymorph.mixin.accessor.AbstractCraftingGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.accessor.PatternGridContainerMenuAccessor;
import gripe._90.refinedpolymorph.mixin.accessor.RecipeMatrixAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.Nullable;

@Mod(RefinedPolymorphism.MODID)
public class RefinedPolymorphism {
    public static final String MODID = "refinedpolymorph";

    public RefinedPolymorphism(IEventBus eventBus) {
        PolymorphApi.getInstance().registerMenu(menu -> {
            if (menu instanceof AbstractCraftingGridContainerMenuAccessor craftingGrid
                    && craftingGrid.getCraftingGrid() instanceof BlockEntity be) {
                return be;
            }

            if (menu instanceof PatternGridContainerMenuAccessor patternGrid
                    && patternGrid.getPatternGrid() instanceof BlockEntity be) {
                return be;
            }

            return null;
        });

        PolymorphApi.getInstance().registerBlockEntity(be -> {
            if (be instanceof CraftingGridBlockEntity craftingGrid) {
                return new CraftingGridRecipeData(craftingGrid);
            }

            if (be instanceof PatternGridBlockEntity patternGrid) {
                return new PatternGridRecipeData(patternGrid);
            }

            return null;
        });

        ExtendedPatternComponents.DR.register(eventBus);
        eventBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1")
                .playToServer(
                        GridRecipeSelectPacket.TYPE,
                        GridRecipeSelectPacket.STREAM_CODEC,
                        GridRecipeSelectPacket::handle));
    }

    public static void onSelect(@Nullable RecipeMatrixContainer container) {
        if (container == null) {
            return;
        }

        var matrix = (RecipeMatrixAccessor) ((MatrixAwareContainer) container).refpoly$getMatrix();

        if (matrix != null) {
            matrix.setCurrentRecipe(null);
            container.changed();
        }
    }
}
