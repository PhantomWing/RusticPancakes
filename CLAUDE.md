@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/legacy-forge.md

# Rustic Pancakes - `forge/1.20`

**This file describes the `forge/1.20` line**: Minecraft 1.20.1, Forge.
Built with ForgeGradle (legacy, maintenance only).

It belongs to whichever folder has this branch checked out - the main `RusticPancakes` folder or a worktree
under `RusticPancakes/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A rustic mod for baking different kinds of pancakes and placing them in the world. Mod ID
`rusticpancakes`. Spun out of Rustic Delight.

**Maintenance only.** 1.0.0 and 1.1.0 were uploaded by hand; from 1.1.1 the line publishes through the plugin.

## This line

- Java 17, Mojang mappings with Parchment.
- Datagen: `runData`. Output: `src/generated/resources`, never hand-edited.
- Game tests in `src/gametest`, a source set of their own so neither the tests nor their `empty` structure ship, run with `runGameTestServer`: `PancakeGameTest`, a `@GameTestHolder` class. Add the next one for whatever is ported next.
- Gradle 8.9, which the Mod Publish Plugin 2.0.0 needs; ForgeGradle 6 runs on it unchanged.
- Published with `publishMods` from `build.gradle`, with the `-PpublishDryRun` flag, to Modrinth and CurseForge as both Forge and NeoForge (NeoForge's 1.20.1 is Forge 47.1 forked and runs this jar, and the hand uploads said both), tagged `1.20.1` (`supported_minecraft_versions`).
