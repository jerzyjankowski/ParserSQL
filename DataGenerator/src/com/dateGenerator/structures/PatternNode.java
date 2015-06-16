package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class PatternNode {
	private String name;
	private List<String> restrictions;

	public PatternNode(String name) {
		super();
		this.name = name;
		restrictions = new ArrayList<String>();
	}
	
	public void copy(PatternNode patternNode) {
		this.name = patternNode.getName();
		this.restrictions.addAll(patternNode.getRestrictions());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addRestriction(String restriction) {
		restrictions.add(restriction);
	}
	
	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return "PatternNode [name=" + name + ", restrictions=" + restrictions + "]";
	}

}
