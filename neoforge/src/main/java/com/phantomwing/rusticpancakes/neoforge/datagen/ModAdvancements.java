package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

/** The mod's advancement tab, laid out as Rustic Delight's pancake branch: Batter, then Syrup and Pancakes. */
public class ModAdvancements extends AdvancementSubProvider {
    private static final Identifier BACKGROUND =
            Identifier.withDefaultNamespace("block/dirt_path_top");

    public ModAdvancements(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStackTemplate(ModItems.PANCAKES.get()), title("root"), description("root"),
                        Optional.of(new ClientAsset.ResourceTexture(BACKGROUND)), AdvancementType.TASK, false, false, false))
                // No predicate: fires on any inventory change, so the tab appears immediately.
                // The empty array picks an overload - a bare hasItems() is ambiguous.
                .addCriterion("any_item", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[0]))
                .save(this.output, id("root"));

        HolderGetter<Item> items = this.output.lookup(Registries.ITEM);
        AdvancementHolder batter = obtainMatching(root, "batter", ModItems.BATTER.get(),
                ItemPredicate.Builder.item().of(items, ModItems.BATTER.get()));
        obtainMatching(batter, "syrup", ModItems.SYRUP.get(), ItemPredicate.Builder.item().of(items, ModTags.Items.SYRUP));
        obtainMatching(batter, "pancakes", ModItems.PANCAKES.get(), ItemPredicate.Builder.item().of(items, ModTags.Items.PANCAKES));
    }

    /**
     * An advancement granted by picking up anything matching {@code match}. Pass a tag predicate so
     * datapacks and add-ons can grant it with their own items, or an item predicate for a one-off.
     */
    private AdvancementHolder obtainMatching(AdvancementHolder parent, String name, Item icon, ItemPredicate.Builder match) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), AdvancementType.TASK, true, true, false)
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(match))
                .save(this.output, id(name));
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
