package Util;

import java.util.ArrayList;
import java.util.List;

import com.dateGenerator.structures.PatternRestriction;
import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;
import com.dateGenerator.structures.Restriction;

public class TestPatternAll {
	
	public static void main(String... args) {
		TestPatternAll t = new TestPatternAll();
		t.tests0();
	}
	/**
	 * first table with one column without restriction - one row
	 * second table with two columns without restriction - one rows
	 * third table with two columns without restriction - one row
	 * 
	 * second - add one restriction
	 * first and second - add one restriction
	 * second and third - add one restriction
	 */
	public void tests0() {

		/*PatternTable1*/
		
		PatternNode node0 = new PatternNode("integer", "premia_min");
		PatternRow row0 = new PatternRow();
		row0.addPatternNode(node0);
		
		PatternTable patternTable0 = new PatternTable("zespoly");
		patternTable0.addPatternRow(row0);
		
		/*PatternTable2*/
		
		PatternNode node10 = new PatternNode("integer", "placa_pod");
		PatternNode node11 = new PatternNode("integer", "placa_dod");
		PatternRow row1 = new PatternRow();
		row1.addPatternNode(node10);
		row1.addPatternNode(node11);
		
		PatternTable patternTable1 = new PatternTable("pracownicy");
		patternTable1.addPatternRow(row1);
		
		/*PatternTable2*/
		
		PatternNode node20 = new PatternNode("integer", "placa_min");
		PatternNode node21 = new PatternNode("integer", "nazwa");
		PatternRow row2 = new PatternRow();
		row2.addPatternNode(node20);
		row2.addPatternNode(node21);
		
		PatternTable patternTable2 = new PatternTable("etaty");
		patternTable2.addPatternRow(row2);
		
		/*PatternAll*/
		
		PatternAll patternAll = new PatternAll();
		patternAll.addPatternTable(patternTable0);
		patternAll.addPatternTable(patternTable1);
		patternAll.addPatternTable(patternTable2);
		
		/*Restriction*/

		Restriction restriction0 = new Restriction("placa_pod>placa_dod", null);
		restriction0.addColumn("placa_pod");
		restriction0.addColumn("placa_dod");
		
		Restriction restriction1 = new Restriction("placa_dod>premia_min", null);
		restriction1.addColumn("placa_dod");
		restriction1.addColumn("premia_min");

		Restriction restriction2 = new Restriction("placa_pod>placa_min", null);
		restriction2.addColumn("placa_pod");
		restriction2.addColumn("placa_min");
		
		List<Restriction> restrictions = new ArrayList<>();

		/*Run*/
		
		System.out.println("\n\n********************* --0-- *********************  tests0 - TestPatternAll.java\n\n");
		restrictions.clear();
		restrictions.add(restriction0);
		patternAll.addRestrictions(restrictions);
		System.out.println(patternAll);
		System.out.println("\n\n********************* --1-- *********************\n\n");
		
		restrictions.clear();
		restrictions.add(restriction1);
		patternAll.addRestrictions(restrictions);
		System.out.println(patternAll);
		System.out.println("\n\n********************* --2-- *********************\n\n");
		
		restrictions.clear();
		restrictions.add(restriction2);
		patternAll.addRestrictions(restrictions);
		System.out.println(patternAll);
		System.out.println("\n\n*************************************************\n\n");
	}
}
