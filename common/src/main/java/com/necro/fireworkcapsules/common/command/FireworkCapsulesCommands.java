package com.necro.fireworkcapsules.common.command;

import com.cobblemon.mod.common.api.permission.CobblemonPermission;
import com.cobblemon.mod.common.api.permission.Permission;
import com.cobblemon.mod.common.api.permission.PermissionLevel;
import com.cobblemon.mod.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public class FireworkCapsulesCommands {
    private static final Permission RESET_CLEARS = new CobblemonPermission("command.resetclears", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);

    public static final SuggestionProvider<CommandSourceStack> STICKERS = (context, builder) -> {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        List<StickerExplosion> stickers = context.getSource().registryAccess().registryOrThrow(StickerExplosion.STICKERS).stream().toList();

        for (StickerExplosion sticker : stickers) {
            ResourceLocation id = sticker.id();
            if (id.toString().toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(id.toString());
            }
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(PermissionUtilsKt.permission(Commands.literal("givesticker")
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("sticker", ResourceLocationArgument.id())
                    .suggests(STICKERS)
                    .executes(context -> giveSticker(
                        EntityArgument.getPlayer(context, "player"),
                        ResourceLocationArgument.getId(context, "sticker"),
                        1
                    ))
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                        .executes(context -> giveSticker(
                            EntityArgument.getPlayer(context, "player"),
                            ResourceLocationArgument.getId(context, "sticker"),
                            IntegerArgumentType.getInteger(context, "amount")
                        ))
                    )
                )
            ), RESET_CLEARS, true)
        );
    }

    @SuppressWarnings("unused")
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        register(dispatcher);
    }

    private static int giveSticker(ServerPlayer player, ResourceLocation sticker, int amount) {
        if (player == null || sticker == null) return 0;
        ItemStack item = FireworkCapsuleItems.STICKER.value().getDefaultInstance();
        StickerExplosion explosion = player.registryAccess().registryOrThrow(StickerExplosion.STICKERS).get(sticker);
        if (explosion == null) return 0;
        item.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), explosion);
        item.setCount(amount);

        if (!player.getInventory().add(item)) {
            ItemEntity itemEntity = player.drop(item, false);
            if (itemEntity == null) return 0;
            itemEntity.setNoPickUpDelay();
            itemEntity.setTarget(player.getUUID());
        }
        return 1;
    }
}
