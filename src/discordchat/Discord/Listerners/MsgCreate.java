package discordchat.Discord.Listerners;

import discordchat.Discord.internal.sendMsgToGame;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

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

            if (channel.getIdAsString().equals(config.getString("channel_chat"))) {
                if (!event.getMessageContent().startsWith(prefix)) {
                    new sendMsgToGame(bot, event, config);
                    return;
                }

                return;
            }
        }
    }
}
