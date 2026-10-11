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

**Maintenance only.** 1.0.0 was uploaded by hand; from 1.2.0 the line publishes through the plugin. It
is `forge/1.20` without Cherry Blossom Pancakes: Minecraft 1.19.2 has no cherry trees for their recipe.

## This line

- Java 17, Mojang mappings, no Parchment.
- No datagen code: the providers were dropped when this line was made from `forge/1.20`, and their API is
  1.19.3's and later. `src/generated/resources` is `forge/1.20`'s output carried down, Cherry Blossom
  left out, which 1.19.2 reads as it is. After a change to the data, run `forge/1.20`'s datagen and copy
  what it writes here the same way.
- `PancakeBlock`, the items and `ModEvents` are `forge/1.20`'s, except that a block's support is checked
  through its `Material`, and the creative tab is `RusticPancakes.CREATIVE_TAB` set on each item's
  properties: 1.19.2 has no creative tab registry.
- A stack's `servings` is packed so worlds from before 1.2 keep their stacks (`PancakeBlock`): never renumber it.
- Game tests in `src/gametest`, a source set of their own so neither the tests nor their `empty` structure ship, run with `runGameTestServer`, laid out as on `forge/1.20` and the 1.21 lines: a body in `PancakeGameTest`, an entry in `GameTests`, `GameTestRegistration` as the `@GameTestHolder` generator, and whatever differs between versions in `TestCompat` - a `FakePlayer` for the tests' player, as on `forge/1.20`. The run takes `forceExit false`: ForgeGradle 5 otherwise calls `System.exit` in Gradle's own JVM once the game ends, and the build failed with "Gradle build daemon disappeared unexpectedly" even when every test passed. `runClient` and `runServer` keep it, so closing the game still ends their build.
- Published with `publishMods` from `build.gradle`, with the `-PpublishDryRun` flag, to Modrinth and CurseForge as Forge, tagged `1.19.2` (`supported_minecraft_versions`). The plugin is pinned to 0.6.3, whose CurseForge side flags are `clientRequired`/`serverRequired`: 2.x needs Gradle 8.9, and from 0.7 it brings a jackson-core with Java 21 classes that Gradle 7.4 can't read. ForgeGradle 5 keeps the line on Gradle 7.
