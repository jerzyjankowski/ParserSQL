package pl.put.tpd.datagenerator.datagenerating;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;

public class FinderInItemsFromList implements ItemsListVisitor{

	List<Object> expressionList;
	
	public List<String> getExpressionList() {
		List<String> sa = new ArrayList<>();
		for(Object s : expressionList) 
			sa.add(s.toString());
		return sa;
	}
	
	@Override
	public void visit(SubSelect paramSubSelect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExpressionList paramExpressionList) {
		expressionList = paramExpressionList.getExpressions();
	}

}
