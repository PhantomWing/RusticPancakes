package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.tags.CommonTags;
import com.phantomwing.rusticpancakes.tags.CompatibilityTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RusticPancakes.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS).add(
                Items.PINK_PETALS,
                Items.CHERRY_LEAVES,
                Items.CHERRY_SAPLING
        );

        this.tag(CommonTags.FOODS_MILK).add(Items.MILK_BUCKET);
        this.tag(CommonTags.FOODS_PUMPKIN).add(Items.PUMPKIN);
        // Fabric API had no c:eggs before 1.21.4, and a recipe naming a tag that doesn't exist doesn't load.
        this.tag(Tags.Items.EGGS).add(Items.EGG);

        // Syrup
        this.tag(ModTags.Items.SYRUP_INGREDIENTS).add(
                Items.APPLE
        ).addTag(Tags.Items.CROPS_BEETROOT);

        this.tag(ModTags.Items.SYRUP).add(
                ModItems.SYRUP.get()
        // Hearth and Harvest's syrup bottle works anywhere our syrup does.
        ).add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath(CompatibilityTags.HEARTH_AND_HARVEST, "syrup_bottle")));

        // The pancakes block tag's items. 1.21.6 dropped copy(), and with it the block tags this provider read.
        ModBlockTagsProvider.PANCAKES.forEach(block -> this.tag(ModTags.Items.PANCAKES).add(block.get().asItem()));
    }
}
