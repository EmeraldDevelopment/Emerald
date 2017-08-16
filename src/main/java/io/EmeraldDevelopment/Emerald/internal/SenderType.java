package io.EmeraldDevelopment.Emerald.internal;

public enum SenderType {
    USER, CONSOLE;

    public static SenderType getSenderType() {
        return SenderType.USER;
    }
}
