package com.phantomwing.rusticpancakes.neoforge.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The calls a test makes whose shape differs between Minecraft versions, each behind a signature every
 * line shares. This is the one test file that differs from line to line, so the bodies don't have to:
 * when a port turns up another such call, it goes here rather than into a body.
 */
public final class TestCompat {
    private TestCompat() {
    }

    /** Fails the test. {@code GameTestHelper.fail} takes only a Component from 1.21.5 to 1.21.8. */
    public static void fail(GameTestHelper helper, String message) {
        helper.fail(Component.literal(message));
    }

    /** What a comparator reads from {@code state}. From 1.21.9 the read takes the side it comes from. */
    public static int analogSignal(BlockState state, ServerLevel level, BlockPos pos) {
        return state.getAnalogOutputSignal(level, pos, Direction.NORTH);
    }

    /** A server player for the test to act as. Forge 1.19.2 and 1.20.1 need a FakePlayer here instead. */
    public static ServerPlayer mockServerPlayer(GameTestHelper helper) {
        return helper.makeMockServerPlayerInLevel();
    }

    /**
     * Whether a mock player can be made yet. On 1.21.6 to 1.21.8 placing one waits for the entities
     * around the world spawn, so the tests wait for them first. Here placing a player waits for nothing
     * (a login waits in PrepareSpawnTask, which a mock player skips), and nothing keeps the spawn area
     * loaded, so waiting for it can only time out.
     */
    public static boolean entitiesLoadedAroundSpawn(ServerLevel level, int radius) {
        return true;
    }
}
