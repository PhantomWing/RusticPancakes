package com.phantomwing.rusticpancakes.block;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.food.FoodValues;
import net.minecraft.world.level.block.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RusticPancakes.MOD_ID);

    // Pancake blocks
    public static final RegistryObject<Block> HONEY_PANCAKES = BLOCKS.register("honey_pancakes",
            () -> new PancakeBlock(FoodValues.HONEY_PANCAKE, Block.Properties.copy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHOCOLATE_PANCAKES = BLOCKS.register("chocolate_pancakes",
            () -> new PancakeBlock(FoodValues.CHOCOLATE_PANCAKE, Block.Properties.copy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VEGETABLE_PANCAKES = BLOCKS.register("vegetable_pancakes",
            () -> new PancakeBlock(FoodValues.VEGETABLE_PANCAKE, Block.Properties.copy(Blocks.CAKE).sound(SoundType.WOOD)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}