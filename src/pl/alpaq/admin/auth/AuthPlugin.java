package pl.alpaq.admin.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin{
	
	public void onEnable(){
		long time = System.currentTimeMillis();

		loadConfig();
		loadListeners();
		loadPinManager();
		
		getLogger().info("Zaladowano poprawnie plugin w "+(System.currentTimeMillis()-time) + "ms");
	}
	
	public void onDisable(){
	
	}
	public void loadListeners(){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InteractListener(), this);
	}

	public void loadConfig(){
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		
	}
	public void loadPinManager(){
		PinManager manager = new PinManager(this);
	}
	
}
