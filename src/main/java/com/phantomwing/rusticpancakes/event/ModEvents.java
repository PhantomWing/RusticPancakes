package com.phantomwing.rusticpancakes.event;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RusticPancakes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    /**
     * Lets a sneaking player put a pancake back on the stack.
     *
     * <p>Vanilla skips a block's own interaction while the player sneaks with a full hand, which is
     * exactly the case this needs. Forge lets an event allow it anyway, on the client and the server
     * alike, and {@link PancakeBlock#use} does the rest.
     */
    @SubscribeEvent
    public static void allowPuttingPancakesBack(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (!player.isSecondaryUseActive()) {
            return;
        }

        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (!(state.getBlock() instanceof PancakeBlock pancake)) {
            return;
        }

        ItemStack heldStack = event.getItemStack();
        if (heldStack.is(pancake.servingItem.get())) {
            event.setUseBlock(Event.Result.ALLOW);
        }
    }
}
