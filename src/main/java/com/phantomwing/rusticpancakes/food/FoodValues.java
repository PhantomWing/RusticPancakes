package com.phantomwing.rusticpancakes.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodValues {
    // Cooking products
    public static final FoodProperties BATTER = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).fast().build();

    // Pancakes
    public static final FoodProperties HONEY_PANCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0, false, false), 1.0F).build();
    public static final FoodProperties CHOCOLATE_PANCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0, false, false), 1.0F).build();
    public static final FoodProperties VEGETABLE_PANCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0, false, false), 1.0F).build();
}