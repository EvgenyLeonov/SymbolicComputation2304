package com.eugene.demos.rabbits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RabbitGenerationTask extends BukkitRunnable {
    private final JavaPlugin plugin;
    private int totalNumber;

    public int getTotalNumber() {
        return totalNumber;
    }

    private void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    private void increaseCurrentValue() {
        this.currentValue++;
    }

    private int currentValue;


    private World getWorld() {
        return Bukkit.getWorld("world");
    }

    public RabbitGenerationTask(JavaPlugin plugin) {
        this.plugin = plugin;
        setTotalNumber(0);
        this.currentValue = 0;
    }

    private int fibonacci(int n)
    {
        // Base Case
        if (n <= 1)
            return n;

        // Recursive call
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    @Override
    public void run() {
        List<Player> players = getWorld().getPlayers();
        //System.out.println("Players count=" + players.size());
        try {
            if (!players.isEmpty()) {
                final Player player = players.get(0);
                final Location location = player.getLocation();
                increaseCurrentValue();
                final int fibNum = fibonacci(getCurrentValue());
                System.out.println("Creating " + fibNum + " rabbits...");
                for (int i = 0; i < fibNum; i++) {
                    getWorld().spawnEntity(location, EntityType.RABBIT);
                }
                setTotalNumber(getTotalNumber() + fibNum);
                System.out.println("Total number: " + getTotalNumber());
            }
        } catch (Exception exc) {
            System.out.println("Error=" + exc.getMessage());
        }
    }
}
