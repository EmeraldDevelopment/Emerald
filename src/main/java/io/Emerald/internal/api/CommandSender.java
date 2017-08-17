package io.Emerald.internal.api;

import io.Emerald.commandmanager.Command;

/**
 * Emerald's undefined command sender interface.
 */
public interface CommandSender {

    void sendMessage(String message);

    boolean hasPermission(Command command);

    boolean hasModifier(Command command, String modifier);

    SenderType getSenderType();
}
