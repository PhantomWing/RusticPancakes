package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/** Block states and item models: the client item definitions under {@code items/} come with them. */
public class ModModelProvider extends ModelProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public ModModelProvider(PackOutput output) {
        super(output, RusticPancakes.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        pancakeBlock(blockModels, ModBlocks.PANCAKES.get());
        pancakeBlock(blockModels, ModBlocks.HONEY_PANCAKES.get());
        pancakeBlock(blockModels, ModBlocks.CHOCOLATE_PANCAKES.get());
        pancakeBlock(blockModels, ModBlocks.CHERRY_BLOSSOM_PANCAKES.get());
        pancakeBlock(blockModels, ModBlocks.VEGETABLE_PANCAKES.get());
        pancakeBlock(blockModels, ModBlocks.PUMPKIN_PANCAKES.get());

        simpleItem(itemModels, ModItems.BATTER);
        simpleItem(itemModels, ModItems.SYRUP);
        simpleItem(itemModels, ModItems.CHERRY_BLOSSOM_PANCAKE);
        simpleItem(itemModels, ModItems.CHERRY_BLOSSOM_PANCAKES);
        simpleItem(itemModels, ModItems.CHOCOLATE_PANCAKE);
        simpleItem(itemModels, ModItems.CHOCOLATE_PANCAKES);
        simpleItem(itemModels, ModItems.HONEY_PANCAKE);
        simpleItem(itemModels, ModItems.HONEY_PANCAKES);
        simpleItem(itemModels, ModItems.VEGETABLE_PANCAKE);
        simpleItem(itemModels, ModItems.VEGETABLE_PANCAKES);
        simpleItem(itemModels, ModItems.PUMPKIN_PANCAKE);
        simpleItem(itemModels, ModItems.PUMPKIN_PANCAKES);
        simpleItem(itemModels, ModItems.PANCAKE);
        simpleItem(itemModels, ModItems.PANCAKES);
    }

    /** The hand-made stack models are named for the pancakes they show, so the packed servings value maps straight onto them. */
    private void pancakeBlock(BlockModelGenerators blockModels, Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.properties(PancakeBlock.FACING, PancakeBlock.SERVINGS)
                        .generate((facing, servings) -> rotated(Variant.variant()
                                .with(VariantProperties.MODEL, RusticPancakes.resourceLocation(
                                        "block/" + name + "_stack_" + PancakeBlock.pancakesPresentFor(servings))), facing))));
    }

    /** Facing north needs no rotation, and says none. */
    private static Variant rotated(Variant variant, Direction facing) {
        return switch (((int) facing.toYRot() + DEFAULT_ANGLE_OFFSET) % 360) {
            case 90 -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
            case 180 -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
            case 270 -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
            default -> variant;
        };
    }

    /** A flat item model from the item's own sprite. */
    private void simpleItem(ItemModelGenerators itemModels, RegistrySupplier<Item> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }
}
