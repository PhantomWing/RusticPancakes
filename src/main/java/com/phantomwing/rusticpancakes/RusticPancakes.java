package com.phantomwing.rusticpancakes;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.item.ModItems;
import com.phantomwing.rusticpancakes.ui.ModCreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(RusticPancakes.MOD_ID)
public class RusticPancakes
{
    public static final String MOD_ID = "rusticpancakes";

    public RusticPancakes(IEventBus eventBus, ModContainer container)
    {
        NeoForge.EVENT_BUS.register(this);

        registerManagers(eventBus);
    }

    // Register all managers to the event bus.
    private void registerManagers(IEventBus eventBus) {
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeModeTab.register(eventBus);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
