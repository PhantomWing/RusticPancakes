package com.phantomwing.rusticpancakes.item.custom;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ConsumableItem extends Item {
    private static final MutableComponent NO_EFFECTS;
    private final boolean hasFoodEffectTooltip;

    public ConsumableItem(Item.Properties properties) {
        super(properties);
        this.hasFoodEffectTooltip = false;
    }

    public ConsumableItem(Item.Properties properties, boolean hasFoodEffectTooltip) {
        super(properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
            if (this.hasFoodEffectTooltip) {
                Objects.requireNonNull(tooltip);
                this.addFoodEffectTooltip(stack, tooltip::add, 1.0F, context.tickRate());
            }
    }

    public void addFoodEffectTooltip(ItemStack stack, Consumer<Component> tooltipAdder, float durationFactor, float tickRate) {
        // 1.21.2 moved a food's effects into its consumable component.
        Consumable consumable = stack.get(DataComponents.CONSUMABLE);
        if (consumable != null) {
            List<MobEffectInstance> effectList = consumable.onConsumeEffects().stream()
                    .filter(onConsume -> onConsume instanceof ApplyStatusEffectsConsumeEffect)
                    .flatMap(onConsume -> ((ApplyStatusEffectsConsumeEffect) onConsume).effects().stream())
                    .toList();
            List<Pair<Holder<Attribute>, AttributeModifier>> attributeList = Lists.newArrayList();
            MutableComponent mutableComponent;
            Iterator var8;
            MobEffect effect;
            if (effectList.isEmpty()) {
                tooltipAdder.accept(NO_EFFECTS);
            } else {
                for(var8 = effectList.iterator(); var8.hasNext(); tooltipAdder.accept(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()))) {
                    MobEffectInstance instance = (MobEffectInstance)var8.next();
                    mutableComponent = Component.translatable(instance.getDescriptionId());
                    effect = (MobEffect)instance.getEffect().value();
                    effect.createModifiers(instance.getAmplifier(), (attributeHolder, attributeModifier) -> {
                        attributeList.add(new Pair(attributeHolder, attributeModifier));
                    });
                    if (instance.getAmplifier() > 0) {
                        mutableComponent = Component.translatable("potion.withAmplifier", new Object[]{mutableComponent, Component.translatable("potion.potency." + instance.getAmplifier())});
                    }

                    if (instance.getDuration() > 20) {
                        mutableComponent = Component.translatable("potion.withDuration", new Object[]{mutableComponent, MobEffectUtil.formatDuration(instance, durationFactor, tickRate)});
                    }
                }
            }

            if (!attributeList.isEmpty()) {
                tooltipAdder.accept(CommonComponents.EMPTY);
                tooltipAdder.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
                var8 = attributeList.iterator();

                while(var8.hasNext()) {
                    Pair<Holder<Attribute>, AttributeModifier> pair = (Pair)var8.next();
                    AttributeModifier attributemodifier = (AttributeModifier)pair.getSecond();
                    double amount = attributemodifier.amount();
                    double formattedAmount;
                    if (attributemodifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && attributemodifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                        formattedAmount = attributemodifier.amount();
                    } else {
                        formattedAmount = attributemodifier.amount() * 100.0;
                    }

                    if (amount > 0.0) {
                        tooltipAdder.accept(Component.translatable("attribute.modifier.plus." + attributemodifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((Attribute)((Holder)pair.getFirst()).value()).getDescriptionId())}).withStyle(ChatFormatting.BLUE));
                    } else if (amount < 0.0) {
                        formattedAmount *= -1.0;
                        tooltipAdder.accept(Component.translatable("attribute.modifier.take." + attributemodifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((Attribute)((Holder)pair.getFirst()).value()).getDescriptionId())}).withStyle(ChatFormatting.RED));
                    }
                }
            }

        }
    }

    static {
        NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    }
}
