package com.hackhalo2.creative;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@Deprecated
public class PixlBlock extends BlockListener {
        private final Object _lock = new Object();
        private final Pixl plugin;
        @Deprecated
        public PixlBlock(final Pixl p) { this.plugin = p; }

        @Deprecated
        public void onBlockRightClick(Player p, Block b) {
                synchronized(_lock) {
                        if(plugin.checkPermissions(p, "pixl.use", false)) {
                                if(p.getItemInHand().getType() == Material.AIR)
                                        { pixlArt(b, p); }
                        }
                }
        }
        @Deprecated
        public boolean detect(Block b) {

                if(detectId(b) == Material.LOG.getId() || detectId(b) == Material.WOOL.getId()
                                || detectId(b) == Material.STEP.getId() || detectId(b) == Material.DOUBLE_STEP.getId()
                                || detectId(b) == Material.LEAVES.getId() || detectId(b) == Material.COBBLESTONE_STAIRS.getId()
                                || detectId(b) == Material.WOOD_STAIRS.getId())
                        { return true; }

                return false;
        }
        @Deprecated
        public Material detectType(Block b) { return b.getType(); }
        @Deprecated
        public int detectId(Block b) { return b.getTypeId(); }
        @Deprecated
        public void pixlArt(Block b, Player p) {
                //Very hackish detection
                BlockBreakEvent event1 = new BlockBreakEvent(b, p);
                plugin.getServer().getPluginManager().callEvent(event1);
                if(event1.isCancelled()) {
                        return;
                } else {
                        Block previousBlock = b;
                        if (detectType(b) == Material.LOG || detectType(b) == Material.LEAVES) {
                                if(b.getData() < (byte)(2)) { b.setData((byte)(b.getData()+1)); } //add one
                                else { b.setData((byte)(0)); } //reset it
                        } else if (detectType(b) == Material.WOOL) {
                                if(plugin.isSet(p) == null) {
                                        if(b.getData() < (byte)(15)) { b.setData((byte)(b.getData()+1)); } //add one
                                        else { b.setData((byte)(0)); } //reset it
                                } else { b.setData(plugin.isSet(p).byteValue()); } //should set the byte value...
                        } else if (detectType(b) == Material.STEP || detectType(b) == Material.DOUBLE_STEP) {
                                if(b.getData() < (byte)(3)) { b.setData((byte)(b.getData()+1)); } //add one
                                else { b.setData((byte)(0)); } //reset it
                        } else if (detectType(b) == Material.WOOD_STAIRS || detectType(b) == Material.COBBLESTONE_STAIRS) {
                                if(b.getData() == (byte)(0)) { //Ascending south
                                        b.setData((byte)(2)); //Ascending west
                                } else if(b.getData() == (byte)(1)) { //Ascending north
                                        b.setData((byte)(3)); //Ascending east
                                } else if(b.getData() == (byte)(2)) { //Ascending west
                                        b.setData((byte)(1)); //Ascending north
                                } else if(b.getData() == (byte)(3)) { //Ascending east
                                        b.setData((byte)(0)); //Ascending south
                                }
                        } else if (detectType(b) == Material.SPONGE) { //Turn Sponges into fences!
                                b.setType(Material.FENCE);
                        }
                        BlockPlaceEvent event2 = new BlockPlaceEvent(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
                        plugin.getServer().getPluginManager().callEvent(event2);
                }
        }


}
