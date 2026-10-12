# Villager Trade Labels

See what a trade gives you without hovering every item.

Villager Trade Labels adds a small tooltip-style label under the result of every trade in the vanilla trading screen. Enchanted books show their enchantment, enchanted gear shows its first enchantment, and explorer maps show where they lead. Scroll through a librarian's trades and spot Mending at a glance.

Available for Minecraft 1.20.1 on Forge (also runs on NeoForge 1.20.1) and Fabric 1.20-1.20.1. Client-side only.

## Features

- **Enchanted books:** the stored enchantment, for example `Mending` or `Sharpness V`.
- **Enchanted gear:** the first enchantment in the order the vanilla tooltip uses, plus how many more there are, for example `Protection III +1`.
- **Explorer maps:** the map's name, for example `Ocean Explorer Map`.
- **Looks like vanilla.** Labels use the vanilla tooltip background and the same text colors as the tooltip: gray enchantments, red curses, map names in the item's rarity color and in italics, like the custom map names in the vanilla 1.20.1 tooltip.
- **Fits the row.** Labels are centered under the result item and never cover the next trade, the first cost item or a discounted price. Long names are shortened with `…`, but the level and `+N` always stay visible: `Bane of Arthrop… V`.
- **Stays out of the way.** Hover tooltips and the item on your cursor are always drawn above the labels.
- **No secrets revealed.** Only information the vanilla tooltip shows is used. Enchantments hidden with `HideFlags` and items without such information get no label.
- **Every merchant.** Works for villagers and wandering traders, and with trades added by other mods.
- **One click to hide.** A small tag button at the top left of the trading screen, on the "Trades" line, turns all labels on or off. The choice is remembered.

## Requirements

| | Forge / NeoForge | Fabric |
|---|---|---|
| Minecraft | 1.20.1 | 1.20 or 1.20.1 |
| Loader | Forge 47.1.0 or newer, or NeoForge 1.20.1 | Fabric Loader 0.14.21 or newer |
| Other | | Fabric API 0.83.0 or newer |
| Jar | `villagertradelabels-forge-1.20.1-<version>.jar` | `villagertradelabels-fabric-1.20-1.20.1-<version>.jar` |

NeoForge 1.20.1 still runs Forge mods, so it uses the Forge jar. There is no separate NeoForge jar for 1.20.1.

### Client and server

The mod only changes what the trading screen shows, so it is purely client-side on every loader.

- Install it on the client only. It does not need to be on the server, and a client with the mod can join any server, modded or vanilla, whatever loader the server uses.
- Installing it on a dedicated server does no harm: Fabric and Forge 47.3.25 or newer skip it there, and on older Forge versions and NeoForge 1.20.1 it is loaded but does nothing.

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

- **Villager Trade Peek (1.20.1):** works together without depending on it. When Trade Peek is installed, its greyed-out locked trades get labels too, drawn on top of the grey overlay, so you can see which future trade has the book you want.
- **Villager Trade Swap (1.20.1):** works together. Its toggle sits to the right of the "Trades" label, this mod's toggle at the left edge of the trade list, and labels update right after a trade is swapped.

## Building from source

```
./gradlew build
```

The jars are written to `forge/build/libs` and `fabric/build/libs`. The project follows the [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template) layout: shared code lives in `common`, loader-specific code in `forge` and `fabric`. This branch needs Java 17.

## License

MIT. See [LICENSE](LICENSE).
