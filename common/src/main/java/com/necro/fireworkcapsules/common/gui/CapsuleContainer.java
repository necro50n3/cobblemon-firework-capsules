package com.necro.fireworkcapsules.common.gui;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.util.ICapsuleHolder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CapsuleContainer extends SimpleContainer {
    private final RegistryAccess registryAccess;
    private final Pokemon[] pokemon;

    public CapsuleContainer(List<ItemStack> items, RegistryAccess registryAccess, Pokemon[] pokemon) {
        super(items.toArray(ItemStack[]::new));
        this.registryAccess = registryAccess;
        this.pokemon = pokemon;
    }

    public static CapsuleContainer create(Player player) {
        return player instanceof ServerPlayer serverPlayer ? createServer(serverPlayer) : createClient(player);
    }

    private static CapsuleContainer createClient(Player player) {
        List<ItemStack> items = new ArrayList<>();
        Pokemon[] pokemonList = new Pokemon[6];

        ClientParty party = CobblemonClient.INSTANCE.getStorage().getParty();
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = party.get(i);
            if (pokemon == null) items.add(ItemStack.EMPTY);
            else items.add(((ICapsuleHolder) pokemon).getCapsule(player.registryAccess()));
            pokemonList[i] = pokemon;
        }

        return new CapsuleContainer(items, player.registryAccess(), pokemonList);
    }

    private static CapsuleContainer createServer(ServerPlayer player) {
        List<ItemStack> items = new ArrayList<>();
        Pokemon[] pokemonList = new Pokemon[6];

        PlayerPartyStore party = PlayerExtensionsKt.party(player);
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = party.get(i);
            if (pokemon == null) items.add(ItemStack.EMPTY);
            else items.add(((ICapsuleHolder) pokemon).getCapsule(player.registryAccess()));
            pokemonList[i] = pokemon;
        }

        return new CapsuleContainer(items, player.registryAccess(), pokemonList);
    }

    @Override
    public boolean canAddItem(ItemStack stack) {
        return stack.is(FireworkCapsuleItems.BALL_CAPSULE);
    }

    @Override
    public boolean canPlaceItem(int i, ItemStack stack) {
        return stack.is(FireworkCapsuleItems.BALL_CAPSULE);
    }

    public void onCapsuleChange(int slot, ItemStack itemStack) {
        Pokemon pokemon = this.pokemon[slot];
        if (pokemon == null) return;

        ((ICapsuleHolder) pokemon).setCapsule(this.registryAccess, itemStack);
    }

    public boolean isActive(int slot) {
        return this.pokemon[slot] != null;
    }
}
