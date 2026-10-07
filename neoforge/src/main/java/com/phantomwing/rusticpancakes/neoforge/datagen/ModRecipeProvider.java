package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.CommonTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import dev.architectury.registry.registries.RegistrySupplier;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    /** What the data generator registers: it makes the provider once the registries are there. */
    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(@NotNull HolderLookup.Provider registries, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return "Rustic Pancakes recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        buildCraftingRecipes();
    }

    private void buildCraftingRecipes() {
        // Batter
        shapeless(RecipeCategory.FOOD, ModItems.BATTER.get(), 1)
                .requires(Items.BOWL)
                .requires(CommonTags.FOODS_MILK)
                .requires(Tags.Items.EGGS)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);

        // Syrup
        shapeless(RecipeCategory.FOOD, ModItems.SYRUP.get(), 1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModTags.Items.SYRUP_INGREDIENTS)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);
        oneToOne(RecipeCategory.MISC, ModItems.SYRUP.get(), Items.SUGAR, 3);

        // Pancakes
        pancakeRecipes(ModItems.PANCAKES, ModItems.PANCAKE, tag(ModTags.Items.SYRUP), Ingredient.of(Items.SUGAR));
        pancakeRecipes(ModItems.HONEY_PANCAKES, ModItems.HONEY_PANCAKE, Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(Items.SWEET_BERRIES), Ingredient.of(Items.SUGAR));
        pancakeRecipes(ModItems.CHOCOLATE_PANCAKES, ModItems.CHOCOLATE_PANCAKE, tag(CommonTags.FOODS_MILK), Ingredient.of(Items.COCOA_BEANS));
        pancakeRecipes(ModItems.VEGETABLE_PANCAKES, ModItems.VEGETABLE_PANCAKE, tag(CommonTags.FOODS_MILK), tag(Tags.Items.FOODS_VEGETABLE));
        pancakeRecipes(ModItems.CHERRY_BLOSSOM_PANCAKES, ModItems.CHERRY_BLOSSOM_PANCAKE, tag(CommonTags.FOODS_MILK), tag(ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS));
        pancakeRecipes(ModItems.PUMPKIN_PANCAKES, ModItems.PUMPKIN_PANCAKE, tag(ModTags.Items.SYRUP), tag(CommonTags.FOODS_PUMPKIN), Ingredient.of(Items.SUGAR));
    }

    protected void pancakeRecipes(@NotNull RegistrySupplier<Item> pancakeBlock, @NotNull RegistrySupplier<Item> singlePancake, Ingredient topping, Ingredient ingredient) {
        pancakeRecipes(pancakeBlock, singlePancake, topping, ingredient, ingredient);
    }

    protected void pancakeRecipes(@NotNull RegistrySupplier<Item> pancakeBlockItem, @NotNull RegistrySupplier<Item> singlePancakeItem, Ingredient topping, Ingredient ingredient, Ingredient ingredient2) {
        Item pancakeBlock = pancakeBlockItem.get();
        Item singlePancake = singlePancakeItem.get();
        var batter = ModItems.BATTER.get();
        var servingItem = Items.BOWL;

        // Crafting a pancake block.
        shaped(RecipeCategory.FOOD, pancakeBlock, 1)
                .pattern(" T ")
                .pattern("XMX")
                .pattern("YBY")
                .define('T', topping) // Topping
                .define('X', ingredient) // Main ingredient
                .define('Y', ingredient2) // Optional secondary ingredient
                .define('M', batter)
                .define('B', servingItem)
                .unlockedBy(getHasName(batter), has(batter))
                .save(output);

        // Split a stack of pancakes into separate pancakes.
        oneToOne(RecipeCategory.MISC, pancakeBlock, singlePancake, PancakeBlock.MAX_SERVINGS);

        // Combine separate pancakes together into a single stack
        shapeless(RecipeCategory.FOOD, pancakeBlock)
                .requires(singlePancake, PancakeBlock.MAX_SERVINGS)
                .requires(servingItem) // Pancakes are always placed on a bowl
                .unlockedBy(getHasName(singlePancake), has(singlePancake))
                .save(output, getRecipeName(singlePancake, pancakeBlock));
    }

    protected void oneToOne(RecipeCategory category, ItemLike item, ItemLike result, int count) {
        shapeless(category, result, count)
                .requires(item)
                .unlockedBy(getHasName(item), has(item))
                .save(output, getRecipeName(item, result));
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return RusticPancakes.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
