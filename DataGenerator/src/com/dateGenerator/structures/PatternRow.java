package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class PatternRow {
	private int id;
	static private int lastId = 0;
	private List<PatternNode> patternNodes;

	public PatternRow() {
		this.id = lastId++;
		patternNodes = new ArrayList<PatternNode>();
	}

	public PatternRow(PatternRow patternRow) {
		this.id = lastId++;
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			PatternNode newPatternNode = new PatternNode(patternNode.getName());
			newPatternNode.copy(patternNode);
			this.patternNodes.add(newPatternNode);
		}
	}

	public void addRestriction(Restriction restriction) {
		for (String column : restriction.getColumns()) {

		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addPatternNode(PatternNode patternNode) {
		patternNodes.add(patternNode);
	}

	public List<PatternNode> getPatternNodes() {
		return patternNodes;
	}

	public void setPatternNodes(List<PatternNode> patternNodes) {
		this.patternNodes = patternNodes;
	}

	@Override
	public String toString() {
		return "PatternRow [id=" + id + ", patternNodes=" + patternNodes + "]";
	}

}
