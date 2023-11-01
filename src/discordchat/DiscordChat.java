package discordchat;

import arc.Events;
import arc.util.*;
import discordchat.Discord.Bot;
import discordchat.Mindustry.Events.playerChat;
import discordchat.Mindustry.Events.playerJoin;
import discordchat.Mindustry.Events.playerLeave;
import discordchat.Mindustry.Events.worldLoad;
import mindustry.game.EventType;
import mindustry.gen.Player;
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
                Log.info("[DiscordChat] A error has ocurred!");
                ex.printStackTrace();
            }
        });

        Events.on(EventType.PlayerJoin.class, e -> {
            try {
                playerJoin.run(bot, config, e);
            } catch (IOException | InterruptedException ex) {
                Log.info("[DiscordChat] A error has ocurred!");
                ex.printStackTrace();
            }
        });

        Events.on(EventType.PlayerLeave.class, e -> {
            try {
                playerLeave.run(bot, config, e);
            } catch (IOException | InterruptedException ex) {
                Log.info("[DiscordChat] A error has ocurred!");
                ex.printStackTrace();
            }
        });

        Events.on(EventType.WorldLoadEvent.class, e -> worldLoad.run(bot, config, e));
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
        handler.<Player>register("discord", "Discord invite link", (args, player) -> {
            if (config.has("discord_invite") && !config.getString("discord_invite").isEmpty()) {
                player.sendMessage("Join our discord!\n" + config.getString("discord_invite"));
            } else player.sendMessage("The discord invite is not configured! Ask the server owner to configure it!");
        });
    }

}
