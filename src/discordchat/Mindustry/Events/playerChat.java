package discordchat.Mindustry.Events;

import discordchat.Discord.internal.sendMsgToDiscord;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.json.JSONObject;

import java.io.IOException;

public class playerChat {
    public static void run(DiscordApi bot, JSONObject config, EventType.PlayerChatEvent e) throws IOException, InterruptedException {
        // Send message to discord
        if (!config.getString("bot_token").isEmpty()) {
            if (!e.message.startsWith("/")) {
                new sendMsgToDiscord(bot, config, e);
            }
        }
    }
}
