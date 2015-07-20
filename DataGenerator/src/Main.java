import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.put.tpd.datagenerator.datagenerating.DataGenerator;
import pl.put.tpd.datagenerator.datagenerating.TestData;
import pl.put.tpd.datagenerator.graphbuilding.FinderAliases;
import pl.put.tpd.datagenerator.graphbuilding.SQLSelectParser;
import pl.put.tpd.datagenerator.inputloding.XMLParser;
import pl.put.tpd.datagenerator.inputloding.SpecificationLoader;
import pl.put.tpd.datagenerator.outputwriting.CSVWriter;
import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.pattern.PatternAll;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

 
public class Main {

	public static void main(String[] args) throws JSQLParserException {

		PatternAll patternAll;
		OutputAll outputAll;
		String sqlQuery;
		
//		used for test purpose, without external input files
//		TestData testData = new TestData(70);
//		patternAll = testData.getPatternAll();
//		outputAll = testData.getOutputAll();
//		sqlQuery = testData.getSqlQuery();
		
		SpecificationLoader loader = new SpecificationLoader();
		loader.load();
		patternAll = loader.getPatternAll();
		outputAll = loader.getOutputAll();
		sqlQuery = loader.getSqlQuery();
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement statement = parserManager.parse(new StringReader(sqlQuery));
		
		if (statement instanceof Select) 
		{
			
			System.out.println("\"Select type\" SQL statement=" + sqlQuery + "\n");
			
			Select selectStatement = (Select) statement;
			
			//finds aliases in sql statement and writes them into patternAll
			FinderAliases finderAliases = new FinderAliases(patternAll);
			PatternAll  patternAllWithAliases = finderAliases.parse(selectStatement);
			
			//Create graph
			SQLSelectParser sqlParser = new SQLSelectParser(patternAllWithAliases);
			PatternAll parsedPatternAll = sqlParser.parse(selectStatement);			

			DataGenerator dataGenerator = new DataGenerator(patternAll, outputAll);
			dataGenerator.generate(20, 20);

			CSVWriter csvWriter = new CSVWriter(); 
			csvWriter.write(outputAll);
			System.out.println("parsedPatternAll: " + parsedPatternAll);
			
			System.out.println("STOP ALL: \n");

		}
		else {
			System.out.println("Statament type is not supported");
		}		
	}
}
