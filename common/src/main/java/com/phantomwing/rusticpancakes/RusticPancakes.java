package com.phantomwing.rusticpancakes;

import net.minecraft.resources.ResourceLocation;

/** Shared constants. The bootstrap is {@link RusticPancakesCommon#init()}, called from each loader's entry point. */
public final class RusticPancakes {
    public static final String MOD_ID = "rusticpancakes";

    private RusticPancakes() {
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
