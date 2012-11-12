package org.pan.voucher.rest.model;

public enum AmountModel {
	
	FIRST(100, "R 100.00"),
	SECOND(200, "R 200.00"),
	THIRD(300, "R 300.00"),
	FOURTH(400, "R 400.00"),
	FIFTH(500, "R 500.00");
	
	private Integer value;
	private String name;
	
	private AmountModel(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
}
