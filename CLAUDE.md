@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# Rustic Pancakes - `version/1.21.3`

**This file describes the `version/1.21.3` line**: Minecraft 1.21.3, Fabric and NeoForge.
Built with Architectury Loom (`dev.architectury.loom`) with Mojang mappings layered with Parchment; `remapJar` is the shipped jar.

It belongs to whichever folder has this branch checked out - the main `RusticPancakes` folder or a worktree
under `RusticPancakes/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A rustic mod for baking different kinds of pancakes and placing them in the world. Mod ID
`rusticpancakes`. Spun out of Rustic Delight, whose pancakes it follows.

This line was migrated from the 1.21.1 baseline, `version/1.21`, at 1.2.0. New features start there and are
ported here.

## This line

- Java 21, Mojang mappings with Parchment. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first. CI overrides it with `-Dorg.gradle.java.home`.
- Everything but the entry points lives in `common`. Registration goes through Architectury's `DeferredRegister`; there is no access widener and no mixin. Every item and block sets its id on its Properties inside the register supplier, and block items take `useBlockDescriptionPrefix()`.
- Common code compiles against vanilla, so NeoForge's extensions (`ItemStack#getFoodProperties`, `ItemTags.create`) are not available there.
- Eating is split in two: hunger and saturation in `FoodValues`, and the animation, time and effects in `ConsumableValues` (1.21.2's `Consumable`). Syrup is drunk and leaves its bottle through `usingConvertsTo`; there is no drinkable item class.
- Datagen: `:neoforge:runData`. Output: `common/src/generated/resources`, never hand-edited, and shipped to both loaders, so a recipe may only use ingredients both read: no `neoforge:difference`, and no `c:` tag Fabric API doesn't ship unless the mod's tags define it - 1.21.3's has no `c:eggs`, so `ModItemTagsProvider` adds it, and a recipe naming a missing tag doesn't load. The run finishes its providers in a second and then doesn't exit; stop it once the log says `Caching: total files`.
- Game tests in `neoforge/src/test`, run with `:neoforge:runGameTest`, laid out as The Lead Age's are: a body in `PancakeGameTest`, an entry in `GameTests`, and whatever differs between versions in `TestCompat`. They click through `ServerPlayerGameMode#useItemOn`, so the sneak-to-add handler runs; the Fabric side of that handler has no test.
- A stack's `servings` is packed so worlds from before 1.2 keep their stacks (`PancakeBlock`): never renumber it.
- Dev runs carry AppleSkin (with Cloth Config on Fabric). JEI has no build for 1.21.2 or 1.21.3, so this line goes without it.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged `1.21.3` alone (`supported_minecraft_versions`), not the rung's `1.21.2` too: the NeoForge jar needs NeoForge 21.3, which exists only for 1.21.3. The baseline is tagged the same way.
- GitHub Actions: `build.yml`.
