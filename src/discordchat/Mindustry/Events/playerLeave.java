package discordchat.Mindustry.Events;

import discordchat.Discord.internal.sendMsgToDiscord;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.json.JSONObject;

import java.io.IOException;

import static arc.util.Strings.stripColors;

public class playerLeave {
    public static void run(DiscordApi bot, JSONObject config, EventType.PlayerLeave e) throws IOException, InterruptedException {

        String msg = ":outbox_tray: **" + stripColors(e.player.name()) + "** left";
        new sendMsgToDiscord(bot, config, msg);
    }
}