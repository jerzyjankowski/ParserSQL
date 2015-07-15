package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternTable {
	private String name;
	private List<PatternRow> patternRows;
	private Set<String> columnNames;

	public static void main(String... args) {
		PatternNode node1 = new PatternNode("integer", "placa_dod", 190);
		PatternNode node2 = new PatternNode("integer", "placa_pod", 191);
		PatternRestriction concreteRestriction = new PatternRestriction(1, new Restriction("placa_pod<placa_dod", null));
		node1.addConcreteRestriction(concreteRestriction);
		node2.addConcreteRestriction(concreteRestriction);
		PatternRow row1 = new PatternRow(301);
		row1.addPatternNode(node1);
		row1.addPatternNode(node2);
		
		node1 = new PatternNode("integer", "placa_dod");
		node2 = new PatternNode("integer", "placa_pod");
		concreteRestriction = new PatternRestriction(1, new Restriction("placa_pod>=placa_dod", null));
		node1.addConcreteRestriction(concreteRestriction);
		node2.addConcreteRestriction(concreteRestriction);
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
		concreteRestriction = new PatternRestriction(1, restriction);
		for(int i = 0; i < 2; i++) {
			concRestrList.add(new PatternRestriction(concreteRestriction));
		}
		restriction = new Restriction("placa_pod<=placa_min", null);
		restriction.addColumn("placa_pod");
		restriction.addColumn("placa_min");
		concreteRestriction = new PatternRestriction(1, restriction);
		for(int i = 0; i < 2; i++) {
			concRestrList.add(new PatternRestriction(concreteRestriction));
		}
		List<Integer> intList = new ArrayList<>();
		intList.add(1); 
		intList.add(2);
		patternTable.addConcreteRestriction(concRestrList, intList, 1);
		
		System.out.println(patternTable);
	}
	
	public PatternTable(String name) {
		this.name = name;
		patternRows = new ArrayList<PatternRow>();
		columnNames = new HashSet<String>();
	}
	
	public void addConcreteRestriction(List<PatternRestriction> concRestrList, List<Integer> intList, int i) {
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
					PatternRow newPatternRow = new PatternRow(patternRow, null);
					newPatternRow.addConcreteRestriction(concRestrList.get(m++));
					patternRows.add(newPatternRow);
				}
			}
		}
		for(PatternRow patternRow : oldPatternRows) {
			patternRow.remove();
		}
	}
/**
 * 
 * @param allRestrictions means all restrictions in where clause, later on restrictions will be filtered by adequacy. 
 * In restriction list there are only those restriction that are relevant for that table.
 */
	public void addRestrictions(List<Restriction> allRestrictions) {
		
		List<Restriction> restrictions = new ArrayList<Restriction>();
		for(Restriction restriction : allRestrictions) {
			for(String column : restriction.getColumns()) {
				if(columnNames.contains(column)) {
					restrictions.add(restriction);
					break;
				}
			}
		}
		
		int xCombinations = (int) Math.pow(2, restrictions.size());
		List<Restriction> negRestrictions = new ArrayList<Restriction>();
		for (Restriction restriction : restrictions) {
			negRestrictions.add(restriction.getNegative());
		}

		List<PatternRow> oldPatternRows = new ArrayList<PatternRow>();
		for (PatternRow patternRow : patternRows) {
			oldPatternRows.add(new PatternRow(patternRow));
		}
		patternRows.clear();

		for (int i = 0; i < xCombinations; i++) {
			List<Restriction> tempRestrictions = new ArrayList<Restriction>();
			for (int j = 0; j < restrictions.size(); j++) {
				if ((i >> j) % 2 == 0)
					tempRestrictions.add(negRestrictions.get(j));
				else
					tempRestrictions.add(restrictions.get(j));
			}
			
			for(PatternRow patternRow : oldPatternRows) {
				PatternRow newPatternRow = new PatternRow(patternRow);
				for(Restriction restriction : tempRestrictions) {
					newPatternRow.addRestriction(restriction);
				}
				patternRows.add(newPatternRow);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPatternRow(PatternRow patternRow) {
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

	@Override
	public String toString() {
		return "\n   PatternTable [name=" + name + ", patternRows(" + patternRows.size() + ")=" + patternRows
				+ "]";
	}
	
	public boolean containsColumn(String column) {
		return columnNames.contains(column);
	}
	
	public PatternTable copy() {
		PatternTable patternTable = new PatternTable(name);
		for(PatternRow patternRow : patternRows) {
			patternTable.addPatternRow(patternRow.copy());
		}
		return patternTable;
	}

}
