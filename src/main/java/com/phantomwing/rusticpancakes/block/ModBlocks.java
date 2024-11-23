package com.phantomwing.rusticpancakes.block;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.food.FoodValues;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Pancake blocks
    public static final Block HONEY_PANCAKES = registerBlock("honey_pancakes",
            new PancakeBlock(FoodValues.HONEY_PANCAKE, AbstractBlock.Settings.copy(Blocks.CAKE).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHOCOLATE_PANCAKES = registerBlock("chocolate_pancakes",
            new PancakeBlock(FoodValues.CHOCOLATE_PANCAKE, AbstractBlock.Settings.copy(Blocks.CAKE).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHERRY_BLOSSOM_PANCAKES = registerBlock("cherry_blossom_pancakes",
            new PancakeBlock(FoodValues.CHERRY_BLOSSOM_PANCAKE, AbstractBlock.Settings.copy(Blocks.CAKE).sounds(BlockSoundGroup.WOOD)));
    public static final Block VEGETABLE_PANCAKES = registerBlock("vegetable_pancakes",
            new PancakeBlock(FoodValues.VEGETABLE_PANCAKE, AbstractBlock.Settings.copy(Blocks.CAKE).sounds(BlockSoundGroup.WOOD)));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(RusticPancakes.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        RusticPancakes.LOGGER.info("Registering blocks for " + RusticPancakes.MOD_ID);
    }
}