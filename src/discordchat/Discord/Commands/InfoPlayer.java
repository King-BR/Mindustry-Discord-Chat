package discordchat.Discord.Commands;

import arc.struct.ObjectSet;
import mindustry.net.Administration;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

import static mindustry.Vars.netServer;

public class InfoPlayer {
    public InfoPlayer(DiscordApi bot, JSONObject config, MessageCreateEvent event, String[] args) throws IOException {
        if (!event.getServerTextChannel().isPresent()) return;
        ServerTextChannel channel = event.getServerTextChannel().get();

        if (args.length < 2) {
            new MessageBuilder()
                    .append("You forgot to say the player name!")
                    .send(channel)
                    .join();
            return;
        }

        if (netServer.admins.findByName(args[1]).size > 0 || netServer.admins.searchNames(args[1]).size > 0) {
            ObjectSet<Administration.PlayerInfo> players = netServer.admins.findByName(args[1]);

            if (players.size <= 1) players = netServer.admins.searchNames(args[1]);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTimestampToNow()
                    .setColor(Color.GREEN)
                    .setTitle("Player information")
                    .setDescription(players.size + " players found");

            int i = 0;
            for (Administration.PlayerInfo info : players) {
                if (i < 21) {
                    embed.addInlineField((i + 1) + " - " + info.lastName, "UUID: `" + info.id + "`\n" +
                            "Names used: `" + info.names.toString(", ") + "`\n" +
                            "Joined " + info.timesJoined + " time" + (info.timesJoined > 1 ? "s": "") + "\n" +
                            "Kicked " + info.timesKicked + " time" + (info.timesKicked > 1 ? "s": "") + "\n");
                }

                i++;
            }

            new MessageBuilder()
                    .setEmbed(embed)
                    .send(channel)
                    .join();
        } else {
            new MessageBuilder()
                    .append("Couldn't find any player with this name!")
                    .send(channel)
                    .join();
        }
    }
}
