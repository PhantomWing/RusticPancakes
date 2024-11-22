package com.phantomwing.rusticpancakes.tags;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Block tags
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(RusticPancakes.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> CHERRY_BLOSSOM_INGREDIENTS = tag("cherry_blossom_ingredients");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(RusticPancakes.MOD_ID, name));
        }
    }
}