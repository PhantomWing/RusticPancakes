package com.phantomwing.rusticpancakes.item.custom;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

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

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            this.affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();
        Player player;
        if (stack.getFoodProperties(consumer) != null) {
            super.finishUsingItem(stack, level, consumer);
        } else {
            player = consumer instanceof Player ? (Player)consumer : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
            }

            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        if (stack.isEmpty()) {
            return containerStack;
        } else {
            if (consumer instanceof Player) {
                player = (Player)consumer;
                if (!((Player)consumer).getAbilities().instabuild && !player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
            }

            return stack;
        }
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
            if (this.hasFoodEffectTooltip) {
                Objects.requireNonNull(tooltip);
                this.addFoodEffectTooltip(stack, tooltip::add, 1.0F, context.tickRate());
            }
    }

    public void addFoodEffectTooltip(ItemStack stack, Consumer<Component> tooltipAdder, float durationFactor, float tickRate) {
        FoodProperties foodStats = stack.getFoodProperties((LivingEntity)null);
        if (foodStats != null) {
            List<FoodProperties.PossibleEffect> effectList = foodStats.effects();
            List<Pair<Holder<Attribute>, AttributeModifier>> attributeList = Lists.newArrayList();
            MutableComponent mutableComponent;
            Iterator var8;
            MobEffect effect;
            if (effectList.isEmpty()) {
                tooltipAdder.accept(NO_EFFECTS);
            } else {
                for(var8 = effectList.iterator(); var8.hasNext(); tooltipAdder.accept(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()))) {
                    FoodProperties.PossibleEffect possibleEffect = (FoodProperties.PossibleEffect)var8.next();
                    MobEffectInstance instance = possibleEffect.effect();
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
