package pl.put.tpd.datagenerator.datagenerating;

import java.util.HashSet;
import java.util.Set;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRestriction;
import pl.put.tpd.datagenerator.structures.pattern.exceptions.UnexpectedExpression;

public class RestrictionChecking {
	/**
	 * 
	 * @return if expression is valid or true if there are some nodes connected that doesn't have values generated
	 * @throws UnexpectedExpression
	 */
	static public boolean check(PatternRestriction patternRestriction) throws UnexpectedExpression {
		for(PatternNode node : patternRestriction.getPatternNodes()) {
			if(node.getValue() == null)
				return true;
		} 
		Expression expression = patternRestriction.getRestriction().getExpression();
		if(expression instanceof BinaryExpression) {
			String operation = ((BinaryExpression)expression).getStringExpression();
			String expression0 = patternRestriction.getRestriction().getExpression().toString();
			String expression1 = new String(expression0);
			try {
				for(PatternNode node : patternRestriction.getPatternNodes()) {
					expression1 = node.replaceNameWithValue(expression1);
				}
			}
			catch(Exception e){
				System.out.println("[RestrictionChecking.check()] cought exception " + e);
			}
			expression1 = expression1.replace(" ", "");
			expression1 = expression1.replace("\"", "");
			expression1 = expression1.replace("\'", "");
			
			
			
			Set<String> typesInExpression = new HashSet<String>();
			for(PatternNode node : patternRestriction.getPatternNodes()) {
				typesInExpression.add(node.getType());
			}
			
			if(typesInExpression.size()==1 && typesInExpression.contains("STRING"))
			{
				if(operation.equals("=")) {
					String[] expressionArr = expression1.split("=");
					return (expressionArr[0].equals(expressionArr[1]));
				} else if(operation.equals("<>")) {
					String[] expressionArr = expression1.split("<>");
					return (!expressionArr[0].equals(expressionArr[1]));
				}
				throw new UnexpectedExpression();
			}
			else if(typesInExpression.size()==1 && typesInExpression.contains("INTEGER"))
			{
				if(operation.equals("=")) {
					String[] expressionArr = expression1.split("=");
					return ((Integer.parseInt(expressionArr[0]))==(Integer.parseInt(expressionArr[1])));
				} else if(operation.equals("<>")) {
					String[] expressionArr = expression1.split("<>");
					return ((Integer.parseInt(expressionArr[0]))!=(Integer.parseInt(expressionArr[1])));
				} else if(operation.equals("<")) {
					String[] expressionArr = expression1.split("<");
					return ((Integer.parseInt(expressionArr[0]))<(Integer.parseInt(expressionArr[1])));
				} else if(operation.equals("<=")) {
					String[] expressionArr = expression1.split("<=");
					return ((Integer.parseInt(expressionArr[0]))<=(Integer.parseInt(expressionArr[1])));
				} else if(operation.equals(">")) {
					String[] expressionArr = expression1.split(">");
					return ((Integer.parseInt(expressionArr[0]))>(Integer.parseInt(expressionArr[1])));
				} else if(operation.equals(">=")) {
					String[] expressionArr = expression1.split(">=");
					return ((Integer.parseInt(expressionArr[0]))>=(Integer.parseInt(expressionArr[1])));
				}
				throw new UnexpectedExpression();
			}
			else
				throw new UnexpectedExpression();
				
		}
		else if(expression instanceof InExpression) {
			InExpression inExpression = (InExpression) expression;
			String leftExpression = new String(inExpression.getLeftExpression().toString());
			
			FinderInItemsFromList finderInItemsFromList = new FinderInItemsFromList();
			inExpression.getItemsList().accept(finderInItemsFromList);
			
			try {
				for(PatternNode node : patternRestriction.getPatternNodes()) {
					leftExpression = node.replaceNameWithValue(leftExpression);
				}
			}
			catch(Exception e){
				System.out.println("[RestrictionChecking.check()] cought exception " + e);
			}
			int value = Integer.parseInt(leftExpression.trim());
			
			if(!inExpression.isNot()) {
				for(String stringVal : finderInItemsFromList.getExpressionList()) {
					if(value == Integer.parseInt(stringVal))
						return true;
				}
				return false;
			}
			else {
				for(String stringVal : finderInItemsFromList.getExpressionList()) {
					if(value == Integer.parseInt(stringVal))
							return false;
				}
				return true;
			}
		}
		return false;
	}
}
