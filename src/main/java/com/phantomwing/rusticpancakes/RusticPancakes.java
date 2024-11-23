package com.phantomwing.rusticpancakes;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.itemGroup.ModItemGroups;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RusticPancakes implements ModInitializer {
    public static final String MOD_ID = "rusticpancakes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Items
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

        // UI
		ModItemGroups.registerModItemGroups();
    }
}
