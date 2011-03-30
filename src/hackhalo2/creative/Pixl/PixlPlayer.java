package hackhalo2.creative.Pixl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PixlPlayer extends PlayerListener {
	private final Object _lock = new Object();
	private final Pixl plugin;
	
	public PixlPlayer(Pixl p) { this.plugin = p; }
	
	public class detect {
		private final int[] supported = { 17, 18, 19, 35, 43, 44, 53, 67 }; //Supported Block ID's
		public detect() { }
		
		public boolean Block(Block b) {
		    for (int i : supported) {
			if(ID(b) == supported[i]) { return true; }
		    }
		    return false;
		}
		
		public Material Type(Block b) { return b.getType(); }
		
		public int ID(Block b) { return b.getTypeId(); }
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent e) {
	    detect a = new detect();
	    if(e.getAction().toString().equals("RIGHT_CLICK_BLOCK") && a.Block(e.getClickedBlock())) {
		synchronized(_lock) {
		    if(plugin.checkPermissions(e.getPlayer(), "pixl.use", false) && e.getPlayer().getItemInHand().getType() == Material.AIR)
		    	{ pixlArt(e.getClickedBlock(), e.getPlayer()); }
		}
	    }
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent e) {
		//TODO load user settings upon login
		//TODO message user that Pixl is enabled/disabled on login
		if(plugin.isToggled(e.getPlayer())) 
			{ plugin.setToggle(e.getPlayer(), false); }
	}
	
	public void pixlArt(Block b, Player p) {
		detect a = new detect();
		//Very hackish detection
		BlockBreakEvent event1 = new BlockBreakEvent(b, p);
		plugin.getServer().getPluginManager().callEvent(event1);
		if(event1.isCancelled()) {
			return;
		} else {
			Block previousBlock = b;
			if (a.Type(b) == Material.LOG || a.Type(b) == Material.LEAVES) {
				if(b.getData() < (byte)(2)) { b.setData((byte)(b.getData()+1)); } //add one
				else { b.setData((byte)(0)); } //reset it
			} else if (a.Type(b) == Material.WOOL) {
				if(plugin.isSet(p) == null) {
					if(b.getData() < (byte)(15)) { b.setData((byte)(b.getData()+1)); } //add one
					else { b.setData((byte)(0)); } //reset it
				} else { b.setData(plugin.isSet(p).byteValue()); } //should set the byte value...
			} else if (a.Type(b) == Material.STEP || a.Type(b) == Material.DOUBLE_STEP) {
				if(b.getData() < (byte)(3)) { b.setData((byte)(b.getData()+1)); } //add one
				else { b.setData((byte)(0)); } //reset it
			} else if (a.Type(b) == Material.WOOD_STAIRS || a.Type(b) == Material.COBBLESTONE_STAIRS) {
				if(b.getData() == (byte)(0)) { //Ascending south
					b.setData((byte)(2)); //Ascending west
				} else if(b.getData() == (byte)(1)) { //Ascending north
					b.setData((byte)(3)); //Ascending east
				} else if(b.getData() == (byte)(2)) { //Ascending west
					b.setData((byte)(1)); //Ascending north
				} else if(b.getData() == (byte)(3)) { //Ascending east
					b.setData((byte)(0)); //Ascending south
				}
			} else if (a.Type(b) == Material.SPONGE) { //Turn Sponges into fences!
				b.setType(Material.FENCE);
			}
			BlockPlaceEvent event2 = new BlockPlaceEvent(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
			plugin.getServer().getPluginManager().callEvent(event2);
		}
	}
}
