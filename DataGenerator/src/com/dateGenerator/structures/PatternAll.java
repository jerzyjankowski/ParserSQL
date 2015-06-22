package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;
/**
 * contains all PatternTables which contains all PatternRows which contains all PatternNodes. 
 * Tree-like architecture provides maintainable managing from one object only
 */
public class PatternAll {
	private List<PatternTable> patternTables;

	public static void main(String... args) {
		

	}
	
	public PatternAll() {
		patternTables = new ArrayList<PatternTable>();
	}
	
	public void addRestrictions(List<Restriction> restrictions) {
		
		boolean containsColumnFlag;
		for(Restriction restriction : restrictions) {
			List<Integer> intList = new ArrayList<>();//contains list of numbers of PatternRows in tables
			List<ConcreteRestriction> concRestrList = new ArrayList<ConcreteRestriction>();
			int k = 0;
			int product = 2;
			int number = 0;
			for(PatternTable patternTable : patternTables) {
				containsColumnFlag = false;
				for(String column : restriction.getColumns()) {
					if(patternTable.containsColumn(column)) {
						containsColumnFlag = true;
						break;
					}
				}
				if(containsColumnFlag) {
					int x = patternTable.getPatternRows().size();
					intList.add(x);
					product *= x;
					number++;
				}
			}
			for(int i = 0; i < product; i++) {
				if(i<product/2)
					concRestrList.add(new ConcreteRestriction(number, restriction));
				else
					concRestrList.add(new ConcreteRestriction(number, restriction.getNegative()));					
			}
			int i = 0;
			for(PatternTable patternTable : patternTables) {
				containsColumnFlag = false;
				for(String column : restriction.getColumns()) {
					if(patternTable.containsColumn(column)) {
						containsColumnFlag = true;
						break;
					}
				}
				if(containsColumnFlag) {
					patternTable.addConcreteRestriction(concRestrList, intList, i);
					i++;
				}
			}
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
		return "PatternAll [patternTables(" + patternTables.size() + ")=" + patternTables + "]";
	}
	
	public PatternAll copy() {
		PatternAll patternAll = new PatternAll();
		for(PatternTable patternTable : patternTables) {
			patternAll.addPatternTables(patternTable.copy());
		}
		return patternAll;
	}

}
