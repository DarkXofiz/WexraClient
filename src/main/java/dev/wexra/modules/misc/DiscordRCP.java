package dev.wexra.modules.misc;

import dev.wexra.com.discord.DiscordEventHandlers;
import dev.wexra.com.discord.DiscordRPC;
import dev.wexra.com.discord.DiscordRichPresence;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.events.impl.render.EventRender2D;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "DiscordRPC", desc = "Активность в дискорде", type = Type.Misc)
public class DiscordRCP extends Function {
    private final DiscordRPC rpc = DiscordRPC.INSTANCE;
    private volatile boolean started = false;
    private Thread thread;
    private final DiscordRichPresence presence = new DiscordRichPresence();

    @Override
    public void onEvent(Event event) {
        if (rpc == null) return; // Discord RPC kutuphanesi yuklenemedi, ozellik devre disi
        if (event instanceof EventUpdate) {
            startRpc();
        }
    }

    public synchronized void startRpc() {
        if (rpc == null || started) return;
        started = true;
        try {
            DiscordEventHandlers handlers = new DiscordEventHandlers();
            rpc.Discord_Initialize("1384873696375603281", handlers, true, "");
            presence.startTimestamp = System.currentTimeMillis() / 1000L;
            presence.largeImageText = "https://t.me/wexraclient";

            updatePresenceFields();

            rpc.Discord_UpdatePresence(presence);
        } catch (Throwable t) {
            System.err.println("[WexraClient] Discord RPC baslatilamadi:");
            t.printStackTrace();
            started = false;
            return;
        }

        thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    rpc.Discord_RunCallbacks();

                    updatePresenceFields();

                    rpc.Discord_UpdatePresence(presence);

                    Thread.sleep(2000L);
                }
            } catch (InterruptedException ignored) {
            } catch (Throwable t) {
                System.err.println("[WexraClient] Discord RPC dongusunde hata olustu, durduruluyor:");
                t.printStackTrace();
            } finally {
                started = false;
            }
        }, "TH-RPC-Handler");
        thread.setDaemon(true);
        thread.start();
    }

    private void updatePresenceFields() {
        presence.details = "User: " + Manager.USER_PROFILE.getName();
        presence.state = "Role: " + Manager.USER_PROFILE.getRole();

        presence.button_label_1 = "Купить";
        presence.button_url_1 = "https://wexraclient.ru";
        presence.button_label_2 = "Телеграмм";
        presence.button_url_2 = "https://t.me/wexraclient";

        presence.largeImageKey = "https://api.wexraclient.ru/api/loader/discord.gif";
    }

    @Override
    public void onDisable() {
        started = false;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        if (rpc != null) {
            try {
                rpc.Discord_Shutdown();
            } catch (Throwable t) {
                System.err.println("[WexraClient] Discord RPC kapatilirken hata olustu:");
                t.printStackTrace();
            }
        }
    }
}
