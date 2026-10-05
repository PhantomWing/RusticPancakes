package com.phantomwing.rusticpancakes;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.event.ModEvents;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.ui.ModCreativeModeTab;

/** The loader-agnostic bootstrap, called from each loader's entry point. */
public final class RusticPancakesCommon {
    private RusticPancakesCommon() {
    }

    public static void init() {
        // Blocks before items: on Fabric, Architectury creates each entry as it registers, and the
        // block items look their block up as they are created.
        ModBlocks.register();
        ModItems.register();
        ModCreativeModeTab.register();
        ModEvents.register();
    }
}
