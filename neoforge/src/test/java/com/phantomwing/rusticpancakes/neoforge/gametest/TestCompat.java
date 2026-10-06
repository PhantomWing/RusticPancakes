package com.phantomwing.rusticpancakes.neoforge.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

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
        helper.fail(message);
    }

    /**
     * Whether the entities within {@code radius} chunks of the world spawn have loaded, which a mock
     * player, placed near the spawn, needs first. 1.21.9 moved the spawn into the level's respawn
     * data, and 26.1 renamed ChunkPos's factory and packing.
     */
    public static boolean entitiesLoadedAroundSpawn(ServerLevel level, int radius) {
        return ChunkPos.rangeClosed(new ChunkPos(level.getSharedSpawnPos()), radius)
                .allMatch(chunk -> level.areEntitiesLoaded(chunk.toLong()));
    }
}
