package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.ForgeTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
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

        // Pancakes
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.HONEY_PANCAKES.get(), 1)
                .pattern("XHX")
                .pattern("SBS")
                .pattern("XYX")
                .define('S', Items.SWEET_BERRIES)
                .define('H', Items.HONEY_BOTTLE)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER.get())
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER.get()), has(ModItems.BATTER.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHOCOLATE_PANCAKES.get(), 1)
                .pattern("XCX")
                .pattern("CBC")
                .pattern("XYX")
                .define('C', Items.COCOA_BEANS)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER.get())
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER.get()), has(ModItems.BATTER.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHERRY_BLOSSOM_PANCAKES.get(), 1)
                .pattern("XPX")
                .pattern("PBP")
                .pattern("XYX")
                .define('P', ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS)
                .define('X', Items.SUGAR)
                .define('B', ModItems.BATTER.get())
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER.get()), has(ModItems.BATTER.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.VEGETABLE_PANCAKES.get(), 1)
                .pattern("LVL")
                .pattern("VBV")
                .pattern("LYL")
                .define('L', Items.SUGAR)
                .define('V', ForgeTags.VEGETABLES)
                .define('B', ModItems.BATTER.get())
                .define('Y', Items.BOWL)
                .unlockedBy(getHasName(ModItems.BATTER.get()), has(ModItems.BATTER.get()))
                .save(output);
    }
}
