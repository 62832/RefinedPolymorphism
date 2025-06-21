package gripe._90.refinedpolymorph;

import java.util.ServiceLoader;

public interface Platform {
    Platform INSTANCE = ServiceLoader.load(Platform.class).findFirst().orElseThrow();

    boolean isModLoaded(String modId);
}
