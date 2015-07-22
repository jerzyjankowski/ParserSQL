package pl.put.tpd.datagenerator.structures.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRow;
import pl.put.tpd.datagenerator.structures.pattern.PatternTable;

public class OutputTable {
	private String name;
	private List<OutputRow> rows;
	private List<String> columns;
	private int rowNum;
	
	public OutputTable(String name) {
		super();
		this.name = name;
		rows = new ArrayList<>();
		columns = new ArrayList<>();
	}

	public OutputTable(PatternTable patternTable) {
		this(patternTable.getName());
		for(String columnName : patternTable.getColumnNames()) {
			addColumn(columnName);
		}
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

	/**
	 * 
	 * @param row
	 * @return if row where added, means that return if table wasn't full
	 */
	public boolean addRow(PatternRow row) { 
		if(rows.size() < rowNum) {
			Map<String, String> m = new HashMap<>();
			for(PatternNode pn : row.getPatternNodes()) {
				m.put(pn.getName(), pn.getValue().toString());
			}
			OutputRow tempRow = new OutputRow();
			for(String column : columns) {
				tempRow.addNode(new String(m.get(column)));
			}
			addRow(tempRow);
			return true;
		}
		else
			return false;
		
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
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	@Override
	public String toString() {
		return "\n\nTable [name=" + name + ", \n   columns="
				+ columns + ", \n   rows=" + rows + "]";
	}
}
