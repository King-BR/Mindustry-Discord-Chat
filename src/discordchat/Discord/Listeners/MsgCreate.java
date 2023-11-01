package discordchat.Discord.Listeners;

import arc.util.Log;
import discordchat.Discord.Commands.GameInfo;
import discordchat.Discord.Commands.InfoPlayer;
import discordchat.Discord.Commands.Ip;
import discordchat.Discord.internal.sendMsgToGame;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.IOException;
import java.util.stream.Stream;

import static discordchat.DiscordChat.config;

public class MsgCreate implements MessageCreateListener {
    private final DiscordApi bot;

    public MsgCreate(DiscordApi _bot) {
        this.bot = _bot;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String prefix = config.getString("prefix");

        if (event.getServerTextChannel().isPresent() && event.getMessageAuthor().isRegularUser()) {
            ServerTextChannel channel = event.getServerTextChannel().get();

            if (!event.getMessageContent().startsWith(prefix)) {
                if (channel.getIdAsString().equals(config.getString("channel_chat"))) {
                    new sendMsgToGame(bot, event, config);
                }
            } else if (channel.getIdAsString().equals(config.getString("channel_chat")) ||
                        channel.getIdAsString().equals(config.getString("channel_log")) ||
                        channel.getIdAsString().equals(config.getString("channel_announcement"))) {
                String[] args = Stream.of(event.getMessageContent().split(" ")).filter(str -> !str.isEmpty()).toArray(String[]::new);

                switch (args[0].replaceFirst(prefix, "")) {
                    case "ip" -> new Ip(bot, config, event, args);
                    case "gameinfo", "gi" -> new GameInfo(bot, config, event, args);
                    case "playerinfo", "pi" -> {
                        try {
                            new InfoPlayer(bot, config, event, args);
                        } catch (IOException e) {
                            Log.info("[DiscordChat] A error has ocurred on playerinfo discord command!\n");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
