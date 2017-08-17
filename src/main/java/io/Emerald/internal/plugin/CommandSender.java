package io.EmeraldDevelopment.Emerald.internal.plugin;

import io.EmeraldDevelopment.Emerald.commandmanager.Command;
import io.EmeraldDevelopment.Emerald.internal.api.commandsender.SenderType;

public interface CommandSender {

    void sendMessage(String message);

    boolean hasPermission(Command command);

    SenderType getSenderType();
}
