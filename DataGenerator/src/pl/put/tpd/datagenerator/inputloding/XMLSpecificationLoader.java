package pl.put.tpd.datagenerator.inputloding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class XMLSpecificationLoader {
	
	private String sqlQuery = new String();
	private PatternAll patternAll = new PatternAll();
	private OutputAll outputAll = new OutputAll();
	
	public static void main(String... args) {
		XMLSpecificationLoader loader = new XMLSpecificationLoader();
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
			
			System.out.println(xmlTable);
			PatternTable patternTable = new PatternTable(xmlTable.getName());
			PatternRow patternRow = new PatternRow();
			
			for(XMLColumn xmlColumn : xmlTable.getColumns()) {
				System.out.println("   " + xmlColumn);
				PatternNode patternNode = new PatternNode(xmlColumn.getType().toString(), xmlColumn.getName());
				addMinValueRestriction(patternNode, xmlTable, xmlColumn);
				addMaxValueRestriction(patternNode, xmlTable, xmlColumn);
				patternRow.addPatternNode(patternNode);
			}
			
			patternTable.setMainPatternRow(patternRow);
			patternAll.addPatternTable(patternTable);
			outputAll.addTable(new OutputTable(patternTable)); 
		}
		patternAll.initiatePatternRow();
//		System.out.println(patternAll);
		
		loadSqlQuery("input/query.sql");
//		System.out.println(sqlString);
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
			// TODO Auto-generated catch block
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
