package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.tags.ModTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    /** The pancakes tag's blocks, which the item tag of the same name lists too. */
    static final List<RegistrySupplier<Block>> PANCAKES = List.of(
            ModBlocks.PANCAKES,
            ModBlocks.HONEY_PANCAKES,
            ModBlocks.CHOCOLATE_PANCAKES,
            ModBlocks.CHERRY_BLOSSOM_PANCAKES,
            ModBlocks.VEGETABLE_PANCAKES,
            ModBlocks.PUMPKIN_PANCAKES
    );

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RusticPancakes.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        PANCAKES.forEach(block -> this.tag(ModTags.Blocks.PANCAKES).add(block.get()));
    }
}
