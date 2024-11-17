package com.phantomwing.rusticpancakes.datagen.loot;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.*;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    // Actually add our loot tables.
    @Override
    protected void generate() {
        dropFoodBlock(ModBlocks.HONEY_PANCAKES.get(), PancakeBlock.SERVINGS, Items.BOWL);
        dropFoodBlock(ModBlocks.CHOCOLATE_PANCAKES.get(), PancakeBlock.SERVINGS, Items.BOWL);
        dropFoodBlock(ModBlocks.CHERRY_BLOSSOM_PANCAKES.get(), PancakeBlock.SERVINGS, Items.BOWL);
        dropFoodBlock(ModBlocks.VEGETABLE_PANCAKES.get(), PancakeBlock.SERVINGS, Items.BOWL);
    }

    // The contents of this Iterable are used for validation.
    // We return an Iterable over our block registry's values here.
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    private void dropFoodBlock(Block block, IntegerProperty servings, ItemLike containerItem) {
        this.add(block, blockParam -> createFoodBlockDrops(blockParam, servings, containerItem));
    }

    private LootTable.Builder createFoodBlockDrops(Block block, IntegerProperty servings, ItemLike containerItem) {
        // Condition that checks if any servings have been taken.
        LootItemCondition.Builder noServingsTaken = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(servings, 0));

        return this.applyExplosionDecay(
                block,
                LootTable.lootTable()
                        // If no servings have been taken yet, drop the block.
                        .withPool(LootPool.lootPool()
                                .when(noServingsTaken)
                                .add(LootItem.lootTableItem(block))
                        )
                        // Else, drop the container item.
                        .withPool(LootPool.lootPool()
                                .when(InvertedLootItemCondition.invert(noServingsTaken))
                                .add(LootItem.lootTableItem(containerItem))
                        )
        );
    }
}
