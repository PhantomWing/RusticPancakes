package com.phantomwing.rusticpancakes.item;

import com.google.common.collect.Sets;
import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.food.FoodValues;
import com.phantomwing.rusticpancakes.item.custom.ConsumableItem;
import com.phantomwing.rusticpancakes.item.custom.DrinkableItem;
import com.phantomwing.rusticpancakes.item.custom.PlaceableItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {
    public static final int BOWL_STACK_SIZE = 16;
    public static final int BOTTLE_STACK_SIZE = 16;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(RusticPancakes.MOD_ID, Registries.ITEM);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Cooking products
    public static final RegistrySupplier<Item> SYRUP = registerWithTab("syrup", () -> new DrinkableItem(
            bottleItem().food(FoodValues.SYRUP), true));
    public static final RegistrySupplier<Item> BATTER = registerWithTab("batter", () -> new Item(
            bowlItem().food(FoodValues.BATTER)));

    // Pancakes
    public static final RegistrySupplier<Item> PANCAKES = registerBlockWithTab(ModBlocks.PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> PANCAKE = registerWithTab("pancake", () -> new Item(
            baseItem().food(FoodValues.PANCAKE)));
    public static final RegistrySupplier<Item> HONEY_PANCAKES = registerBlockWithTab(ModBlocks.HONEY_PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> HONEY_PANCAKE = registerWithTab("honey_pancake", () -> new ConsumableItem(
            baseItem().food(FoodValues.HONEY_PANCAKE), true));
    public static final RegistrySupplier<Item> CHOCOLATE_PANCAKES = registerBlockWithTab(ModBlocks.CHOCOLATE_PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> CHOCOLATE_PANCAKE = registerWithTab("chocolate_pancake", () -> new ConsumableItem(
            baseItem().food(FoodValues.CHOCOLATE_PANCAKE), true));
    public static final RegistrySupplier<Item> CHERRY_BLOSSOM_PANCAKES = registerBlockWithTab(ModBlocks.CHERRY_BLOSSOM_PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> CHERRY_BLOSSOM_PANCAKE = registerWithTab("cherry_blossom_pancake", () -> new ConsumableItem(
            baseItem().food(FoodValues.CHERRY_BLOSSOM_PANCAKE), true));
    public static final RegistrySupplier<Item> VEGETABLE_PANCAKES = registerBlockWithTab(ModBlocks.VEGETABLE_PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> VEGETABLE_PANCAKE = registerWithTab("vegetable_pancake", () -> new ConsumableItem(
            baseItem().food(FoodValues.VEGETABLE_PANCAKE), true));
    public static final RegistrySupplier<Item> PUMPKIN_PANCAKES = registerBlockWithTab(ModBlocks.PUMPKIN_PANCAKES, bowlItem());
    public static final RegistrySupplier<Item> PUMPKIN_PANCAKE = registerWithTab("pumpkin_pancake", () -> new ConsumableItem(
            baseItem().food(FoodValues.PUMPKIN_PANCAKE), true));

    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    public static Item.Properties bottleItem() {
        return baseItem().craftRemainder(Items.GLASS_BOTTLE).stacksTo(BOTTLE_STACK_SIZE);
    }

    public static Item.Properties bowlItem() {
        return baseItem().craftRemainder(Items.BOWL).stacksTo(BOWL_STACK_SIZE);
    }

    // Registry functions
    public static RegistrySupplier<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistrySupplier<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static RegistrySupplier<Item> registerBlockWithTab(RegistrySupplier<Block> block, Item.Properties properties) {
        return registerWithTab(block.getId().getPath(), () -> new PlaceableItem(block.get(), properties));
    }

    public static void register() {
        ITEMS.register();
    }
}
