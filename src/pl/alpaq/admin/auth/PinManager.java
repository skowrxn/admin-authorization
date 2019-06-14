package pl.alpaq.admin.auth;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PinManager implements Listener{

	
	
	public static HashMap<Player, Integer> proby = new HashMap<>();
	public static AuthPlugin plugin;
	static FileConfiguration cfg = plugin.getConfig();
	public PinManager(AuthPlugin plu){
		plugin = plu;
		Bukkit.getPluginManager().registerEvents(this, plu);
	}
	public static String getPin(){
		
		return cfg.getString("pin");
	}
	public static String getTitlePorsba(){
		return cfg.getString("title1");
	}
	public static String getSubtitleProsba(){
		return cfg.getString("subtitle1");
	}
	public static String getSubtitleWrong(){
		return cfg.getString("subtitle2");
	}
	public static String getTitleWrong(){
		return cfg.getString("title2");
	}
	public static String getTitleOK(){
		return cfg.getString("title3");
	}
	public static String getSubtitleOK(){
		return cfg.getString("subtitle3");
	}
	public static int getMaxAttemps(){
		return cfg.getInt("max_proby");
	}
	public static String getKickMSG(){
		return cfg.getString("kick_msg");
	
	}
	public static void addAttemp(Player p){
		if(proby.containsKey(p)){
			proby.put(p, proby.get(p)+1);
		}
	}
	public static Integer getAttemps(Player p){
		int i = 0;
		if(proby.containsKey(p)){
			i = proby.get(p);
		}
		return i;
	}
	
	
}
