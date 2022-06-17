# BlockHealth
A Minecraft plugin to deal with block health in a way that doesn't completely suck.



### What does this plugin do?

BlockHealth allows blocks to have health, meaning, they take more than one breaking to destroy. 
This plugin handled block dealt by directly adding the data to blocks, meaning, there is no big database to worry about. 
Decide this plugin isn't for you? Just remove it, no errors, no problems.

### Messages are not taking legacy color (&8, &f, etc.)

This plugin uses [MiniMessage](https://docs.adventure.kyori.net/minimessage/format.html), which is much more powerful, including support for RGB, hover, and cool features that allow you to bring the full potential out.

*How do I add more blocks?*

You can add more Minecraft blocks by heading to [config.yml](https://github.com/LoJoSho/BlockHealth/blob/master/src/main/resources/config.yml).
```yml
Blocks:
  OAK_PLANKS:
    HEALTH: 250
```

To add more blocks, simply paste below it,

```yml
Blocks:
  OAK_PLANKS:
    HEALTH: 250
  COBBLESTONE:
    HEALTH: 500
```

### How do I add more tools?

Works the same way as blocks, but instead of health, you do it with damage.

```yaml
Tools:
  AIR:
    DAMAGE: 1
  WOODEN_PICKAXE:
    DAMAGE: 10
  STONE_PICKAXE:
    DAMAGE: 20
```

Have any questions? Head over to the [Discord](https://discord.gg/tn8M5CEBat) server!
