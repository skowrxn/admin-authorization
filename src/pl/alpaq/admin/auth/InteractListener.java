package pl.alpaq.admin.auth;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class InteractListener implements Listener {

	public static ArrayList<Player> tocancel = new ArrayList<>();
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(tocancel.contains(p))e.setCancelled(true);
	}
	@EventHandler
	public void drop(PlayerDropItemEvent e){
		if(tocancel.contains(e.getPlayer()))e.setCancelled(true);
	}
	@EventHandler
	public void move(PlayerMoveEvent e){
		if(tocancel.contains(e.getPlayer())){
			Location l = loc.get(e.getPlayer());
			int x = (int)l.getX();
			int y = (int)l.getY();
			int z = (int)l.getZ();
			int cx = (int)e.getPlayer().getLocation().getX();
			int cy = (int)e.getPlayer().getLocation().getY();
			int cz = (int)e.getPlayer().getLocation().getZ();
			if(cx != x || cy != y || cz != z){
				e.getPlayer().teleport(l);
			}
			
			
		}
	}
	private HashMap<Player, Location> loc = new HashMap<>();
	@EventHandler
	public void pvp(EntityDamageEvent e){
		
		if(e.getEntity().getType().equals(EntityType.PLAYER)){
			if(tocancel.contains((Player)e.getEntity())){
			e.setCancelled(true);
			}
		}
	}@EventHandler
	public void pvpx(EntityDamageByEntityEvent e){
		
		if(e.getEntity().getType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.PLAYER)){
			if(tocancel.contains((Player)e.getEntity())){
			e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
	
		Player p = e.getPlayer();
		if(p.hasPermission("alpaq.adminauth")){
			tocancel.add(p);
			ChatUtil.sendTitle(p, 11, 33, 11, PinManager.getTitlePorsba(), PinManager.getSubtitleProsba());
			PinManager.proby.put(p, 1);
			loc.put(p, p.getLocation());
		
		}
		
	}
	@EventHandler
	public void cmd(PlayerCommandPreprocessEvent e){
		if(tocancel.contains(e.getPlayer())){
			e.setCancelled(true);
		
		}
	}
	public void AsyncPlayerKick(Player p){
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick "+p.getName()+" "+PinManager.getKickMSG().replace('&', '§'));
	}
	@EventHandler
	public void chat(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		if(tocancel.contains(p)){
			e.setCancelled(true);
			
			String msg = e.getMessage();
			String pin = PinManager.getPin();
			if(!msg.equalsIgnoreCase(pin)){
				if(PinManager.getAttemps(p)>=PinManager.getMaxAttemps()){
					this.AsyncPlayerKick(p);
					return;
				}
				PinManager.addAttemp(p);
				ChatUtil.sendTitle(p, 11, 33, 11, PinManager.getTitleWrong(), PinManager.getSubtitleWrong().replace("{PROBY}", String.valueOf((PinManager.getMaxAttemps()+1)-PinManager.getAttemps(p))));
				return;
				
			}else{
				tocancel.remove(p);
				PinManager.proby.remove(p);
				ChatUtil.sendTitle(p, 11, 33, 11, PinManager.getTitleOK(), PinManager.getSubtitleOK());
			}
		}
		
	}
	
}
