package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.PackGenerator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Every provider, client and server alike: the {@code clientData} run fires {@link GatherDataEvent.Client}, whose
 * environment is a full client, so the server data generates beside the models.
 */
@EventBusSubscriber(modid = RusticPancakes.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getWorldLookupProvider();
        PackGenerator pack = event.getDefaultPackGenerator();

        // Recipes, loot tables and advancements are reloadable registries, each made by a bootstrap. Only the
        // namespaces listed are written: minecraft too, so an override of vanilla's data isn't dropped without a word.
        pack.createReloadableRegistryObjects(new RegistrySetBuilder()
                        .add(ModRecipeProvider.create())
                        .add(Registries.LOOT_TABLE, ModLootTableProvider.create())
                        .add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(ModAdvancements::new))),
                Set.of(RusticPancakes.MOD_ID, "minecraft"));

        event.addProvider(new ModModelProvider(output));

        event.addProvider(new ModBlockTagsProvider(output, lookupProvider));
        event.addProvider(new ModItemTagsProvider(output, lookupProvider));
    }
}
