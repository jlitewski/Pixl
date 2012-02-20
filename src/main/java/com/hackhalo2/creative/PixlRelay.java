package com.hackhalo2.creative;

import org.bukkit.block.Block;

import com.hackhalo2.creative.pixlable.CustomPixlable;
import com.hackhalo2.creative.pixlable.PixlableBlock;
import com.hackhalo2.creative.pixlable.PixlableStairs;

public class PixlRelay {

    private final Pixl plugin;

    public PixlRelay(Pixl p) {
	this.plugin = p;
    }

    public void rotateData(Block block, int max) {
	rotateData(block, max, null);
    }

    public void rotateData(Block block, int max, Integer hardset) {
	if(hardset == null || hardset < 0) {
	    if(block.getData() < (byte)(max)) { block.setData((byte)(block.getData()+1)); } //add one
	    else { block.setData((byte)(0)); } //reset it
	} else {
	    block.setData(hardset.byteValue());
	}
    }

    public void rotateData(PixlableBlock block) {
	rotateData(block, null);
    }

    public void rotateData(PixlableBlock block, Integer hardset) {
	if(hardset == null || hardset < 0) {

	} else {

	}
    }

    public void rotateData(CustomPixlable block) {

    }

    public void rotateStairs(Block block) {
	if(block.getData() == (byte)(0)) { //Ascending south
	    block.setData((byte)(2)); //Ascending west
	} else if(block.getData() == (byte)(1)) { //Ascending north
	    block.setData((byte)(3)); //Ascending east
	} else if(block.getData() == (byte)(2)) { //Ascending west
	    block.setData((byte)(1)); //Ascending north
	} else if(block.getData() == (byte)(3)) { //Ascending east
	    block.setData((byte)(0)); //Ascending south
	}
    }

    public void rotateStairs(PixlableStairs block) {

    }
}