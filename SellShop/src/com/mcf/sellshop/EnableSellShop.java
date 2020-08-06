package com.mcf.sellshop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class EnableSellShop extends JavaPlugin {
	
	public static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public static HashMap<ItemStack, Double> item_prices = new HashMap<ItemStack, Double>();
	
	public static File f = new File("plugins/SellShop/prices.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(f);
	
	public static Economy econ = null;
	
	public void onEnable() {
		
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
		
		loadItems(); //get items from config
		getCommand("sell").setExecutor(new SellShopHandler()); //set item in config
		getServer().getPluginManager().registerEvents(new RegisterSell(), this);//inv click - sell
		getServer().getPluginManager().registerEvents(new RegisterCloseShop(), this); //remove from pickup deny list and give items left on close
		getServer().getPluginManager().registerEvents(new DenyPickup(), this); // cancel item pickup
	}

	public void onDisable() {
		save();
	}
	
	private void loadItems() {
		if(!f.exists()) {
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(String path : config.getConfigurationSection("").getKeys(false)) {
			
			ItemStack item = config.getItemStack(path + ".item");
			Double price = config.getDouble(path + ".price");
			
			items.add(item);
			item_prices.put(item, price);
		}
	}
	
	public static void save() {
		try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
