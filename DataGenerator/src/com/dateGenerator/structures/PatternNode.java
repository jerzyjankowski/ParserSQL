package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.dateGenerator.engine.datagenerating.NodeValueGenerator;

public class PatternNode {
	private String name;
	private static int lastId = 0;
	private int id;
	private String type;
	private Integer value;


	private String tableName = "";
	private String tableAlias = "";

	private List<String> restrictions;
	private List<Integer> patternRestrictionIds;
	private List<PatternRestriction> patternRestrictions;
	
	public static void main(String... args) {
		PatternNode node1 = new PatternNode("integer", "bonus", 190);
		PatternNode node2 = new PatternNode("integer", "placa_pod", 191);
		PatternRestriction patternRestriction = new PatternRestriction(new Restriction("placa_pod<bonus", null));
		node1.addPatternRestriction(patternRestriction);
		node2.addPatternRestriction(patternRestriction);
		
		//PatternNode node3 = new PatternNode(node2, new ArrayList<Integer>(Arrays.asList(192,193)));
		//PatternNode node4 = new PatternNode(node1, new ArrayList<Integer>(Arrays.asList(192,193)));
		
		PatternNode node5 = node1.copy();
		PatternNode node6 = node2.copy();
		
		Map<PatternNode, PatternNode> mapa = new HashMap<>();
		mapa.put(node1, node5);
		mapa.put(node2, node6);
		Set<PatternRestriction> set = new HashSet<>();
		set.add(patternRestriction);
		set.add(patternRestriction);
		
		for(PatternRestriction cr : set) {
			PatternRestriction patternRestriction2 = new PatternRestriction(cr);
			for(PatternNode pn : cr.getPatternNodes()) {
				if(mapa.containsKey(pn)) {
					patternRestriction2.addNode2(mapa.get(pn));
				}
				else {
					patternRestriction2.addNode2(pn);
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
	
	public PatternNode(String type, String name) {
		this.name = name;
		restrictions = new ArrayList<String>();
		patternRestrictions = new ArrayList<>();
		patternRestrictionIds = new ArrayList<>();
		this.type = type;
		this.id = lastId++;
	}

	public PatternNode(String type, String name, int id) {
		this.name = name;
		restrictions = new ArrayList<String>();
		patternRestrictions = new ArrayList<>();
		patternRestrictionIds = new ArrayList<>();
		this.type = type;
		this.id = id;
	}
	
	public String replaceNameWithValue(String expression) {
		
		String notCharPattern = "[^a-zA-Z0-9_.]";
		
		String columnName = name;
		String columnPattern0 = "("+notCharPattern+")(" + columnName + ")("+notCharPattern+")";
		String columnPattern1 = "^(" + columnName + ")("+notCharPattern+")";
		String columnPattern2 = "("+notCharPattern+")(" + columnName + ")$";
		
		String tnColumnName = tableName + "." + name;
		String tnColumnPattern0 = "("+notCharPattern+")(" + tnColumnName + ")("+notCharPattern+")";
		String tnColumnPattern1 = "^(" + tnColumnName + ")("+notCharPattern+")";
		String tnColumnPattern2 = "("+notCharPattern+")(" + tnColumnName + ")$";
		
		String taColumnName = tableAlias + "." + name;
		String taColumnPattern0 = "("+notCharPattern+")(" + taColumnName + ")("+notCharPattern+")";
		String taColumnPattern1 = "^(" + taColumnName + ")("+notCharPattern+")";
		String taColumnPattern2 = "("+notCharPattern+")(" + taColumnName + ")$";
		
    	expression = expression.replaceAll(columnPattern0, "$1" + value + "$3").replaceAll(columnPattern1, value + "$2").replaceAll(columnPattern2, "$1" + value);

		if(!tableName.equals(""))
    		expression = expression.replaceAll(tnColumnPattern0, "$1" + value + "$3").replaceAll(tnColumnPattern1, value + "$2").replaceAll(tnColumnPattern2, "$1" + value);

		if(!tableAlias.equals(""))
    		expression = expression.replaceAll(taColumnPattern0, "$1" + value + "$3").replaceAll(taColumnPattern1, value + "$2").replaceAll(taColumnPattern2, "$1" + value);

    	System.out.println("Expression after replaces: " + expression);
		return expression;
	}
	
	public PatternNode copy() {
		PatternNode patternNode = new PatternNode(type, name);
		patternNode.setTableName(tableName);
		patternNode.setTableAlias(tableAlias);
		patternNode.restrictions.addAll(this.getRestrictions());
		return patternNode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPatternRestriction(PatternRestriction patternRestriction) {
		patternRestrictions.add(patternRestriction);
		patternRestriction.addNode(this);
	}
	
	public void addPatternRestrictionUnrecursively(PatternRestriction patternRestriction) {
		patternRestrictions.add(patternRestriction);
	}
	
	public void removePatternRestrictionUnrecursively(PatternRestriction patternRestriction) {
		patternRestrictions.remove(patternRestriction);
	}
	
	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Integer> getPatternRestrictionIds() {
		return patternRestrictionIds;
	}

	public void setPatternRestrictionIds(List<Integer> patternRestrictionIds) {
		this.patternRestrictionIds = patternRestrictionIds;
	}

	public List<PatternRestriction> getPatternRestrictions() {
		return patternRestrictions;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	@Override
	public String toString() {
		return "\n         PatternNode [type=" + type + " name=" + name + ", id=" + id + ", tableAlias=" + tableAlias + ", value=" + value 
				+ ", patternRestrictions=" + patternRestrictions + "]";
	}

	public String toString2() {
		return "PatternNode [name=" + name + ", restrictions=" + restrictions + "]";
	}

	public void remove() {
		int i=0, j=0;
		List<PatternRestriction> listCR = new ArrayList<>();
		for(PatternRestriction cr : patternRestrictions) {
			listCR.add(cr);
			i++;
		}
		for(PatternRestriction cr : listCR) {
			cr.remove();
			j++;
		}
		
	}
	
	public void generateValue() {
		NodeValueGenerator.generateValue(this);
	}
	
	public void generateSpamValue() {
		NodeValueGenerator.generateSpamValue(this);
	}
	
	public void clearValues() {
		setValue(null);
		for(PatternRestriction patternRestriction : patternRestrictions) {
			patternRestriction.clearCollisionCnt();
		}
	}

}
