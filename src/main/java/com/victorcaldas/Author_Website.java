package com.victorcaldas;


import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.plugin.BrowserLauncher;

@Plugin(type = Command.class, headless = true, menuPath = "VU>Help>Author website")

public class Author_Website implements Command {

	@Parameter
	LogService log;
	
	@Override
	public void run() {
		try { BrowserLauncher.openURL("http://www.victorcaldas.com"); }
		catch (Throwable e) { log.info("Could not open default internet browser"); }
		
	}
	
}