package discordchat.utils;

import arc.Core;
import arc.util.Log;
import discordchat.DiscordChat;
import org.json.JSONObject;

public class Config {

    public static JSONObject createConfig(DiscordChat plugin) {
        // Load config file if it already exists
        if (Core.settings.getDataDirectory().child("mods/DiscordChat/config.json").exists()) {
            return loadConfig(plugin);
        }

        // Make default config
        JSONObject defaultConfig = new JSONObject();

        defaultConfig.put("bot_token", "")
                .put("prefix", "!")
                .put("channel_chat", "")
                .put("channel_log", "")
                .put("channel_announcement", "");

        Core.settings.getDataDirectory().child("mods/DiscordChat/config.json").writeString(defaultConfig.toString(4));

        Log.info("[DiscordChat] Config created");
        return defaultConfig;
    }

    public static JSONObject loadConfig(DiscordChat plugin) {
        JSONObject config = new JSONObject(plugin.getConfig().readString());
        Log.info("[DiscordChat] Config loaded");
        return  config;
    }
}
