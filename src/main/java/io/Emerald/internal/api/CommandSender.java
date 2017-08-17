package io.Emerald.internal.api;

import io.Emerald.commandmanager.Command;

public interface CommandSender {

    void sendMessage(String message);

    boolean hasPermission(Command command);

    boolean hasModifier(Command command, String modifier);

    SenderType getSenderType();
}
