package com.hackhalo2.creative;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Pixl extends JavaPlugin {
    public PluginDescriptionFile pdf;
    public String version = null;
    
    //YAML stuff
    File configData = new File(this.getDataFolder(), "playerdata"); //The Player Data Directory

    private final PixlListener listener = new PixlListener(this);
    
    //TODO: Replace ALL the things for the new Configuration stuffs

    // The list of materials that can be PixlBroken by limited PixlBreak users.
    public final Material[] limitedUserMaterials = {
         // Stairs
         Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.SMOOTH_STAIRS, Material.BRICK_STAIRS,

         // Ores
         Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
         Material.DIAMOND_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE,

         // Other hard-to-break stuff.
         Material.OBSIDIAN, Material.WEB, Material.BRICK, Material.FENCE, Material.IRON_FENCE, Material.SMOOTH_BRICK
    };

    // The list of materials that count as pickaxes for PixlBreak and PixlShatter.
    public final Material[] pickaxeMaterials = {
        Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
        Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE
    };
    
    // The list of materials that count as shovels for PixlBreak or PixlShatter.
    public final Material[] shovelMaterials = {
	Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE,
	Material.GOLD_SPADE, Material.DIAMOND_SPADE
    };
    
    //The List of materials that have special case drops
    public final Material[] specialCaseMaterials = {
	Material.GLASS, Material.GLOWSTONE
    };
    
    @Override
    public void onLoad() {
	//Set up the directories Pixl needs
	configData.mkdirs();
	//TODO: Query and load the GIST into memory, defaulting to built in if it's unaccessible.
	//TODO: Overload the Loaded GIST with local stuff, if enabled
    }

    public void onEnable() {

        pdf = this.getDescription();
        version = pdf.getVersion();
        //Set up the Plugin Manager
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this); //new Event system handler
        System.out.println("[Pixl] "+version+" Loaded");

        getCommand("pixl").setExecutor(new PixlCommand(this));
    }

    public void onDisable() {
	
    }

    public boolean isPickaxe(Material material) {
        for (Material pick : pickaxeMaterials) {
            if (pick.equals(material)) {
                return true;
            }
        }

        return false;
    }
    
    public boolean isSpade(Material material) {
	for (Material spade : shovelMaterials) {
	    if (spade.equals(material)) {
		return true;
	    }
	}
	
	return false;
    }
    
    public void logBlockPlace(Block a, BlockState b, Block c, ItemStack d, Player e, boolean f) {
	this.getServer().getPluginManager().callEvent(new BlockPlaceEvent(a, b, c, d, e, f));
    }
    
    public boolean logBlockBreak(Block a, Player b) {
	BlockBreakEvent e = new BlockBreakEvent(a, b);
	this.getServer().getPluginManager().callEvent(e);
	return !(e.isCancelled()); //Make sure to reverse this so true = can break
    }
}
