package discordchat.Discord.Commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.JSONObject;

import java.awt.*;

public class Ip {
    public Ip(DiscordApi bot, JSONObject config, MessageCreateEvent event, String[] args) {
        if (!event.getServerTextChannel().isPresent()) return;
        ServerTextChannel channel = event.getServerTextChannel().get();

        EmbedBuilder embed = new EmbedBuilder()
                .setTimestampToNow()
                .setTitle("Server IP")
                .setDescription("```\n" + config.getString("server_ip") + "\n```")
                .setColor(Color.green);

        new MessageBuilder()
                .setEmbed(embed)
                .send(channel).join();
    }
}
