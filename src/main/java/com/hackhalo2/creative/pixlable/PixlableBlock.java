package com.hackhalo2.creative.pixlable;

import org.bukkit.block.Block;

public class PixlableBlock implements Pixlable {
    
    private Integer id;
    private Byte data;
    private Boolean hardsetable;
    
    public PixlableBlock(int id, byte maxData, boolean hardsetable) {
	this.id = id;
	this.data = maxData;
	this.hardsetable = hardsetable;
    }
    
    public boolean isPixlable(int id) {
	if(id == this.id) return true;
	
	return false;
    }

    public void increment(Block source) {
	if(source.getData() < (data)) { source.setData((byte)(source.getData()+1)); } //add one
	else { source.setData((byte)(0)); } //reset it
    }
    
    public void hardset(Block source, byte data) {
	source.setData(data);
    }
    
    public boolean isHardsetable() {
	return hardsetable;
    }

    public void destroy() {
	this.id = null;
	this.data = null;
	this.hardsetable = null;
    }

}
