package com.mcf.sellshop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class SellShopHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player player = (Player) sender; 
			
			if(args.length > 0) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("additem")) {
						Double price = Double.parseDouble(args[1]);
						if(player.getInventory().getItemInMainHand() != null) {
							ItemStack item = player.getInventory().getItemInMainHand();
							item.setAmount(1);
							String path = item.getType().toString();
							if(item.hasItemMeta()) {
								if(item.getItemMeta().hasDisplayName()) {
									path = ChatColor.stripColor(item.getItemMeta().getDisplayName());
									path = path.replaceAll(" ", "");
								}
							}
							EnableSellShop.item_prices.put(item, price);
							EnableSellShop.items.add(item);
							EnableSellShop.config.set(path + ".item", item);
							EnableSellShop.config.set(path + ".price", price);
							EnableSellShop.save();
							
							sender.sendMessage("§a" + path + " now sells for $" + String.format("%,.2f", price) + ".");
							
						} else {
							player.sendMessage(ChatColor.RED + "You must have an item in your hand to do this!");
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect Usage! Try: " + ChatColor.GRAY + "/sellshop additem <price>");
				}
			} else {
				if(player.hasPermission("sellshop.open")) {
					new SellShop().open(player);
				} else {
					player.sendMessage(ChatColor.RED + "Error! You do not have permission to use this command!");
				}
			}
			
		}
		
		return false;
	}

}
