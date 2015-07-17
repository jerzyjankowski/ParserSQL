package com.dateGenerator.engine;

import java.util.Iterator;

import com.dateGenerator.structures.PatternAll;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

public class FinderAliases implements SelectVisitor, FromItemVisitor{
	
	private PatternAll patternAll;
	
	public FinderAliases(PatternAll patternAll) {
		this.patternAll = patternAll;
	}
	
	public PatternAll parse(Select select) {
		select.getSelectBody().accept(this);
		return patternAll;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		plainSelect.getFromItem().accept(this);
		
		if (plainSelect.getJoins() != null) {
			for (Iterator joinsIt = plainSelect.getJoins().iterator(); joinsIt.hasNext();) {
				Join join = (Join) joinsIt.next();
				patternAll.setTableAlias(((Table)join.getRightItem()).getName(), join.getRightItem().getAlias());
			}
		}
	}
	
	@Override
	public void visit(Table arg0) {
		patternAll.setTableAlias(arg0.getName(), arg0.getAlias());		
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
	public void visit(Union arg0) {
		// TODO Auto-generated method stub
		
	}

}
