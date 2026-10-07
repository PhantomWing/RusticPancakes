package com.phantomwing.rusticpancakes.item;

import com.google.common.collect.Sets;
import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.food.ConsumableValues;
import com.phantomwing.rusticpancakes.food.FoodValues;
import com.phantomwing.rusticpancakes.item.custom.ConsumableItem;
import com.phantomwing.rusticpancakes.item.custom.PlaceableItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static final int BOWL_STACK_SIZE = 16;
    public static final int BOTTLE_STACK_SIZE = 16;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(RusticPancakes.MOD_ID, Registries.ITEM);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Cooking products
    public static final RegistrySupplier<Item> SYRUP = registerWithTab("syrup", props -> new ConsumableItem(props, true),
            bottleItem().food(FoodValues.SYRUP, ConsumableValues.SYRUP).usingConvertsTo(Items.GLASS_BOTTLE));
    public static final RegistrySupplier<Item> BATTER = registerWithTab("batter", Item::new,
            bowlItem().food(FoodValues.BATTER, ConsumableValues.BATTER));

    // Pancakes
    public static final RegistrySupplier<Item> PANCAKES = registerBlockWithTab(ModBlocks.PANCAKES);
    public static final RegistrySupplier<Item> PANCAKE = registerWithTab("pancake", Item::new,
            baseItem().food(FoodValues.PANCAKE));
    public static final RegistrySupplier<Item> HONEY_PANCAKES = registerBlockWithTab(ModBlocks.HONEY_PANCAKES);
    public static final RegistrySupplier<Item> HONEY_PANCAKE = registerWithTab("honey_pancake", props -> new ConsumableItem(props, true),
            baseItem().food(FoodValues.HONEY_PANCAKE, ConsumableValues.HONEY_PANCAKE));
    public static final RegistrySupplier<Item> CHOCOLATE_PANCAKES = registerBlockWithTab(ModBlocks.CHOCOLATE_PANCAKES);
    public static final RegistrySupplier<Item> CHOCOLATE_PANCAKE = registerWithTab("chocolate_pancake", props -> new ConsumableItem(props, true),
            baseItem().food(FoodValues.CHOCOLATE_PANCAKE, ConsumableValues.CHOCOLATE_PANCAKE));
    public static final RegistrySupplier<Item> CHERRY_BLOSSOM_PANCAKES = registerBlockWithTab(ModBlocks.CHERRY_BLOSSOM_PANCAKES);
    public static final RegistrySupplier<Item> CHERRY_BLOSSOM_PANCAKE = registerWithTab("cherry_blossom_pancake", props -> new ConsumableItem(props, true),
            baseItem().food(FoodValues.CHERRY_BLOSSOM_PANCAKE, ConsumableValues.CHERRY_BLOSSOM_PANCAKE));
    public static final RegistrySupplier<Item> VEGETABLE_PANCAKES = registerBlockWithTab(ModBlocks.VEGETABLE_PANCAKES);
    public static final RegistrySupplier<Item> VEGETABLE_PANCAKE = registerWithTab("vegetable_pancake", props -> new ConsumableItem(props, true),
            baseItem().food(FoodValues.VEGETABLE_PANCAKE, ConsumableValues.VEGETABLE_PANCAKE));
    public static final RegistrySupplier<Item> PUMPKIN_PANCAKES = registerBlockWithTab(ModBlocks.PUMPKIN_PANCAKES);
    public static final RegistrySupplier<Item> PUMPKIN_PANCAKE = registerWithTab("pumpkin_pancake", props -> new ConsumableItem(props, true),
            baseItem().food(FoodValues.PUMPKIN_PANCAKE, ConsumableValues.PUMPKIN_PANCAKE));

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
    public static RegistrySupplier<Item> registerWithTab(final String name, final Function<Item.Properties, Item> factory, final Item.Properties properties) {
        RegistrySupplier<Item> item = ITEMS.register(name, () -> factory.apply(properties.setId(itemKey(name))));
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static RegistrySupplier<Item> registerBlockWithTab(RegistrySupplier<Block> block) {
        return registerWithTab(block.getId().getPath(), props -> new PlaceableItem(block.get(), props),
                bowlItem().useBlockDescriptionPrefix());
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, RusticPancakes.resourceLocation(name));
    }

    public static void register() {
        ITEMS.register();
    }
}
