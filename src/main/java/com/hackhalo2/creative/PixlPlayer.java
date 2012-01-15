package com.hackhalo2.creative;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PixlPlayer extends PlayerListener {
    public class helper {
	private final int[] supported = new int[] { 4, 6, 17, 18, 19, 31, 33, 35, 43, 44, 48, 53, 67, 78, 85, 98, 99, 100, 108, 109, 114, 118}; //Supported Block ID's
	public helper() { }

	public boolean Block(Block b) {
	    for (int i=0; i < supported.length; i++) {
		if(ID(b) == supported[i]) { return true; }
	    }
	    return false;
	}

	public Material Type(Block b) { return b.getType(); }

	public int ID(Block b) { return b.getTypeId(); }
    }

    private final Pixl plugin;
    private final PixlRelay relay;
    private final helper a = new helper();

    public PixlPlayer(Pixl p) {
	this.plugin = p;
	this.relay = new PixlRelay(p);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {
	if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    if(e.getPlayer().getItemInHand().getType() == Material.AIR) { //make sure the item in hand is air
		if(plugin.checkPermissions(e.getPlayer(), "pixl.toggle", false) && plugin.isToggled(e.getPlayer()) && a.Block(e.getClickedBlock())) {
		    pixlArt(e.getClickedBlock(), e.getPlayer());
		}
	    }
	}
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent e) {
	//TODO load user settings upon login
	//TODO message user that Pixl is enabled/disabled on login
	if(plugin.isToggled(e.getPlayer())) { plugin.setToggle(e.getPlayer(), false); }
	if(plugin.breakMode(e.getPlayer())) { plugin.setBreak(e.getPlayer(), false); }
    }

    public void pixlArt(Block b, Player p) {
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
    }
}
