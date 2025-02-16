package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.ForgeTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        buildCraftingRecipes(output);
    }

    private void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> output) {
        // Batter
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BATTER.get(), 1)
                .requires(Items.BOWL)
                .requires(ForgeTags.MILK)
                .requires(Tags.Items.EGGS)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);

        // Syrup
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SYRUP.get(), 1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModTags.Items.SYRUP_INGREDIENTS)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output);
        oneToOne(output, RecipeCategory.MISC, ModItems.SYRUP.get(), Items.SUGAR, 3);

        // Pancakes
        pancakeRecipes(output, ModItems.PANCAKES, ModItems.PANCAKE, Ingredient.of(ModTags.Items.SYRUP), Ingredient.of(Items.SUGAR));
        pancakeRecipes(output, ModItems.HONEY_PANCAKES, ModItems.HONEY_PANCAKE, Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(Items.SWEET_BERRIES), Ingredient.of(Items.SUGAR));
        pancakeRecipes(output, ModItems.CHOCOLATE_PANCAKES, ModItems.CHOCOLATE_PANCAKE, Ingredient.of(ForgeTags.MILK), Ingredient.of(Items.COCOA_BEANS));
        pancakeRecipes(output, ModItems.VEGETABLE_PANCAKES, ModItems.VEGETABLE_PANCAKE, Ingredient.of(ForgeTags.MILK), Ingredient.of(ForgeTags.VEGETABLES));
        pancakeRecipes(output, ModItems.CHERRY_BLOSSOM_PANCAKES, ModItems.CHERRY_BLOSSOM_PANCAKE, Ingredient.of(ForgeTags.MILK), Ingredient.of(ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS));
        pancakeRecipes(output, ModItems.PUMPKIN_PANCAKES, ModItems.PUMPKIN_PANCAKE, Ingredient.of(ModTags.Items.SYRUP), Ingredient.of(ForgeTags.VEGETABLES_PUMPKIN), Ingredient.of(Items.SUGAR));
    }

    protected static void pancakeRecipes(@NotNull Consumer<FinishedRecipe> recipeOutput, @NotNull RegistryObject<Item> pancakeBlock, @NotNull RegistryObject<Item> singlePancake, Ingredient topping, Ingredient ingredient) {
        pancakeRecipes(recipeOutput, pancakeBlock, singlePancake, topping, ingredient, ingredient);
    }

    protected static void pancakeRecipes(@NotNull Consumer<FinishedRecipe> recipeOutput, @NotNull RegistryObject<Item> pancakeBlock, @NotNull RegistryObject<Item> singlePancake, Ingredient topping, Ingredient ingredient, Ingredient ingredient2) {
        var batter = ModItems.BATTER;
        var servingItem = Items.BOWL;

        // Crafting a pancake block.
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, pancakeBlock.get(), 1)
                .pattern(" T ")
                .pattern("XMX")
                .pattern("YBY")
                .define('T', topping) // Topping
                .define('X', ingredient) // Main ingredient
                .define('Y', ingredient2) // Optional secondary ingredient
                .define('M', batter.get())
                .define('B', servingItem)
                .unlockedBy(getHasName(batter.get()), has(batter.get()))
                .save(recipeOutput);

        // Split a stack of pancakes into separate pancakes.
        oneToOne(recipeOutput, RecipeCategory.MISC, pancakeBlock.get(), singlePancake.get(), PancakeBlock.MAX_SERVINGS);

        // Combine separate pancakes together into a single stack
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, pancakeBlock.get())
                .requires(singlePancake.get(), PancakeBlock.MAX_SERVINGS)
                .requires(servingItem) // Pancakes are always placed on a bowl
                .unlockedBy(getHasName(singlePancake.get()), has(singlePancake.get()))
                .save(recipeOutput, getRecipeName(singlePancake.get(), pancakeBlock.get()));
    }

    protected static void oneToOne(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return RusticPancakes.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
