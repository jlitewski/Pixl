package com.hackhalo2.creative;

//import me.taylorkelly.help.Help;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class PixlServer extends ServerListener {

    private final Pixl plugin;
    //private boolean helpFail = false;
    private boolean permissionsFail = false;

    public PixlServer(final Pixl p) {
	this.plugin = p;
    }

    @Override
    public void onPluginEnable(PluginEnableEvent e) {
	Plugin test = plugin.getServer().getPluginManager().getPlugin("Permissions");
	//Plugin test3 = plugin.getServer().getPluginManager().getPlugin("Help");
	if(plugin.permissionsEnabled == false && test != null) { //Permissions check
	    permissionsFail = false;
	    if(Pixl.Permissions == null) {

		plugin.permissionsType = test.getDescription().getFullName();
		Pixl.Permissions = ((Permissions) test).getHandler();
		System.out.println("[Pixl] Hooking into Permissions");
		plugin.permissionsEnabled = true;
		test = null;
	    } 
	} else if(plugin.permissionsEnabled != true && permissionsFail != true) {
	    permissionsFail = true;
	    System.out.println("[Pixl] Permissions not found, defaulting to ops.txt");
	    plugin.permissionsEnabled = false;
	}

	/*if(plugin.helpEnabled == false && test3 != null) { //Help check
	    helpFail = false;
	    Help helpPlugin = ((Help) test3);
	    helpPlugin.registerCommand("help Pixl", "Display Pixl's help menu", plugin, true, "pixl.use");
	    helpPlugin.registerCommand("pixl toggle", "Toggle Pixl on/off", plugin, "pixl.use");
	    helpPlugin.registerCommand("pixl set [value]", "Set wool color to byte [value]", plugin, "pixl.use");
	    helpPlugin.registerCommand("pixl clear", "Clear the value set by /pixl set", plugin, "pixl.use");
	    helpPlugin.registerCommand("pixl version", "Detailed version info for Pixl", plugin, "pixl.admin");
	    System.out.println("[Pixl] Advanced /help support enabled");
	    plugin.helpEnabled = true;
	} else if(plugin.helpEnabled != true && helpFail != true) {
	    helpFail = true;
	    System.out.println("[Pixl] Help plugin isn't detected, Advanced /help support disabled");
	    plugin.helpEnabled = false;
	}*/
    }

    @Override
    public void onPluginDisable(PluginDisableEvent e) {
	Plugin test = plugin.getServer().getPluginManager().getPlugin("Permissions");
	//Plugin test2 = plugin.getServer().getPluginManager().getPlugin("Help");
	String name = e.getPlugin().getDescription().getName();

	if((plugin.permissionsEnabled != false && Pixl.Permissions != null && test != null) /*|| (plugin.helpEnabled != false && test2 != null)*/) {
	    if(name.equals("Permissions")) {
		System.out.println("[Pixl] Unhooking Permissions");
		Pixl.Permissions = null;
		plugin.permissionsEnabled = false;
		//helpFail = false;
	    }/* else if(name.equals("Help")) {
		System.out.println("[Pixl] Unhooking Help");
		plugin.helpEnabled = false;
		helpFail = false;
	    }*/
	}
    }
}