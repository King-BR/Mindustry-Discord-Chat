package discordchat.Discord.internal;


import arc.util.Log;
import arc.util.Strings;
import mindustry.gen.Call;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.JSONObject;

public class sendMsgToGame {
    public sendMsgToGame(DiscordApi bot, MessageCreateEvent event, JSONObject config) {
        if (event.getServerTextChannel().isPresent()) {
            String name = Strings.stripColors(event.getMessageAuthor().getDisplayName());
            String msg = event.getMessage().getReadableContent().replace("\n", " ");
            msg = msg.replaceAll("_", "\\_").replaceAll("\\*", "\\*").replaceAll("~", "\\~");

            Call.sendMessage("[blue]\uE80D[] [orange][[[]" + name + "[orange]]:[] " + msg);
            Log.info("DISCORD > [" + name + "]: " + msg);
        }
    }

    public sendMsgToGame(DiscordApi bot, MessageAuthor author, String msg, JSONObject config) {
        String name = author.getDisplayName();
        msg = msg.replaceAll("_", "\\_").replaceAll("\\*", "\\*").replaceAll("~", "\\~");

        Call.sendMessage("[blue]\uE80D[] [orange][[[]" + name + "[orange]]:[] " + msg);
        Log.info("DISCORD > [" + name + "]: " + msg);
    }

    public sendMsgToGame(DiscordApi bot, String author, String msg, JSONObject config) {
        String name = Strings.stripColors(author);
        name = name.replaceAll("_", "").replaceAll("\\*", "").replaceAll("~", "");
        msg = msg.replaceAll("_", "").replaceAll("\\*", "").replaceAll("~", "");

        Call.sendMessage("[blue]\uE80D[] [orange][[[]" + name + "[orange]]:[] " + msg);
        Log.info("DISCORD > [" + name + "]: " + msg);
    }
}
