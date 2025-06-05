package gripe._90.refinedpolymorph;

import com.refinedmods.refinedstorage.common.grid.CraftingGrid;

public interface HostAwareMatrix {
    CraftingGrid refpoly$getHost();

    void refpoly$setHost(CraftingGrid host);
}
