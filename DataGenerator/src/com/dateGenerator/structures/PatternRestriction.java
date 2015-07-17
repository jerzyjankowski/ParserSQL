package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatternRestriction {

	private int number;
	private Restriction restriction;
	private List<PatternNode> nodes;
	private int collisionCnt = 0;
	
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
		return "\n            ConcreteRestriction [number=" + number + ", restriction="
				+ restriction + ", nodes=" + nodes + "]";
	}

	public void remove() {
		for(PatternNode pn : nodes) {
			pn.removeConcreteRestrictionUnrecursively(this);
		}
		
	}
	
	public class Unfinished extends Exception {
	}
	class UnexpectedExpression extends Exception{
		
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
				if(expression1.contains(node.getName())) {//always?
					expression1 = expression1.replace(node.getName(), node.getValue().toString());
//					expression1 = expression1.replace(node.getName(), ""+node.getId());
					
				}
			}
//			System.out.println("expression: " + expression1);
		}
		catch(Exception e){
			System.out.println("catched exception " + e.getMessage());
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
