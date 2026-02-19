# Cobblemon Firework Capsules  
**Send out your Pokemon with a bang using fireworks and custom stickers!**  

![showcase](https://i.imgur.com/JAhsMox.gif)

# Basics
<details open>

<summary></summary>

## Ball Capsule Recipe  
![ball capsule recipe](https://i.imgur.com/Dg5le5v.png)  
## Adding Fireworks  
Ball Capsules can be combined with any number of Firework Stars.  
![adding fireworks](https://i.imgur.com/XgNb8ML.png)  
## Capsule Station Recipe  
You can equip your ball capsule to your Pokemon using a Capsule Station.  
Special thanks to **dogtorbloo** for the custom block model!  
![capsule station recipe](https://i.imgur.com/rX7FBL3.png)  

</details>

# Stickers
<details open>

<summary></summary>

## Sticker Recipe  
Stickers can be crafted using Paper + Gunpowder + an ingredient.  
As of v2.0.0 there are 21 stickers, one for each type, which can be crafted using the type gems, and three unique stickers.  
![sticker recipe](https://i.imgur.com/iF51p8f.png)  

## Combining Stickers
Stickers can be combined with a Ball Capsule along with other stickers and Firework Stars to create all kinds of effects.  
![capsule station recipe](https://i.imgur.com/SOsV3oh.png)  

</details>

# Addon Support
<details>

<summary></summary>

Stickers rely on the `fireworkcapsules:sticker_explosion` data component to determine how to render the sticker effects.

In order to create a custom sticker, you can refer to the [example resource/datapack](https://github.com/necro50n3/cobblemon-firework-capsules/tree/main/example/example_sticker).

The simple codec allows for the following, where `id` is the only required field.:
```
{
    "id": "fireworkcapsules:thunder_sticker",
    "color": 16769024,
    "sound": "cobblemon:move.thunder.target",
    "type": "BEDROCK"
}
```

The complex codec, used only for CUSTOM or FIREWORK sticker types allows for the following:
```
{
    "id": "fireworkcapsules:thunder_sticker",
    "colors": [ 16769024 ],
    "fadeColors": [],
    "hasTrail": false,
    "hasTwinkle": false,
    "sound": "cobblemon:move.thunder.target",
    "type": "CUSTOM"
}
```
</details>
