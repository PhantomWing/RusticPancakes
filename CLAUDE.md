@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/legacy-forge.md

# Rustic Pancakes - `forge/1.19`

**This file describes the `forge/1.19` line**: Minecraft 1.19.2, Forge.
Built with ForgeGradle (legacy, maintenance only).

It belongs to whichever folder has this branch checked out - the main `RusticPancakes` folder or a worktree
under `RusticPancakes/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A rustic mod for baking different kinds of pancakes and placing them in the world. Mod ID
`rusticpancakes`. Spun out of Rustic Delight.

**Maintenance only.** 1.0.0 was uploaded by hand; from 1.0.1 the line publishes through the plugin.

## This line

- Java 17, Mojang mappings, no Parchment.
- Datagen: `runData`. Output: `src/generated/resources`, never hand-edited.
- Game tests in `src/gametest`, a source set of their own so neither the tests nor their `empty` structure ship, run with `runGameTestServer`: `PancakeGameTest`, a `@GameTestHolder` class. Add the next one for whatever is ported next. The run takes `forceExit false`: ForgeGradle 5 otherwise calls `System.exit` in Gradle's own JVM once the game ends, and the build failed with "Gradle build daemon disappeared unexpectedly" even when every test passed. `runClient` and `runServer` keep it, so closing the game still ends their build.
- Published with `publishMods` from `build.gradle`, with the `-PpublishDryRun` flag, to Modrinth and CurseForge as Forge, tagged `1.19.2` (`supported_minecraft_versions`). The plugin is pinned to 0.6.3, whose CurseForge side flags are `clientRequired`/`serverRequired`: 2.x needs Gradle 8.9, and from 0.7 it brings a jackson-core with Java 21 classes that Gradle 7.4 can't read. ForgeGradle 5 keeps the line on Gradle 7.
