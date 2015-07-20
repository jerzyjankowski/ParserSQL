import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import output.CSVWriter;

import com.dateGenerator.engine.DataGenerator;
import com.dateGenerator.engine.FinderAliases;
import com.dateGenerator.engine.SQLParser;
import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.output.OutputAll;
import com.dateGenerator.xml.XMLParser;
import com.dateGenerator.xml.XMLSpecificationLoader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import Util.TestData;

 
public class Main {

	public static void main(String[] args) throws JSQLParserException {
		/**
		 * for test purposes in class TestData there are example sqls and example table with column
		 * later these all will be loaded from files
		 */

		PatternAll patternAll;
		OutputAll outputAll;
		String sqlQuery;

//		TestData testData = new TestData(70);
//		patternAll = testData.getPatternAll();
//		outputAll = testData.getOutputAll();
//		sqlQuery = testData.getSqlQuery();
		
		XMLSpecificationLoader loader = new XMLSpecificationLoader();
		loader.load();
		patternAll = loader.getPatternAll();
		outputAll = loader.getOutputAll();
		sqlQuery = loader.getSqlQuery();
		

		System.out.println("\n\n\npatternAll:\n" + patternAll);
		System.out.println("\n\n\noutputAll:\n" + outputAll);
		System.out.println("\n\n\nsqlQuery:\n" + sqlQuery);
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement statement = parserManager.parse(new StringReader(sqlQuery));
		
		if (statement instanceof Select) 
		{
			
			System.out.println("START SELECT SQL: " + sqlQuery);
			System.out.println("START ALL: \n");
			
			Select selectStatement = (Select) statement;
			FinderAliases finderAliases = new FinderAliases(patternAll);
			PatternAll  patternAllWithAliases = finderAliases.parse(selectStatement);
			
			SQLParser sqlParser = new SQLParser(patternAllWithAliases);
			PatternAll parsedPatternAll = sqlParser.parse(selectStatement);			

			DataGenerator dataGenerator = new DataGenerator(patternAll, outputAll);
			dataGenerator.generate();

			CSVWriter csvWriter = new CSVWriter(); 
			csvWriter.write(outputAll);
			System.out.println("parsedPatternAll: " + parsedPatternAll);
			
			System.out.println("STOP ALL: \n");

		}
		
		XMLParser parser = new XMLParser();
		parser.enablePrintMode(true); //wlaczanie/wylaczanie wyswietlania sparsowanych danych z XML
		//parser.parseToObjects("./resources/tabele.xml");
		
	}

}
