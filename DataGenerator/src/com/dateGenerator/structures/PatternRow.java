package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternRow {
	private int id;
	static private int lastId = 0;
	private List<PatternNode> patternNodes;
	private Set<String> columnNames;

	public PatternRow() {
		this.id = lastId++;
		patternNodes = new ArrayList<PatternNode>();
		columnNames = new HashSet<String>();
	}

	public PatternRow(PatternRow patternRow) {
		this.id = lastId++;
		this.patternNodes = new ArrayList<PatternNode>();
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			PatternNode newPatternNode = new PatternNode(patternNode.getName());
			newPatternNode.copy(patternNode);
			this.patternNodes.add(newPatternNode);
		}
	}

	public void addRestriction(Restriction restriction) {
		for (String column : restriction.getColumns()) {
			if(getNodeByName(column) != null)
				getNodeByName(column).addRestriction(restriction.getRestrictionString());
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
		return "PatternRow [id=" + id + ", patternNodes(" + patternNodes.size() + ")=" + patternNodes + "]";
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

}
