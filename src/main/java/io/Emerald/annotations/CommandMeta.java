package io.Emerald.annotations;

import io.Emerald.internal.api.ChannelType;
import io.Emerald.internal.api.SenderType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class header annotation used to identify a command.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMeta {

    // The text that the command is assigned to.
    String command();
    // The usage of the command.
    String usage() default "No usage provided.";
    // The command's description
    String description() default "No description provided.";
    // Toggles whether permissions should be used for this command.
    boolean usePermissions() default false;
    // The minimum argument count for this command.
    int minimumArgs() default 0;
    // The valid sender types for this command.
    SenderType[] validSenders() default {SenderType.USER, SenderType.CONSOLE};
    // The valid channel types for this command.
    ChannelType[] validChannels() default {ChannelType.PRIVATE, ChannelType.PUBLIC};
}
