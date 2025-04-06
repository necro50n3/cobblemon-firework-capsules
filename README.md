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
As of v1.0.0 there are 18 stickers, one for each type, which can be crafted using the type gems.  
![sticker recipe](https://i.imgur.com/iF51p8f.png)  

## Modifying Stickers  
Stickers can be modified with dyes, a Diamond or Glowstone Dust to modify its properties the same way as Firework Stars (note that most stickers have been designed without these properties in mind).  
![modifying stickeres](https://i.imgur.com/1mDI16j.png)  

## Combining Stickers
Stickers can be combined with a Ball Capsule along with other stickers and Firework Stars to create all kinds of effects.  
![capsule station recipe](https://i.imgur.com/SOsV3oh.png)  

</details>

# Addon Support
<details>

<summary></summary>

Firework Capsules uses a custom particle engine which expands on the existing Minecraft fireworks system, allowing for custom particle integration.  
Refer to below for the steps required to add a custom sticker.

<details>
<summary>StickerExplosion Class</summary>

The StickerExplosion class requires five fields:
```
ResourceLocation id;
IntList colors;
IntList fadeColors;
boolean hasTrail;
boolean hasTwinkle;
```
The id and colors are unmodifiable and must be set when registering.
The other three parameters can optionally be set during initialization and/or by players during gameplay.
</details>

<details>
<summary>Sticker Item Class</summary>

The sticker item must be registered with a corresponding StickerExplosion.
```
new StickerItem(new StickerExplosion(...));
```
</details>

<details>
<summary>Custom Particle Function</summary>

The custom particle function is a consumer that provides parameters which you can use to set up your custom particles.  
The **scale** parameter can be used scale your radius and particle sizes, and is dependent on the size of the Pokemon.  
The **scaleFactor** parameter is determined by the scale parameter and can be used to adjust the number of particles generated based on the Pokemon size.  
```
@FunctionalInterface
public interface CustomParticleFunction {
    void accept(ClientLevel clientLevel, double x, double y, double z, float rot, ParticleEngine particleEngine, StickerExplosion explosion, float scale, double scaleFactor);
}
```
</details>

<details>
<summary>Sticker Registration Event</summary>

Each StickerExplosion must also be registered on the Sticker Registration Event, which is run on the client side.

<details>
<summary>Fabric</summary>

```
public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        StickerRegistryEvent.EVENT.register((event) -> {
            event.register(StickerExplosion, CustomParticleFunction);
        );
    }
}
```

</details>

<details>
<summary>NeoForge</summary>

This event is run on the **MOD** bus.

```

@EventBusSubscriber(value = Dist.CLIENT, modid = Example.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public static class ClientEvents {
    @SubscribeEvent
    public static void registerStickers(StickerRegistryEvent event) {
        event.register(StickerExplosion, CustomParticleFunction);
    }
}
```

</details>

</details>

</details>
