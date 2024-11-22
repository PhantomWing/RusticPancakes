package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.tags.ForgeTags;
import com.phantomwing.rusticpancakes.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, RusticPancakes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.CHERRY_BLOSSOM_INGREDIENTS).add(
                Items.PINK_PETALS,
                Items.CHERRY_LEAVES,
                Items.CHERRY_SAPLING
        );

        // Milk
        this.tag(ForgeTags.MILK).add(Items.MILK_BUCKET);

        // Handle vegetables
        this.tag(ForgeTags.VEGETABLES)
            .addTag(ForgeTags.VEGETABLES_BEETROOT)
            .addTag(ForgeTags.VEGETABLES_CARROT)
            .addTag(ForgeTags.VEGETABLES_POTATO);
        tag(ForgeTags.VEGETABLES_BEETROOT).add(Items.BEETROOT);
        tag(ForgeTags.VEGETABLES_CARROT).add(Items.CARROT);
        tag(ForgeTags.VEGETABLES_POTATO).add(Items.POTATO);
    }
}
