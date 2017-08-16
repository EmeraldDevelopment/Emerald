package io.EmeraldDevelopment.Emerald.internal.plugin;

import io.EmeraldDevelopment.Emerald.commandmanager.Command;
import io.EmeraldDevelopment.Emerald.internal.SenderType;

public interface CommandSender {

    void sendMessage(String message);

    boolean hasPermission(Command command);

    String getName();

    SenderType getSenderType();
}
