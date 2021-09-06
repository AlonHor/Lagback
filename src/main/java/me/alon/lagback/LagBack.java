package me.alon.lagback;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class LagBack extends JavaPlugin implements Listener{

    HashMap<Player, Boolean> jumpTimer = new HashMap<>();
    HashMap<Player, Boolean> fallTimer = new HashMap<>();
    HashMap<Player, Location> loc = new HashMap<>();
    HashMap<Player, Location> lastMove = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        // jump
        Player p = e.getPlayer();
        lastMove.put(p, p.getLocation());
        if(Math.floor(e.getFrom().getY()) + 1 == Math.floor(e.getTo().getY()) || Math.floor(e.getFrom().getY()) + 2 == Math.floor(e.getTo().getY())) { // checking if the player is going up 1 or 2 blocks
            jumpTimer.put(p, true);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "UP"));
            loc.put(p, p.getLocation());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    jumpTimer.put(p, false);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + ""));
                }
            }, 20);
        }

        // fall
        if(Math.floor(e.getFrom().getY()) - 1 == Math.floor(e.getTo().getY()) || Math.floor(e.getFrom().getY()) - 2 == Math.floor(e.getTo().getY())) { // checking if the player is going down 1 or 2 blocks
            fallTimer.put(p, true);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "DOWN"));
            loc.put(p, p.getLocation());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    fallTimer.put(p, false);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + ""));
                }
            }, 10);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            if (e.getBlock().getX() == (int) e.getPlayer().getLocation().getX()) {
                if (e.getBlock().getZ() == (int) e.getPlayer().getLocation().getZ()) {
                    if (e.getBlock().getType().isSolid()) {
                        p.teleport(lastMove.get(p));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            if (e.getBlock().getX() == (int) e.getPlayer().getLocation().getX()) {
                if (e.getBlock().getZ() == (int) e.getPlayer().getLocation().getZ()) {
                    // e.getPlayer().sendMessage(ChatColor.GREEN + "Lagging");
                    if (jumpTimer.get(p)) {
                        e.getPlayer().teleport(loc.get(p));
                    }
                } // else {e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging"); }
            } // else {e.getPlayer().sendMessage(ChatColor.RED + "Not Lagging");}
        }
    }
}
