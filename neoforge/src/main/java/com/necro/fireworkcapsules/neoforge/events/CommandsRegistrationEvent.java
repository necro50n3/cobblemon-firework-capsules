package com.necro.fireworkcapsules.neoforge.events;

import com.mojang.brigadier.CommandDispatcher;
import com.necro.fireworkcapsules.common.command.FireworkCapsulesCommands;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class CommandsRegistrationEvent {
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        FireworkCapsulesCommands.register(dispatcher);
    }
}
