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
		ConcreteRestriction concreteRestriction = new ConcreteRestriction(1, restriction);
//		node1.addConcreteRestriction(concreteRestriction);
//		node2.addConcreteRestriction(concreteRestriction);

		PatternNode node1 = new PatternNode("bonus", 190);
		PatternNode node2 = new PatternNode("placa_pod", 191);
		PatternRow row1 = new PatternRow(301);
		row1.addPatternNode(node1);
		row1.addPatternNode(node2);
		
		node1 = new PatternNode("placa_min", 192);
		node2 = new PatternNode("nazwa", 193);
		PatternRow row0 = new PatternRow(302);
		row0.addPatternNode(node1);
		row0.addPatternNode(node2);
		
		row1.addConcreteRestriction(concreteRestriction);
		row0.addConcreteRestriction(concreteRestriction);
		
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

	public PatternRow(int id) {
		this.id = id;
		patternNodes = new ArrayList<PatternNode>();
		columnNames = new HashSet<String>();
	}

	public PatternRow(PatternRow patternRow) {
		this.id = lastId++;
		this.patternNodes = new ArrayList<PatternNode>();
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			this.patternNodes.add(patternNode.copy());
		}
	}

	public PatternRow(PatternRow patternRow, Object object) {
		this.id = lastId++;
		this.patternNodes = new ArrayList<PatternNode>();
		Map<PatternNode, PatternNode> mapOldNodesToNewNodes = new HashMap<>();
		Set<ConcreteRestriction> concreteRestrictionsSet = new HashSet<>();
		
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			PatternNode newPatternNode = patternNode.copy();
			this.patternNodes.add(newPatternNode);
			mapOldNodesToNewNodes.put(patternNode, newPatternNode);
			for(ConcreteRestriction concreteRestriction : patternNode.getConcreteRestrictions()) {
				concreteRestrictionsSet.add(concreteRestriction);
			}
		}

		for(ConcreteRestriction cr : concreteRestrictionsSet) {
			ConcreteRestriction cr2 = new ConcreteRestriction(cr);
			for(PatternNode pn : cr.getNodes()) {
				if(mapOldNodesToNewNodes.containsKey(pn)) {
					cr2.addNode2(mapOldNodesToNewNodes.get(pn));
				}
				else {
					cr2.addNode2(pn);
				}
			}
		}
	}

	public void addRestriction(Restriction restriction) {
		for (String column : restriction.getColumns()) {
			if(getNodeByName(column) != null)
				getNodeByName(column).addRestriction(restriction.getRestrictionString());
		}
	}

	public void addConcreteRestriction(ConcreteRestriction concreteRestriction) {
		for (String column : concreteRestriction.getColumns()) {
			if(getNodeByName(column) != null)
				getNodeByName(column).addConcreteRestriction(concreteRestriction);
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
	
	public PatternRow copy2() {
		PatternRow patternRow = new PatternRow(id);
		for(PatternNode patternNode : patternNodes) {
			patternRow.addPatternNode(patternNode.copy2());
		}
		return patternRow;
	}

}
