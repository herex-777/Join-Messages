package me.herex.joinmessages;

import me.herex.joinmessages.listener.JoinListener;
import me.herex.joinmessages.utils.ColorUtil;
import me.herex.joinmessages.utils.SilentJoinManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinMessagesPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /* /joinmessages reload */
        if (command.getName().equalsIgnoreCase("joinmessages")) {

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

                if (!sender.hasPermission("joinmessages.reload")) {
                    sender.sendMessage(ColorUtil.color("&cNo permission."));
                    return true;
                }

                reloadConfig();
                sender.sendMessage(ColorUtil.color("&aJoinMessages config reloaded."));
                return true;
            }

            sender.sendMessage(ColorUtil.color("&cUsage: /joinmessages reload"));
            return true;
        }

        /* /silentjoin */
        if (command.getName().equalsIgnoreCase("silentjoin")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("joinmessages.silent")) {
                player.sendMessage(ColorUtil.color("&cNo permission."));
                return true;
            }

            SilentJoinManager.toggle(player.getUniqueId());

            boolean enabled = SilentJoinManager.isSilent(player.getUniqueId());
            player.sendMessage(ColorUtil.color(
                    enabled
                            ? "&aSilent join enabled."
                            : "&cSilent join disabled."
            ));

            return true;
        }

        return false;
    }
}
