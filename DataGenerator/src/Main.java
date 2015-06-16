import Util.TestData;


public class Main {

	public static void main(String[] args) {
		TestData testData = new TestData(1);
		System.out.println(testData.getSqlString());
		System.out.println(testData.getPatternAll());
	}

}
