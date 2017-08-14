package io.EmeraldDevelopment.Emerald;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.util.DiscordException;

import java.io.File;

/**
 * Emerald is a Discord bot built with the same idea as Bukkit. Most Discord bots are generally designed
 * as a single unchanging instance that cannot be changed or modified in at best simple ways. Emerald is
 * designed to change that by allowing developers to simply build plugins to expand the capabilities of
 * their bots.
 *
 * @author Atrius
 * @author mikethemaker
 */
public class Emerald {

    // Emerald Initialization
    public static void main(String[] args) {

        // Create directories
        createDirectories();
        // Login to the service
        login(args[0]);
    }

    // Create directories for users and guilds
    private static void createDirectories() {

        File users = new File("users/");
        File guilds = new File("guilds/");

        if (!users.exists()) users.mkdirs();
        if (!guilds.exists()) guilds.mkdirs();
    }

    // Log into the service with the given token
    private static void login(String token) {

        try {
            new ClientBuilder().withToken(token).login();
        } catch (DiscordException e) {
            // TODO: Throw a text error here later.
        }
    }
}
