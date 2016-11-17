package com.exfantasy.test.enu;

public enum Type {
	ALL(0, "全部"),
	BREAKFAST(1, "早餐"),
	LUNCH(2, "中餐"),
	DINNER(3, "晚餐"),
	DRINK(4, "飲料"),
	CIGARETTE(5, "香菸"),
	GAS(6, "加油"),
	PARKING(7, "停車費"),
	PLAY(8, "娛樂");
	
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
