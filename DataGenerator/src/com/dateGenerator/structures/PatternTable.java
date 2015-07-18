package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternTable {
	private String name;
	private List<PatternRow> patternRows;
	private PatternRow mainPatternRow;
	private Set<String> columnNames;
	private String alias = "";

	public static void main(String... args) {
		PatternNode node1 = new PatternNode("integer", "placa_dod", 190);
		PatternNode node2 = new PatternNode("integer", "placa_pod", 191);
		PatternRestriction patternRestriction = new PatternRestriction(new Restriction("placa_pod<placa_dod", null));
		node1.addPatternRestriction(patternRestriction);
		node2.addPatternRestriction(patternRestriction);
		PatternRow row1 = new PatternRow();
		row1.addPatternNode(node1);
		row1.addPatternNode(node2);
		
		node1 = new PatternNode("integer", "placa_dod");
		node2 = new PatternNode("integer", "placa_pod");
		patternRestriction = new PatternRestriction(new Restriction("placa_pod>=placa_dod", null));
		node1.addPatternRestriction(patternRestriction);
		node2.addPatternRestriction(patternRestriction);
		PatternRow row2 = new PatternRow();
		row2.addPatternNode(node1);
		row2.addPatternNode(node2);
		
		PatternTable patternTable = new PatternTable("pracownicy");
		patternTable.addPatternRow(row1);
		patternTable.addPatternRow(row2);
		
		for(PatternRow row : patternTable.getPatternRows()) {
			System.out.println("\nrow: " + row);
		}
		
		List<PatternRestriction> concRestrList = new ArrayList<>();
		Restriction restriction = new Restriction("placa_pod>placa_min", null);
		restriction.addColumn("placa_pod");
		restriction.addColumn("placa_min");
		patternRestriction = new PatternRestriction(restriction);
		for(int i = 0; i < 2; i++) {
			concRestrList.add(new PatternRestriction(patternRestriction));
		}
		restriction = new Restriction("placa_pod<=placa_min", null);
		restriction.addColumn("placa_pod");
		restriction.addColumn("placa_min");
		patternRestriction = new PatternRestriction(restriction);
		for(int i = 0; i < 2; i++) {
			concRestrList.add(new PatternRestriction(patternRestriction));
		}
		List<Integer> intList = new ArrayList<>();
		intList.add(1); 
		intList.add(2);
		patternTable.addPatternRestriction(concRestrList, intList, 1);
		
		System.out.println(patternTable);
	}
	
	public PatternTable(String name) {
		this.name = name;
		patternRows = new ArrayList<PatternRow>();
		columnNames = new HashSet<String>();
	}
	
	public void prepare() {
		for(PatternRow patternRow : patternRows) {
			for(PatternNode patternNode : patternRow.getPatternNodes()) {
				
			}
		}
	}
	
	public void addPatternRestriction(List<PatternRestriction> pattRestrList, List<Integer> intList, int i) {
		int product = 2;
		int productBefore = 1;
		int sequence;
		int repeat;
		for(int j = 0; j < intList.size(); j++) {
			product *= intList.get(j);
			if(j <= i)
				productBefore = product/2;
		}
		sequence = product/productBefore/2;
		repeat = product/(sequence*patternRows.size());
		
		List<PatternRow> oldPatternRows = new ArrayList<PatternRow>();
		oldPatternRows.addAll(patternRows);
		patternRows = new ArrayList<PatternRow>();
		int m = 0;
		
		for(int j = 0; j < repeat; j++) {
			for(PatternRow patternRow : oldPatternRows) {
				for(int k = 0; k < sequence; k++) {
					PatternRow newPatternRow = new PatternRow(patternRow);
					newPatternRow.addPatternRestriction(pattRestrList.get(m++));
					patternRows.add(newPatternRow);
				}
			}
		}
		for(PatternRow patternRow : oldPatternRows) {
			patternRow.remove();
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPatternRow(PatternRow patternRow) {
		patternRow.setTableName(name);
		this.patternRows.add(patternRow);
		for(String column : patternRow.getColumnNames()) 
			this.columnNames.add(column);
	}

	public List<PatternRow> getPatternRows() {
		return patternRows;
	}

	public void setPatternRows(List<PatternRow> patternRows) {
		this.patternRows = patternRows;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
		for(PatternRow patternRow : patternRows) {
			patternRow.setTableAlias(alias);
		}
	}

	public PatternRow getMainPatternRow() {
		return mainPatternRow;
	}

	public void setMainPatternRow(PatternRow mainPatternRow) {
		this.mainPatternRow = mainPatternRow;
		for(String column : mainPatternRow.getColumnNames()) 
			this.columnNames.add(column);
	}
	
	public void initiatePatternRow() {
		addPatternRow(mainPatternRow.copy());
	}

	@Override
	public String toString() {
		return "\n   PatternTable [name=" + name + " as " + alias + ",\n     mainPatternRow=" + mainPatternRow +
				",\n     patternRows(" + patternRows.size() + ")=" + patternRows + "]";
	}
	
	public boolean containsColumn(String column) {
		for(String c : columnNames) {
			if(column.equals(c))
				return true;
			if(column.equals(name + "." + c))
				return true;
			if(column.equals(alias + "." + c))
				return true;
		}
		return false;
	}
	
	public PatternTable copy() {
		PatternTable patternTable = new PatternTable(name);
		for(PatternRow patternRow : patternRows) {
			patternTable.addPatternRow(patternRow.copy());
		}
		patternTable.setMainPatternRow(mainPatternRow);
		return patternTable;
	}
	
	public void clearValues() {
		for(PatternRow patternRow : patternRows) {
			patternRow.clearValues();
		}
	}

}
