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

**Dormant** - no commits since 2025-02. Released since 2024-11, uploaded by hand: no line has
`publishMods` or platform IDs in `gradle.properties`. The Modrinth and CurseForge IDs are in
`mods.json`; add the block and copy them before publishing a line through the plugin.

## This line

- Java 17, Mojang mappings with Parchment.
- Datagen: `runData`. Output: `src/generated/resources`, never hand-edited.
- Game tests in `src/gametest`, a source set of their own so neither the tests nor their `empty` structure ship, run with `runGameTestServer`: `PancakeGameTest`, a `@GameTestHolder` class. Add the next one for whatever is ported next.
- No `publishMods` on this line: add the block before publishing it through the plugin (`publishing.md`).
