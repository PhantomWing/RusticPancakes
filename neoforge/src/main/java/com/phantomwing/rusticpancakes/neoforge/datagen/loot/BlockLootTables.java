package com.phantomwing.rusticpancakes.neoforge.datagen.loot;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    // Actually add our loot tables.
    @Override
    protected void generate() {
        dropPancakeBlock(ModBlocks.PANCAKES.get(), ModItems.PANCAKE.get());
        dropPancakeBlock(ModBlocks.HONEY_PANCAKES.get(), ModItems.HONEY_PANCAKE.get());
        dropPancakeBlock(ModBlocks.CHOCOLATE_PANCAKES.get(), ModItems.CHOCOLATE_PANCAKE.get());
        dropPancakeBlock(ModBlocks.CHERRY_BLOSSOM_PANCAKES.get(), ModItems.CHERRY_BLOSSOM_PANCAKE.get());
        dropPancakeBlock(ModBlocks.VEGETABLE_PANCAKES.get(), ModItems.VEGETABLE_PANCAKE.get());
        dropPancakeBlock(ModBlocks.PUMPKIN_PANCAKES.get(), ModItems.PUMPKIN_PANCAKE.get());
    }

    // The contents of this Iterable are used for validation.
    // We return an Iterable over our block registry's values here.
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> entry : ModBlocks.BLOCKS) {
            blocks.add(entry.get());
        }
        return blocks;
    }

    private void dropPancakeBlock(Block block, ItemLike pancakeItem) {
        this.add(block, blockParam -> createPancakeDrops(blockParam, pancakeItem));
    }

    /**
     * A pancake stack holds 0 to {@link PancakeBlock#MAX_TOTAL_SERVINGS} pancakes. Breaking one that
     * is exactly a crafted plate returns the placeable block; any other height returns the loose
     * pancakes plus the bowl, and an empty tray the bowl alone.
     */
    private LootTable.Builder createPancakeDrops(Block block, ItemLike pancakeItem) {
        LootItemCondition.Builder isCraftedPlate = servingsIs(block, 0);

        LootTable.Builder lootTable = LootTable.lootTable()
                // An untouched plate drops the block itself, matching what the recipe produces.
                .withPool(LootPool.lootPool().when(isCraftedPlate).add(LootItem.lootTableItem(block)));

        for (int servings = 1; servings < PancakeBlock.MAX_TOTAL_SERVINGS; servings++) {
            lootTable.withPool(LootPool.lootPool()
                    .when(servingsIs(block, servings))
                    .add(LootItem.lootTableItem(pancakeItem)
                            .apply(SetItemCountFunction.setCount(
                                    ConstantValue.exactly(PancakeBlock.pancakesPresentFor(servings))))));
        }

        // The plate is only left over once the stack is no longer a whole crafted block.
        lootTable.withPool(LootPool.lootPool()
                .when(InvertedLootItemCondition.invert(isCraftedPlate))
                .add(LootItem.lootTableItem(Items.BOWL)));

        return this.applyExplosionDecay(block, lootTable);
    }

    private static LootItemCondition.Builder servingsIs(Block block, int servings) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(PancakeBlock.SERVINGS, servings));
    }
}
