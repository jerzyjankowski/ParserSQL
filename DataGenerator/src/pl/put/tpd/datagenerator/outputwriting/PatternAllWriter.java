package pl.put.tpd.datagenerator.outputwriting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.output.OutputRow;
import pl.put.tpd.datagenerator.structures.output.OutputTable;
import pl.put.tpd.datagenerator.structures.pattern.PatternAll;

public class PatternAllWriter {

	public void write(PatternAll patternAll) { 
		PrintWriter resultWriter;
		try {
			resultWriter = new PrintWriter("output/patternAll.txt", "UTF-8");
			
			resultWriter.println(patternAll);
			resultWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
