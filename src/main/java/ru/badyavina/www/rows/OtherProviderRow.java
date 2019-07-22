package ru.badyavina.www.rows;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherProviderRow {
	String code;
	String name;
	String leftOver;
	private static final Pattern p = Pattern.compile(",|.[0-9]*( )*$");
	private static Matcher m = null;
	private static final String replaceWith = "";

	public OtherProviderRow() {
		super();
	}

	public OtherProviderRow(String code, String name, String leftOver) {
		super();
		this.code = code;
		this.name = name;
		this.leftOver = leftOver;
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
		m = p.matcher(leftOver);
		this.leftOver = m.replaceAll(replaceWith);
	}

	@Override
	public String toString() {
		return "OtherProviderRow [code=" + code + ", name=" + name + ", leftOver=" + leftOver + "]";
	}

}
