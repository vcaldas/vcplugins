package com.victorcaldas.plugins.folsomiaCounter.controller;

import com.victorcaldas.plugins.folsomiaCounter.model.MVCFactory;
import com.victorcaldas.plugins.folsomiaCounter.view.FCounterFrame;

public class FCounterController {
	
	private FCounterFrame appFrame;
	private MVCFactory appFactory;
	
	public MVCFactory getAppFactory() {
		return appFactory;
	}
	
	
	public FCounterFrame getAppFrame() {
		return appFrame;
	}
	
	public FCounterController() {
		
		appFactory = new MVCFactory();
				
	}
	
	public void start() {
		
		appFrame = new FCounterFrame(this);
		
	}

}
