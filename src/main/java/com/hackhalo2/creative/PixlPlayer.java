package com.hackhalo2.creative;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class PixlPlayer {

    private static final int playerYAMLFileVersion = 1; //The current file version (for upgrading later)
    private boolean update = false;
    File playerData; //The player.yml file
    String playerName; //For Reference later? I dunno
    int setBlockId; //The block ID for the Set command. -1 is none
    int setData; //The data to the set block. -1 is none
    boolean modeEnabled; //True for yes, False for no
    boolean dropItems; //True for Break, False for Shatter
    //TODO: Add the rest of the 

    public PixlPlayer(String playerName, File dataFolder) {
	this.playerData = new File(dataFolder, playerName+".yml");
	this.playerName = playerName; 
	this.load();
    }

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
	    //Force Defaults
	    this.setBlockId = -1;
	    this.setData = -1;
	    this.modeEnabled = false;
	    this.dropItems = true;
	    return;
	} else if(currentFileVersion < playerYAMLFileVersion) { //This shouldn't happen with Version 1, but meh
	    Bukkit.getLogger().info("[Pixl] "+this.playerName+".yml has be marked to be upgraded. Trying to pull values...");
	    this.update = true;
	} 

	this.setBlockId = config.getInt("pixl.set.blockID", -1);
	this.setData = config.getInt("pixl.set.blockData", -1);
	this.modeEnabled = config.getBoolean("pixl.mode", false);
	this.dropItems = config.getBoolean("pixl.mode.dropItems", true);

	return;
    }

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

}
