package fr.swoking.AndoraUnlimitedSlot;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	private int maxSlots;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
		
		File config = new File(getDataFolder() + "/config/", "configTest.yml");
		try {
			if(!config.exists()) {
				getConfig().options().copyDefaults(true);
				getConfig().set("slots", Integer.valueOf(-1));
				saveConfig();
				saveDefaultConfig();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
        this.maxSlots = this.getConfig().getInt("slots");
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		event.setResult(PlayerLoginEvent.Result.ALLOWED);
		if (this.maxSlots <= 0) {
            event.setResult(PlayerLoginEvent.Result.ALLOWED);
        } else {
            int online = Bukkit.getOnlinePlayers().size();
            if (online >= this.maxSlots) {
                event.setResult(PlayerLoginEvent.Result.KICK_FULL);
            } else {
                event.setResult(PlayerLoginEvent.Result.ALLOWED);
            }
        }
	}
	
	@Override
	public void onDisable() {
		
	}
}
