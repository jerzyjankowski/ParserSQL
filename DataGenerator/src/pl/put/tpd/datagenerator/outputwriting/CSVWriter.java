package pl.put.tpd.datagenerator.outputwriting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.output.OutputRow;
import pl.put.tpd.datagenerator.structures.output.OutputTable;

public class CSVWriter {

	public void write(OutputAll outputAll) { 
		PrintWriter resultWriter;
		try {
			for(OutputTable outputTable : outputAll.getTables()) {
				resultWriter = new PrintWriter("output/" + outputTable.getName() + ".csv", "UTF-8");
				String line = "";
				for(String s : outputTable.getColumns()) {
					line += s + ",";
				}
				line = line.substring(0, line.length()-1);
				resultWriter.println(line);
				for(OutputRow outputRow : outputTable.getRows()) {
					line = "";
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
