package pl.put.tpd.datagenerator.inputloding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.output.OutputTable;
import pl.put.tpd.datagenerator.structures.pattern.PatternAll;
import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRestriction;
import pl.put.tpd.datagenerator.structures.pattern.PatternRow;
import pl.put.tpd.datagenerator.structures.pattern.PatternTable;
import pl.put.tpd.datagenerator.structures.restriction.Restriction;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
/**
 * Create patternAll, outputAll and sqlQuery based on input files
 *
 */
public class SpecificationLoader {
	
	private String sqlQuery = new String();
	private PatternAll patternAll = new PatternAll();
	private OutputAll outputAll = new OutputAll();
	
	public static void main(String... args) {
		SpecificationLoader loader = new SpecificationLoader();
		loader.load();

		PatternAll patternAll;
		OutputAll outputAll;
		String sqlQuery;
		
		patternAll = loader.getPatternAll();
		outputAll = loader.getOutputAll();
		sqlQuery = loader.getSqlQuery();
		
		System.out.println("\n\n\npatternAll:\n" + patternAll);
		System.out.println("\n\n\noutputAll:\n" + outputAll);
		System.out.println("\n\n\nsqlQuery:\n" + sqlQuery);
	}
	
	public void load() {
		XMLParser xmlParser = new XMLParser();
		xmlParser.parseToObjects("input/tables.xml");
		System.out.println("load");
		
		for(XMLTable xmlTable : xmlParser.getTables()) {
			
			PatternTable patternTable = new PatternTable(xmlTable.getName());
			PatternRow patternRow = new PatternRow();
			
			for(XMLColumn xmlColumn : xmlTable.getColumns()) {
				PatternNode patternNode = new PatternNode(xmlColumn.getType().toString(), xmlColumn.getName());
				addMinValueRestriction(patternNode, xmlTable, xmlColumn);
				addMaxValueRestriction(patternNode, xmlTable, xmlColumn);
				addInListValueRestriction(patternNode, xmlTable, xmlColumn);
				patternRow.addPatternNode(patternNode);
			}
			
			patternTable.setMainPatternRow(patternRow);
			patternAll.addPatternTable(patternTable);
			
			OutputTable outputTable = new OutputTable(patternTable);
			outputTable.setRowNum(50);
			outputAll.addTable(outputTable); 
		}
		patternAll.initiatePatternRow();
		
		loadSqlQuery("input/query.sql");
	}
	
	private void addInListValueRestriction(PatternNode patternNode, XMLTable xmlTable, XMLColumn xmlColumn) {
		if(!xmlColumn.getValues().isEmpty()) {
			System.out.println("xmlColumn.getValues()=" + xmlColumn.getValues());
			Table table = new Table(null, xmlTable.getName());
			Column column = new Column(table, xmlColumn.getName());
			ExpressionList list = new ExpressionList();
			list.setExpressions(xmlColumn.getValues());
//			for(String s : xmlColumn.getValues()) {
//				LongValue longValue = new LongValue(s);
//				list.add(longValue);
//			}
			
			InExpression inExpression = new InExpression();
			inExpression.setLeftExpression(column);
			inExpression.setItemsList(list);
			
			System.out.println("inExpression=" + xmlColumn.getName() + " IN " + inExpression.getItemsList());
			
			Restriction restriction = new Restriction(xmlColumn.getName() + " IN " + inExpression.getItemsList(), inExpression);
			restriction.addColumn(xmlColumn.getName()); 
			patternNode.addPatternRestriction(new PatternRestriction(restriction));
		}
	}
	
	private void addMinValueRestriction(PatternNode patternNode, XMLTable xmlTable, XMLColumn xmlColumn) {
		if(xmlColumn.getMinValue() != null) {
			Table table = new Table(null, xmlTable.getName());
			Column column = new Column(table, xmlColumn.getName());
			LongValue longValue = new LongValue(xmlColumn.getMinValue().toString());
			BinaryExpression binaryExpression = new MinorThanEquals();
			binaryExpression.setLeftExpression(column);
			binaryExpression.setRightExpression(longValue);
			Restriction restriction = new Restriction(xmlColumn.getName() + ">=" + xmlColumn.getMinValue(), binaryExpression);
			restriction.addColumn(xmlColumn.getName()); 
			patternNode.addPatternRestriction(new PatternRestriction(restriction));
		}
	}

	private void addMaxValueRestriction(PatternNode patternNode, XMLTable xmlTable, XMLColumn xmlColumn) {
		if(xmlColumn.getMaxValue() != null) {
			Table table = new Table(null, xmlTable.getName());
			Column column = new Column(table, xmlColumn.getName());
			LongValue longValue = new LongValue(xmlColumn.getMaxValue().toString());
			BinaryExpression binaryExpression = new MinorThanEquals();
			binaryExpression.setLeftExpression(column);
			binaryExpression.setRightExpression(longValue);
			Restriction restriction = new Restriction(xmlColumn.getName() + "<=" + xmlColumn.getMaxValue(), binaryExpression);
			restriction.addColumn(xmlColumn.getName()); 
			patternNode.addPatternRestriction(new PatternRestriction(restriction));
		}
	}
	
	public void loadSqlQuery(String path) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			while((line = in.readLine())!=null) {
				sqlQuery += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlQuery = sqlQuery.replaceAll("\\s+", " ");
	}
	
	public void testWrite() {
		PrintWriter resultWriter;
		try {
			resultWriter = new PrintWriter("results.csv", "UTF-8");
			resultWriter.print("helloWorld");
			resultWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public PatternAll getPatternAll() {
		return patternAll;
	}

	public OutputAll getOutputAll() {
		return outputAll;
	}
	
}
