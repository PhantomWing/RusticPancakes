package com.phantomwing.rusticpancakes.event;

import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ModEvents {
    public static void register() {
        allowPuttingPancakesBack();
    }

    /**
     * Lets a sneaking player put a pancake back on the stack.
     *
     * <p>Vanilla skips a block's own interaction while the player sneaks with a full hand, which is
     * exactly the case this needs, so run {@link PancakeBlock#useItemOn} ourselves before that check.
     * Failing at the top of the stack keeps the held pancake from being eaten instead,
     * and on Fabric keeps the client from sending the click at all.
     */
    private static void allowPuttingPancakesBack() {
        // fromMinecraft keeps the InteractionResult itself, so FAIL reaches the game as FAIL and stops the click.
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) ->
                EventResult.fromMinecraft(putPancakeBack(player, hand, pos, face)));
    }

    private static InteractionResult putPancakeBack(Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (!player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }

        BlockState state = player.level().getBlockState(pos);
        if (!(state.getBlock() instanceof PancakeBlock pancake)) {
            return InteractionResult.PASS;
        }

        ItemStack heldStack = player.getItemInHand(hand);
        if (!heldStack.is(pancake.servingItem.get())) {
            return InteractionResult.PASS;
        }

        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), face, pos, false);
        InteractionResult result = pancake.useItemOn(heldStack, state, player.level(), pos, player, hand, hit);
        return result == InteractionResult.SUCCESS ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }
}
