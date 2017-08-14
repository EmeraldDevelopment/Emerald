package io.EmeraldDevelopment.Emerald;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.util.DiscordException;

import java.io.File;

/** --- Authors ---
 * @author Atrius
 * @author Khriox
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
