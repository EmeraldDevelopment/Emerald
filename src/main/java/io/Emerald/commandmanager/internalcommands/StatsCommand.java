package io.Emerald.commandmanager.internalcommands;

import io.Emerald.Emerald;
import io.Emerald.annotations.CommandMeta;
import io.Emerald.commandmanager.InternalCommand;
import io.Emerald.internal.Util;
import io.Emerald.internal.api.CommandSender;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.Embed;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@CommandMeta(
        command = "stats",
        usage = "!stats",
        description = "Displays some information on the bot."
)
public class StatsCommand extends InternalCommand {

    @Override
    public void execute(CommandSender sender, IMessage message, String[] args) {
        IDiscordClient client = message.getClient();
        IChannel channel = message.getChannel();
        Runtime runtime = Runtime.getRuntime();
        String memoryUsage = new DecimalFormat("0.00").format((double) (runtime.totalMemory() - runtime.freeMemory()) / runtime.freeMemory() * 100);
        String memoryInMB = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + "/" + runtime.freeMemory() / (1024 * 1024);
        LocalDateTime dateTime = LocalDateTime.now();
        DecimalFormat format = new DecimalFormat("00");
        File[] file = new File("users/").listFiles();
        // Check if there is any user data
        if (file == null) {
            file = new File[0];
        }
        long uptime = System.currentTimeMillis() - Emerald.getStartupTime();
        int days, hours, minutes;
        // Get the number of days
        for (days = 0; uptime >= 86400000 ; days++) {
            uptime -= 86400000;
        }
        // Get the number of hours
        for (hours = 0; uptime >= 3600000 ; hours++) {
            uptime -= 3600000;
        }
        // Get the number of minutes
        for (minutes = 0; uptime >= 60000; minutes++) {
            uptime -= 60000;
        }
        // Fields for embed object
        Embed.EmbedField guildsField = new Embed.EmbedField("Total Guilds", "**" + client.getGuilds().size() + "**", true);
        Embed.EmbedField userCountField = new Embed.EmbedField("Total Users", "" + file.length, true);
        Embed.EmbedField memoryField = new Embed.EmbedField("Memory Used", "**[" + memoryInMB + "] MB | " + memoryUsage + "%**", true);
        Embed.EmbedField localTimeField = new Embed.EmbedField("Local Time", "**" + Util.parseDate(dateTime) + " " + (dateTime.getHour() > 12 ?
                dateTime.getHour() - 12 : dateTime.getHour()) + " : " + dateTime.getMinute() + (dateTime.getHour() > 12 ? " PM" : " AM") + "**", true);
        Embed.EmbedField pingField = new Embed.EmbedField("Ping to Channel", "**" + channel.getShard().getResponseTime() + "ms**", true);
        Embed.EmbedField uptimeField = new Embed.EmbedField("Uptime", "**" + days + " Days | " + format.format(hours) + " : " + format.format(minutes) + "**", true);
        // Create statistic object
        EmbedObject object = new EmbedBuilder().withTitle("Bot Statistics").appendField(guildsField).appendField(userCountField)
                .appendField(memoryField).appendField(localTimeField).appendField(pingField).appendField(uptimeField)
                .withThumbnail(client.getOurUser().getAvatarURL()).withColor(new Color(0, 255, 60)).build();
        // Send statistic message
        channel.sendMessage(object);
    }
}
