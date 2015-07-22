package pl.put.tpd.datagenerator.graphbuilding;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
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
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class NegateRestriction {

	static public Expression negateExpression(Expression expression) {
		if(expression instanceof BinaryExpression) {
			BinaryExpression binaryExpression = (BinaryExpression)expression;
			if (binaryExpression instanceof EqualsTo) {
				BinaryExpression be = new NotEqualsTo();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}else if (binaryExpression instanceof NotEqualsTo) {
				BinaryExpression be = new EqualsTo();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}else if (binaryExpression instanceof GreaterThan) {
				BinaryExpression be = new MinorThanEquals();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}else if (binaryExpression instanceof GreaterThanEquals) {
				BinaryExpression be = new MinorThan();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}else if (binaryExpression instanceof MinorThan) {
				BinaryExpression be = new GreaterThanEquals();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}else if (binaryExpression instanceof MinorThanEquals) {
				BinaryExpression be = new GreaterThan();
				be.setLeftExpression(binaryExpression.getLeftExpression());
				be.setRightExpression(binaryExpression.getRightExpression());
				return be;
			}
		}
		else if(expression instanceof InExpression) {
			InExpression inExpression = (InExpression)expression;
			InExpression negativeExpression = new InExpression(inExpression.getLeftExpression(), inExpression.getItemsList());
			negativeExpression.setNot(true);
			return negativeExpression;
		}
		else {
			System.out.println("[NegateRestriction.negateBinaryExpression()] unknown expression to make negative=" + expression);
		}
		return null;

	}
}
