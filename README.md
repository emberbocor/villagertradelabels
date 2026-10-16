# Villager Trade Labels

See what a trade gives you without hovering every item.

Villager Trade Labels adds a small tooltip-style label under the result of every trade in the vanilla trading screen. Enchanted books show their enchantment, enchanted gear shows its first enchantment, and explorer maps show where they lead. Scroll through a librarian's trades and spot Mending at a glance.

Available for Minecraft 26.2 on NeoForge, Forge and Fabric. Client-side only.

## Features

- **Enchanted books:** the stored enchantment, for example `Mending` or `Sharpness V`.
- **Enchanted gear:** the first enchantment in the order the vanilla tooltip uses, plus how many more there are, for example `Protection III +1`.
- **Explorer maps:** the map's name, for example `Ocean Explorer Map`.
- **Looks like vanilla.** Labels use the vanilla tooltip background and the same text colors as the tooltip: gray enchantments, red curses, map names in the item's rarity color.
- **Fits the row.** Labels are centered under the result item and never cover the next trade, the first cost item or a discounted price. Long names are shortened with `…`, but the level and `+N` always stay visible: `Bane of Arthrop… V`.
- **Stays out of the way.** Hover tooltips and the item on your cursor are always drawn above the labels.
- **No secrets revealed.** Only information the vanilla tooltip shows is used. Hidden enchantments, hidden tooltips and items without such information get no label.
- **Every merchant.** Works for villagers and wandering traders, and with trades added by other mods.
- **One click to hide.** A small tag button at the top left of the trading screen, on the "Trades" line, turns all labels on or off. The choice is remembered.

## Requirements

| | NeoForge | Forge | Fabric |
|---|---|---|---|
| Minecraft | 26.2 | 26.2 | 26.2 |
| Loader | NeoForge 26.2.0.0-beta or newer | Forge 65.0.0 or newer | Fabric Loader 0.18.4 or newer |
| Other | | | Fabric API 0.152.1 or newer |
| Jar | `villagertradelabels-neoforge-26.2-<version>.jar` | `villagertradelabels-forge-26.2-<version>.jar` | `villagertradelabels-fabric-26.2-<version>.jar` |

Minecraft 26.2 needs Java 25. The first stable NeoForge build for 26.2 is 26.2.0.57; the earlier beta builds work too. NeoForge and Forge are separate loaders that do not run each other's mods, so pick the jar that matches your loader.

### Client and server

The mod only changes what the trading screen shows, so it is purely client-side on every loader.

- Install it on the client only. It does not need to be on the server, and a client with the mod can join any server, modded or vanilla, whatever loader the server uses.
- Installing it on a dedicated server does no harm: Forge and Fabric skip it there, and on NeoForge it does nothing.

## Configuration

### Client: `config/villagertradelabels-client.json`

```json
{
  "showLabels": true
}
```

- `showLabels`: whether labels are shown. This is the tag button in the trading screen, saved automatically.

The file is created when it is missing. An invalid value is replaced by the default `true` (with a warning in the log).

## Compatibility notes

- **Villager Trade Peek and Villager Trade Swap:** there are no 26.2 versions of them yet, so this version has not been tested with them. The optional Trade Peek integration is still included; without Trade Peek it simply stays off.

## Building from source

```
./gradlew build
```

The jars are written to `neoforge/build/libs`, `forge/build/libs` and `fabric/build/libs`. Building needs Java 25. The development runs use `runs/26.2/...`. The project follows the [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template) layout: shared code lives in `common`, loader-specific code in `neoforge`, `forge` and `fabric`.

## License

MIT. See [LICENSE](LICENSE).
