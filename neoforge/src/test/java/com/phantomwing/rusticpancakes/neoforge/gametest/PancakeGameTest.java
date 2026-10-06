package com.phantomwing.rusticpancakes.neoforge.gametest;

import com.phantomwing.rusticpancakes.block.ModBlocks;
import com.phantomwing.rusticpancakes.block.custom.PancakeBlock;
import com.phantomwing.rusticpancakes.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

import static com.phantomwing.rusticpancakes.neoforge.gametest.TestCompat.entitiesLoadedAroundSpawn;
import static com.phantomwing.rusticpancakes.neoforge.gametest.TestCompat.fail;

/**
 * Game tests for taking pancakes off a placed stack and putting them back ({@link PancakeBlock}), and
 * for what a stack drops. Listed in {@link GameTests}; run headless with {@code ./gradlew :neoforge:runGameTest}.
 *
 * <p>A click goes through the server's own right-click handling, {@code ServerPlayerGameMode#useItemOn},
 * so NeoForge's right-click event fires and with it the handler that lets a sneaking player put a
 * pancake back. Calling the block directly would skip exactly the part that needs testing.</p>
 *
 * <p>These bodies read the same on every line: a call whose shape differs between Minecraft versions
 * goes through {@link TestCompat} rather than being written out here.</p>
 */
public final class PancakeGameTest {
    /** Where the stack sits, on a stone block placed beneath it. */
    private static final BlockPos STACK = new BlockPos(1, 2, 1);

    /** How long to wait for the entities around the world spawn, which a mock player needs. */
    private static final int SPAWN_TICKS = 100;

    /** How long a dropped item gets to show up in an entity search. */
    private static final int DROP_TICKS = 20;

    private PancakeGameTest() {
    }

    public static void takingAPancakePutsItInTheInventory(GameTestHelper helper) {
        withPlayer(helper, player -> {
            placeStack(helper, ModBlocks.PANCAKES.get(), 6);

            click(helper, player);

            expectPancakes(helper, 5);
            expectCount(helper, player, ModItems.PANCAKE.get(), 1, "taking a pancake");
            helper.succeed();
        });
    }

    /** Only a sneaking player puts one back; holding a different pancake still takes one off. */
    public static void holdingAnotherPancakeStillTakesOne(GameTestHelper helper) {
        withPlayer(helper, player -> {
            placeStack(helper, ModBlocks.PANCAKES.get(), 6);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.HONEY_PANCAKE.get()));

            click(helper, player);

            expectPancakes(helper, 5);
            expectCount(helper, player, ModItems.PANCAKE.get(), 1, "taking a pancake while holding a Honey Pancake");
            expectCount(helper, player, ModItems.HONEY_PANCAKE.get(), 1, "taking a pancake while holding a Honey Pancake");
            helper.succeed();
        });
    }

    /**
     * The last pancake comes out once, into the inventory, and the bowl it sat on drops beside it.
     * Rustic Delight breaks the block there without loot, so the pancake isn't dropped a second time,
     * and loses the bowl with it.
     */
    public static void theLastPancakeComesOutOnceAndDropsTheBowl(GameTestHelper helper) {
        withPlayer(helper, player -> {
            placeStack(helper, ModBlocks.PANCAKES.get(), 1);

            click(helper, player);

            if (!helper.getBlockState(STACK).isAir()) {
                fail(helper, "taking the last pancake should leave no stack behind, found " + helper.getBlockState(STACK));
                return;
            }
            expectCount(helper, player, ModItems.PANCAKE.get(), 1, "taking the last pancake");
            // Once the bowl turns up, a search finds what was dropped, so the pancake not turning up means something.
            whenDropped(helper, Items.BOWL, DROP_TICKS, () -> {
                if (droppedNear(helper, ModItems.PANCAKE.get()) > 0) {
                    fail(helper, "taking the last pancake also dropped a pancake on the ground");
                    return;
                }
                helper.succeed();
            });
        });
    }

    public static void sneakingWithAPancakePutsItBack(GameTestHelper helper) {
        withPlayer(helper, player -> {
            placeStack(helper, ModBlocks.PANCAKES.get(), 6);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.PANCAKE.get(), 3));
            player.setShiftKeyDown(true);

            click(helper, player);

            expectPancakes(helper, 7);
            expectCount(helper, player, ModItems.PANCAKE.get(), 2, "putting a pancake back");
            helper.succeed();
        });
    }

    /** At twelve the stack is full: the pancake stays in hand and the stack stays as it is. */
    public static void aStackStopsAtTwelve(GameTestHelper helper) {
        withPlayer(helper, player -> {
            placeStack(helper, ModBlocks.PANCAKES.get(), PancakeBlock.MAX_TOTAL_SERVINGS);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.PANCAKE.get(), 3));
            player.setShiftKeyDown(true);

            click(helper, player);

            expectPancakes(helper, PancakeBlock.MAX_TOTAL_SERVINGS);
            expectCount(helper, player, ModItems.PANCAKE.get(), 3, "putting a pancake on a full stack");
            helper.succeed();
        });
    }

    /** A whole crafted plate breaks into the block itself; any other height into its pancakes and the bowl. */
    public static void breakingAStackDropsWhatIsLeft(GameTestHelper helper) {
        Block pancakes = ModBlocks.PANCAKES.get();
        Item pancake = ModItems.PANCAKE.get();

        expectDrops(helper, 6, List.of(new ItemStack(pancakes.asItem())));
        expectDrops(helper, 4, List.of(new ItemStack(pancake, 4), new ItemStack(Items.BOWL)));
        expectDrops(helper, 1, List.of(new ItemStack(pancake, 1), new ItemStack(Items.BOWL)));
        expectDrops(helper, 9, List.of(new ItemStack(pancake, 9), new ItemStack(Items.BOWL)));
        expectDrops(helper, 12, List.of(new ItemStack(pancake, 12), new ItemStack(Items.BOWL)));
        helper.succeed();
    }

    /**
     * {@code servings} 0 to 5 still mean the servings taken off a plate of 6, as they did before stacks
     * could grow, so a stack placed in an existing world keeps its height. 6 to 11 hold 7 to 12.
     */
    public static void placedStacksKeepTheirHeight(GameTestHelper helper) {
        int[] expected = {6, 5, 4, 3, 2, 1, 7, 8, 9, 10, 11, 12};
        for (int servings = 0; servings < expected.length; servings++) {
            int present = PancakeBlock.pancakesPresentFor(servings);
            if (present != expected[servings]) {
                fail(helper, "servings " + servings + " should show " + expected[servings] + " pancakes, shows " + present);
                return;
            }
            if (PancakeBlock.servingsFor(present) != servings) {
                fail(helper, present + " pancakes should be stored as servings " + servings + ", are stored as " + PancakeBlock.servingsFor(present));
                return;
            }
        }
        helper.succeed();
    }

    // ---------------------------------------------------------------- helpers

    /** Runs {@code body} with a survival mock player, once the entities around spawn have loaded. */
    private static void withPlayer(GameTestHelper helper, Consumer<ServerPlayer> body) {
        whenSpawnLoaded(helper, SPAWN_TICKS, () -> {
            ServerPlayer player = helper.makeMockServerPlayerInLevel();
            player.setGameMode(GameType.SURVIVAL);
            body.accept(player);
        });
    }

    /**
     * A mock player waits for the entities of the chunks around the world spawn, which only load as the
     * level ticks, so making one before they have can hang the run for good.
     */
    private static void whenSpawnLoaded(GameTestHelper helper, int ticksLeft, Runnable then) {
        if (entitiesLoadedAroundSpawn(helper.getLevel(), 2)) {
            then.run();
        } else if (ticksLeft <= 0) {
            fail(helper, "the entities around the world spawn never loaded, so no mock player could be made");
        } else {
            helper.runAfterDelay(1, () -> whenSpawnLoaded(helper, ticksLeft - 1, then));
        }
    }

    private static void placeStack(GameTestHelper helper, Block block, int pancakes) {
        helper.setBlock(STACK.below(), Blocks.STONE);
        helper.setBlock(STACK, stackOf(block, pancakes));
    }

    private static BlockState stackOf(Block block, int pancakes) {
        return block.defaultBlockState().setValue(PancakeBlock.SERVINGS, PancakeBlock.servingsFor(pancakes));
    }

    /** A right-click on the top of the stack with whatever the player holds, as the server handles one. */
    private static void click(GameTestHelper helper, ServerPlayer player) {
        BlockPos pos = helper.absolutePos(STACK);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        player.gameMode.useItemOn(player, helper.getLevel(), player.getItemInHand(InteractionHand.MAIN_HAND),
                InteractionHand.MAIN_HAND, hit);
    }

    private static void expectPancakes(GameTestHelper helper, int expected) {
        BlockState state = helper.getBlockState(STACK);
        if (!(state.getBlock() instanceof PancakeBlock)) {
            fail(helper, "expected a stack of " + expected + " pancakes, found " + state);
            return;
        }
        int present = PancakeBlock.getPancakesPresent(state);
        if (present != expected) {
            fail(helper, "expected a stack of " + expected + " pancakes, found " + present);
        }
    }

    private static void expectCount(GameTestHelper helper, ServerPlayer player, Item item, int expected, String after) {
        int found = player.getInventory().countItem(item);
        if (found != expected) {
            fail(helper, "after " + after + " the player should have " + expected + " " + item + ", has " + found);
        }
    }

    /** Fails the test unless breaking a stack of {@code pancakes} drops exactly {@code expected}. */
    private static void expectDrops(GameTestHelper helper, int pancakes, List<ItemStack> expected) {
        BlockState state = stackOf(ModBlocks.PANCAKES.get(), pancakes);
        List<ItemStack> drops = Block.getDrops(state, helper.getLevel(), helper.absolutePos(STACK), null);
        boolean same = drops.size() == expected.size();
        for (int i = 0; same && i < drops.size(); i++) {
            same = ItemStack.matches(drops.get(i), expected.get(i));
        }
        if (!same) {
            fail(helper, "breaking a stack of " + pancakes + " should drop " + expected + ", dropped " + drops);
        }
    }

    /**
     * Runs {@code then} once {@code item} lies beside the stack, failing when it never turns up. A drop
     * can take a tick or two to show up in an entity search, so it is looked for again for a while.
     */
    private static void whenDropped(GameTestHelper helper, Item item, int ticksLeft, Runnable then) {
        if (droppedNear(helper, item) > 0) {
            then.run();
        } else if (ticksLeft <= 0) {
            fail(helper, "no " + item + " was dropped beside the stack");
        } else {
            helper.runAfterDelay(1, () -> whenDropped(helper, item, ticksLeft - 1, then));
        }
    }

    private static int droppedNear(GameTestHelper helper, Item item) {
        AABB around = new AABB(helper.absolutePos(STACK)).inflate(2.0);
        return helper.getLevel().getEntitiesOfClass(ItemEntity.class, around).stream()
                .filter(entity -> entity.getItem().is(item))
                .mapToInt(entity -> entity.getItem().getCount())
                .sum();
    }
}
