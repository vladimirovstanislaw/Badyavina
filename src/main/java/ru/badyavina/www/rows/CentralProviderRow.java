package ru.badyavina.www.rows;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CentralProviderRow {
	String code;
	String retailPrice;
	private static final Pattern p = Pattern.compile(",|.[0-9]*( )*$");
	private static Matcher m = null;
	private static final String replaceWith = "";

	public CentralProviderRow() {
		super();
	}

	public CentralProviderRow(String code, String retailPrice) {
		super();
		this.code = code;
		this.retailPrice = retailPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		m = p.matcher(retailPrice);
		this.retailPrice = m.replaceAll(replaceWith);
	}

	@Override
	public String toString() {
		return "CentralProviderRow [code=" + code + ", retailPrice=" + retailPrice + "]";
	}

}
