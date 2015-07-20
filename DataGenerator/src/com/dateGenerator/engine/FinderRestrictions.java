package com.dateGenerator.engine;

import java.util.Stack;

import com.dateGenerator.structures.Restriction;
import com.dateGenerator.structures.RestrictionAndOr;
import com.dateGenerator.structures.RestrictionInterface;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

/**
 * For where clause finds all restrictions and puts it in hierarchical structure where root is rootRestriction, 
 * there are normal restrictions - leafes and and/or restrictions which are inner nodes of a tree
 *
 */
public class FinderRestrictions implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor, StatementVisitor {

	private RestrictionAndOr lastRestriction;
	private Stack<RestrictionAndOr> restrictionStack = new Stack<>();
	private RestrictionInterface rootRestriction;

	public RestrictionInterface getRootRestriction() {
		return rootRestriction;
	}
	
//	public void visitUnaryExpression(Expression expression) {
//		
//	}
	
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		if(binaryExpression.getStringExpression().equals("AND") ||
				binaryExpression.getStringExpression().equals("OR")) {
			RestrictionAndOr restrictionAndOr = new RestrictionAndOr(binaryExpression.getStringExpression(), binaryExpression.toString());
			if(rootRestriction == null) {
				rootRestriction = restrictionAndOr;
			}
			else {
				if(lastRestriction == null) {
					lastRestriction = restrictionStack.pop();
					lastRestriction.setRestrictionR(restrictionAndOr);
				}
				else {
					lastRestriction.setRestrictionL(restrictionAndOr);
				}
			}
			lastRestriction = restrictionAndOr;
			restrictionStack.push(restrictionAndOr);
			binaryExpression.getLeftExpression().accept(this);
			binaryExpression.getRightExpression().accept(this);
			
		}
		else {
			Restriction restriction = new Restriction(binaryExpression.toString(), binaryExpression);
			if(rootRestriction == null) {
				rootRestriction = restriction;
			}
			else {
				if(lastRestriction == null) {
					lastRestriction = restrictionStack.pop();
					lastRestriction.setRestrictionR(restriction);
				}
				else {
					lastRestriction.setRestrictionL(restriction);
				}
			}
			lastRestriction = null;
		}
			
	}
	
	@Override
	public void visit(Select arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Delete arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Update arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Insert arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Replace arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Drop arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Truncate arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CreateTable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExpressionList arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NullValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Function arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(InverseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JdbcParameter arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LongValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DateValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimeValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimestampValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Parenthesis parenthesis) {
		parenthesis.getExpression().accept(this);
		
	}

	@Override
	public void visit(StringValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Addition arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Division arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Multiplication arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Subtraction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AndExpression andExpression) {
		visitBinaryExpression(andExpression);
	}

	@Override
	public void visit(OrExpression orExpression) {
		visitBinaryExpression(orExpression);
	}

	@Override
	public void visit(Between between) {
		GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
		MinorThanEquals minorThanEquals = new MinorThanEquals();
		
		greaterThanEquals.setLeftExpression(between.getLeftExpression());
		greaterThanEquals.setRightExpression(between.getBetweenExpressionStart());
		
		minorThanEquals.setLeftExpression(between.getLeftExpression());
		minorThanEquals.setRightExpression(between.getBetweenExpressionEnd());

		System.out.println("Between: " + between+ ", gte: " + greaterThanEquals);
		
		AndExpression andExpression = new AndExpression(minorThanEquals, greaterThanEquals);
		
		visitBinaryExpression(andExpression);
	}

	@Override
	public void visit(EqualsTo equalsTo) {
		visitBinaryExpression(equalsTo);
		
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		visitBinaryExpression(greaterThan);
		
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		visitBinaryExpression(greaterThanEquals);
		
	}

	@Override
	public void visit(InExpression inExpression) {
		System.out.println("inExpression=" + inExpression);
		System.out.println("  leftExpression=" + inExpression.getLeftExpression() + ", items=" + inExpression.getItemsList());
		//visitBinaryExpression(inExpression);
		
	}

	@Override
	public void visit(IsNullExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LikeExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MinorThan minorThan) {
		visitBinaryExpression(minorThan);
		
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		visitBinaryExpression(minorThanEquals);
		
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		visitBinaryExpression(notEqualsTo);
		
	}

	@Override
	public void visit(Column arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CaseExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhenClause arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExistsExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AllComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnyComparisonExpression arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Matches arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseAnd arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseOr arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseXor arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Table arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SubSelect arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SubJoin arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlainSelect arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Union arg0) {
		// TODO Auto-generated method stub
		
	}

}
