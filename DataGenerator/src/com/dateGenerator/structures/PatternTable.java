package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternTable {
	private String name;
	private List<PatternRow> patternRows;
	private Set<String> columnNames;

	public PatternTable(String name) {
		this.name = name;
		patternRows = new ArrayList<PatternRow>();
		columnNames = new HashSet<String>();
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
				if(columnNames.contains(column))
					restrictions.add(restriction);
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
		return "PatternTable [name=" + name + ", patternRows=" + patternRows
				+ "]";
	}

}
