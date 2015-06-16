package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class PatternTable {
	private String name;
	private List<PatternRow> patternRows;

	public PatternTable(String name) {
		this.name = name;
		patternRows = new ArrayList<PatternRow>();
	}

	public void addRestrictions(List<Restriction> restrictions) {
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
	}

	public List<PatternRow> getPatternRows() {
		return patternRows;
	}

	public void setPatternRows(List<PatternRow> patternRows) {
		this.patternRows = patternRows;
	}

	@Override
	public String toString() {
		return "PatternTable [name=" + name + ", patternRows=" + patternRows
				+ "]";
	}

}
