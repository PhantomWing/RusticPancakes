package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

/** The mod's advancement tab, laid out as Rustic Delight's pancake branch: Batter, then Syrup and Pancakes. */
public class ModAdvancements implements AdvancementSubProvider {
    private static final ResourceLocation BACKGROUND =
            ResourceLocation.withDefaultNamespace("textures/block/dirt_path_top.png");

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(ModItems.PANCAKES.get(), title("root"), description("root"),
                        BACKGROUND, AdvancementType.TASK, false, false, false)
                // No predicate: fires on any inventory change, so the tab appears immediately.
                // The empty array picks an overload - a bare hasItems() is ambiguous.
                .addCriterion("any_item", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[0]))
                .save(saver, id("root"));

        AdvancementHolder batter = obtainMatching(saver, root, "batter", ModItems.BATTER.get(),
                ItemPredicate.Builder.item().of(ModItems.BATTER.get()));
        obtainMatching(saver, batter, "syrup", ModItems.SYRUP.get(), ItemPredicate.Builder.item().of(ModTags.Items.SYRUP));
        obtainMatching(saver, batter, "pancakes", ModItems.PANCAKES.get(), ItemPredicate.Builder.item().of(ModTags.Items.PANCAKES));
    }

    /**
     * An advancement granted by picking up anything matching {@code match}. Pass a tag predicate so
     * datapacks and add-ons can grant it with their own items, or an item predicate for a one-off.
     */
    private static AdvancementHolder obtainMatching(Consumer<AdvancementHolder> saver, AdvancementHolder parent,
                                                    String name, Item icon, ItemPredicate.Builder match) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, AdvancementType.TASK, true, true, false)
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(match))
                .save(saver, id(name));
    }

    private static String id(String name) {
        return RusticPancakes.MOD_ID + ":main/" + name;
    }

    private static Component title(String name) {
        return Component.translatable("advancements." + RusticPancakes.MOD_ID + "." + name + ".title");
    }

    private static Component description(String name) {
        return Component.translatable("advancements." + RusticPancakes.MOD_ID + "." + name + ".description");
    }
}
