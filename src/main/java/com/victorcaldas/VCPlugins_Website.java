package com.victorcaldas;


import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.plugin.BrowserLauncher;

@Plugin(type = Command.class, headless = true, menuPath = "VU>Help>Plugin Website")

public class VCPlugins_Website implements Command {

	@Parameter
	LogService log;
	
	@Override
	public void run() {
		try { BrowserLauncher.openURL("https://github.com/vcaldas/vcplugins"); }
		catch (Throwable e) { log.info("Could not open default internet browser"); }
		
	}
	
}