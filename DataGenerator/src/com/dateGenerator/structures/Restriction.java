package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

import com.dateGenerator.engine.FinderUsedColumns;

public class Restriction implements RestrictionInterface {
	private String restrictionString;
	private List<String> columns;

	public Restriction(String restrictionString) {
		super();
		this.restrictionString = restrictionString;
		columns = new ArrayList<String>();
	}

	@Override
	public List<Restriction> getAllRestrictions() {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(this);
		return restrictions;
	}

	@Override
	public void getUsedColumns() {
		FinderUsedColumns finderUsedColumns = new FinderUsedColumns();
		// TODO Auto-generated method stub
		
	}
	
	public Restriction getNegative() {
		Restriction restriction = new Restriction("not(" + this.restrictionString + ")");
		restriction.columns.addAll(this.getColumns());
		return restriction;
	}

	public String getRestrictionString() {
		return restrictionString;
	}

	public void setRestrictionString(String restrictionString) {
		this.restrictionString = restrictionString;
	}

	public void addColumn(String column) {
		this.columns.add(column);
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "Restriction [restrictionString=" + restrictionString + "]";
	}
}
