package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

public class PatternRestriction {

	private int number;
	private Restriction restriction;
	private List<PatternNode> nodes;
	
	public void addNode(PatternNode node) {
		nodes.add(node);
	}
	
	public void addNode2(PatternNode node) {
		nodes.add(node);
		node.addConcreteRestrictionUnrecursively(this);
	}
	
	public void removeNode(PatternNode node) {
		node.removeConcreteRestrictionUnrecursively(this);
		nodes.remove(node);
	}
	
	public List<PatternNode> getPatternNodes() {
		return nodes;
	}

	public void setNodes(List<PatternNode> nodes) {
		this.nodes = nodes;
	}

	public PatternRestriction(PatternRestriction concreteRestriction) {
		this.number = concreteRestriction.getNumber();
		this.restriction = concreteRestriction.getRestriction();
		this.nodes = new ArrayList<>();
	}

	public PatternRestriction(int number, Restriction restriction) {
		this.nodes = new ArrayList<>();
		this.number = number;
		this.restriction = restriction.copy();
	}

	public List<String> getColumns() {
		return restriction.getColumns();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Restriction getRestriction() {
		return restriction;
	}

	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}

	@Override
	public String toString() {
		List<String> nodes = new ArrayList<>();
		for(PatternNode node : this.nodes) {
			nodes.add("PatternNode [name=" + node.getName() + ", id=" + node.getId() + "]");
		}
		return "\n            ConcreteRestriction [number=" + number + ", restriction="
				+ restriction + ", nodes=" + nodes + "]";
	}

	public void remove() {
		for(PatternNode pn : nodes) {
			pn.removeConcreteRestrictionUnrecursively(this);
		}
		
	}

}
