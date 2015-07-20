package pl.put.tpd.datagenerator.graphbuilding;

import java.util.ArrayList;
import java.util.List;

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
 * for a restriction finds columns by name used in it and contain them in usedColumns list
 * @author Jurij
 *
 */
public class FinderUsedColumns implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor, StatementVisitor {

	private List<String> usedColumns = new ArrayList<String>();
	
	public List<String> getUsedColumns() {
		return usedColumns;
	}

	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		if(binaryExpression.getLeftExpression() instanceof Column) {
			usedColumns.add(binaryExpression.getLeftExpression().toString());
		}
		else {
			binaryExpression.getLeftExpression().accept(this);
		}
		if(binaryExpression.getRightExpression() instanceof Column) {
			usedColumns.add(binaryExpression.getRightExpression().toString());
		}
		else {
			binaryExpression.getRightExpression().accept(this);
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
	public void visit(Addition addition) {
		visitBinaryExpression(addition);
		
	}

	@Override
	public void visit(Division division) {
		visitBinaryExpression(division);
		
	}

	@Override
	public void visit(Multiplication multiplication) {
		visitBinaryExpression(multiplication);
		
	}

	@Override
	public void visit(Subtraction subtraction) {
		visitBinaryExpression(subtraction);
		
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
	public void visit(Between arg0) {
		// TODO Auto-generated method stub
		
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
	public void visit(InExpression arg0) {
		// TODO Auto-generated method stub
		
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
	public void visit(NotEqualsTo arg0) {
		// TODO Auto-generated method stub
		
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
