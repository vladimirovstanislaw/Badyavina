package ru.badyavina.www.rows;

import org.apache.poi.ss.usermodel.Row;

public class RowNomenclature {
	String code;
	String name;

	public RowNomenclature() {
		super();
	}

	public RowNomenclature(String code, String name) {
		super();
		this.code = code;
		this.name = name;
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

	@Override
	public String toString() {
		return "RowNomenclature [code=" + code + ", name=" + name + "]";
	}

}
