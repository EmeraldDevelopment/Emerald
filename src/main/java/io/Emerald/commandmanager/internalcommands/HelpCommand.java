package io.Emerald.commandmanager.internalcommands;

import io.Emerald.annotations.CommandMeta;
import io.Emerald.commandmanager.Command;
import io.Emerald.commandmanager.EmeraldCommandRegistry;
import io.Emerald.commandmanager.InternalCommand;
import io.Emerald.internal.api.CommandSender;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.Embed;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.Arrays;

@CommandMeta(
        command = "help",
        usage = "!help",
        description = "Displays all commands in the bot."
)
public class HelpCommand extends InternalCommand {

    @Override
    public void execute(CommandSender sender, IMessage message, String[] args) {
        IChannel channel = message.getChannel();
        String commands = "", descriptions = "";
        for (Command command : EmeraldCommandRegistry.getRegistry().getCommands()) {
            // Ignore commands that aren't valid for this area or are unlisted commands
            if (!Arrays.asList(command.getValidSenders()).contains(sender.getSenderType())) {
                continue;
            }
            // Check arguments for a command; serve single command description page if command exists
            if (args.length > 0 && args[0].equalsIgnoreCase(command.getCommand())) {
                Embed.EmbedField commandField = new Embed.EmbedField("Command", "**!" + command.getCommand() + "**", true);
                Embed.EmbedField usageField = new Embed.EmbedField("Usage", "**" + command.getUsage() + "**", true);
                Embed.EmbedField descriptionField = new Embed.EmbedField("Description", "```" + command.getDescription() + "```", false);
                EmbedObject object = new EmbedBuilder().appendField(commandField).appendField(usageField).appendField(descriptionField)
                        .withFooterText("Use \"!help\" to see other commands!").withColor(Color.MAGENTA).build();
                // Send the message
                channel.sendMessage(object);
                return;
            }

            commands += "**!" + command.getCommand() + "**\n";
            descriptions += "_" + command.getDescription() + "_\n";
        }
        
        Embed.EmbedField commandField = new Embed.EmbedField("Command", commands, true);
        Embed.EmbedField descriptionField = new Embed.EmbedField("Description", descriptions, true);
        EmbedObject object = new EmbedBuilder().withTitle("Here you go " + sender.getName() + "!").appendField(commandField)
                .appendField(descriptionField).withFooterText("Use \"!help <command>\" to get more info!").withColor(Color.MAGENTA).build();
        // Send the message
        channel.sendMessage(object);
    }
}
