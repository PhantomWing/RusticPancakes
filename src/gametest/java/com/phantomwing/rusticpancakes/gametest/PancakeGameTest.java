package com.phantomwing.rusticpancakes.gametest;

import com.phantomwing.rusticpancakes.RusticPancakes;
import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

/**
 * Game tests for a placed stack ({@link PancakeBlock}). Run headless with {@code ./gradlew runGameTestServer}.
 */
@GameTestHolder(RusticPancakes.MOD_ID)
@PrefixGameTestTemplate(false)
public class PancakeGameTest {
    /** Where the stack sits. */
    private static final BlockPos STACK = new BlockPos(1, 2, 1);

    /** A comparator reads the pancakes left on a stack, 1 to 6. */
    @GameTest(template = "empty")
    public static void aComparatorReadsThePancakesLeft(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(STACK);
        for (int taken = 0; taken < PancakeBlock.MAX_SERVINGS; taken++) {
            int left = PancakeBlock.MAX_SERVINGS - taken;
            int signal = ModBlocks.HONEY_PANCAKES.get().defaultBlockState()
                    .setValue(PancakeBlock.SERVINGS, taken)
                    .getAnalogOutputSignal(helper.getLevel(), pos);
            if (signal != left) {
                helper.fail("a comparator should read " + left + " from a stack of " + left + ", reads " + signal);
                return;
            }
        }
        helper.succeed();
    }
}
