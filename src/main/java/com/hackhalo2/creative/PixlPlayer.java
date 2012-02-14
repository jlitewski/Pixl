package com.hackhalo2.creative;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PixlPlayer {

    private static final int playerYAMLFileVersion = 1; //The current file version (for upgrading later)
    private boolean update = false;
    File playerData; //The player.yml file
    String playerName; //For Reference later? I dunno
    int setBlockId; //The block ID for the Set command. -1 is none
    int setData; //The data to the set block. -1 is none
    boolean modeEnabled; //True for yes, False for no
    boolean dropItems; //True for Break, False for Shatter
    //TODO: Add the rest of the stuff needed at some point

    public PixlPlayer(String playerName, File dataFolder) {
	this.playerData = new File(dataFolder, playerName+".yml");
	this.playerName = playerName; 
	this.load();
    }
    
    /*
     * Load up the player from disk.
     * This is very limited currently, I'm hoping to add more stuff to it as I flesh out new
     * features to Pixl (maybe even store PixlSP stuff too?)
     */
    private void load() {
	YamlConfiguration config = new YamlConfiguration();

	//Check to see if the file exists before trying to load it
	if(this.playerData != null && this.playerData.exists()) {
	    try {
		config.load(playerData);
	    } catch (Exception e) {
		Bukkit.getLogger().warning("[Pixl] Error loading "+this.playerName+".yml! Using default values...");
	    }
	}

	//Load the file stuffs
	int currentFileVersion = config.getInt("fileVersion", playerYAMLFileVersion);
	if(currentFileVersion > playerYAMLFileVersion) {
	    Bukkit.getLogger().warning("[Pixl] "+this.playerName+".yml is newer then version "+playerYAMLFileVersion+"! (File version: "+currentFileVersion+")");
	    Bukkit.getLogger().warning("[Pixl] Defaulting file for sanity...");
	    
	    //Force defaults for sanity sake, because I'm not going to support versions I haven't created yet >:|
	    this.setBlockId = -1;
	    this.setData = -1;
	    this.modeEnabled = false;
	    this.dropItems = true;
	    
	    return;
	} else if(currentFileVersion < playerYAMLFileVersion) { //This shouldn't happen with Version 1, but meh
	    Bukkit.getLogger().info("[Pixl] "+this.playerName+".yml has be marked to be upgraded. Trying to pull values...");
	    this.update = true;
	}
	
	//If it get's here, then load up them datas! :D
	this.setBlockId = config.getInt("pixl.set.blockID", -1);
	this.setData = config.getInt("pixl.set.blockData", -1);
	this.modeEnabled = config.getBoolean("pixl.mode", false);
	this.dropItems = config.getBoolean("pixl.mode.dropItems", true);

	return;
    }
    
    /*
     * Save the Player to the disk under
     * {$BUKKITDIR}/{$PLUGINDIR}/Pixl/playerdata/{$PLAYERNAME}.yml
     */
    public void save() {
	YamlConfiguration config = new YamlConfiguration();
	//set and save the file
	config.set("pixl.set.blockID", this.setBlockId);
	config.set("pixl.set.blockData", this.setData);
	config.set("pixl.mode", this.modeEnabled);
	config.set("pixl.mode.dropItems", this.dropItems);
	try {
	    config.save(playerData);
	} catch (Exception e) {
	    Bukkit.getLogger().warning("[Pixl] Error saving "+this.playerName+".yml! Aborting save!");
	    Bukkit.getLogger().info(e.getCause().getMessage());
	}
    }
    
    /*
     * Generate the Minecraft Server Player Reference
     * Since this object only holds the name of the Player, and not the
     * Minecraft Player Object, it kind of makes it hard to do anything.
     * This Method solves that issue :P
     */
    public Player getPlayerReference() { //XXX: Should I check for null? Is it even possible?
	return Bukkit.getPlayerExact(this.playerName);
    }
    
    public boolean getBreakMode() { //True = break, False = Shatter
	return this.dropItems;
    }
    
    public void setBreakMode(boolean mode) {
	this.dropItems = mode;
    }
    
    public boolean isBreakModeEnabled() { //True for yes, False for no
	return this.modeEnabled;
    }
    
    public void setBreakModeEnabled(boolean enabled) {
	this.modeEnabled = enabled;
    }
    
    public int getBlockIDSet() {
	return this.setBlockId;
    }
    
    public byte getBlockDataSet() {
	return (byte)(this.setData);
    }
    
    public void quickSet(Block block) {
	this.setBlockIDSet(block.getTypeId());
	this.setBlockDataSet(block.getData());
    }
    
    public void setBlockIDSet(Block block) {
	this.setBlockIDSet(block.getTypeId());
    }
    
    public void setBlockIDSet(int ID) {
	this.setBlockId = ID;
    }
    
    public void setBlockDataSet(byte data) {
	this.setData = data;
    }

}
