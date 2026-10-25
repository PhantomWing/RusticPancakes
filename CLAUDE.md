@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# Rustic Pancakes - `version/26.3`

**This file describes the `version/26.3` line**: Minecraft 26.3, Fabric and NeoForge.
Built with Architectury Loom without remapping (`dev.architectury.loom-no-remap`): Minecraft 26.x ships unobfuscated, so there are no mappings and `shadowJar` is the shipped jar.

It belongs to whichever folder has this branch checked out - the main `RusticPancakes` folder or a worktree
under `RusticPancakes/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A rustic mod for baking different kinds of pancakes and placing them in the world. Mod ID
`rusticpancakes`. Spun out of Rustic Delight, whose pancakes it follows.

This line was migrated from `version/26.2` at 1.2.0. New features start on the 1.21.1 baseline, `version/1.21`, and are
ported here.

## This line

- Java 25, no mappings and no Parchment (Mojang's names are the runtime names), Gradle 9.4, which Architectury Loom 1.17 needs. Dependencies take plain `implementation` and `api`, never the `mod`-prefixed ones, and Shadow builds the shipped jar on top of the Loom-processed one marked `raw`. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first. CI overrides it with `-Dorg.gradle.java.home`.
- Everything but the entry points lives in `common`. Registration goes through Architectury's `DeferredRegister`; there is no access widener and no mixin. The sneak-to-add handler (`ModEvents`) answers Architectury's `RIGHT_CLICK_BLOCK` (an `EventResult` from 21) through `EventResult.fromMinecraft`, which keeps its FAIL as the click's result on both loaders. Every item and block sets its id on its Properties inside the register supplier, and block items take `useBlockDescriptionPrefix()`.
- Common code compiles against vanilla, so NeoForge's extensions (`ItemStack#getFoodProperties`, `ItemTags.create`) are not available there.
- Eating is split in two: hunger and saturation in `FoodValues`, and the animation, time and effects in `ConsumableValues` (1.21.2's `Consumable`). Syrup and Batter leave their bottle and bowl through `usingConvertsTo`; there is no drinkable item class.
- Datagen: `:neoforge:runData`, NeoForge's `clientData` run, which fires `GatherDataEvent.Client` and generates the server data too. Recipes, loot tables and advancements are reloadable registries on 26.3, each made by a bootstrap registered on the event's default `PackGenerator` (`createReloadableRegistryObjects`, writing the `rusticpancakes` and `minecraft` namespaces): `ModRecipeProvider.create()`, `ModLootTableProvider.create()` and `ModAdvancements`, an `AdvancementSubProvider` with its background on `DisplayInfo`. A loot condition on a block state is `MatchBlock.blockMatches` with the provider's `blocks`. Block states and item models come from vanilla's `ModelProvider` (`ModModelProvider`), which writes the `items/` client item definitions as well. Output: `common/src/generated/resources`, never hand-edited, and shipped to both loaders, so a recipe may only use ingredients both read: no `neoforge:difference`, and no `c:` tag Fabric API doesn't ship unless the mod's tags define it, since a recipe naming a missing tag doesn't load. `ModItemTagsProvider` is NeoForge's own from 1.21.6, which reads no block tags, so the `pancakes` item tag lists the block items itself rather than copying the block tag. It defines `c:eggs`, which Fabric API lacked before 1.21.4; from then the two merge. The run finishes its providers in a second and then doesn't exit; stop it once the log says `Caching: total files`.
- Game tests in `neoforge/src/test`, run with `:neoforge:runGameTest` through NeoForge's `gameTestServer` run template, laid out as The Lead Age's are: a body in `PancakeGameTest`, an entry in `GameTests`, and whatever differs between versions in `TestCompat`. `GameTestRegistration` registers them in code - the bodies as test functions in `RegisterEvent`, the tests in `RegisterGameTestsEvent`, their environment a `Holder<TestEnvironmentDefinition<?>>` from 26.1 - so there are no `test_instance` JSONs to keep. They click through `ServerPlayerGameMode#useItemOn`, so the sneak-to-add handler runs; the Fabric side of that handler has no test. The JUnit `test` task finds none of them and is told not to fail on that, which Gradle 9 would. From 1.21.9 a mock player waits for nothing around the spawn, so `TestCompat.entitiesLoadedAroundSpawn` is always true here.
- A stack's `servings` is packed so worlds from before 1.2 keep their stacks (`PancakeBlock`): never renumber it.
- Dev runs carry JEI and AppleSkin, with Cloth Config on Fabric for AppleSkin's settings.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Both uploads are tagged 26.3 (`supported_minecraft_versions`, and `neoforge_minecraft_versions` for NeoForge), the whole of the rung. The manifests ask for NeoForge from 26.3.0.48 and Architectury API from `architectury_min_version`, 22.0.4: Architectury 22.0.4 and newer need NeoForge 26.3.0.48 or newer, and nothing older loads there. Fabric Loader and Fabric API come from `fabric_loader_version` and `fabric_api_min_version`, 0.19.5 and 0.161.0, meeting the 0.19.5 and 0.160.5 Architectury 22 needs.
- GitHub Actions: `build.yml`.
