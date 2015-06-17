package com.dateGenerator.xml;

import java.util.ArrayList;
import java.util.List;

public class XMLColumn {
	
	private String name;
	private float nullPercentage = 0;
	private XMLColumnTypes type = XMLColumnTypes.INT;

	private int minValue;
	private int maxValue;
	
	private float minUniquePercentage = 0;
	private float maxUniquePercentage = 100;
	
	List<String> values = new ArrayList<String>();
	
	
	
	@Override
	public String toString() {
		return "SQLColumn [name=" + name + ", nullPercentage=" + nullPercentage
				+ ", type=" + type + ", minValue=" + minValue + ", maxValue="
				+ maxValue + ", minUniquePercentage=" + minUniquePercentage
				+ ", maxUniquePercentage=" + maxUniquePercentage + ", values="
				+ values + "]";
	}
	
	public void addValue(String value)
	{
		values.add(value);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public XMLColumnTypes getType() {
		return type;
	}
	public void setType(String rec) {
		if(rec.equals("DATE")) this.type = XMLColumnTypes.DATE;
		else if(rec.equals("FLOAT")) this.type = XMLColumnTypes.FLOAT;
		else if(rec.equals("STRING")) this.type = XMLColumnTypes.STRING;
		else  this.type = XMLColumnTypes.INT;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public float getNullPercentage() {
		return nullPercentage;
	}
	public void setNullPercentage(float nullPercentage) {
		this.nullPercentage = nullPercentage;
	}
	public float getMinUniquePercentage() {
		return minUniquePercentage;
	}
	public void setMinUniquePercentage(float minUniquePercentage) {
		this.minUniquePercentage = minUniquePercentage;
	}
	public float getMaxUniquePercentage() {
		return maxUniquePercentage;
	}
	public void setMaxUniquePercentage(float maxUniquePercentage) {
		this.maxUniquePercentage = maxUniquePercentage;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	
	

}
