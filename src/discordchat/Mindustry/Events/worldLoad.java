package discordchat.Mindustry.Events;

import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONObject;

import java.awt.*;
import java.util.Optional;

public class worldLoad {
    public static boolean started = false;

    public static void run(DiscordApi bot, JSONObject config, EventType.WorldLoadEvent e) {
        if (started) return;
        started = true;

        String channelID = config.has("channel_announcement") && !config.getString("channel_announcement").isEmpty() ? config.getString("channel_announcement") : config.getString("channel_chat");

        Optional<ServerTextChannel> optionalChannel = bot.getServerTextChannelById(channelID);
        if (!optionalChannel.isPresent()) return;
        ServerTextChannel channel = optionalChannel.get();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Server online!")
                .setColor(Color.green);

        if (config.has("server_ip") && !config.getString("server_ip").isEmpty()) embed.setDescription("**IP:** ```" + config.getString("server_ip") + "```");

        new MessageBuilder()
                .setEmbed(embed)
                .send(channel).join();
    }
}
