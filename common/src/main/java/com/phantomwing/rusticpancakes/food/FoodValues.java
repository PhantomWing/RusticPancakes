package com.phantomwing.rusticpancakes.food;

import net.minecraft.world.food.FoodProperties;

/** Hunger and saturation. What eating does beyond that - effects, speed, sound - is in {@link ConsumableValues}. */
public class FoodValues {
    // Cooking products
    public static final FoodProperties BATTER = food(2, 0.2F);
    public static final FoodProperties SYRUP = food(2, 0.2F);

    // Pancakes
    public static final FoodProperties PANCAKE = food(4, 0.6F);
    public static final FoodProperties HONEY_PANCAKE = food(4, 0.6F);
    public static final FoodProperties CHOCOLATE_PANCAKE = food(4, 0.6F);
    public static final FoodProperties CHERRY_BLOSSOM_PANCAKE = food(4, 0.6F);
    public static final FoodProperties VEGETABLE_PANCAKE = food(4, 0.6F);
    public static final FoodProperties PUMPKIN_PANCAKE = food(4, 0.6F);

    private static FoodProperties food(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build();
    }
}
