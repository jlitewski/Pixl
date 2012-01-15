package com.hackhalo2.creative.pixlable;

import org.bukkit.block.Block;

public interface Pixlable {
    
    public boolean isPixlable(int id);
    
    public void increment(Block source);
    
    public void hardset(Block source, byte data);
    
    public void destroy();
    
}
