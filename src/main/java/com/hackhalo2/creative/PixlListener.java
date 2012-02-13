package com.hackhalo2.creative;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PixlListener implements Listener {
    
    private final Pixl plugin;

    public PixlListener(final Pixl p) { this.plugin = p; }
    
    /*
     * The blockDamageHandler handles all the possible block damage things Pixl listens for
     * This Includes PixlBreak and Shatter (Both Fine and Coarse), and future tools that need it
     */
    @EventHandler
    public void blockDamageHandler(BlockDamageEvent e) {
	Player player = e.getPlayer();
	Block block = e.getBlock();
	
	/*
	 * Make sure the player has permission to break the block, and log it if they can
	 * 
	 * Plugins like WorldGuard can prevent block breaking, and plugins like
	 * LogBlock can log the break if it succeeds.
	 * 
	 * All this function does is create a BlockBreakEvent and pass it to the event handler.
	 * Bukkit does all the hard work, and my code stays clean. Win win.
	 */
	if(plugin.logBlockBreak(e.getBlock(), e.getPlayer())) { //true = can break
	    //TODO: Finish this
	}
	
	return;
    }
    
    /*
     * The interactHandler deals with most of the PixlMode stuff, like stairs and
     * wool blocks and so on
     */
    @EventHandler
    public void interactHandler(PlayerInteractEvent e) {
	
    }
    
    /*
     * The joinHandler and partHandler monitors join and leave events
     * so Pixl can load the players YAML configurations into memory (for joining)
     * and save it (for leaving)
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void joinHandler(PlayerJoinEvent e) {
	
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void partHandler(PlayerQuitEvent e) {
	
    }
    
    
    /**
     * The Method that contains the logic for PixlBreak/PixlShatter (Fine Mode)
     * @param player
     * @param block
     * @param shatterMode
     */
    public void pixlBreakFine(Player player, Block block, boolean shatterMode) {
	
    }
    
    /*public void pixlArt(Block b, Player p) {
   	//Very hackish detection
   	BlockBreakEvent event1 = new BlockBreakEvent(b, p);
   	plugin.getServer().getPluginManager().callEvent(event1);
   	if(event1.isCancelled()) {
   	    return;
   	} else {
   	    //switch statement later on?
   	    Block previousBlock = b;
   	    if(a.Type(b) == Material.LOG || a.Type(b) == Material.LEAVES || a.Type(b) == Material.SMOOTH_BRICK ||
   		    a.Type(b) == Material.LONG_GRASS || a.Type(b) == Material.SAPLING) {
   		relay.rotateData(b, 2);
   	    } else if(a.Type(b) == Material.WOOL) {
   		if(plugin.isSet(p) == null) {
   		    relay.rotateData(b, 15);
   		} else { relay.rotateData(b, 15, plugin.isSet(p)); }
   	    } else if(a.Type(b) == Material.STEP || a.Type(b) == Material.DOUBLE_STEP) {
   		relay.rotateData(b, 5);
   	    } else if(a.Type(b) == Material.WOOD_STAIRS || a.Type(b) == Material.COBBLESTONE_STAIRS ||
   		    a.Type(b) == Material.BRICK_STAIRS || a.Type(b) == Material.SMOOTH_STAIRS || a.ID(b) == 114) {
   		relay.rotateStairs(b);
   	    } else if(a.Type(b) == Material.SPONGE || a.Type(b) == Material.FENCE) { //Turn Sponges into fences and back again!
   		if(a.Type(b) == Material.SPONGE) { b.setType(Material.FENCE); }
   		else if(a.Type(b) == Material.FENCE) { b.setType(Material.SPONGE); }
   	    } else if(a.Type(b) == Material.COBBLESTONE || a.Type(b) == Material.MOSSY_COBBLESTONE) { //Turn Cobble into mossy and back again!
   		if(a.Type(b) == Material.COBBLESTONE) { b.setType(Material.MOSSY_COBBLESTONE); }
   		else if(a.Type(b) == Material.MOSSY_COBBLESTONE) { b.setType(Material.COBBLESTONE); }
   	    } else if(a.Type(b) == Material.HUGE_MUSHROOM_1 || a.Type(b) == Material.HUGE_MUSHROOM_2) {
   		relay.rotateData(b, 10);
   	    } else if(a.Type(b) == Material.SNOW) {
   		relay.rotateData(b, 7);
   	    } else if(a.Type(b) == Material.CAULDRON) {
   		relay.rotateData(b, 3);
   	    }
   	    plugin.logBlockPlace(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
   	}
       }*/
}
