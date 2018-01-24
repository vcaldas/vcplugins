package com.victorcaldas.plugins.folsomiaCounter.controller;

/**
 * Runner object for the Folsomia Counter application/Fiji Plugin
 * @author Victor Caldas
 *
 */
public class FCPluginRunner {

	
	
	/**
	 * Main starter method and entry point for the plugin 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		FCounterController  baseApp = new FCounterController() ;
		baseApp.start();
		
	}

}
