package io.Emerald.internal.plugin;

import io.Emerald.commandmanager.Command;
import io.Emerald.internal.api.SenderType;

public interface CommandSender {

    void sendMessage(String message);

    boolean hasPermission(Command command);

    SenderType getSenderType();
}
