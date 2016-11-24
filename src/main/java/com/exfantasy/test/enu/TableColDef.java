package com.exfantasy.test.enu;

public enum TableColDef {
	GOT(0, DataType.Boolean, "got", false), 
	PRIZE(1, DataType.Integr, "prize", false), 
	LOTTERY_NO(2, DataType.String, "lotteryNo", false), 
	CONSUME_DATE(3, DataType.LocalDate, "consumeDate", false), 
	AMOUNT(4, DataType.Integr, "amount", true), 
	TYPE(5, DataType.Type, "type", false), 
	PROD_NAME(6, DataType.String, "prodName", true);

	private int tableColIndex;
	private DataType dataType;
	private String mappingVoField;
	private boolean isEditable;

	private TableColDef(int tableColIndex, DataType dataType, String mappingVoField, boolean isEditable) {
		this.tableColIndex = tableColIndex;
		this.dataType = dataType;
		this.mappingVoField = mappingVoField;
		this.isEditable = isEditable;
	}

	public int getTableColIndex() {
		return this.tableColIndex;
	}

	public DataType getDateType() {
		return this.dataType;
	}

	public String getMappingVoField() {
		return this.mappingVoField;
	}
	
	public boolean isEditable() {
		return this.isEditable;
	}

	public static TableColDef convertByTableIndex(int tableColIndex) {
		for (TableColDef col : TableColDef.values()) {
			if (col.getTableColIndex() == tableColIndex) {
				return col;
			}
		}
		return null;
	}
}
