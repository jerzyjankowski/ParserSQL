package com.dateGenerator.structures.output;

import java.util.ArrayList;
import java.util.List;

public class OutputRow {
	private List<String> nodes;

	public OutputRow() { 
		nodes = new ArrayList<>();
	}
	
	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(String node) {
		this.nodes.add(node);
	}

	@Override
	public String toString() {
		return "\n      Row [nodes=" + nodes + "]";
	}
	
	
}
