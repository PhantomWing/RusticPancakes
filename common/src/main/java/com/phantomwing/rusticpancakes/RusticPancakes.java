package com.phantomwing.rusticpancakes;

import net.minecraft.resources.Identifier;

/** Shared constants. The bootstrap is {@link RusticPancakesCommon#init()}, called from each loader's entry point. */
public final class RusticPancakes {
    public static final String MOD_ID = "rusticpancakes";

    private RusticPancakes() {
    }

    public static Identifier resourceLocation(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
