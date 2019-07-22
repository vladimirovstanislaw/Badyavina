package ru.badyavina.www.rows;

public class RowUpload {
	String code;
	String price;
	String leftOver;

	public RowUpload() {
		super();
	}

	public RowUpload(String code, String price, String leftovers) {
		super();
		this.code = code;
		this.price = price;
		this.leftOver = leftovers;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {

		this.price = price;
	}

	public String getLeftovers() {
		return leftOver;
	}

	public void setLeftovers(String leftOver) {

		this.leftOver = leftOver;
	}

	@Override
	public String toString() {
		return "RowUpload [name = " + code + ", price = " + price + ", leftovers = " + leftOver + "]";
	}

}
