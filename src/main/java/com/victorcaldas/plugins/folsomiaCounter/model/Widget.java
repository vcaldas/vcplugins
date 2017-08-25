package com.victorcaldas.plugins.folsomiaCounter.model;


public class Widget {
	
	private double weight;
	private String description;
	
	public Widget(double weight, String description) {
		this.weight = weight;
		this.description = description;
	}

	public double getWeight() {
		return weight;
	}

	public String getDescription() {
		return description;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
