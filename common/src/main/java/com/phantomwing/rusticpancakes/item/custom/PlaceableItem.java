package com.phantomwing.rusticpancakes.item.custom;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

/** A block item that says it can be placed, in the same words and style as Farmer's Delight's feasts and pies. */
public class PlaceableItem extends BlockItem {
    private static final Component PLACEABLE = Component.translatable("tooltip." + RusticPancakes.MOD_ID + ".placeable")
            .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);

    public PlaceableItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(PLACEABLE);
    }
}
