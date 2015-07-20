package pl.put.tpd.datagenerator.structures.pattern;

import java.util.ArrayList;
import java.util.List;

import pl.put.tpd.datagenerator.structures.restriction.Restriction;
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
	
	public void initiatePatternRow() {
		for(PatternTable patternTable : patternTables) {
			patternTable.initiatePatternRow();
		}
	}
	
	public void prepare() {
		for(PatternTable pt : patternTables) {
			pt.prepare();
		}
	}
	
	public void setTableAlias(String table, String alias) {
		for(PatternTable pt : patternTables) {
			if(pt.getName().equals(table)) {
				pt.setAlias(alias);
			}
		}
	}
	/**
	 * 
	 * @param restrictions
	 */
	public void addRestrictions(List<Restriction> restrictions) {
		
		boolean containsColumnFlag;
		for(Restriction restriction : restrictions) {
			List<Integer> intList = new ArrayList<>();//contains list of numbers of PatternRows in tables
			List<PatternRestriction> pRestrList = new ArrayList<PatternRestriction>();
			int k = 0;
			int product = 2;
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
				}
			}
			for(int i = 0; i < product; i++) {
				if(i<product/2)
					pRestrList.add(new PatternRestriction(restriction));
				else
					pRestrList.add(new PatternRestriction(restriction.getNegative()));					
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
					patternTable.addPatternRestriction(pRestrList, intList, i);
					i++;
				}
			}
		}
	}
	
	public void addPatternTable(PatternTable patternTable) {
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
			patternAll.addPatternTable(patternTable.copy());
		}
		return patternAll;
	}
	
	public void clearValues() {
		for(PatternTable patternTable : patternTables) {
			patternTable.clearValues();
		}
	}

}
