package com.exfantasy.test.enu;

public enum Type {
	ALL(0, ""),
	BREAKFAST(1, "早餐"),
	LUNCH(2, "中餐"),
	DINNER(3, "晚餐"),
	DRINK(4, "7-11"),
	GAS(5, "加油"),
	PARKING(6, "停車費"),
	PLAY(7, "娛樂");
	
	private int code;
	private String description;
	
	private Type(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static Type convert(int code) {
		for (Type e : Type.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
