package com.hackhalo2.creative;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.hackhalo2.creative.pixlable.CustomPixlable;
import com.hackhalo2.creative.pixlable.PixlableBlock;
import com.hackhalo2.creative.pixlable.PixlableStairs;

public class Pixl extends JavaPlugin {
    public PluginDescriptionFile pdf;
    public String version = null;
    public HashMap<Player, Boolean> toggled = new HashMap<Player, Boolean>();
    public HashMap<Player, Integer> set = new HashMap<Player, Integer>();
    public HashMap<Player, Boolean> breakMode = new HashMap<Player, Boolean>();
    public HashMap<Player, Boolean> shatterMode = new HashMap<Player, Boolean>();

    private final PixlPlayer pListener = new PixlPlayer(this);
    private final PixlBlock bListener = new PixlBlock(this);

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

    //The Custom blocks plugins define
    public static ArrayList<PixlableBlock> pixlableBlocks;
    public static ArrayList<PixlableStairs> pixlableStairs;
    public static ArrayList<CustomPixlable> customBlocks;

    public void onEnable() {

	pdf = this.getDescription();
	version = pdf.getVersion();
	//Set up the Plugin Manager
	PluginManager pm = getServer().getPluginManager();
	//Register Listeners
	pm.registerEvents(this.pListener, this);
	pm.registerEvents(this.pListener, this);
	pm.registerEvents(this.pListener, this);
	pm.registerEvents(this.bListener,  this);
	System.out.println("[Pixl] "+version+" Loaded (R5) ");

	getCommand("pixl").setExecutor(new PixlCommand(this));
    }

    public void onDisable() {
	toggled = null;
	set = null;
	breakMode = null;
	shatterMode = null;
    }

    public void setToggle(final Player p, final boolean v) { toggled.put(p, v); }
    public boolean isToggled(final Player p) {
	if(toggled.containsKey(p)) { return toggled.get(p); }
	else { return false; }
    }

    public void setBreak(final Player p, final boolean v) { breakMode.put(p, v); }
    public boolean breakMode(final Player p) {
	if(breakMode.containsKey(p)) { return breakMode.get(p); }
	else { return false; }
    }

    public void setShatter(final Player p, final boolean v) { shatterMode.put(p, v); }
    public boolean shatterMode(final Player p) {
	if(shatterMode.containsKey(p)) { return shatterMode.get(p); }
	else { return false; }
    }

    public void removeValue(final Player p) { set.remove(p); }
    public void setValue(final Player p, final int v) { set.put(p, v); }
    public Integer isSet(final Player p) {
	if(set.containsKey(p)) { return set.get(p); }
	else { return null; }
    }

    public boolean checkPermissions(Player p, String s, boolean f) {
	if(isToggled(p) || breakMode(p) || shatterMode(p) || f) { //check to see if player is toggled or forced
	    return p.hasPermission(s);
	}
	return false;
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
	return e.isCancelled();
    }
}