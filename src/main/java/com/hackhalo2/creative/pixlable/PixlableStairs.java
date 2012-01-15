package com.hackhalo2.creative.pixlable;

import org.bukkit.block.Block;

public class PixlableStairs implements Pixlable {
    
    private Integer id;
    private Byte data;
    
    public PixlableStairs(int id, byte maxData) {
	this.id = id;
	this.data = maxData;
    }
    
    public boolean isPixlable(int id) {
	if(id == this.id) return true;
	
	return false;
    }

    public void increment(Block source) {
	if(source.getData() == (byte)(0)) { //Ascending south
	    source.setData((byte)(2)); //Ascending west
	} else if(source.getData() == (byte)(1)) { //Ascending north
	    source.setData((byte)(3)); //Ascending east
	} else if(source.getData() == (byte)(2)) { //Ascending west
	    source.setData((byte)(1)); //Ascending north
	} else if(source.getData() == (byte)(3)) { //Ascending east
	    source.setData((byte)(0)); //Ascending south
	}
    }

    public void hardset(Block source, byte data) {
	//Not needed for stairs
    }

    public void destroy() {
	this.id = null;
	this.data = null;
    }


}
