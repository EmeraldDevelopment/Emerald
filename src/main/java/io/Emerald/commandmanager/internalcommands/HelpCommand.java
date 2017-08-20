package io.Emerald.commandmanager.internalcommands;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.commandmanager.InternalCommand;
import io.Emerald.internal.api.CommandSender;

@CommandMeta(
        command = "help",
        usage = "!help",
        description = "Displays all commands in the bot."
)
public class HelpCommand extends InternalCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
