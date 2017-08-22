package io.Emerald.internal.api;

import io.Emerald.commandmanager.Command;
import io.Emerald.commandmanager.PluginCommand;

/**
 * Emerald's undefined command sender interface.
 */
public interface CommandSender {

    String getName();

    void sendMessage(String message);

    boolean hasPermission(Command command);

    boolean hasModifier(PluginCommand command, String modifier);

    SenderType getSenderType();
}
