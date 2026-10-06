@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# Rustic Pancakes - `version/1.21`

**This file describes the `version/1.21` line**: Minecraft 1.21.1, Fabric and NeoForge. A **baseline** line: new features start here.
Built with Architectury Loom (`dev.architectury.loom`) with Mojang mappings layered with Parchment; `remapJar` is the shipped jar.

It belongs to whichever folder has this branch checked out - the main `RusticPancakes` folder or a worktree
under `RusticPancakes/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A rustic mod for baking different kinds of pancakes and placing them in the world. Mod ID
`rusticpancakes`. Spun out of Rustic Delight, whose pancakes it follows.

This line replaces the single-loader `neoforge/1.21` and `fabric/1.21` lines, now legacy, and started as
a copy of `neoforge/1.21`. Their releases, 1.1.0 on NeoForge and 1.0.0 on Fabric, were uploaded by hand;
this line is the first to publish through the plugin.

## This line

- Java 21, Mojang mappings with Parchment. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first. CI overrides it with `-Dorg.gradle.java.home`.
- Everything but the entry points lives in `common`. Registration goes through Architectury's `DeferredRegister`; there is no access widener and no mixin.
- Common code compiles against vanilla, so NeoForge's extensions (`ItemStack#getFoodProperties`, `ItemTags.create`, the `Supplier` overload of `FoodProperties.Builder#effect`) are not available there.
- Datagen: `:neoforge:runData`. Output: `common/src/generated/resources`, never hand-edited, and shipped to both loaders, so a recipe may only use ingredients both read: no `neoforge:difference`. The run finishes its providers in a second and then doesn't exit; stop it once the log says `Caching: total files`.
- Game tests in `neoforge/src/test`, run with `:neoforge:runGameTest`, laid out as The Lead Age's are: a body in `PancakeGameTest`, an entry in `GameTests`, and whatever differs between versions in `TestCompat`. They click through `ServerPlayerGameMode#useItemOn`, so the sneak-to-add handler runs; the Fabric side of that handler has no test.
- A stack's `servings` is packed so worlds from before 1.2 keep their stacks (`PancakeBlock`): never renumber it.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged `1.21.1` alone (`supported_minecraft_versions`), not the rung's `1.21` too: the NeoForge jar needs NeoForge 21.1, which exists only for 1.21.1. The hand-made uploads before it were tagged both.
- GitHub Actions: `build.yml`.
