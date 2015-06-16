package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class PatternAll {
	private List<PatternTable> patternTables;

	public PatternAll() {
		patternTables = new ArrayList<PatternTable>();
	}
	
	public void addRestrictions(List<Restriction> restrictions) {
		for(PatternTable patternTable : patternTables) {
			patternTable.addRestrictions(restrictions);
		}
	}
	
	public void addPatternTables(PatternTable patternTable) {
		this.patternTables.add(patternTable);
	}
	
	public List<PatternTable> getPatternTables() {
		return patternTables;
	}

	public void setPatternTables(List<PatternTable> patternTables) {
		this.patternTables = patternTables;
	}

	@Override
	public String toString() {
		return "PatternAll [patternTables=" + patternTables + "]";
	}

}