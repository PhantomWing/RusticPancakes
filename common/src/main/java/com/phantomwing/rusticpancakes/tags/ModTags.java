package com.phantomwing.rusticpancakes.tags;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Block tags
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, RusticPancakes.resourceLocation(name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> CHERRY_BLOSSOM_INGREDIENTS = tag("cherry_blossom_ingredients");
        public static final TagKey<Item> SYRUP_INGREDIENTS = tag("syrup_ingredients");
        public static final TagKey<Item> SYRUP = tag("syrup");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, RusticPancakes.resourceLocation(name));
        }
    }
}
