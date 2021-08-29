package me.alon.lagback;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class LagBack extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(true);
        Location loc = e.getPlayer().getLocation();
        Material block = e.getBlock().getType();
        if (block.isSolid()) {
            if (e.getBlock().getX() == (int) e.getPlayer().getLocation().getX()) {
                if (e.getBlock().getZ() == (int) e.getPlayer().getLocation().getZ()) {
                    e.getPlayer().sendMessage(ChatColor.GREEN + "Lagging");
                    long ping = e.getPlayer().getPing();
                    try {
                        Thread.sleep(ping);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    e.getPlayer().teleport(loc);
                } else {
                    // DEBUG
                    // e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging");
                }
            } else {
                // DEBUG
                // e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging");
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
        if (e.getBlock().getX() == (int) e.getPlayer().getLocation().getX()) {
            if (e.getBlock().getZ() == (int) e.getPlayer().getLocation().getZ()) {
                e.getPlayer().sendMessage(ChatColor.GREEN + "Lagging");
                Location loc = e.getPlayer().getLocation();
                long ping = e.getPlayer().getPing();
                try {
                    Thread.sleep(ping);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                e.getPlayer().teleport(loc);
            } else {
                // DEBUG
                // e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging");
            }
        } else {
            // DEBUG
            // e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging");
        }
    }
}
