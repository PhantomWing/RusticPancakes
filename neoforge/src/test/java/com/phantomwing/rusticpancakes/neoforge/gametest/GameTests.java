package com.phantomwing.rusticpancakes.neoforge.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Every game test on this line. A test is its body and its entry here, and both read the same on
 * every line: how the list is registered changed at 1.21.5 and belongs to {@link GameTestRegistration}
 * alone, so porting a test never touches a registration class or a JSON.
 */
public final class GameTests {
    public static final List<Test> ALL = List.of(
            test("taking_a_pancake_puts_it_in_the_inventory", PancakeGameTest::takingAPancakePutsItInTheInventory).maxTicks(200),
            test("holding_another_pancake_still_takes_one", PancakeGameTest::holdingAnotherPancakeStillTakesOne).maxTicks(200),
            test("the_last_pancake_leaves_an_empty_tray", PancakeGameTest::theLastPancakeLeavesAnEmptyTray).maxTicks(200),
            test("taking_up_an_empty_tray_drops_the_bowl", PancakeGameTest::takingUpAnEmptyTrayDropsTheBowl).maxTicks(200),
            test("sneaking_with_a_pancake_puts_it_back", PancakeGameTest::sneakingWithAPancakePutsItBack).maxTicks(200),
            test("a_pancake_goes_back_on_an_empty_tray", PancakeGameTest::aPancakeGoesBackOnAnEmptyTray).maxTicks(200),
            test("a_stack_stops_at_twelve", PancakeGameTest::aStackStopsAtTwelve).maxTicks(200),
            test("breaking_a_stack_drops_what_is_left", PancakeGameTest::breakingAStackDropsWhatIsLeft),
            test("placed_stacks_keep_their_height", PancakeGameTest::placedStacksKeepTheirHeight));

    private GameTests() {
    }

    /** A required test in the mod's {@code empty} structure, with vanilla's default of 100 ticks. */
    private static Test test(String name, Consumer<GameTestHelper> body) {
        return new Test(name, body, "empty", 100);
    }

    /** One test: its id in the mod's namespace, its body, the structure it runs in and its time limit. */
    public record Test(String name, Consumer<GameTestHelper> body, String structure, int maxTicks) {
        public Test maxTicks(int ticks) {
            return new Test(name, body, structure, ticks);
        }
    }
}
