package me.doruk.launchvector3.Command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class LaunchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("launch")) {
            if (args.length != 4) {
                commandSender.sendMessage(ChatColor.DARK_RED + "Usage: /launch <player|@p> <angleX> <angleY> <angleZ>" + org.bukkit.ChatColor.RESET);
                return false;
            }

            Player target = null;
            if (args[0].equalsIgnoreCase("@p")) {
                target = getNearestPlayer(commandSender);
                if (target == null) {
                    commandSender.sendMessage("No players found nearby.");
                    return false;
                }
            } else {
                target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    commandSender.sendMessage("Player not found: " + args[0]);
                    return false;
                }
            }

            try {
                double angleX = Double.parseDouble(args[1]);
                double angleY = Double.parseDouble(args[2]);
                double angleZ = Double.parseDouble(args[3]);
                Vector launchVector = new Vector(angleX, angleY, angleZ);
                launchVector = launchVector.normalize().multiply(Math.sqrt(angleX * angleX + angleY * angleY));
                target.setVelocity(launchVector);
                commandSender.sendMessage("Launched " + target.getName() + " with vector (" + launchVector.getX() + ", " + launchVector.getY() + ", " + launchVector.getZ() + ").");
                return true;

            } catch (NumberFormatException e) {
                commandSender.sendMessage("Invalid angles: Angles must be numeric.");
                return false;
            }
        }
        return false;
    }

    private Player getNearestPlayer(CommandSender sender) {
        Player nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        if (sender instanceof Player) {
            Player playerSender = (Player) sender;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.equals(playerSender)) {
                    double distance = player.getLocation().distance(playerSender.getLocation());
                    if (distance < nearestDistance) {
                        nearest = player;
                        nearestDistance = distance;
                    }
                }
            }
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender blockSender = (BlockCommandSender) sender;
            for (Player player : Bukkit.getOnlinePlayers()) {
                double distance = player.getLocation().distance(blockSender.getBlock().getLocation());
                if (distance < nearestDistance) {
                    nearest = player;
                    nearestDistance = distance;
                }
            }
        }

        return nearest;
    }
}