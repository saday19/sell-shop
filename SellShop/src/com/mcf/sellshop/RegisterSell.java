package com.mcf.sellshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mcf.ItemBuilder;

public class RegisterSell implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', "&a&lSell Shop"))) {
			e.setCancelled(true);
			
			if(e.getClick().isShiftClick()) {
				e.setCancelled(true);
				return;
			}

			ItemStack item = e.getCurrentItem();
			
			if(item == null) return;
			
			Player player = (Player) e.getWhoClicked();
			Inventory currentInventory = e.getInventory();
			Inventory playerInventory = player.getInventory();
			
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a&lSell Items"))) {
						if (item.getItemMeta().hasLore()) {
							String valueString = ChatColor.stripColor(item.getItemMeta().getLore().get(1));
							valueString = valueString.replace("Worth: $", "");
							valueString = valueString.replace(",", "");
							Double value = Double.valueOf(valueString);
							if(value == 0.0) {
								return;
							}
							// add value to balance
							if(EnableSellShop.econ != null) {
								EnableSellShop.econ.depositPlayer(player.getName(), value);
								player.sendMessage("§aYour items were sold for §2$" + String.format("%,.2f", value) +"§a!");
							}
							for(int i = 0; i < 45; i++) {
								ItemStack itemStack = currentInventory.getItem(i);
								if(itemStack != null) {
									int amt = itemStack.getAmount();
									itemStack.setAmount(1);
									if(EnableSellShop.items.contains(itemStack)) {
										itemStack.setAmount(0);
									} else {
										itemStack.setAmount(amt);
									}
								}
							}
						}
						currentInventory.setItem(49, new ItemBuilder(Material.PAPER).setName("&a&lSell Items").addLoreLine("§7").addLoreLine("§7Worth: §2$0").addLoreLine("§7").addLoreLine("§7Click to sell all items.").toItemStack());
						return;
					}
				}
			}

			ItemStack sellItem = currentInventory.getItem(49);
			String valueString = ChatColor.stripColor(sellItem.getItemMeta().getLore().get(1));
			valueString = valueString.replace("Worth: $", "");
			valueString = valueString.replace(",", "");
			Double value = Double.valueOf(valueString);
			
			int itemAmount = item.getAmount();
			item.setAmount(1);
			
			if (e.getClickedInventory().getHolder() instanceof Player) {
				if(EnableSellShop.items.contains(item)) {
					double price = EnableSellShop.item_prices.get(item);
					item.setAmount(itemAmount);
					double toAdd = price * itemAmount;
					value = value + toAdd;
				}	
				item.setAmount(itemAmount);
				currentInventory.addItem(item);
				playerInventory.removeItem(item);

				ItemStack newSellItem = new ItemBuilder(sellItem).addLoreLine(ChatColor.translateAlternateColorCodes('&', "&7Worth: &2$" + String.format("%,.2f", value)), 1).toItemStack();
				
				currentInventory.setItem(49, newSellItem);
			} else {
				if (e.getSlot() < 45) {
					if(EnableSellShop.items.contains(item)) {
						double price = EnableSellShop.item_prices.get(item);
						item.setAmount(itemAmount);
						double toAdd = price * itemAmount;
						value = value - toAdd;
					}	
					item.setAmount(itemAmount);

					ItemStack newSellItem = new ItemBuilder(sellItem).addLoreLine(ChatColor.translateAlternateColorCodes('&', "&7Worth: &2$" + String.format("%,.2f", value)), 1).toItemStack();
					
					currentInventory.setItem(49, newSellItem);
					currentInventory.removeItem(item);
					playerInventory.addItem(item);
				}
			}

		}
	}

}
