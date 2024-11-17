package com.phantomwing.rusticpancakes.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

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
    private void simpleItem(DeferredItem<Item> item) {
        withExistingParent(getItemName(item), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", getItemResourceLocation(item));
    }

    private String getItemName(DeferredItem<Item> item) {
        return item.getId().getPath();
    }

    private ResourceLocation getItemResourceLocation(DeferredItem<Item> item) {
        return ResourceLocation.fromNamespaceAndPath(RusticPancakes.MOD_ID, "item/" + getItemName(item));
    }
}
