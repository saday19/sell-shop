package com.mcf.sellshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class DenyPickup implements Listener {

	@EventHandler
	public void onPickup(EntityPickupItemEvent e) {
		
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(SellShop.players.contains(player)) {
				e.setCancelled(true);
			}
			
		}
		
	}
	
}
