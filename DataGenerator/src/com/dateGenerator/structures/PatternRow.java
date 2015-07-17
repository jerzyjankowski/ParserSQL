package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PatternRow {
	private int id;
	static private int lastId = 0;
	private List<PatternNode> patternNodes;
	private Set<String> columnNames;

	public static void main(String... args) {
		Restriction restriction = new Restriction("placa_min<placa_pod", null);
		restriction.addColumn("placa_pod");
		restriction.addColumn("placa_min");
		PatternRestriction concreteRestriction = new PatternRestriction(1, restriction);
//		node1.addConcreteRestriction(concreteRestriction);
//		node2.addConcreteRestriction(concreteRestriction);

		PatternNode node1 = new PatternNode("integer", "bonus", 190);
		PatternNode node2 = new PatternNode("integer", "placa_pod", 191);
		PatternRow row1 = new PatternRow();
		row1.addPatternNode(node1);
		row1.addPatternNode(node2);
		
		node1 = new PatternNode("integer", "placa_min", 192);
		node2 = new PatternNode("integer", "nazwa", 193);
		PatternRow row0 = new PatternRow();
		row0.addPatternNode(node1);
		row0.addPatternNode(node2);
		
		row1.addPatternRestriction(concreteRestriction);
		row0.addPatternRestriction(concreteRestriction);
		
		PatternRow row2 = new PatternRow(row1, null);

		row1.remove();
		
		System.out.println("\nrow0: " + row0);
		System.out.println("\nrow1: " + row1);
		System.out.println("\nrow2: " + row2);
	}
	
	public void remove() {
		for(PatternNode patternNode : patternNodes) {
			patternNode.remove();
		}
		patternNodes.clear();
	}

	public PatternRow() {
		this.id = lastId++;
		patternNodes = new ArrayList<PatternNode>();
		columnNames = new HashSet<String>();
	}

	public PatternRow(PatternRow patternRow, Object object) {
		this.id = lastId++;
		this.patternNodes = new ArrayList<PatternNode>();
		Map<PatternNode, PatternNode> mapOldNodesToNewNodes = new HashMap<>();
		Set<PatternRestriction> concreteRestrictionsSet = new HashSet<>();
		
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			PatternNode newPatternNode = patternNode.copy();
			this.patternNodes.add(newPatternNode);
			mapOldNodesToNewNodes.put(patternNode, newPatternNode);
			for(PatternRestriction concreteRestriction : patternNode.getPatternRestrictions()) {
				concreteRestrictionsSet.add(concreteRestriction);
			}
		}

		for(PatternRestriction cr : concreteRestrictionsSet) {
			PatternRestriction cr2 = new PatternRestriction(cr);
			for(PatternNode pn : cr.getPatternNodes()) {
				if(mapOldNodesToNewNodes.containsKey(pn)) {
					cr2.addNode2(mapOldNodesToNewNodes.get(pn));
				}
				else {
					cr2.addNode2(pn);
				}
			}
		}
	}

	public void addPatternRestriction(PatternRestriction concreteRestriction) {
		for (String column : concreteRestriction.getColumns()) {
			if(getNodeByName(column) != null)
				getNodeByName(column).addPatternRestriction(concreteRestriction);
		}
	}

	private PatternNode getNodeByName(String name) {
		for(PatternNode patternNode : patternNodes) {
			if(patternNode.getName().equals(name))
				return patternNode;
		}
		return null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addPatternNode(PatternNode patternNode) {
		patternNodes.add(patternNode);
		columnNames.add(patternNode.getName());
	}

	public List<PatternNode> getPatternNodes() {
		return patternNodes;
	}

	public void setPatternNodes(List<PatternNode> patternNodes) {
		this.patternNodes = patternNodes;
	}

	@Override
	public String toString() {
		return "\n      PatternRow [id=" + id + ", patternNodes(" + patternNodes.size() + ")=" + patternNodes + "]";
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}
	
	public PatternRow copy() {
		PatternRow patternRow = new PatternRow();
		for(PatternNode patternNode : patternNodes) {
			patternRow.addPatternNode(patternNode.copy());
		}
		return patternRow;
	}
	
	public void clearValues() {
		for(PatternNode patternNode : patternNodes) {
			patternNode.clearValues();
		}
	}
}
