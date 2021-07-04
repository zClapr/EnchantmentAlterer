package com.smoothley.enchalterer;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class listener implements Listener {
    Plugin pl = main.plugin();
    FileConfiguration c = pl.getConfig();
    ConfigurationSection cs = c.getConfigurationSection("lores");

    @EventHandler
    public void onEnchant(EnchantItemEvent e){
        ItemStack i = e.getItem();
        ArrayList<String> illegalEnchs = new ArrayList<>();
        Map<Enchantment, Integer> x = e.getEnchantsToAdd();
        ArrayList<String> stringArrayList = new ArrayList<>();

        if (i.getItemMeta() != null && i.getItemMeta().getLore() != null) {
            for (String key : cs.getKeys(false)) {
                String str = i.getItemMeta().getLore().toString();
                if (str.contains(key)) {
                    for (String ench : cs.getStringList(key)) {
                        illegalEnchs.add(ench);
                    }
                }
            }
        }

        for (Enchantment enchantment : x.keySet()){
            stringArrayList.add(enchantment.toString());
        }
        for (String ench : illegalEnchs) {
            if (stringArrayList.toString().contains(ench)) {
                e.setExpLevelCost(99999);
            }
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent e){
        ItemStack i1 = e.getInventory().getItem(0);
        ItemStack i2 = e.getInventory().getItem(1);
        if (i1 != null && i2 != null) {
            ItemStack item = e.getResult();
            ArrayList<String> illegalEnchs = new ArrayList<>();

            for (String key : cs.getKeys(false)) {
                if (i1.getItemMeta() != null) {
                    if (i1.getItemMeta().getLore() != null) {
                        String str = i1.getItemMeta().getLore().toString();
                        if (str.contains(key)) {
                            for (String x: cs.getStringList(key)){
                                illegalEnchs.add(x);
                            }
                        }
                    }
                }
                if (i2.getItemMeta() != null) {
                    if (i2.getItemMeta().getLore() != null) {
                        String str = i2.getItemMeta().getLore().toString();
                        if (str.contains(key)) {
                            for (String x: cs.getStringList(key)){
                                illegalEnchs.add(x);
                            }
                        }
                    }
                }
            }

            if (item != null && item.getItemMeta() != null) {
                if (item.getItemMeta().hasEnchants()) {
                    Map<Enchantment, Integer> x = e.getResult().getItemMeta().getEnchants();
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    for (Enchantment enchantment : x.keySet()){
                        stringArrayList.add(enchantment.toString());
                    }
                    for (String ench : illegalEnchs) {
                        if (stringArrayList.toString().contains(ench)) {
                            e.setResult(null);
                        }
                    }
                }
            }
        }
    }
}
