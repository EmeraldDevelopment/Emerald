package io.Emerald.commandmanager.internalcommands;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.commandmanager.InternalCommand;
import io.Emerald.internal.api.CommandSender;

@CommandMeta(
        command = "stats",
        usage = "!stats",
        description = "Displays some information on the bot."
)
public class StatsCommand extends InternalCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
