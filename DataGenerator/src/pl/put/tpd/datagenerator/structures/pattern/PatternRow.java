package pl.put.tpd.datagenerator.structures.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.put.tpd.datagenerator.structures.pattern.exceptions.NotOnlySelfRestrictions;
import pl.put.tpd.datagenerator.structures.restriction.Restriction;

public class PatternRow {
	private int id;
	static private int lastId = 0;
	private List<PatternNode> patternNodes;
	private Set<String> columnNames;
	private boolean doneWithGenerating;
	
	private String tableName = "";
	private String tableAlias = "";

	public static void main(String... args) {
		Restriction restriction = new Restriction("placa_min<placa_pod", null);
		restriction.addColumn("placa_pod");
		restriction.addColumn("placa_min");
		PatternRestriction patternRestriction = new PatternRestriction(restriction);
//		node1.addPatternRestriction(patternRestriction);
//		node2.addPatternRestriction(patternRestriction);

		PatternNode node1 = new PatternNode("INTEGER", "bonus", 190);
		PatternNode node2 = new PatternNode("INTEGER", "placa_pod", 191);
		PatternRow row1 = new PatternRow();
		row1.addPatternNode(node1);
		row1.addPatternNode(node2);
		
		node1 = new PatternNode("INTEGER", "placa_min", 192);
		node2 = new PatternNode("INTEGER", "nazwa", 193);
		PatternRow row0 = new PatternRow();
		row0.addPatternNode(node1);
		row0.addPatternNode(node2);
		
		row1.addPatternRestriction(patternRestriction);
		row0.addPatternRestriction(patternRestriction);
		
		PatternRow row2 = new PatternRow(row1);

		row1.remove();
		
		System.out.println("\nrow0: " + row0);
		System.out.println("\nrow1: " + row1);
		System.out.println("\nrow2: " + row2);
	}

	public PatternRow() {
		this.id = lastId++;
		patternNodes = new ArrayList<PatternNode>();
		columnNames = new HashSet<String>();
	}
	
	/**
	 * 
	 */
	public void remove() {
		for(PatternNode patternNode : patternNodes) {
			patternNode.remove();
		}
		patternNodes.clear();
	}

	/**
	 * 
	 * @param patternRow
	 */
	public PatternRow(PatternRow patternRow) {
		this.id = lastId++;
		this.patternNodes = new ArrayList<PatternNode>();
		this.tableName = patternRow.getTableName();
		this.tableAlias= patternRow.getTableAlias();
		
		Map<PatternNode, PatternNode> mapOldNodesToNewNodes = new HashMap<>();
		Set<PatternRestriction> patternRestrictionsSet = new HashSet<>();
		
		for (PatternNode patternNode : patternRow.getPatternNodes()) {
			PatternNode newPatternNode = patternNode.copy();
			this.patternNodes.add(newPatternNode);
			mapOldNodesToNewNodes.put(patternNode, newPatternNode);
			for(PatternRestriction patternRestriction : patternNode.getPatternRestrictions()) {
				patternRestrictionsSet.add(patternRestriction);
			}
		}

		for(PatternRestriction cr : patternRestrictionsSet) {
			PatternRestriction cr2 = new PatternRestriction(cr);
			for(PatternNode pn : cr.getPatternNodes()) {
				if(mapOldNodesToNewNodes.containsKey(pn)) {
					cr2.addNodeAddingSelfToNode(mapOldNodesToNewNodes.get(pn));
				}
				else {
					cr2.addNodeAddingSelfToNode(pn);
				}
			}
		}
	}

	/**
	 * 
	 * @param patternRestriction
	 */
	public void addPatternRestriction(PatternRestriction patternRestriction) {
		for (String column : patternRestriction.getColumns()) {
			if(getNodeByName(column) != null) {
				getNodeByName(column).addPatternRestriction(patternRestriction);
			}
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private PatternNode getNodeByName(String name) {
		for(PatternNode patternNode : patternNodes) {
			if(name.equals(patternNode.getName()))
				return patternNode;
			if(name.equals(tableName + "." + patternNode.getName()))
				return patternNode;
			if(name.equals(tableAlias + "." + patternNode.getName()))
				return patternNode;
			
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void clearValues() {
		for(PatternNode patternNode : patternNodes) {
			patternNode.clearValue();
		}
	}
	
	/**
	 * copy patternRow without restriction on included patternNodes
	 * @return
	 */
	public PatternRow copy() {
		PatternRow patternRow = new PatternRow();
		patternRow.setTableName(tableName);
		patternRow.setTableAlias(tableAlias);
		for(PatternNode patternNode : patternNodes) {
			patternRow.addPatternNode(patternNode.copy());
		}
		return patternRow;
	}

	/**
	 * copy patternRow with restrictions from patternNodes from that patternRow that have only one node connected
	 * used to initiate patternRow
	 * @return
	 */
	public PatternRow copyWithSelfRestrictions() {
		PatternRow patternRow = new PatternRow();
		patternRow.setTableName(tableName);
		patternRow.setTableAlias(tableAlias);
		for(PatternNode patternNode : patternNodes) {
			try {
				patternRow.addPatternNode(patternNode.copyWithSelfRestrictions());
			} catch (NotOnlySelfRestrictions e) {
				e.printStackTrace();
			}
		}
		return patternRow;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		for(PatternNode patternNode : patternNodes) {
			patternNode.setTableName(tableName);
		}
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
		for(PatternNode patternNode : patternNodes) {
			patternNode.setTableAlias(tableAlias);
		}
	}

	public void addPatternNode(PatternNode patternNode) {
		patternNode.setTableName(tableName);
		patternNodes.add(patternNode);
		columnNames.add(patternNode.getName());
	}

	public List<PatternNode> getPatternNodes() {
		return patternNodes;
	}

	public void setPatternNodes(List<PatternNode> patternNodes) {
		this.patternNodes = patternNodes;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public boolean isDoneWithGenerating() {
		return doneWithGenerating;
	}

	public void setDoneWithGenerating(boolean doneWithGenerating) {
		this.doneWithGenerating = doneWithGenerating;
	}

	public boolean isDone() {
		return isDoneWithGenerating();
	}

	public void markDone() {
		setDoneWithGenerating(true);
	}

	public void unmarkDone() {
		setDoneWithGenerating(false);
	}

	@Override
	public String toString() {
		return "\n        PatternRow [id=" + id + ", tableName=" + tableName + ", tableAlias=" + tableAlias +
				", patternNodes(" + patternNodes.size() + ")=" + patternNodes + "]";
	}
}
