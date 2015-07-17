package Util;

import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;
import com.dateGenerator.structures.output.OutputAll;
import com.dateGenerator.structures.output.OutputRow;
import com.dateGenerator.structures.output.OutputTable;


public class TestData {
	private String sqlString = new String();
	private PatternAll patternAll = new PatternAll();
	private OutputAll outputAll = new OutputAll();

	public TestData(int i) {
		switch (i) {
		case 10:
			sqlString = "SELECT * FROM pracownicy WHERE etat = 'ASYSTENT';  ";
			break;
		case 11:
			sqlString = "SELECT * FROM pracownicy WHERE (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 12:
			sqlString = "SELECT * FROM pracownicy WHERE placa_pod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 13:
			sqlString = "SELECT * FROM pracownicy WHERE placa_pod + placa_dod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 14:
			sqlString = "SELECT * FROM pracownicy WHERE placa_pod = placa_dod;  ";
			break;
		case 20:
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa;  ";
			break;
		case 21:
			sqlString = "SELECT nazwisko, etat, nazwa  FROM pracownicy CROSS JOIN etaty;   ";
			break;
		case 22:
			sqlString = "SELECT nazwisko, placa_pod, nazwa, placa_min  FROM pracownicy INNER JOIN etaty ON etat = nazwa;      ";
			break;
		case 23:
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa  where placa_pod > placa_min;  ";
			break;
//		case 23:
//			sqlString = "SELECT * FROM pracownicy JOIN zespoly USING (id_zesp);  ";
//			break;
//		case 24:
//			sqlString = "SELECT nazwa, nazwisko, etat  FROM PRACOWNICY p LEFT OUTER JOIN zespoly z  ON z.id_zesp = p.id_zesp;   ";
//			break;
		case 30:
			sqlString = "SELECT * FROM pracownicy, etaty WHERE (etat = 'ADIUNKT' OR nazwa = 'ASYSTENT');  ";
			break;
		case 31:
			sqlString = "SELECT * FROM pracownicy, etaty WHERE placa_pod + placa_min > 500;  ";
			break;
		case 32:
			sqlString = "SELECT * FROM pracownicy, etaty WHERE placa_pod = placa_min;  ";
			break;
		case 40:
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa where placa_pod > 300;  ";
			break;
		case 41:
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa join zespoly on placa_dod > dodatek_min;  ";
			break;
		case 42:
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa join zespoly on placa_dod > dodatek_min where placa_pod >2;  ";
			break;
		case 50:// infinite loop because of unsatisfied where conditions
			sqlString = "SELECT * FROM pracownicy join etaty on placa_pod = placa_min where placa_pod < 4 AND placa_min > 4;  ";
			break;
		case 60:
			sqlString = "SELECT * FROM pracownicy AS p WHERE p.placa_pod = 2048;  ";
			break;
		case 61:
			sqlString = "SELECT * FROM pracownicy AS p join etaty AS e WHERE p.placa_dod = 2048 and p.placa_pod > e.placa_min;  ";
			break;
		case 62:
			sqlString = "SELECT * FROM pracownicy AS p join zespoly AS z WHERE p.placa_dod = z.placa_dod;  ";
			break;
		case 63:
//			sqlString = "SELECT * FROM T1 INNER JOIN T2 AS t2a  ON T1.col1 = t2a.col1 " +
//					" INNER JOIN T2 AS t2b  ON T1.col2 = t2b.col2 " + 
//					" WHERE t2a.col3 = 'A' AND t2b.col3 = 'B';  ";
			sqlString = "SELECT * FROM zespoly INNER JOIN pracownicy AS pa  ON zespoly.placa_dod = pa.placa_dod " +
					" INNER JOIN pracownicy AS pb  ON zespoly.dodatek_min = pb.placa_dod " + 
					" WHERE pa.placa_pod = 100 AND pb.placa_pod = 200;  ";
			break;
			
			
			  
			   
		default:
			sqlString = "SELECT * FROM pracownicy;  ";
			break;
		}

		
		PatternTable patternTable = new PatternTable("pracownicy");
		PatternRow patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("integer", "nazwisko"));
		patternRow.addPatternNode(new PatternNode("integer", "etat"));
		patternRow.addPatternNode(new PatternNode("integer", "placa_pod"));
		patternRow.addPatternNode(new PatternNode("integer", "placa_dod"));
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);

		patternTable = new PatternTable("etaty");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("integer", "nazwa"));
		patternRow.addPatternNode(new PatternNode("integer", "placa_min"));
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);

		patternTable = new PatternTable("zespoly");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("integer", "zespol_nazwa"));
		patternRow.addPatternNode(new PatternNode("integer", "dodatek_min"));
		patternRow.addPatternNode(new PatternNode("integer", "placa_dod"));
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);
		
		
		outputAll = new OutputAll();
		OutputTable table = new OutputTable("pracownicy");
		table.addColumn("nazwisko");
		table.addColumn("etat");
		table.addColumn("placa_pod");
		table.addColumn("placa_dod");
		outputAll.addTable(table);

		table = new OutputTable("etaty");
		table.addColumn("nazwa");
		table.addColumn("placa_min");
		outputAll.addTable(table);

		table = new OutputTable("zespoly");
		table.addColumn("zespol_nazwa");
		table.addColumn("dodatek_min");
		table.addColumn("placa_dod");
		outputAll.addTable(table);
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public PatternAll getPatternAll() {
		return patternAll;
	}

	public void setPatternAll(PatternAll patternAll) {
		this.patternAll = patternAll;
	}

	public OutputAll getOutputAll() {
		return outputAll;
	}

	public void setOutputAll(OutputAll outputAll) {
		this.outputAll = outputAll;
	}
	
	
}
