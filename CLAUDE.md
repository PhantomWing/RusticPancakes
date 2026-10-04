@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/fabric-loom.md

# Rustic Pancakes - `fabric/1.21`

**This file describes the `fabric/1.21` line**: Minecraft 1.21.1 (the jar covers 1.21 and 1.21.1), Fabric. A **baseline** line: new features start here.
Built with Fabric Loom.

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

- Java 21, Mojang mappings, no Parchment.
- Datagen: `runDatagen`. No generated resources are committed on this line. **Not pinned with `modId = mod_id`**: check its diff for files in the `minecraft` namespace (`fabric-loom.md`).
- No game tests yet. Writing the first one for whatever is ported next is the highest-value test available (`verification.md`).
- No `publishMods` on this line: add the block before publishing it through the plugin (`publishing.md`).
- GitHub Actions: `build.yml`.
