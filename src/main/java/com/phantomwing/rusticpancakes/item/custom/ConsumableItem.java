//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.phantomwing.rusticpancakes.item.custom;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
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
        if (stack.isEdible()) {
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

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (this.hasFoodEffectTooltip) {
            this.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("farmersdelight." + key, args);
    }

    private void addFoodEffectTooltip(ItemStack itemIn, List<Component> lores, float durationFactor) {
        FoodProperties foodStats = itemIn.getItem().getFoodProperties();
        if (foodStats != null) {
            List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
            List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
            Iterator var6;
            Pair pair;
            MutableComponent iformattabletextcomponent;
            MobEffect effect;
            if (effectList.isEmpty()) {
                lores.add(NO_EFFECTS);
            } else {
                for(var6 = effectList.iterator(); var6.hasNext(); lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()))) {
                    pair = (Pair)var6.next();
                    MobEffectInstance instance = (MobEffectInstance)pair.getFirst();
                    iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
                    effect = instance.getEffect();
                    Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                    if (!attributeMap.isEmpty()) {
                        Iterator var12 = attributeMap.entrySet().iterator();

                        while(var12.hasNext()) {
                            Map.Entry<Attribute, AttributeModifier> entry = (Map.Entry)var12.next();
                            AttributeModifier rawModifier = (AttributeModifier)entry.getValue();
                            AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
                            attributeList.add(new Pair((Attribute)entry.getKey(), modifier));
                        }
                    }

                    if (instance.getAmplifier() > 0) {
                        iformattabletextcomponent = Component.translatable("potion.withAmplifier", new Object[]{iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier())});
                    }

                    if (instance.getDuration() > 20) {
                        iformattabletextcomponent = Component.translatable("potion.withDuration", new Object[]{iformattabletextcomponent, MobEffectUtil.formatDuration(instance, durationFactor)});
                    }
                }
            }

            if (!attributeList.isEmpty()) {
                lores.add(CommonComponents.EMPTY);
                lores.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
                var6 = attributeList.iterator();

                while(var6.hasNext()) {
                    pair = (Pair)var6.next();
                    AttributeModifier modifier = (AttributeModifier)pair.getSecond();
                    double amount = modifier.getAmount();
                    double formattedAmount;
                    if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                        formattedAmount = modifier.getAmount();
                    } else {
                        formattedAmount = modifier.getAmount() * 100.0;
                    }

                    if (amount > 0.0) {
                        lores.add(Component.translatable("attribute.modifier.plus." + modifier.getOperation().toValue(), new Object[]{ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((Attribute)pair.getFirst()).getDescriptionId())}).withStyle(ChatFormatting.BLUE));
                    } else if (amount < 0.0) {
                        formattedAmount *= -1.0;
                        lores.add(Component.translatable("attribute.modifier.take." + modifier.getOperation().toValue(), new Object[]{ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((Attribute)pair.getFirst()).getDescriptionId())}).withStyle(ChatFormatting.RED));
                    }
                }
            }

        }
    }

    static {
        NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    }
}
