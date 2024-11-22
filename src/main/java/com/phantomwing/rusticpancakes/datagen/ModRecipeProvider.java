package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.CommonTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;
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

        // Pancakes
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.HONEY_PANCAKES, 1)
                .pattern("XHX")
                .pattern("SBS")
                .pattern("XYX")
                .define('S', Items.SWEET_BERRIES)
                .define('H', Items.HONEY_BOTTLE)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER)
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER), has(ModItems.BATTER))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHOCOLATE_PANCAKES, 1)
                .pattern("XCX")
                .pattern("CBC")
                .pattern("XYX")
                .define('C', Items.COCOA_BEANS)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER)
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER), has(ModItems.BATTER))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHERRY_BLOSSOM_PANCAKES, 1)
                .pattern("XPX")
                .pattern("PBP")
                .pattern("XYX")
                .define('P', ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER)
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER), has(ModItems.BATTER))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.VEGETABLE_PANCAKES, 1)
                .pattern("LVL")
                .pattern("VBV")
                .pattern("LYL")
                .define('L', Items.SUGAR)
                .define('V', vegetablesPatch())
                .define('B', ModItems.BATTER)
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER), has(ModItems.BATTER))
                .save(output);
    }

    private static Ingredient vegetablesPatch() {
        return DifferenceIngredient.of(Ingredient.of(Tags.Items.FOODS_VEGETABLE), Ingredient.of(Items.MELON_SLICE));
    }
}
