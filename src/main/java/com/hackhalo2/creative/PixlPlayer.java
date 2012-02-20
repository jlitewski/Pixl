package com.hackhalo2.creative;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PixlPlayer implements Listener {
    public class helper {
	private final int[] supported = new int[] { 2, 3, 4, 5, 6, 17, 18, 19, 20, 31, 33, 35, 37, 38, 39, 40, 43, 44, 48, 53, 67, 70, 72, 78, 85, 86, 91, 98, 99, 100, 108, 102, 109, 114, 118}; //Supported Block ID's
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


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
	if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    if(e.getPlayer().getItemInHand().getType() == Material.AIR) { //make sure the item in hand is air
		if(plugin.checkPermissions(e.getPlayer(), "pixl.toggle", false) && plugin.isToggled(e.getPlayer()) && a.Block(e.getClickedBlock())) {
		    pixlArt(e.getClickedBlock(), e.getPlayer());
		}
	    }
	}
    }

    @EventHandler
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
	    } else if(a.Type(b) == Material.WOOD || a.Type(b) == Material.FENCE) { //Turn Planks into fences and back again!
		if(a.Type(b) == Material.WOOD) { b.setType(Material.FENCE); }
		else if(a.Type(b) == Material.FENCE) { b.setType(Material.WOOD); }
	    } else if(a.Type(b) == Material.COBBLESTONE || a.Type(b) == Material.MOSSY_COBBLESTONE) { //Turn Cobble into mossy and back again!
		if(a.Type(b) == Material.COBBLESTONE) { b.setType(Material.MOSSY_COBBLESTONE); }
		else if(a.Type(b) == Material.MOSSY_COBBLESTONE) { b.setType(Material.COBBLESTONE); }
	    } else if(a.Type(b) == Material.HUGE_MUSHROOM_1 || a.Type(b) == Material.HUGE_MUSHROOM_2) {
		relay.rotateData(b, 10);
	    } else if(a.Type(b) == Material.SNOW) {
		relay.rotateData(b, 7);
	    } else if(a.Type(b) == Material.CAULDRON) {
		relay.rotateData(b, 3);
	    } else if(a.Type(b) == Material.PUMPKIN || a.Type(b) == Material.JACK_O_LANTERN) { //Turn pumpkins into Jack O Lanterns and back again!
		if(a.Type(b) == Material.PUMPKIN) { b.setType(Material.JACK_O_LANTERN); }
		else if(a.Type(b) == Material.JACK_O_LANTERN) { b.setType(Material.PUMPKIN); }
	    } else if(a.Type(b) == Material.STONE_PLATE || a.Type(b) == Material.WOOD_PLATE) { //Change Pressure plate types
		if(a.Type(b) == Material.STONE_PLATE ) { b.setType(Material.WOOD_PLATE); }
		else if(a.Type(b) == Material.WOOD_PLATE) { b.setType(Material.STONE_PLATE ); }
	    } else if(a.Type(b) == Material.GLASS || a.Type(b) == Material.THIN_GLASS) { //Change Glass to panes and back
		if(a.Type(b) == Material.GLASS ) { b.setType(Material.THIN_GLASS); }
		else if(a.Type(b) == Material.THIN_GLASS) { b.setType(Material.GLASS); }
	    } else if(a.Type(b) == Material.DIRT || a.Type(b) == Material.GRASS) { //Change Dirt to Grass and back
		if(a.Type(b) == Material.DIRT ) { b.setType(Material.GRASS); }
		else if(a.Type(b) == Material.GRASS) { b.setType(Material.DIRT); }
	    } else if(a.Type(b) == Material.RED_ROSE || a.Type(b) == Material.YELLOW_FLOWER) { //Change Roses to Flowers and back
		if(a.Type(b) == Material.RED_ROSE ) { b.setType(Material.YELLOW_FLOWER); }
		else if(a.Type(b) == Material.YELLOW_FLOWER) { b.setType(Material.RED_ROSE); }
	    } else if(a.Type(b) == Material.RED_MUSHROOM|| a.Type(b) == Material.BROWN_MUSHROOM) { //Change Red Shrooms to Brown Shrooms and back
		if(a.Type(b) == Material.RED_MUSHROOM ) { b.setType(Material.BROWN_MUSHROOM); }
		else if(a.Type(b) == Material.BROWN_MUSHROOM) { b.setType(Material.RED_MUSHROOM); }

	    }
	    plugin.logBlockPlace(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
	}
    }
}