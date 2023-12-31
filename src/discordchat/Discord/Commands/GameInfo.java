package discordchat.Discord.Commands;

import arc.util.Log;
import arc.util.Strings;
import discordchat.Mindustry.Events.worldLoad;
import discordchat.utils.Utils;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Mods;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.modules.ItemModule;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.JSONObject;

import java.awt.*;

import static mindustry.Vars.mods;
import static mindustry.Vars.state;

public class GameInfo {
    public GameInfo(DiscordApi bot, JSONObject config, MessageCreateEvent event, String[] args) {
        if (!event.getServerTextChannel().isPresent()) return;
        ServerTextChannel channel = event.getServerTextChannel().get();

        if (!worldLoad.started || !state.isPlaying()) {
            new MessageBuilder()
                    .append("The server is offline!")
                    .send(channel)
                    .join();
            return;
        }

        Teams.TeamData data = !Groups.player.isEmpty() ? Groups.player.first().team().data() : state.teams.get(Team.sharded);
        CoreBlock.CoreBuild core = data.cores.first();
        ItemModule items = core.items;

        String waves = "Wave: " + state.wave +
                "\nNext wave in: " + Utils.msToDuration((state.wavetime / 60) * 1000, true) +
                "\nEnemies alive: " + state.enemies;

        StringBuilder res = new StringBuilder();
        items.each((arg1, arg2) -> res.append(Strings.stripColors(arg1.name))
                .append(": ")
                .append(items.get(arg1.id))
                .append("\n"));

        String map = "Name: " + state.map.name() +
                "\nAuthor: " + state.map.author() +
                "\nSize: " + state.map.width + "x" + state.map.height +
                "\nDescription: " + state.map.description();

        StringBuilder players = new StringBuilder();
        for (Player p : Groups.player) {
            players.append(Strings.stripColors(p.name())).append(", ");
        }

        StringBuilder modsStr = new StringBuilder();
        for (Mods.LoadedMod lmod : mods.list()) {
            modsStr.append(lmod.meta.displayName).append(", ");
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTimestampToNow()
                .setTitle("Game info")
                .setDescription(waves)
                .addField("Mods", mods.list().size == 0 ? "No mods" : modsStr.substring(0, modsStr.length()-2))
                .addField("Map", map)
                .addField("Players", Groups.player.isEmpty() ? "No players online" : players.substring(0, players.length()-2));

        EmbedBuilder resEmbed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTimestampToNow()
                .setTitle("Resources")
                .setDescription(res.toString());

        if (state.map.previewFile().exists()) embed.setImage(state.map.previewFile().file());

        new MessageBuilder()
                .setEmbeds(embed, resEmbed)
                .send(channel)
                .join();
    }
}
