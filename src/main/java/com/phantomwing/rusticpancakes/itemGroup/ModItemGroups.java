package com.phantomwing.rusticpancakes.itemGroup;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(RusticPancakes.MOD_ID, "item_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CHOCOLATE_PANCAKES))
                    .displayName(Text.translatable("itemGroup." + RusticPancakes.MOD_ID))
                    .entries((displayContext, entries) -> {
                        // Add items to this tab.
                        ModItems.CREATIVE_TAB_ITEMS.forEach(entries::add);
                    })
                    .build());

    public static void registerModItemGroups() {
        RusticPancakes.LOGGER.info("Registering item group for " + RusticPancakes.MOD_ID);
    }
}