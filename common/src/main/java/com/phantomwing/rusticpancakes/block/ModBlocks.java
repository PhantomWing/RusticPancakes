package com.phantomwing.rusticpancakes.block;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(RusticPancakes.MOD_ID, Registries.BLOCK);

    // Pancake blocks. The serving is looked up lazily: ModItems refers back to these blocks.
    public static final RegistrySupplier<Block> PANCAKES = registerPancakes("pancakes", () -> ModItems.PANCAKE.get());
    public static final RegistrySupplier<Block> HONEY_PANCAKES = registerPancakes("honey_pancakes", () -> ModItems.HONEY_PANCAKE.get());
    public static final RegistrySupplier<Block> CHOCOLATE_PANCAKES = registerPancakes("chocolate_pancakes", () -> ModItems.CHOCOLATE_PANCAKE.get());
    public static final RegistrySupplier<Block> CHERRY_BLOSSOM_PANCAKES = registerPancakes("cherry_blossom_pancakes", () -> ModItems.CHERRY_BLOSSOM_PANCAKE.get());
    public static final RegistrySupplier<Block> VEGETABLE_PANCAKES = registerPancakes("vegetable_pancakes", () -> ModItems.VEGETABLE_PANCAKE.get());
    public static final RegistrySupplier<Block> PUMPKIN_PANCAKES = registerPancakes("pumpkin_pancakes", () -> ModItems.PUMPKIN_PANCAKE.get());

    private static RegistrySupplier<Block> registerPancakes(String name, Supplier<Item> servingItem) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, RusticPancakes.resourceLocation(name));
        return BLOCKS.register(name, () -> new PancakeBlock(servingItem, Block.Properties.ofFullCopy(Blocks.CAKE)
                .sound(SoundType.WOOD)
                .setId(key)));
    }

    public static void register() {
        BLOCKS.register();
    }
}
