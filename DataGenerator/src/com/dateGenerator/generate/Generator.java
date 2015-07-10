package com.dateGenerator.generate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dateGenerator.structures.ConcreteRestriction;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;
import com.dateGenerator.xml.XMLTable;

public class Generator {

	List<PatternTable> tables;
	List<XMLTable> xmlTables;
	List<JoinedRow> joinedRows;
	
	List<PatternRow> currentRows;
	
	HashMap<Integer,Integer> nodeToRow = new HashMap<Integer,Integer>();
	HashMap<Integer,PatternRow> idToRow = new HashMap<Integer,PatternRow>();
	HashMap<Integer, String> rowToTable = new HashMap<Integer,String>();
	Set<String> tableNames = new HashSet<>();

	public Generator(List<PatternTable> tables) {
		this.tables = tables;
		//this.xmlTables = xmlTables;
		joinedRows = new ArrayList<JoinedRow>();
		currentRows = new ArrayList<PatternRow>();
		for(PatternTable table : tables)
			for(PatternRow row : table.getPatternRows())
				for(PatternNode node: row.getPatternNodes()){
					nodeToRow.put(node.getId(), row.getId());
					idToRow.put(row.getId(),row);
					rowToTable.put(row.getId(),table.getName());
					tableNames.add(table.getName());
				}
	}
	
	public List<JoinedRow> generate()
	{
		for (PatternTable table : tables) 
			for (PatternRow row : table.getPatternRows()) 
				generateJoinedRows(new JoinedRow(),row);
		
		Set<JoinedRow> orderedJoinedRows = new HashSet<>(); 
		orderedJoinedRows.addAll(joinedRows); //usuwanie duplikatów powsta³ych w algorytmie
		
		List<JoinedRow> tempJoinedRows = new ArrayList<JoinedRow>(); //kopia listy potrzebna do funkcji splitRowSet
		List<String> tableNames = new ArrayList<String>();
		tableNames.addAll(this.tableNames); 
		tempJoinedRows.addAll(orderedJoinedRows);
		
		orderedJoinedRows.clear();
		joinedRows.clear();
			
		for(JoinedRow joinedRow : tempJoinedRows){
			joinedRow.removeDuplicates();
			splitRowSet(joinedRow.getPatternRows(),tableNames, new ArrayList<PatternRow>());
		}

		return joinedRows;
	}
	


	private void splitRowSet(List<PatternRow> allRows,
			List<String> tables, List<PatternRow> visitedRows) {
		//System.out.println("--------------------------");
		//show_visited(visitedRows);
		//showTables(tables);
		if(tables.isEmpty()){
			//System.out.print("DODANO Wiersz : ");
			//show_visited(visitedRows);
			this.joinedRows.add(new JoinedRow(visitedRows));
		}else{
			String actualTable = tables.get(0);
			tables.remove(0);
			//System.out.println("[table:"+ actualTable + "]");
			for(PatternRow row : allRows){
				//System.out.println("--->"+ row.getId() + "[" + rowToTable.get(row.getId()) + "]");
				if( rowToTable.get(row.getId()).equals(actualTable)){
					//System.out.println("TEST3");
					
					List<PatternRow> tmpVisitedRows = new ArrayList<PatternRow>();
					tmpVisitedRows.addAll(visitedRows);
					tmpVisitedRows.add(row);
					
					splitRowSet(allRows,tables,tmpVisitedRows);
				}
			}
			tables.add(actualTable);
		}
		return;
		
	}
	
/*	private void showTables(List<String> strings){
		System.out.print("names[");
		for(String rw : strings)
			System.out.print(rw+",");
		System.out.println("]");
	}
	
	private void show_visited(List<PatternRow> visited){
		System.out.print("visited[");
		for(PatternRow rw : visited)
			System.out.print(rw.getId()+",");
		System.out.println("]");
	}*/

	public void generateJoinedRows(JoinedRow joinedRow, PatternRow row) {
		if(rowAlreadyProcessed(row.getId())) return;
		currentRows.add(row);
		if(allForeignRestrictionProcessed(row))
		{
			joinedRow.add(row);
			joinedRows.add(joinedRow);
			for(PatternRow rowToDelete : joinedRow.getPatternRows())
				currentRows.remove(rowToDelete);
			return;
		}
		for (PatternNode node : row.getPatternNodes()) {
			for (ConcreteRestriction restriction : getForeignRestrictions(node, row.getId())) {
				for(PatternNode restrictionNode: restriction.getNodes()){
					if(rowAlreadyProcessed(nodeToRow.get(restrictionNode.getId()))) {
						continue;
					}else{
						joinedRow.add(row);
						int nextRowId = nodeToRow.get(restrictionNode.getId());
						PatternRow nextRow = idToRow.get(nextRowId);
						generateJoinedRows(joinedRow,nextRow);
					}
				}
			}
		}	

	}
	
	

	private boolean allForeignRestrictionProcessed(PatternRow row) {
		for (PatternNode node : row.getPatternNodes()) {
			for (ConcreteRestriction restriction : getForeignRestrictions(node, row.getId())) {
				for(PatternNode restrictionNode: restriction.getNodes()){
						if(!rowAlreadyProcessed(nodeToRow.get(restrictionNode.getId()))) return false;
				}
			}
		}	
		return true;
	}

	//Zwraca tylko te ograniczenia ktore dotycza innych PatternRow niz ten do ktorego nale¿y obecny node
	private List<ConcreteRestriction> getForeignRestrictions(PatternNode node ,int patternRowId) {
		List<ConcreteRestriction> result = new ArrayList<ConcreteRestriction>();
		for(ConcreteRestriction restriction : node.getConcreteRestrictions()){
			for(PatternNode restrictionNode : restriction.getNodes()){
				if(nodeToRow.get(restrictionNode.getId()) != patternRowId){
					result.add(restriction);
				}
			}
		}
		return result;
	}

	private boolean rowAlreadyProcessed(int rowId) {
		for(PatternRow patternRow : currentRows){
			if(patternRow.getId() == rowId) 
				return true;
		}
		for(JoinedRow joinedRow : joinedRows){
			if(joinedRow.containts(rowId)) 
				return true;
		}
		return false;
	}

	private int getRowNumber(PatternTable table) {
		for (XMLTable xmlTable : xmlTables) {
			if (xmlTable.getName().equals(table.getName())) {
				return xmlTable.getRowNum();
			}
		}
		return 0;
	}

	

}
