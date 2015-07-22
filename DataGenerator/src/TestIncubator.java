import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestIncubator {

	public static void main(String[] args) {
		TestIncubator testIncubator = new TestIncubator();
		//testIncubator.testRegExp();
		testIncubator.testReplaceColumn();
	}
	
	private void testReplaceColumn() {
		String[] sArr= {
				"column < as_s", 
				" column < 1000",
				"placa_dod <> column",
				"placa_dod <> column ",
				"a.column>=wiek",
				" a.column =  -200",
				"column < column1", 
				" column < ccolumn ", 
				"100 + column< ab-",
				"1 IN (sth,column)"
				};
		String notCharPattern = "[^a-zA-Z0-9_.]";
		
    	String columnName = "column";
		String columnPattern0 = "("+notCharPattern+")(" + columnName + ")("+notCharPattern+")";
		String columnPattern1 = "^(" + columnName + ")("+notCharPattern+")";
		String columnPattern2 = "("+notCharPattern+")(" + columnName + ")$";
		
		String tnColumnName = "a.column";
		String tnColumnPattern0 = "("+notCharPattern+")(" + tnColumnName + ")("+notCharPattern+")";
		String tnColumnPattern1 = "^(" + tnColumnName + ")("+notCharPattern+")";
		String tnColumnPattern2 = "("+notCharPattern+")(" + tnColumnName + ")$";
		
		String taColumnName = "a.column";
		String taColumnPattern0 = "("+notCharPattern+")(" + taColumnName + ")("+notCharPattern+")";
		String taColumnPattern1 = "^(" + taColumnName + ")("+notCharPattern+")";
		String taColumnPattern2 = "("+notCharPattern+")(" + taColumnName + ")$";
		
		String value = "128";
    	String result = "default result";
		
        for(String s : sArr) { 
    		System.out.println("\n");
        	result = s.replaceAll(columnPattern0, "$1" + value + "$3").replaceAll(columnPattern1, value + "$2").replaceAll(columnPattern2, "$1" + value)
        			.replaceAll(tnColumnPattern0, "$1" + value + "$3").replaceAll(tnColumnPattern1, value + "$2").replaceAll(tnColumnPattern2, "$1" + value)
        			.replaceAll(taColumnPattern0, "$1" + value + "$3").replaceAll(taColumnPattern1, value + "$2").replaceAll(taColumnPattern2, "$1" + value);
        	System.out.println("---------------------------------------------------\n" +
        			"[replaceAll " + " with " + value + "]  ***" + s + "***   ->   ***" + result + "***");
        }
	}
	
	private void testRegExp() {
		String[] sArr= {
				"bO0bs < as_s", 
				"placa_pod < 1000",
				"placa_dod <> placa_min",
				"18>=wiek",
				"tab1.var12 =  -200",
				"100 < ab-"
				};
		String var = "[.a-zA-Z0-9_ ]*";
		String oper = "[<>=]{1,2}";
		String num = "[ ]*[+-]?[ ]*[0-9]*[ ]*";
		Pattern pattern_ab = Pattern.compile(var+oper+var);
		Pattern pattern_1b = Pattern.compile(num+oper+var);
		Pattern pattern_a1 = Pattern.compile(var+oper+num);
//        Matcher matcher = pattern.matcher("bO0bs < as_s");

		System.out.println("test num [" + num + "] for text: 0 " + (Pattern.compile(num)).matcher("0").matches());
		System.out.println("test num [" + num + "] for text: 5 " + (Pattern.compile(num)).matcher("5").matches());
		System.out.println("test num [" + num + "] for text: +5 " + (Pattern.compile(num)).matcher("+5").matches());
		System.out.println("test num [" + num + "] for text: -5 " + (Pattern.compile(num)).matcher("-5").matches());
		System.out.println("test num [" + num + "] for text: - 1045600 " + (Pattern.compile(num)).matcher("- 1045600").matches());
		System.out.println("test num [" + num + "] for text:    1045600     " + (Pattern.compile(num)).matcher("   1045600    ").matches());
		System.out.println("test num [" + num + "] for text:   - 1045600     " + (Pattern.compile(num)).matcher("  - 1045600    ").matches());
		System.out.println("test num [" + num + "] for text:   + 1045600     " + (Pattern.compile(num)).matcher("  + 1045600    ").matches());
		
		System.out.println("\n");
        for(String s : sArr) { 
        	if(pattern_a1.matcher(s).matches())
        		System.out.println(s + "\n   : kategoria a[<>=]1\n");
           	else if(pattern_1b.matcher(s).matches())
        		System.out.println(s + "\n   : kategoria 1[<>=]b\n");
        	else if(pattern_ab.matcher(s).matches())
        		System.out.println(s + "\n   : kategoria a[<>=]b\n");
        	else 
        		System.out.println(s + "\n   : kategoria pozosta³e\n");
        }
//        while (matcher.find()) {
//            System.out.printf("I found the text" +
//                " \"%s\" starting at " +
//                "index %d and ending at index %d.%n",
//                matcher.group(),
//                matcher.start(),
//                matcher.end());
//        }
	}

}
