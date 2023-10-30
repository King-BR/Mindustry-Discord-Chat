package discordchat.Discord;

import arc.util.Log;
import discordchat.Discord.Listeners.MsgCreate;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import static discordchat.DiscordChat.config;

public class Bot {
    public static boolean logged = false;

    public static DiscordApi run() {
        DiscordApi bot = new DiscordApiBuilder()
                .setToken(config.getString("bot_token"))
                .setAllIntents()
                .login().join();

        bot.setAutomaticMessageCacheCleanupEnabled(true);
        bot.setMessageCacheSize(100, 3600);

        bot.addListener(new MsgCreate(bot));

        Log.info("[DiscordChat] Bot logged in as " + bot.getYourself().getName());

        logged = true;

        return bot;
    }
}
