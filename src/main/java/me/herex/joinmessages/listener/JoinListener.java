package me.herex.joinmessages.listener;

import me.herex.joinmessages.JoinMessagesPlugin;
import me.herex.joinmessages.utils.ColorUtil;
import me.herex.joinmessages.utils.LuckPermsUtil;
import me.herex.joinmessages.utils.SilentJoinManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final JoinMessagesPlugin plugin;

    public JoinListener(JoinMessagesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Silent join
        if (SilentJoinManager.isSilent(player.getUniqueId())) {
            event.setJoinMessage(null);
            return;
        }

        if (!plugin.getConfig().getBoolean("join-message.enabled")) {
            event.setJoinMessage(null);
            return;
        }

        String message = getJoinMessage(player);

        if (message == null) {
            event.setJoinMessage(null);
            return;
        }

        message = message.replace("%player%", player.getName());

        if (message.contains("%prefix%")) {
            message = message.replace(
                    "%prefix%",
                    LuckPermsUtil.getPrefix(player)
            );
        }

        event.setJoinMessage(ColorUtil.color(message));
    }

    private String getJoinMessage(Player player) {

        ConfigurationSection ranks =
                plugin.getConfig().getConfigurationSection("join-message.ranks");

        if (ranks != null) {
            for (String key : ranks.getKeys(false)) {

                String permission = ranks.getString(key + ".permission");
                String message = ranks.getString(key + ".message");

                if (permission != null && player.hasPermission(permission)) {
                    return message;
                }
            }
        }

        return plugin.getConfig().getString("join-message.default");
    }
}
