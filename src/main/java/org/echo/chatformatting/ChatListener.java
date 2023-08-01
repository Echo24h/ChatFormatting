package org.echo.chatformatting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private Main plugin;

    public ChatListener() {
        plugin = Main.getInstance();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        String message = event.getMessage();

        String newMessage = plugin.getColorsFormat().formatMessage(message); // getColoredMessage();

        event.setMessage(newMessage);
    }
}
