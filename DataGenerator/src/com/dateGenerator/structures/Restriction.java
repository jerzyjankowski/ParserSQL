package com.dateGenerator.structures;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;

import com.dateGenerator.engine.FinderUsedColumns;

public class Restriction implements RestrictionInterface {
	private String restrictionString;
	private List<String> columns;
	private BinaryExpression binaryExpression;
	
	public Restriction(String restrictionString, BinaryExpression binaryExpression) {
		this.restrictionString = restrictionString;
		columns = new ArrayList<String>();
		this.binaryExpression = binaryExpression;
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
		finderUsedColumns.visitBinaryExpression(binaryExpression);
		columns.addAll(finderUsedColumns.getUsedColumns());
	}
	
	public Restriction getNegative() {
		Restriction restriction = new Restriction("not(" + this.restrictionString + ")", binaryExpression);//TODO negate expression
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

	public BinaryExpression getBinaryExpression() {
		return binaryExpression;
	}

	public void setBinaryExpression(BinaryExpression binaryExpression) {
		this.binaryExpression = binaryExpression;
	}

	@Override
	public String toString() {
		return "Restriction [restrictionString=" + restrictionString + "]";
	}
}