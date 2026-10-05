package com.phantomwing.rusticpancakes.fabric;

import com.phantomwing.rusticpancakes.RusticPancakesCommon;
import net.fabricmc.api.ModInitializer;

public final class RusticPancakesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RusticPancakesCommon.init();
    }
}
