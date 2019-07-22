package ru.badyavina.www.rows;

public class AllDataRow {
	String code;
	String name;
	String leftOver;
	String retailPrice;

	public AllDataRow() {
		super();
	}

	public AllDataRow(String code, String name, String leftOver, String retailPrice) {
		super();
		this.code = code;
		this.name = name;
		this.leftOver = leftOver;
		this.retailPrice = retailPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeftOver() {
		return leftOver;
	}

	public void setLeftOver(String leftOver) {
		this.leftOver = leftOver;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Override
	public String toString() {
		return "AllDataRow [code=" + code + ", name=" + name + ", leftOver=" + leftOver + ", retailPrice=" + retailPrice
				+ "]";
	}

}
