package discordchat.Mindustry.Events;

import discordchat.Discord.internal.sendMsgToDiscord;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.json.JSONObject;

import java.io.IOException;

import static arc.util.Strings.stripColors;

public class playerJoin {
    public static void run(DiscordApi bot, JSONObject config, EventType.PlayerJoin e) throws IOException, InterruptedException {

        String msg = ":inbox_tray: **" + stripColors(e.player.name()) + "** joined";
        new sendMsgToDiscord(bot, config, msg);
    }
}
