package pl.put.tpd.datagenerator.structures.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.put.tpd.datagenerator.datagenerating.PatternFulfilledChecking;
import pl.put.tpd.datagenerator.structures.pattern.exceptions.UnexpectedExpression;
import pl.put.tpd.datagenerator.structures.restriction.Restriction;

public class PatternRestriction {

	private Restriction restriction;
	private List<PatternNode> nodes;
	private int collisionCnt = 0;
	
	public PatternRestriction(PatternRestriction patternRestriction) {
		this.restriction = patternRestriction.getRestriction();
		this.nodes = new ArrayList<>();
	}

	public PatternRestriction(Restriction restriction) {
		this.nodes = new ArrayList<>();
		this.restriction = restriction.copy();
	}

	/**
	 * removes from connected nodes connections to that restriction
	 */
	public void remove() {
		for(PatternNode pn : nodes) {
			pn.removePatternRestrictionUnrecursively(this); 
		}
	}
	
	/**
	 * 
	 * @return if expression is valid or true if there are some nodes connected that doesn't have values generated
	 * @throws UnexpectedExpression
	 */
	public boolean check() throws UnexpectedExpression {
		return PatternFulfilledChecking.check(this);
	}
	
	/**
	 * add node to list of connected nodes
	 * @param node
	 */
	public void addNode(PatternNode node) {
		nodes.add(node);
	}
	
	/**
	 * add node to list of connected nodes and add self to that node's list of connected restrictions
	 * @param node
	 */
	public void addNodeAddingSelfToNode(PatternNode node) {
		nodes.add(node);
		node.addPatternRestrictionUnrecursively(this);
	}
	
	/**
	 * remove self from node's list of connected restrictions and remove that node from self's list of connected nodes
	 * @param node
	 */
	public void removeNode(PatternNode node) {
		node.removePatternRestrictionUnrecursively(this);
		nodes.remove(node);
	}
	
	public List<PatternNode> getPatternNodes() {
		return nodes;
	}

	public void setNodes(List<PatternNode> nodes) {
		this.nodes = nodes;
	}

	public List<String> getColumns() {
		return restriction.getColumns();
	}

	public Restriction getRestriction() {
		return restriction;
	}

	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}
	
	public int getCollisionCnt() {
		return collisionCnt;
	}

	public void setCollisionCnt(int collisionCnt) {
		this.collisionCnt = collisionCnt;
	}
	
	/**
	 * increment collision counter
	 */
	public void incrementCollisionCnt() {
		this.collisionCnt++;
	}

	public void clearCollisionCnt() {
		this.collisionCnt = 0;
	}
	
	@Override
	public String toString() {
		List<String> nodes = new ArrayList<>();
		for(PatternNode node : this.nodes) {
			nodes.add("PatternNode [name=" + node.getName() + ", id=" + node.getId() + "]");
		}
		return "\n              PatternRestriction [ restriction="
				+ restriction + ", nodes=" + nodes + "]";
	}	
}
