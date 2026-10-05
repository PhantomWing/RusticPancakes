package com.phantomwing.rusticpancakes.neoforge;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.RusticPancakesCommon;
import net.neoforged.fml.common.Mod;

@Mod(RusticPancakes.MOD_ID)
public final class RusticPancakesNeoForge {
    public RusticPancakesNeoForge() {
        RusticPancakesCommon.init();
    }
}
