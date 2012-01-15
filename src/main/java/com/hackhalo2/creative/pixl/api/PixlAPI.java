package com.hackhalo2.creative.pixl.api;

import com.hackhalo2.creative.Pixl;
import com.hackhalo2.creative.pixlable.CustomPixlable;
import com.hackhalo2.creative.pixlable.PixlableBlock;
import com.hackhalo2.creative.pixlable.PixlableStairs;

public class PixlAPI {
    
    public PixlAPI() {}
    
    public void addCustomBlock(PixlableBlock block) {
	if(!Pixl.pixlableBlocks.contains(block)) {
	    Pixl.pixlableBlocks.add(block);
	}
    }
    
    public void removeCustomBlock(PixlableBlock block) {
	if(Pixl.pixlableBlocks.contains(block)) {
	    Pixl.pixlableBlocks.remove(block);
	}
    }
    
    public boolean hasBlock(PixlableBlock block) {
	return Pixl.pixlableBlocks.contains(block);
    }
    
    public void addCustomBlock(PixlableStairs block) {
	if(!Pixl.pixlableStairs.contains(block)) {
	    Pixl.pixlableStairs.add(block);
	}
    }
    
    public void removeCustomBlock(PixlableStairs block) {
	if(Pixl.pixlableStairs.contains(block)) {
	    Pixl.pixlableStairs.remove(block);
	}
    }
    
    public boolean hasBlock(PixlableStairs block) {
	return Pixl.pixlableStairs.contains(block);
    }
    
    public void addCustomBlock(CustomPixlable block) {
	if(!Pixl.customBlocks.contains(block)) {
	    Pixl.customBlocks.add(block);
	}
    }
    
    public void removeCustomBlock(CustomPixlable block) {
	if(Pixl.customBlocks.contains(block)) {
	    Pixl.customBlocks.remove(block);
	}
    }
    
    public boolean hasBlock(CustomPixlable block) {
	return Pixl.customBlocks.contains(block);
    }
}
