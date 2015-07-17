package com.dateGenerator.structures.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;

public class OutputTable {
	private String name;
	private List<OutputRow> rows;
	private List<String> columns;
	
	public OutputTable(String name) {
		super();
		this.name = name;
		rows = new ArrayList<>();
		columns = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OutputRow> getRows() {
		return rows;
	}

	public void setRows(List<OutputRow> rows) {
		this.rows = rows;
	}

	public void addRow(OutputRow row) {
		this.rows.add(row);
	}

	public void addRow(PatternRow row) { 
		Map<String, String> m = new HashMap<>();
		for(PatternNode pn : row.getPatternNodes()) {
			m.put(pn.getName(), pn.getValue().toString());
		}
		OutputRow tempRow = new OutputRow();
		for(String column : columns) {
			tempRow.addNode(new String(m.get(column)));
		}
		addRow(tempRow);
		
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public void addColumn(String column) {
		this.columns.add(column);
	}

	@Override
	public String toString() {
		return "\n\nTable [name=" + name + ", \n   columns="
				+ columns + ", \n   rows=" + rows + "]";
	}
}
