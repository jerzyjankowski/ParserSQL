import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dateGenerator.engine.SQLParser;
import com.dateGenerator.generate.Generator;
import com.dateGenerator.generate.JoinedRow;
import com.dateGenerator.xml.XMLParser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import Util.TestData;

 
public class Main {

	public static void main(String[] args) throws JSQLParserException {
		/**
		 * fowr test purposes in class TestData there are example sqls and example table with column
		 * later these all will be loaded from files
		 */
		TestData testData = new TestData(30);
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement statement = parserManager.parse(new StringReader(testData.getSqlString()));
		
		if (statement instanceof Select) 
		{
			System.out.println("START SQL: " + testData.getSqlString());
			System.out.println("\nDATA: \n" + testData.getPatternAll() + "\n");
			System.out.println("START ALL: \n");
			
			Select selectStatement = (Select) statement;
			SQLParser sqlParser = new SQLParser(testData.getPatternAll());
			sqlParser.parse(selectStatement);
			
			Generator generator = new Generator(sqlParser.getPatternAll().getPatternTables()) ;
			List<JoinedRow> joinedRows = generator.generate();

			
			System.out.println("--------------------------");
			
			for(JoinedRow rw : joinedRows)
				System.out.print(rw.toString()+ " ");
			System.out.println();
			
		}
		
		XMLParser parser = new XMLParser();
		parser.enablePrintMode(true); //wlaczanie/wylaczanie wyswietlania sparsowanych danych z XML
		//parser.parseToObjects("./resources/tabele.xml");
		
	}

}
