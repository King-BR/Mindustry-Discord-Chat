package discordchat;

import arc.Events;
import arc.util.*;
import discordchat.Discord.Bot;
import discordchat.Mindustry.Events.playerChat;
import mindustry.game.EventType;
import mindustry.mod.*;
import org.javacord.api.DiscordApi;
import org.json.JSONObject;
import discordchat.utils.Config;

import java.io.IOException;

public class DiscordChat extends Plugin {
    public static JSONObject config;
    public static DiscordApi bot;

    public DiscordChat() {
        Events.on(EventType.PlayerChatEvent.class, e -> {
            try {
                playerChat.run(bot, config, e);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    //called when game initializes
    @Override
    public void init(){
        config = Config.createConfig(this);

        // Start the discord bot if token was provided
        if (!config.isEmpty() && !config.getString("bot_token").isEmpty()) {
            bot = Bot.run();
        }
    }

    //register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("reloadconfig", "[DiscordChat] Reload plugin config", args -> {
            config = Config.createConfig(this);
        });

        handler.register("startbot", "[force]", "[DiscordChat] Start the discord bot if it isn't already online", args -> {
            // Start the discord bot if token was provided and the bot isn't online
            if (((!config.isEmpty() && !config.getString("bot_token").isEmpty()) || args.length > 0) && !Bot.logged) {
                if (args.length > 0) Log.info("[DiscordChat] Force starting bot");
                bot = Bot.run();
            }
        });

        handler.register("botinfo", "[DiscordChat] Shows bot info", args -> {
            if (Bot.logged) {
                Log.info("[DiscordChat] Bot Info ------\nBot Name: " + bot.getYourself().getName() + "\nID: " + bot.getYourself().getIdAsString() + "\n-------------");
            } else {
                Log.info("[DiscordChat] Bot offline");
            }
        });
    }

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler){

    }

}
