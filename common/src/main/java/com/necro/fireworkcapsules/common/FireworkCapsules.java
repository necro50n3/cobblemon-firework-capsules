package com.necro.fireworkcapsules.common;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.logging.LogUtils;
import com.necro.fireworkcapsules.common.entity.CapsuleEntity;
import com.necro.fireworkcapsules.common.util.ICapsuleHolder;
import kotlin.Unit;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class FireworkCapsules {
    public static final String MOD_ID = "fireworkcapsules";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Initiating {}", MOD_ID);

        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, event -> {
            PokemonEntity entity = event.getPokemonEntity();
            Pokemon pokemon = event.getPokemon();

            ItemStack capsuleStack = ((ICapsuleHolder) pokemon).getCapsule(entity.registryAccess());
            if (capsuleStack.isEmpty()) return Unit.INSTANCE;

            CapsuleEntity capsuleEntity = new CapsuleEntity(entity.level(), entity.getX(),
                entity.getY() + entity.getBoundingBox().getYsize() / 2, entity.getZ(), capsuleStack, entity
            );
            entity.level().addFreshEntity(capsuleEntity);
            capsuleEntity.explode();

            return Unit.INSTANCE;
        });
    }
}
