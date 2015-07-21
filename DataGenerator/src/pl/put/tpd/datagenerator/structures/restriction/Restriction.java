package pl.put.tpd.datagenerator.structures.restriction;

import java.util.ArrayList;
import java.util.List;

import pl.put.tpd.datagenerator.graphbuilding.FinderUsedColumns;
import pl.put.tpd.datagenerator.graphbuilding.NegateRestriction;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.operators.relational.InExpression;

public class Restriction implements RestrictionInterface {
	private String restrictionString;
	private List<String> columns;
	private Expression expression;
	
	public Restriction(String restrictionString, Expression expression) {
		this.restrictionString = restrictionString;
		columns = new ArrayList<String>();
		this.expression = expression;
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
		finderUsedColumns.findUsedColumns(expression);
		columns.addAll(finderUsedColumns.getUsedColumns());
	} 
	
	public Restriction getNegative() {
		if(expression instanceof BinaryExpression) {
			BinaryExpression negativeBinaryExpression = (BinaryExpression)NegateRestriction.negateExpression(expression);
			Restriction restriction = new Restriction(negativeBinaryExpression.toString(), negativeBinaryExpression);
			restriction.columns.addAll(this.getColumns());
			return restriction;
		} else if(expression instanceof InExpression) {
			InExpression inExpression = (InExpression)expression;
			InExpression negativeExpression = (InExpression)NegateRestriction.negateExpression(expression);
			Restriction restriction = new Restriction(negativeExpression.toString(), negativeExpression);
			restriction.columns.addAll(this.getColumns());
			System.out.println(restriction);
			return restriction;
		}
		else {
			System.out.println("Restriction.getNegative(), unknown expression to make negative");
		}
		return null;
	}
	
	public Restriction copy() {
		Restriction restriction = new Restriction(new String(restrictionString), expression) ;
		restriction.columns.addAll(this.columns);
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
		return (BinaryExpression)expression;
	}
	
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Restriction [restrictionString=" + restrictionString + "]";
	}
}
