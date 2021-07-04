package com.smoothley.enchalterer;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class commands implements CommandExecutor {
    Plugin pl = main.plugin();
    ConfigurationSection cs = pl.getConfig().getConfigurationSection("lores");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("enchantalter") || command.getName().equalsIgnoreCase("ea")){
            if (strings[0].equalsIgnoreCase("reload") && commandSender.isOp()){
                pl.reloadConfig();
                commandSender.sendMessage("LISTED LORES: ");
                for (String key : cs.getKeys(false)){
                    commandSender.sendMessage(key);
                }

                ArrayList<String> workables = new ArrayList<>();
                ArrayList<String> nonWorkables = new ArrayList<>();

                for (String key : cs.getKeys(false)){
                    for (String x : cs.getStringList(key)) {
                        if (Enchantment.getByKey(NamespacedKey.minecraft(x.toLowerCase())) != null) {
                            workables.add(x);
                        } else {
                            nonWorkables.add(x);
                        }
                    }
                }

                commandSender.sendMessage("ACCEPTED ENCHANTMENTS: ");
                for (String string : workables){
                    commandSender.sendMessage(string);
                }

                commandSender.sendMessage("DENIED ENCHANTMENTS: ");
                for (String string : nonWorkables){
                    commandSender.sendMessage(string);
                }
                return true;
            }
            return true;
        } else { commandSender.sendMessage("/ea reload");
            return true;
        }
    }
}
