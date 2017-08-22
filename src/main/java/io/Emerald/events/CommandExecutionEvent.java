package io.Emerald.events;

import io.Emerald.commandmanager.Command;
import io.Emerald.internal.api.CommandSender;
import sx.blah.discord.api.events.Event;

public class CommandExecutionEvent extends Event {

    private CommandSender sender;
    private Command command;

    public CommandExecutionEvent(final CommandSender sender, final Command command) {
        this.sender = sender;
        this.command = command;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }
}
