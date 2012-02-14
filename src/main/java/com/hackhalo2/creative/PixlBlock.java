package com.hackhalo2.creative;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;
@Deprecated
//TODO: Find a new use for this, or delete it
public class PixlBlock extends BlockListener {
    private final Pixl plugin;

    public PixlBlock(final Pixl p) { this.plugin = p; }

    @Override
    public void onBlockDamage(BlockDamageEvent e) {
	Player player = e.getPlayer();
	Block block = e.getBlock();
	
	/** PixlBreak and PixlShatter (Fine Mode)
	 * Using PixlBreak or PixlShatter with a pickaxe will destroy 1 block at a time
	 */
	if (plugin.isPickaxe(player.getItemInHand().getType()) && player.hasPermission("pixl.break")) {
	    //Prechecks for this command
	    if (block.getType() == Material.BEDROCK || block.getType() == Material.MOB_SPAWNER) {
		// Can't break bedrock with PixlBreak.
		return;
	    } else if (!plugin.breakMode(player) && !plugin.shatterMode(player)) {
		// PixlBreak and PixlShatter isn't active for this player.
		return;
	    } else if (plugin.logBlockBreak(e.getBlock(), e.getPlayer())) {
		// This player can't break the block here
		return;
	    }
	    
	    Material type = block.getType();
	    boolean limited_ok = false;
	    for (Material available : plugin.limitedUserMaterials) {
		if (available.equals(type)) {
		    limited_ok = true;
		    break;
		}
	    }

	    // Check for special case blocks
	    for (Material available : plugin.specialCaseMaterials) {
		if (available.equals(type)) {
		    if(plugin.shatterMode(player)) {
			e.getBlock().setType(Material.AIR);
		    } else {
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(available));
			e.getBlock().setType(Material.AIR);
		    }
		    return;
		}
	    }

	    // If the player has appropriate permissions, instabreak the block.
	    if (limited_ok || !player.hasPermission("pixl.break.limited") ) {
		if(plugin.shatterMode(player)) {
		    e.getBlock().setType(Material.AIR);
		} else {
		    e.setInstaBreak(true);
		}
	    }
	/** PixlBreak and PixlShatter (Coarse Mode)
	 * Using PixlBreak or PixlShatter with a shovel will break a large area of that block
	 */
	} else if(plugin.isSpade(player.getItemInHand().getType()) && player.hasPermission("pixl.break")) {
	    /*Prechecks for this command
	    if (block.getType() == Material.BEDROCK || block.getType() == Material.MOB_SPAWNER) {
		// Can't break bedrock with PixlBreak.
		return;
	    } else if (!plugin.breakMode(player) && !plugin.shatterMode(player)) {
		// PixlBreak and PixlShatter isn't active for this player.
		return;
	    }
	    Block source = e.getBlock();
	    
	    if(!player.isSneaking()) { //If the player is not sneaking, break a 3x3x3 area
		
	    } else { //break a 5x5x1 area
		
	    } */
	}
    }
}
