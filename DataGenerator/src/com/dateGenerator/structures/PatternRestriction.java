package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatternRestriction {

	private Restriction restriction;
	private List<PatternNode> nodes;
	private int collisionCnt = 0;
	
	public void addNode(PatternNode node) {
		nodes.add(node);
	}
	
	public void addNode2(PatternNode node) {
		nodes.add(node);
		node.addPatternRestrictionUnrecursively(this);
	}
	
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

	public PatternRestriction(PatternRestriction patternRestriction) {
		this.restriction = patternRestriction.getRestriction();
		this.nodes = new ArrayList<>();
	}

	public PatternRestriction(Restriction restriction) {
		this.nodes = new ArrayList<>();
		this.restriction = restriction.copy();
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

	public void remove() {
		for(PatternNode pn : nodes) {
			pn.removePatternRestrictionUnrecursively(this); 
		}
		
	}
	
	public class Unfinished extends Exception {
	}
	
	public class UnexpectedExpression extends Exception{
	}
	
	public boolean check() throws Unfinished, UnexpectedExpression {
		
		for(PatternNode node : nodes) {
			if(node.getValue() == null)
				throw new Unfinished();
		}
		
		String operation = getRestriction().getBinaryExpression().getStringExpression();
		String expression0 = getRestriction().getBinaryExpression().toString();
		String expression1 = new String(expression0);
		try {
			for(PatternNode node : nodes) {
				expression1 = node.replaceNameWithValue(expression1);
			}
		}
		catch(Exception e){
			System.out.println("cought exception " + e);
		}
		expression1 = expression1.replace(" ", "");
		if(operation.equals("=")) {
			String[] expressionArr = expression1.split("=");
			return ((Integer.parseInt(expressionArr[0]))==(Integer.parseInt(expressionArr[1])));
		} else if(operation.equals("<>")) {
			String[] expressionArr = expression1.split("<>");
			return ((Integer.parseInt(expressionArr[0]))!=(Integer.parseInt(expressionArr[1])));
		} else if(operation.equals("<")) {
			String[] expressionArr = expression1.split("<");
			return ((Integer.parseInt(expressionArr[0]))<(Integer.parseInt(expressionArr[1])));
		} else if(operation.equals("<=")) {
			String[] expressionArr = expression1.split("<=");
			return ((Integer.parseInt(expressionArr[0]))<=(Integer.parseInt(expressionArr[1])));
		} else if(operation.equals(">")) {
			String[] expressionArr = expression1.split(">");
			return ((Integer.parseInt(expressionArr[0]))>(Integer.parseInt(expressionArr[1])));
		} else if(operation.equals(">=")) {
			String[] expressionArr = expression1.split(">=");
			return ((Integer.parseInt(expressionArr[0]))>=(Integer.parseInt(expressionArr[1])));
		}
		throw new UnexpectedExpression();
	}

	
}
