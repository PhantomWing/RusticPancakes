package com.phantomwing.rusticpancakes.block;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.food.FoodValues;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RusticPancakes.MOD_ID);

    // Pancake blocks
    public static final DeferredBlock<Block> HONEY_PANCAKES = BLOCKS.register("honey_pancakes",
            () -> new PancakeBlock(FoodValues.HONEY_PANCAKE, Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> CHOCOLATE_PANCAKES = BLOCKS.register("chocolate_pancakes",
            () -> new PancakeBlock(FoodValues.CHOCOLATE_PANCAKE, Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> CHERRY_BLOSSOM_PANCAKES = BLOCKS.register("cherry_blossom_pancakes",
            () -> new PancakeBlock(FoodValues.CHERRY_BLOSSOM_PANCAKE, Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> VEGETABLE_PANCAKES = BLOCKS.register("vegetable_pancakes",
            () -> new PancakeBlock(FoodValues.VEGETABLE_PANCAKE, Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.WOOD)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}