package ru.badyavina.www.rows;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RowAsIs {
	String size;
	String name;
	String leftovers;
	String wholesalePrice;
	String price;

	public RowAsIs() {
		super();
	}

	public RowAsIs(String size, String name, String leftovers, String wholesalePrice, String price) {
		super();
		this.size = size;
		this.name = name;
		this.leftovers = leftovers;
		this.wholesalePrice = wholesalePrice;
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeftovers() {
		return leftovers;
	}

	public void setLeftovers(String leftovers) {
		this.leftovers = leftovers;
	}

	public String getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(String wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "RowAsIs [size=" + size + ", name=" + name + ", leftovers=" + leftovers + ", wholesalePrice="
				+ wholesalePrice + ", price=" + price + "]";
	}

}
