package com.eugene.demos.rabbits;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin implements Listener {
    public BukkitTask getRabbitTask() {
        return rabbitTask;
    }

    public void setRabbitTask(BukkitTask rabbitTask) {
        this.rabbitTask = rabbitTask;
    }

    private BukkitTask rabbitTask;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Fibonacci Rabbits are ready to play!");
        setRabbitTask(new RabbitGenerationTask(this).runTaskLater(this, 20));
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, (Runnable)getRabbitTask(), 0L, 100L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Fibonacci Rabbits: shutdown.");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }
}
