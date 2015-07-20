package pl.put.tpd.datagenerator.inputloding;

import java.util.ArrayList;
import java.util.List;

public class XMLTable {
	
	private String name;
	private int rowNum;
	private String distribution;
	private int minRowSize;
	private List<XMLColumn> columns = new ArrayList<XMLColumn>();
	
		
	public void addColumn(XMLColumn column)
	{
		columns.add(column);
	}

	

	@Override
	public String toString() {
		return "SQLTable [name=" + name + ", rowNum=" + rowNum
				+ ", distribution=" + distribution + ", minRowSize="
				+ minRowSize + "]";
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public int getMinRowSize() {
		return minRowSize;
	}

	public void setMinRowSize(int minRowSize) {
		this.minRowSize = minRowSize;
	}

	public List<XMLColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<XMLColumn> columns) {
		this.columns = columns;
	}
	
	
	
	
	
	
	
	
}
