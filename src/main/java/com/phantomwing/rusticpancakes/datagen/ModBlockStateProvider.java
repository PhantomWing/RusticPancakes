package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RusticPancakes.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        pancakeBlock(ModBlocks.HONEY_PANCAKES.get());
        pancakeBlock(ModBlocks.CHOCOLATE_PANCAKES.get());
        pancakeBlock(ModBlocks.CHERRY_BLOSSOM_PANCAKES.get());
        pancakeBlock(ModBlocks.VEGETABLE_PANCAKES.get());
    }

    private void pancakeBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                            int servings = state.getValue(PancakeBlock.SERVINGS);
                            String suffix = "_stage" + servings;
                            return ConfiguredModel.builder()
                                    .modelFile(existingModel(blockName(block) + suffix))
                                    .rotationY(((int) state.getValue(PancakeBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                                    .build();
                        }
                );
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(RusticPancakes.MOD_ID, "block/" + path);
    }

    private ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }
}
