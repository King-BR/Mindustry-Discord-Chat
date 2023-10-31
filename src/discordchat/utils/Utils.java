package discordchat.utils;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static String msToDuration(float millis, boolean shortTime) {
        if (millis < 0) throw new IllegalArgumentException("[DiscordChat] Duration must be greater than zero!");

        if (!shortTime) {
            long hours = TimeUnit.MILLISECONDS.toHours((long) millis);
            millis -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) millis);
            millis -= TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);

            return (hours + " Hours " + minutes + " Minutes " + seconds + " Seconds");
        } else {
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) millis);
            millis -= TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);

            return (minutes + " Minutes " + seconds + " Seconds");
        }
    }
}
