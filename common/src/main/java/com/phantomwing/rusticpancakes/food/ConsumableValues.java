package com.phantomwing.rusticpancakes.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

/** How each food is eaten or drunk, and the effects it gives, which 1.21.2 moved out of FoodProperties. */
public class ConsumableValues {
    /** What FoodProperties.Builder#fast() used to mean. */
    private static final float FAST = 0.8F;

    // Cooking products
    public static final Consumable BATTER = Consumables.defaultFood()
            .consumeSeconds(FAST)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F))
            .build();
    public static final Consumable SYRUP = Consumables.defaultDrink()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0, false, false), 1.0F))
            .build();

    // Pancakes
    public static final Consumable HONEY_PANCAKE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0, false, false), 1.0F))
            .build();
    public static final Consumable CHOCOLATE_PANCAKE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0, false, false), 1.0F))
            .build();
    public static final Consumable CHERRY_BLOSSOM_PANCAKE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 0, false, false), 1.0F))
            .build();
    public static final Consumable VEGETABLE_PANCAKE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0, false, false), 1.0F))
            .build();
    public static final Consumable PUMPKIN_PANCAKE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 0, false, false), 1.0F))
            .build();
}
