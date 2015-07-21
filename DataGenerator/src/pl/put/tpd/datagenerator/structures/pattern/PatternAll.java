package pl.put.tpd.datagenerator.structures.pattern;

import java.util.ArrayList;
import java.util.List;

import pl.put.tpd.datagenerator.structures.restriction.Restriction;
/**
 * contains all PatternTables which contains all PatternRows which contains all PatternNodes. 
 * Tree-like architecture provides maintainable managing from one object only
 * 
 * plans adding and delegate restrictions to the tables that are connected with that restrictions to add them
 */
public class PatternAll {
	private List<PatternTable> patternTables;
	
	public PatternAll() {
		patternTables = new ArrayList<PatternTable>();
	}
	
	/**
	 * plans and distribute restriction adding through tables
	 * @param restrictions
	 */
	public void addRestrictions(List<Restriction> restrictions) {
		System.out.println(restrictions);
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
				System.out.println(patternTable.getName() + " " + containsColumnFlag);
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

	/**
	 * delegates to all tables to copy mainPatternRow to the first patternRow which will be copied and extended with restrictions
	 */
	public void initiatePatternRow() {
		for(PatternTable patternTable : patternTables) {
			patternTable.initiatePatternRow();
		}
	}
	
	/**
	 * sets only alias to a table
	 * @param table
	 * @param alias
	 */
	public void setTableAlias(String table, String alias) {
		for(PatternTable pt : patternTables) {
			if(pt.getName().equals(table)) {
				pt.setAlias(alias);
			}
		}
	}
	
	/**
	 * delegates to all patternTables to clear values in patternNodes
	 */
	public void clearValues() {
		for(PatternTable patternTable : patternTables) {
			patternTable.clearValues();
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
}
