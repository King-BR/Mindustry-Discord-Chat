# DiscordChat

Link your chat from mindustry to discord and vice versa

### Planned Features

* Log channel for commands used by players
* Game info (map, wave, resources, mods, plugins, etc...)
* Player info
* Game info in real time (maybe?)

### Building a Jar

`gradlew jar` / `./gradlew jar`

Output jar should be in `build/libs`.


### Installing

Simply place the output jar from the step above or from releases in your server's `config/mods` directory and restart the server.
List your currently installed plugins/mods by running the `mods` command.
The mod config will be created at `config/mods/DiscordChat/config.json` after restarting the server, you can reload the config at anytime by using the `reloadconfig` server command
