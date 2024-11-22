package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RusticPancakes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleItem(ModItems.BATTER);
        simpleItem(ModItems.CHERRY_BLOSSOM_PANCAKES);
        simpleItem(ModItems.CHOCOLATE_PANCAKES);
        simpleItem(ModItems.HONEY_PANCAKES);
        simpleItem(ModItems.VEGETABLE_PANCAKES);
    }

    // A simple item with a model generated from its sprite.
    private void simpleItem(RegistryObject<Item> item) {
        withExistingParent(getItemName(item), new ResourceLocation("item/generated"))
                .texture("layer0", getItemResourceLocation(item));
    }

    private String getItemName(RegistryObject<Item> item) {
        return item.getId().getPath();
    }

    private ResourceLocation getItemResourceLocation(RegistryObject<Item> item) {
        return new ResourceLocation(RusticPancakes.MOD_ID, "item/" + getItemName(item));
    }
}
