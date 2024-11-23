package com.phantomwing.rusticpancakes.item;

import com.google.common.collect.Sets;
import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.food.FoodValues;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashSet;

public class ModItems {
    public static final int BOWL_STACK_SIZE = 16;

    public static LinkedHashSet<Item> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Cooking products
    public static final Item BATTER = registerWithTab("batter", new Item(
            bowlItem().food(FoodValues.BATTER)));

    // Pancakes
    public static final Item HONEY_PANCAKES = registerBlockWithTab(ModBlocks.HONEY_PANCAKES, bowlItem());
    public static final Item CHOCOLATE_PANCAKES = registerBlockWithTab(ModBlocks.CHOCOLATE_PANCAKES, bowlItem());
    public static final Item CHERRY_BLOSSOM_PANCAKES = registerBlockWithTab(ModBlocks.CHERRY_BLOSSOM_PANCAKES, bowlItem());
    public static final Item VEGETABLE_PANCAKES = registerBlockWithTab(ModBlocks.VEGETABLE_PANCAKES, bowlItem());

    // Helper functions
    public static Item.Settings baseItem() {
        return new Item.Settings();
    }

    public static Item.Settings bowlItem() {
        return baseItem().recipeRemainder(Items.BOWL).maxCount(BOWL_STACK_SIZE);
    }

    // Registry functions
    private static Item registerWithTab(String name, Item item) {
        Item registeredItem = Registry.register(Registries.ITEM, Identifier.of(RusticPancakes.MOD_ID, name), item);

        CREATIVE_TAB_ITEMS.add(registeredItem);

        return registeredItem;
    }

    private static Item registerBlockWithTab(Block block, Item.Settings settings) {
        String name = Registries.BLOCK.getId(block).getPath();
        Item item = Registry.register(Registries.ITEM, Identifier.of(RusticPancakes.MOD_ID, name),
                new BlockItem(block, settings));

        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void registerModItems() {
        RusticPancakes.LOGGER.info("Registering items for " + RusticPancakes.MOD_ID);
    }
}