# value unlocker

fabric mod for minecraft `26.2` that removes a bunch of annoying fake number limits.

basically if mojang put a random cap somewhere like `255`, `99`, `1024`, `30000000`, or "lol tick rate must be at least 1", this tries to get it out of the way.

this is not a balance mod. this is not safe. this is not polished. it is mostly for people who want to type stupid numbers and have the game actually use them instead of quietly clamping them back down.

## what it does

**gamerules.** vanilla integer gamerules can take decimal values now. they get stored as real doubles, survive save/load, and vanilla code that still asks for an int gets a capped int so it does not immediately eat itself.

**random ticks and minecarts.** some gamerules needed more than just "store a bigger number":
- `randomTickSpeed` supports fractional values by building up leftover ticks over time
- `randomTickSpeed` can also go negative because why not
- `maxMinecartSpeed` uses the real decimal value in minecart physics
- negative minecart speed is not shoved back to zero anymore

**commands.** command argument limits are widened globally where possible:
- integer arguments can use the full int range
- float arguments can use basically the full float range
- double arguments can use basically the full double range
- `/effect give` amplifier is not capped at `255`
- `/effect give` seconds is not capped at `1000000`
- `/item` count is not capped at `99`
- `/tick rate` accepts zero, negative, and huge finite values
- `/enchant` lets huge levels through
- `/enchant` does not care if the item is normally enchantable
- `/enchant` does not care if enchantments are normally compatible

**attributes and entities.** removes a bunch of backend clamps that make big values fake:
- ranged attributes do not sanitize values back into their vanilla min/max
- health setting does not clamp to max health
- player xp gain does not clamp the added amount
- mob effect amplifier does not clamp in the main constructor
- item damage values do not get clamped
- item stack strict validation does not reject oversized counts
- entity nbt position clamps are removed
- entity nbt motion cap is removed

**enchantments.** enchantment storage is less annoying:
- stored enchantment levels are not capped at `255`
- mutable enchantment writes do not `Math.min(level, 255)` anymore
- range codecs around enchantment levels are widened

**codecs and components.** a few central minecraft/datafixer range codecs are widened so values do not get rejected just because some codec had a cute little range on it.

this touches more than one thing. components, nbt-ish validation, item data, datapack stuff, all that kind of area can be affected. if something weird happens, yeah, this is probably why.

**world border and world bounds.** the vanilla world border is treated more like a suggestion:
- `/worldborder` size and center command caps are widened
- border collision shape is removed
- border "inside" checks return yes
- border clamp methods return the original position
- distance to border returns a giant value
- world/chunk valid-position checks are bypassed
- teleport spawnable/world bounds checks are bypassed

this is the part where minecraft itself may still become unhappy because chunk generation, packets, clients, or other mods may still hate ridiculous coordinates. this mod removes the checks it knows about, not the laws of every random system in the game.

**client tick rate stuff.** tries to make weird tick rates stick better:
- client tick target uses the real server tick rate when a level exists
- tick rate is saved/restored across level changes
- respawn sends the tick rate back to the joining player

## what it probably does not do

it does not make every insane value useful.

some values will work but be visually broken. some will save but not display nicely. some will make minecraft do minecraft things and fall over. this mod is about removing artificial caps, not making every possible number a good idea.

also if another mod replaces the same command or validates the same data later, that mod can still get in the way.

## building it

minecraft `26.2` is java 25 bytecode, so java 21 is too old.

this repo is set to compile with a local jdk 26 but emit java 25 classes:

```text
org.gradle.java.home=/home/linuxbrew/.linuxbrew/Cellar/openjdk/26.0.1/libexec
```

normal build:

```sh
gradle clean build --no-daemon
```

the jar ends up here:

```text
build/libs/value-unlocker-0.1.0.jar
```

the class files should say `major version: 69`. if they say `70`, prism/java 25 will not load it.

## weird build note

fabric has the `26.2` game and api artifacts, but the published intermediary metadata is busted right now. the intermediary pom says the wrong version and the mapping jar is basically empty.

because of that, this project compiles directly against loom's downloaded `minecraft-merged-deobf-26.2.jar` instead of using normal loom remapping.

yes this is ugly. it works for now.

## release note

do not upload the `build/` folder or `.gradle/` folder. just build the jar and upload the jar from `build/libs/`.
