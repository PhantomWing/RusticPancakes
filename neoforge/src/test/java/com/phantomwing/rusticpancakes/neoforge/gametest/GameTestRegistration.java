package com.phantomwing.rusticpancakes.neoforge.gametest;

import com.phantomwing.rusticpancakes.RusticPancakes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/**
 * Registers {@link GameTests} the way 1.21.5 on does: each body into the static {@code test_function}
 * registry, and each test into {@code test_instance} with the structure and time limit its entry gives.
 * Before 1.21.5 this class is a {@code @GameTestGenerator} instead. Nothing here changes when a test is
 * added.
 */
@EventBusSubscriber(modid = RusticPancakes.MOD_ID)
public final class GameTestRegistration {
    private GameTestRegistration() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> GameTests.ALL.forEach(
                test -> helper.register(RusticPancakes.resourceLocation(test.name()), test.body())));
    }

    /**
     * The instances, in code rather than as {@code test_instance} JSONs, so no test can be listed without
     * one and silently never run. Only the instances: {@code test_function} is a static registry and is
     * already frozen when this fires ("Registry is already frozen").
     */
    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition> environment = event.registerEnvironment(RusticPancakes.resourceLocation("default"));
        for (GameTests.Test test : GameTests.ALL) {
            event.registerTest(RusticPancakes.resourceLocation(test.name()), new FunctionGameTestInstance(
                    ResourceKey.create(Registries.TEST_FUNCTION, RusticPancakes.resourceLocation(test.name())),
                    new TestData<>(environment, RusticPancakes.resourceLocation(test.structure()), test.maxTicks(), 0, true)));
        }
    }
}
