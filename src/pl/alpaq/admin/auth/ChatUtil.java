package pl.alpaq.admin.auth;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtil {
	
	public static void sendMessage(Player p , String msg){
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		msg = msg.replace('&', '§');
		msg = msg.replace(">>", "»");
		p.sendMessage(msg);
	}
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
		subtitle = subtitle.replace(">>", "»");
		title = ChatColor.translateAlternateColorCodes('&', title);
		title = title.replace(">>", "»");
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        if (subtitle != null) {
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket(packetPlayOutSubTitle);
        }
        if (title != null) {
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }
    }


    public static void sendActionBar(Player p, String msg) {
    	msg = ChatColor.translateAlternateColorCodes('&', msg);
    	msg = msg.replace(">>", "»");
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
    }
    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null) footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try
        {
          Field field = headerPacket.getClass().getDeclaredField("b");
          field.setAccessible(true);
          field.set(headerPacket, tabFoot);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          connection.sendPacket(headerPacket);
        }
      }

}
