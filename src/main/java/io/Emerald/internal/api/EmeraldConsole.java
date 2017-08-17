package io.Emerald.internal.api;

import io.Emerald.Emerald;
import io.Emerald.commandmanager.Command;

/**
 * Emerald's implementation of the console object.
 */
public class EmeraldConsole implements Console {

    private static EmeraldConsole console;

    private static EmeraldConsole getConsole() {

        if (console == null) {
            console = new EmeraldConsole();
        }

        return console;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean hasPermission(Command command) {
        return true;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.CONSOLE;
    }

    @Override
    public String getName() {
        return Emerald.getClient().getApplicationName();
    }
}
