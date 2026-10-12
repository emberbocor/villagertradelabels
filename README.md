# Villager Trade Labels

See what a trade gives you without hovering every item.

Villager Trade Labels adds a small tooltip-style label under the result of every trade in the vanilla trading screen. Enchanted books show their enchantment, enchanted gear shows its first enchantment, and explorer maps show where they lead. Scroll through a librarian's trades and spot Mending at a glance.

Available for NeoForge. Client-side only.

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

| | NeoForge |
|---|---|
| Minecraft | 1.21.1 |
| Loader | NeoForge 21.1.1 or newer |
| Jar | `villagertradelabels-neoforge-1.21.1-<version>.jar` |

### Client and server

The mod only changes what the trading screen shows, so it is purely client-side.

- Install it on the client. It does not need to be on the server, and a client with the mod can join any server, modded or vanilla.
- Installing it on a server does no harm; it simply does nothing there.

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

- **Villager Trade Peek:** works together without depending on it. When Trade Peek is installed, its greyed-out locked trades get labels too, drawn on top of the grey overlay, so you can see which future trade has the book you want.
- **Villager Trade Swap:** works together. Its toggle sits to the right of the "Trades" label, this mod's toggle at the left edge of the trade list, and labels update right after a trade is swapped.

## Building from source

```
./gradlew build
```

The jar is written to `neoforge/build/libs`. The project follows the [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template) layout: shared code lives in `common`, loader-specific code in `neoforge`.

## License

MIT. See [LICENSE](LICENSE).
