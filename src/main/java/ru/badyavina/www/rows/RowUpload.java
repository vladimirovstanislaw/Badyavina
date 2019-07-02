package ru.badyavina.www.rows;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;

public class RowUpload {
	String name;
	String price;
	String leftovers;
	private static final Pattern p = Pattern.compile(",|.[0-9]*( )*$");
	private static Matcher m = null;
	private static final String replaceWith = "";

	public RowUpload() {
		super();
	}

	public RowUpload(String name, String price, String leftovers) {
		super();
		this.name = name;
		this.price = price;
		this.leftovers = leftovers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		m = p.matcher(price);
		this.price = m.replaceAll(replaceWith);
	}

	public String getLeftovers() {
		return leftovers;
	}

	public void setLeftovers(String leftovers) {
		m = p.matcher(leftovers);
		this.leftovers = m.replaceAll(replaceWith);
	}

	@Override
	public String toString() {
		return "RowUpload [name = " + name + ", price = " + price + ", leftovers = " + leftovers + "]";
	}

}
