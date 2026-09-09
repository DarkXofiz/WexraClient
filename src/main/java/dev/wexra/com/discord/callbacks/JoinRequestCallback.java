package dev.wexra.com.discord.callbacks;

import com.sun.jna.Callback;
import dev.wexra.com.discord.DiscordUser;


public interface JoinRequestCallback extends Callback {
    void apply(final DiscordUser p0);
}
