package com.phantomwing.rusticpancakes.food;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FoodValues {
    // Cooking products
    public static final FoodComponent BATTER = (new FoodComponent.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).snack().build();

    // Pancakes
    public static final FoodComponent HONEY_PANCAKE = (new FoodComponent.Builder())
            .nutrition(4).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200, 0, false, false), 1.0F).build();
    public static final FoodComponent CHOCOLATE_PANCAKE = (new FoodComponent.Builder())
            .nutrition(4).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200, 0, false, false), 1.0F).build();
    public static final FoodComponent CHERRY_BLOSSOM_PANCAKE = (new FoodComponent.Builder())
            .nutrition(4).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, 0, false, false), 1.0F).build();
    public static final FoodComponent VEGETABLE_PANCAKE = (new FoodComponent.Builder())
            .nutrition(4).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1200, 0, false, false), 1.0F).build();
}