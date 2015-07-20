package pl.put.tpd.datagenerator.graphbuilding;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.put.tpd.datagenerator.structures.pattern.PatternAll;
import pl.put.tpd.datagenerator.structures.pattern.PatternTable;
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
	private Set<String> tables = new HashSet<>();
	
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
				Table table = (Table)join.getRightItem();
				
				if(tables.contains(table.getName())) {
					System.out.println("duplicated table");
					
					PatternTable patternTable = null;
					for(PatternTable pt : patternAll.getPatternTables()) {
						if(pt.getName().equals(table.getName()))
							patternTable = pt;
					}
					
					if(patternTable != null) {
						PatternTable newPatternTable = patternTable.copy();
						newPatternTable.setAlias(table.getAlias()); 
						patternAll.addPatternTable(newPatternTable);
					}
					else
						System.out.println("There is not patternTable with name of that duplicated table.");
				} else {
					tables.add(table.getName());
					if(table.getAlias() != null)
						patternAll.setTableAlias(table.getName(), table.getAlias());
				}
			}
		}
	}
	
	@Override
	public void visit(Table table) {
		tables.add(table.getName());
		if(table.getAlias() != null)
			patternAll.setTableAlias(table.getName(), table.getAlias());		
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
