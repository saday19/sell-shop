package com.mcf.sellshop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mcf.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class SellShop {

	private ItemStack pane = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("&7").toItemStack();
	private ItemStack sell = new ItemBuilder(Material.PAPER).setName("&a&lSell Items").addLoreLine("§7").addLoreLine("§7Worth: §2$0").addLoreLine("§7").addLoreLine("§7Click to sell all items.").toItemStack();
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public void open(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&a&lSell Shop"));
		
		for(int i = 0; i < 9; i++) {
			inv.setItem(45 + i, pane);
		}
		
		inv.setItem(49, sell);
		
		player.openInventory(inv);
		players.add(player);
	}
	
}
