package com.dateGenerator.structures.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;

public class OutputAll {
	
	public static void main(String... args) {
		PatternAll patternAll = new PatternAll();
		PatternTable patternTable = new PatternTable("pracownicy");
		PatternRow patternRow = new PatternRow();
		PatternNode nodeEtat = new PatternNode("integer", "etat");
		PatternNode nodePPod = new PatternNode("integer", "placa_pod");
		PatternNode nodePDod = new PatternNode("integer", "placa_dod");
		patternRow.addPatternNode(nodeEtat);
		patternRow.addPatternNode(nodePPod);
		patternRow.addPatternNode(nodePDod);
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);
		nodeEtat.setValue(7);
		nodePPod.setValue(8);
		nodePDod.setValue(9);
		
		OutputAll all = new OutputAll();
		OutputTable table = new OutputTable("pracownicy");
		table.addColumn("etat");
		table.addColumn("placa_pod");
		table.addColumn("placa_dod");
		OutputRow row = new OutputRow();
		row.addNode("1");
		row.addNode("2");
		row.addNode("3");
		table.addRow(row);
		row = new OutputRow();
		row.addNode("4");
		row.addNode("5");
		row.addNode("6");
		table.addRow(row);
		all.addTable(table);

		table = new OutputTable("etaty");
		row = new OutputRow();
		row.addNode("11");
		row.addNode("12");
		table.addColumn("nazwa");
		table.addColumn("placa_min");
		table.addRow(row);
		all.addTable(table);

		table = new OutputTable("zespoly");
		row = new OutputRow();
		table.addColumn("zespol_nazwa");
		table.addColumn("dodatek_min");
		table.addRow(row);
		all.addTable(table);
		
		all.addRow(patternRow, patternTable);
		
		System.out.println(all);
	}
	
	private List<OutputTable> tables;
	private Map<String, OutputTable> tablesMap;
	
	public OutputAll(){
		tables = new ArrayList<>();
		tablesMap = new HashMap<>();
	}
	
	public List<OutputTable> getTables() {
		return tables;
	}

	public void setTables(List<OutputTable> tables) {
		this.tables = tables;
	}
	
	public void addTable(OutputTable table) {
		tables.add(table);
		tablesMap.put(table.getName(), table);
	}

	public void addRow(PatternRow patternRow, PatternTable patternTable) {
		if(tablesMap.containsKey(patternTable.getName()))
			tablesMap.get(patternTable.getName()).addRow(patternRow);
		else
			System.out.println("[OutputAll.addRow()] Error unknown table name" + patternTable.getName());
	}
	
	@Override
	public String toString() {
		return "All [tables=" + tables + "]";
	}
	
	
}
