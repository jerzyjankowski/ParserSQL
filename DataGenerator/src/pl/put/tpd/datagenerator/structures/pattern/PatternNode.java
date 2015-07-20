package pl.put.tpd.datagenerator.structures.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import pl.put.tpd.datagenerator.datagenerating.NodeValueGenerator;
import pl.put.tpd.datagenerator.datagenerating.exceptions.NotExpectedNodeTypeException;
import pl.put.tpd.datagenerator.structures.pattern.exceptions.NotOnlySelfRestrictions;
import pl.put.tpd.datagenerator.structures.restriction.Restriction;

public class PatternNode {
	private String name;
	private static int lastId = 0;
	private int id;
	private String type;
	private Integer value;


	private String tableName = "";
	private String tableAlias = "";

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
					patternRestriction2.addNodeAddingSelfToNode(mapa.get(pn));
				}
				else {
					patternRestriction2.addNodeAddingSelfToNode(pn);
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
		patternRestrictions = new ArrayList<>();
		this.type = type;
		this.id = lastId++;
	}

	public PatternNode(String type, String name, int id) {
		this.name = name;
		patternRestrictions = new ArrayList<>();
		this.type = type;
		this.id = id;
	}
	/**
	 * removes all restrictions connected with that patternNode
	 */
	public void remove() {
		List<PatternRestriction> listCR = new ArrayList<>();
		for(PatternRestriction cr : patternRestrictions) {
			listCR.add(cr);
		}
		for(PatternRestriction cr : listCR) {
			cr.remove();
		}
	}
	
	/**
	 * removes patternRestriction from list of patternRestrictions connected to that node
	 * @param patternRestriction
	 */
	public void removePatternRestrictionUnrecursively(PatternRestriction patternRestriction) {
		patternRestrictions.remove(patternRestriction);
	}

	/**
	 * generate value using static class
	 */
	public void generateValue() {
		try {
			NodeValueGenerator.generateValue(this);
		} catch (NotExpectedNodeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * set null value and clear collision counter in connected restrictions
	 */
	public void clearValue() {
		setValue(null);
		for(PatternRestriction patternRestriction : patternRestrictions) {
			patternRestriction.clearCollisionCnt();
		}
	}
	/**
	 * 
	 * @param expression
	 * @return String expression with values of node instead of his fullname appearance with column name or table name and column name or table alias and column name
	 */
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

		return expression;
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
		return "\n           PatternNode [type=" + type + " name=" + name + ", id=" + id + ", tableAlias=" + tableAlias + ", value=" + value 
				+ ", patternRestrictions=" + patternRestrictions + "]";
	}
	
	/**
	 * 
	 * @return patternNode with same type, name, table name and table aliases, connected restrictions are nor preserved nor copied
	 */
	public PatternNode copy() {
		PatternNode patternNode = new PatternNode(type, name);
		patternNode.setTableName(tableName);
		patternNode.setTableAlias(tableAlias);
		return patternNode;
	}
	
	/**
	 * 
	 * @return patternNode with same type, name, table name and table aliases, connected restrictions copied but connected to that new patternNode only when they where connected only to this node
	 * @throws NotOnlySelfRestrictions
	 */
	public PatternNode copyWithSelfRestrictions() throws NotOnlySelfRestrictions{
		PatternNode patternNode = new PatternNode(type, name);
		patternNode.setTableName(tableName);
		patternNode.setTableAlias(tableAlias);
		for(PatternRestriction patternRestriction : patternRestrictions) {
			for(PatternNode pn : patternRestriction.getPatternNodes())
				if(pn != this) 
					throw new NotOnlySelfRestrictions();  
			PatternRestriction newPatternRestriction = new PatternRestriction(patternRestriction);
			newPatternRestriction.addNode(patternNode);
			patternNode.addPatternRestriction(newPatternRestriction);
		}
		return patternNode;
	}

}
