package io.EmeraldDevelopment.Emerald;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.util.DiscordException;

/** --- Authors ---
 * @author Atrius
 * @author Khriox
 */
public class Emerald {

    // Emerald Initialization
    public static void main(String[] args) {

        // Login to the service
        login(args[0]);
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
