package discordchat.Discord.internal;

import arc.util.Log;
import arc.util.Strings;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Optional;

public class sendMsgToDiscord {
    public sendMsgToDiscord(DiscordApi bot, JSONObject config, EventType.PlayerChatEvent e) throws IOException, InterruptedException {
        String msg = "**" + e.player.name() + "**: " + e.message;
        msg = Strings.stripColors(msg).replaceAll("@everyone", "@.everyone").replaceAll("@here", "@.here");

        // Check if channel_id is not blank
        if (!config.getString("channel_chat").isEmpty()) {
            Optional<ServerTextChannel> optionalChannel = bot.getServerTextChannelById(config.getString("channel_chat"));

            // If the channel exists, send message
            if (optionalChannel.isPresent()) {
                optionalChannel.get().sendMessage(msg);
            } else Log.info("[DiscordChat] The channel id is invalid or the channel is unreachable");
        }
    }

    public sendMsgToDiscord(DiscordApi bot, JSONObject config, String msg) {
        // Check if channel_id is not blank
        if (!config.getString("channel_chat").isEmpty()) {
            Optional<ServerTextChannel> optionalChannel = bot.getServerTextChannelById(config.getString("channel_chat"));

            // If the channel exists, send message
            if (optionalChannel.isPresent()) {
                optionalChannel.get().sendMessage(msg);
            } else Log.info("[DiscordChat] The channel id is invalid or the channel is unreachable");
        }
    }
}
