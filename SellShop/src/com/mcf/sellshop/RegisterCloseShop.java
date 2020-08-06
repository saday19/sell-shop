package com.mcf.sellshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RegisterCloseShop implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		
		if(e.getInventory().getName().equals("§a§lSell Shop")) {
			Player player = (Player) e.getPlayer();
			Inventory inv = player.getInventory();
			for(int i = 0; i < 45; i++) {
				ItemStack item = e.getInventory().getItem(i);
				if(item != null) {
					inv.addItem(item);
				}
			}
			
			if(SellShop.players.contains(player)) {
				SellShop.players.remove(player);
			}
			
		}
		
	}
	
}
