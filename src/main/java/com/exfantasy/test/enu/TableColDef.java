package com.exfantasy.test.enu;

public enum TableColDef {
	GOT(0, DataType.Boolean, "got"), 
	PRIZE(1, DataType.Integr, "prize"), 
	LOTTERY_NO(2, DataType.String, "lotteryNo"), 
	CONSUME_DATE(3, DataType.LocalDate, "consumeDate"), 
	AMOUNT(4, DataType.Integr, "amount"), 
	TYPE(5, DataType.Type, "type"), 
	PROD_NAME(6, DataType.String, "prodName");

	private int tableColIndex;
	private DataType dataType;
	private String mappingVoField;

	private TableColDef(int tableColIndex, DataType dataType, String mappingVoField) {
		this.tableColIndex = tableColIndex;
		this.dataType = dataType;
		this.mappingVoField = mappingVoField;
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

	public static TableColDef convertByTableIndex(int tableColIndex) {
		for (TableColDef col : TableColDef.values()) {
			if (col.getTableColIndex() == tableColIndex) {
				return col;
			}
		}
		return null;
	}
}
