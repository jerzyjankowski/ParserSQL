package output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.dateGenerator.structures.output.OutputAll;
import com.dateGenerator.structures.output.OutputRow;
import com.dateGenerator.structures.output.OutputTable;

public class CSVWriter {

	public void write(OutputAll outputAll) { 
		PrintWriter resultWriter;
//		System.out.println("outputAll:" + outputAll);
		try {
			for(OutputTable outputTable : outputAll.getTables()) {
				resultWriter = new PrintWriter("output/" + outputTable.getName() + ".csv", "UTF-8");
				for(OutputRow outputRow : outputTable.getRows()) {
					String line = "";
					for(String s : outputRow.getNodes())
						line += s + ",";
					line = line.substring(0, line.length()-1);
					resultWriter.println(line);
				}
				resultWriter.close();
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
