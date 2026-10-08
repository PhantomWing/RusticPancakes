package com.phantomwing.rusticpancakes.neoforge.datagen;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Every provider, client and server alike: the {@code clientData} run fires {@link GatherDataEvent.Client}, whose
 * environment is a full client, so the server data generates beside the models.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = RusticPancakes.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ModRecipeProvider.Runner(output, lookupProvider));
        event.addProvider(ModLootTableProvider.create(output, lookupProvider));
        event.addProvider(new AdvancementProvider(output, lookupProvider, List.of(new ModAdvancements())));

        event.addProvider(new ModModelProvider(output));

        ModBlockTagsProvider blockTagsProvider = event.addProvider(new ModBlockTagsProvider(output, lookupProvider));
        event.addProvider(new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter()));
    }
}
