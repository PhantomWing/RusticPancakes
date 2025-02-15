package com.phantomwing.rusticpancakes.datagen;

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
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        buildCraftingRecipes(output);
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        // Batter
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BATTER, 1)
                .requires(Items.BOWL)
                .requires(CommonTags.FOODS_MILK)
                .requires(Tags.Items.EGGS)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);

        // Syrup
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SYRUP, 1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModTags.Items.SYRUP_INGREDIENTS)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);
        oneToOne(output, RecipeCategory.MISC, ModItems.SYRUP, Items.SUGAR, 3);

        // Pancakes
        pancakeRecipes(output, ModItems.PANCAKES, ModItems.PANCAKE, Ingredient.of(ModTags.Items.SYRUP), Ingredient.of(Items.SUGAR));
        pancakeRecipes(output, ModItems.HONEY_PANCAKES, ModItems.HONEY_PANCAKE, Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(Items.SWEET_BERRIES), Ingredient.of(Items.SUGAR));
        pancakeRecipes(output, ModItems.CHOCOLATE_PANCAKES, ModItems.CHOCOLATE_PANCAKE, Ingredient.of(CommonTags.FOODS_MILK), Ingredient.of(Items.COCOA_BEANS));
        pancakeRecipes(output, ModItems.VEGETABLE_PANCAKES, ModItems.VEGETABLE_PANCAKE, Ingredient.of(CommonTags.FOODS_MILK), vegetablesPatch());
        pancakeRecipes(output, ModItems.CHERRY_BLOSSOM_PANCAKES, ModItems.CHERRY_BLOSSOM_PANCAKE, Ingredient.of(CommonTags.FOODS_MILK), Ingredient.of(ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS));
        pancakeRecipes(output, ModItems.PUMPKIN_PANCAKES, ModItems.PUMPKIN_PANCAKE, Ingredient.of(ModTags.Items.SYRUP), Ingredient.of(CommonTags.FOODS_PUMPKIN), Ingredient.of(Items.SUGAR));
    }

    protected static void pancakeRecipes(@NotNull RecipeOutput recipeOutput, @NotNull DeferredItem<Item> pancakeBlock, @NotNull DeferredItem<Item> singlePancake, Ingredient topping, Ingredient ingredient) {
        pancakeRecipes(recipeOutput, pancakeBlock, singlePancake, topping, ingredient, ingredient);
    }

    protected static void pancakeRecipes(@NotNull RecipeOutput recipeOutput, @NotNull DeferredItem<Item> pancakeBlock, @NotNull DeferredItem<Item> singlePancake, Ingredient topping, Ingredient ingredient, Ingredient ingredient2) {
        var batter = ModItems.BATTER;
        var servingItem = Items.BOWL;

        // Crafting a pancake block.
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, pancakeBlock, 1)
                .pattern(" T ")
                .pattern("XMX")
                .pattern("YBY")
                .define('T', topping) // Topping
                .define('X', ingredient) // Main ingredient
                .define('Y', ingredient2) // Optional secondary ingredient
                .define('M', batter)
                .define('B', servingItem)
                .unlockedBy(getHasName(batter), has(batter))
                .save(recipeOutput);

        // Split a stack of pancakes into separate pancakes.
        oneToOne(recipeOutput, RecipeCategory.MISC, pancakeBlock, singlePancake.get(), PancakeBlock.MAX_SERVINGS);

        // Combine separate pancakes together into a single stack
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, pancakeBlock)
                .requires(singlePancake, PancakeBlock.MAX_SERVINGS)
                .requires(servingItem) // Pancakes are always placed on a bowl
                .unlockedBy(getHasName(singlePancake), has(singlePancake))
                .save(recipeOutput, getRecipeName(singlePancake, pancakeBlock));
    }

    protected static void oneToOne(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return RusticPancakes.MOD_ID + ":" + getConversionRecipeName(result, item);
    }

    private static Ingredient vegetablesPatch() {
        return DifferenceIngredient.of(Ingredient.of(Tags.Items.FOODS_VEGETABLE), Ingredient.of(Items.MELON_SLICE));
    }
}
