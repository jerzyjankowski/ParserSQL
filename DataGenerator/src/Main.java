import java.io.StringReader;

import com.dateGenerator.engine.SQLParser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import Util.TestData;


public class Main {

	public static void main(String[] args) throws JSQLParserException {
		/**
		 * for test purposes in class TestData there are example sqls and example table with column
		 * later these all will be loaded from files
		 */
		TestData testData = new TestData(10);
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement statement = parserManager.parse(new StringReader(testData.getSqlString()));
		
		if (statement instanceof Select) 
		{
			System.out.println("START SQL: " + testData.getSqlString());
			System.out.println("START ALL: " + testData.getPatternAll() + "\n");
			
			Select selectStatement = (Select) statement;
			SQLParser sqlParser = new SQLParser(testData.getPatternAll());
			sqlParser.parse(selectStatement);
			
		}
	}

}
