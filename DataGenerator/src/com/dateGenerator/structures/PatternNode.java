package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PatternNode {
	private String name;
	private static int lastId = 0;
	private int id;


	private List<String> restrictions;
	private List<Integer> concreteRestrictionIds;
	private List<ConcreteRestriction> concreteRestrictions;
	
	public static void main(String... args) {
		PatternNode node1 = new PatternNode("bonus", 190);
		PatternNode node2 = new PatternNode("placa_pod", 191);
		ConcreteRestriction concreteRestriction = new ConcreteRestriction(1, new Restriction("placa_pod<bonus", null));
		node1.addConcreteRestriction(concreteRestriction);
		node2.addConcreteRestriction(concreteRestriction);
		
		//PatternNode node3 = new PatternNode(node2, new ArrayList<Integer>(Arrays.asList(192,193)));
		//PatternNode node4 = new PatternNode(node1, new ArrayList<Integer>(Arrays.asList(192,193)));
		
		PatternNode node5 = node1.copy();
		PatternNode node6 = node2.copy();
		
		Map<PatternNode, PatternNode> mapa = new HashMap<>();
		mapa.put(node1, node5);
		mapa.put(node2, node6);
		Set<ConcreteRestriction> set = new HashSet<>();
		set.add(concreteRestriction);
		set.add(concreteRestriction);
		
		for(ConcreteRestriction cr : set) {
			ConcreteRestriction concreteRestriction2 = new ConcreteRestriction(cr);
			for(PatternNode pn : cr.getNodes()) {
				if(mapa.containsKey(pn)) {
					concreteRestriction2.addNode2(mapa.get(pn));
				}
				else {
					concreteRestriction2.addNode2(pn);
				}
			}
		}
		
		System.out.println("\nnode1: " + node1);
		System.out.println("\nnode2: " + node2);
		//System.out.println("\nnode3: " + node3);
		//System.out.println("\nnode4: " + node4);
		System.out.println("\nnode5: " + node5);
		System.out.println("\nnode6: " + node6);
	}
	
	public PatternNode(String name) {
		this.name = name;
		restrictions = new ArrayList<String>();
		concreteRestrictions = new ArrayList<>();
		concreteRestrictionIds = new ArrayList<>();
		this.id = lastId++;
	}

	public PatternNode(String name, int id) {
		this.name = name;
		restrictions = new ArrayList<String>();
		concreteRestrictions = new ArrayList<>();
		concreteRestrictionIds = new ArrayList<>();
		this.id = id;
	}
	
	public PatternNode(PatternNode patternNode, List<Integer> idsInRow) {
		this.name = patternNode.getName();
		this.id = lastId++;
		restrictions = new ArrayList<String>();
		concreteRestrictionIds = new ArrayList<>();

		concreteRestrictions = new ArrayList<>();
		List<ConcreteRestriction> listCR = new ArrayList<ConcreteRestriction>();
		for(ConcreteRestriction concreteRestriction : patternNode.getConcreteRestrictions()) {
			listCR.add(concreteRestriction);
		}
		for(ConcreteRestriction concreteRestriction : listCR) {
			//TODO kopiowaæ inaczej concreteRestriciton kiedy odnosz¹ siê tylko do wêz³ów wewnêtrznych
			ConcreteRestriction newConcreteRestriction = new ConcreteRestriction(concreteRestriction);
			newConcreteRestriction.removeNode(patternNode);
			newConcreteRestriction.addNode(this);
			concreteRestrictions.add(newConcreteRestriction);
		}
	}
	
	public PatternNode copy() {
		PatternNode patternNode = new PatternNode(name);
		patternNode.restrictions.addAll(this.getRestrictions());
		return patternNode;
	}
	
	public PatternNode copy2() {//exact copy with references to concreteRestrictions
		PatternNode patternNode = new PatternNode(name, id);
		patternNode.concreteRestrictionIds.addAll(this.getConcreteRestrictionIds());
		patternNode.concreteRestrictions.addAll(this.getConcreteRestrictions());
		return patternNode;
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

	public void addConcreteRestriction(ConcreteRestriction concreteRestriction) {
		concreteRestrictions.add(concreteRestriction);
		concreteRestriction.addNode(this);
	}
	
	public void addConcreteRestrictionUnrecursively(ConcreteRestriction concreteRestriction) {
		concreteRestrictions.add(concreteRestriction);
	}
	
	public void removeConcreteRestrictionUnrecursively(ConcreteRestriction concreteRestriction) {
		concreteRestrictions.remove(concreteRestriction);
	}
	
	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Integer> getConcreteRestrictionIds() {
		return concreteRestrictionIds;
	}

	public void setConcreteRestrictionIds(List<Integer> concreteRestrictionIds) {
		this.concreteRestrictionIds = concreteRestrictionIds;
	}

	public List<ConcreteRestriction> getConcreteRestrictions() {
		return concreteRestrictions;
	}

	public void setConcreteRestrictions(List<ConcreteRestriction> concreteRestrictions) {
		this.concreteRestrictions = concreteRestrictions;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "\n         PatternNode [name=" + name + ", id=" + id + ", concreteRestrictions="
				+ concreteRestrictions + "]";
	}

	public String toString2() {
		return "PatternNode [name=" + name + ", restrictions=" + restrictions + "]";
	}

	public void remove() {
		int i=0, j=0;
		List<ConcreteRestriction> listCR = new ArrayList<>();
		for(ConcreteRestriction cr : concreteRestrictions) {
			listCR.add(cr);
			i++;
		}
		for(ConcreteRestriction cr : listCR) {
			cr.remove();
			j++;
		}
		
	}
	

}
