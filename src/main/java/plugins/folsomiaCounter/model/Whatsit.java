package com.victorcaldas.plugins.folsomiaCounter.model;

public class Whatsit {
	private int size;
	private String name;
	
	
	public Whatsit(int size, String name) {
		this.size = size;
		this.name = name;
		
		
	}


	public int getSize() {
		return size;
	}


	public String getName() {
		return name;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public void setName(String name) {
		this.name = name;
	}

}
