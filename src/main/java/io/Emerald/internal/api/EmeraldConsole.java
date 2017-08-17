package io.Emerald.internal.api;

import io.Emerald.Emerald;
import io.Emerald.commandmanager.Command;

public class EmeraldConsole implements ConsoleSender {

    private static EmeraldConsole console;

    public static EmeraldConsole getConsole() {
        if (console == null) {
            console = new EmeraldConsole();
        }

        return console;
    }

    @Override
    public String getName() {
        return Emerald.getClient().getApplicationName();
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
    public boolean hasModifier(Command command, String modifier) {
        return true;
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.CONSOLE;
    }
}
