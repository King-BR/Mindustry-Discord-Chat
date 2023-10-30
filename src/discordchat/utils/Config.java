package discordchat.utils;

import arc.Core;
import arc.util.Log;
import discordchat.DiscordChat;
import org.json.JSONObject;

public class Config {

    public static String[][] defaultConfigPairs = {{
        "plugin_version", "1.1"
    },{
        "bot_token", ""
    },{
        "prefix", "!"
    },{
        "channel_chat", ""
    },{
        "channel_log", ""
    },{
        "channel_announcement", ""
    }};

    public static JSONObject createConfig(DiscordChat plugin) {
        // Load config file if it already exists
        if (Core.settings.getDataDirectory().child("mods/DiscordChat/config.json").exists()) {
            return loadConfig(plugin);
        }

        // Make default config
        JSONObject defaultConfig = new JSONObject();

        for (String[] pair : defaultConfigPairs) {
            defaultConfig.put(pair[0], pair[1]);
        }

        Core.settings.getDataDirectory().child("mods/DiscordChat/config.json").writeString(defaultConfig.toString(4));

        Log.info("[DiscordChat] Config created");
        return defaultConfig;
    }

    public static JSONObject loadConfig(DiscordChat plugin) {
        JSONObject config = new JSONObject(plugin.getConfig().readString());
        Log.info("[DiscordChat] Config loaded");

        if (!config.has("plugin_version")) config.put("plugin_version", "1.0");

        if (Float.parseFloat(config.getString("plugin_version")) < Float.parseFloat(defaultConfigPairs[0][1])) {
            Log.info("[DiscordChat] Older config version found!\nCurrent version: " + config.getString("plugin_version") +
                    "\nNew version: " + defaultConfigPairs[0][1] + "\n\nUpdating...");

            JSONObject defaultKeys = new JSONObject();

            for (String[] pair : defaultConfigPairs) {
                defaultKeys.put(pair[0], pair[1]);

                if (!config.has(pair[0])) config.put(pair[0], pair[1]);
            }

            for (int i = 0; i < config.keySet().toArray().length; i++){
                if (!defaultKeys.has(config.keySet().toArray()[i].toString()))
                    config.remove(config.keySet().toArray()[i].toString());
            }

            config.put(defaultConfigPairs[0][0], defaultConfigPairs[0][1]);

            Core.settings.getDataDirectory().child("mods/DiscordChat/config.json").writeString(config.toString(4));

            Log.info("Update complete!");
        }

        return  config;
    }
}
