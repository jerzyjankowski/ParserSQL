package pl.put.tpd.datagenerator.datagenerating;

import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.output.OutputRow;
import pl.put.tpd.datagenerator.structures.output.OutputTable;
import pl.put.tpd.datagenerator.structures.pattern.PatternAll;
import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRow;
import pl.put.tpd.datagenerator.structures.pattern.PatternTable;


public class TestDataGenerator {
	private String sqlQuery = new String();
	private PatternAll patternAll = new PatternAll();
	private OutputAll outputAll = new OutputAll();

	public TestDataGenerator(int i) {
		switch (i) {
		case 10:
			sqlQuery = "SELECT * FROM pracownicy WHERE etat = 'ASYSTENT';  ";
			break;
		case 11:
			sqlQuery = "SELECT * FROM pracownicy WHERE (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 12:
			sqlQuery = "SELECT * FROM pracownicy WHERE placa_pod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 13:
			sqlQuery = "SELECT * FROM pracownicy WHERE placa_pod + placa_dod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 14:
			sqlQuery = "SELECT * FROM pracownicy WHERE placa_pod = placa_dod;  ";
			break;
		case 20:
			sqlQuery = "SELECT * FROM pracownicy join etaty on etat = nazwa;  ";
			break;
		case 21:
			sqlQuery = "SELECT nazwisko, etat, nazwa  FROM pracownicy CROSS JOIN etaty;   ";
			break;
		case 22:
			sqlQuery = "SELECT nazwisko, placa_pod, nazwa, placa_min  FROM pracownicy INNER JOIN etaty ON etat = nazwa;      ";
			break;
		case 23:
			sqlQuery = "SELECT * FROM pracownicy join etaty on etat = nazwa  where placa_pod > placa_min;  ";
			break;
		case 230:
			sqlQuery = "SELECT * FROM pracownicy join etaty on placa_pod > placa_min;  ";
			break;
//		case 23:
//			sqlString = "SELECT * FROM pracownicy JOIN zespoly USING (id_zesp);  ";
//			break;
//		case 24:
//			sqlString = "SELECT nazwa, nazwisko, etat  FROM PRACOWNICY p LEFT OUTER JOIN zespoly z  ON z.id_zesp = p.id_zesp;   ";
//			break;
		case 30:
			sqlQuery = "SELECT * FROM pracownicy, etaty WHERE (etat = 'ADIUNKT' OR nazwa = 'ASYSTENT');  ";
			break;
		case 31:
			sqlQuery = "SELECT * FROM pracownicy, etaty WHERE placa_pod + placa_min > 500;  ";
			break;
		case 32:
			sqlQuery = "SELECT * FROM pracownicy, etaty WHERE placa_pod = placa_min;  ";
			break;
		case 40:
			sqlQuery = "SELECT * FROM pracownicy join etaty on etat = nazwa where placa_pod > 300;  ";
			break;
		case 41:
			sqlQuery = "SELECT * FROM pracownicy join etaty on etat = nazwa join zespoly on placa_dod > dodatek_min;  ";
			break;
		case 42:
			sqlQuery = "SELECT * FROM pracownicy join etaty on etat = nazwa join zespoly on placa_dod > dodatek_min where placa_pod >1000 OR placa_dod <200;  ";
			break;
		case 50:// infinite loop because of unsatisfied where conditions
			sqlQuery = "SELECT * FROM pracownicy join etaty on placa_pod = placa_min where placa_pod < 4 AND placa_min > 4;  ";
			break;
		case 60:
			sqlQuery = "SELECT * FROM pracownicy AS p WHERE p.placa_pod = 2048;  ";
			break;
		case 61:
			sqlQuery = "SELECT * FROM pracownicy AS p join etaty AS e WHERE p.placa_dod = 2048 and p.placa_pod > e.placa_min;  ";
			break;
		case 62:
			sqlQuery = "SELECT * FROM pracownicy AS p join zespoly AS z WHERE p.placa_dod = z.placa_dod;  ";
			break;
		case 63:
//			sqlString = "SELECT * FROM T1 INNER JOIN T2 AS t2a  ON T1.col1 = t2a.col1 " +
//					" INNER JOIN T2 AS t2b  ON T1.col2 = t2b.col2 " + 
//					" WHERE t2a.col3 = 'A' AND t2b.col3 = 'B';  ";
			sqlQuery = "SELECT * FROM zespoly INNER JOIN pracownicy AS pa  ON zespoly.placa_dod = pa.placa_dod " +
					" INNER JOIN pracownicy AS pb  ON zespoly.dodatek_min = pb.placa_dod " + 
					" WHERE pa.placa_pod = 100 AND pb.placa_pod = 200;  ";
			break;
		case 70:// x <=-50 didn't work
			sqlQuery = "SELECT * FROM pracownicy WHERE placa_pod < -3;  ";
			break;
			
			
			  
			   
		default:
			sqlQuery = "SELECT * FROM pracownicy;  ";
			break;
		}

		outputAll = new OutputAll();
		
		PatternTable patternTable = new PatternTable("pracownicy");
		PatternRow patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("STRING", "nazwisko"));
		patternRow.addPatternNode(new PatternNode("STRING", "etat"));
		patternRow.addPatternNode(new PatternNode("INTEGER", "placa_pod"));
		patternRow.addPatternNode(new PatternNode("INTEGER", "placa_dod"));
		patternTable.setMainPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);
		outputAll.addTable(new OutputTable(patternTable));

		patternTable = new PatternTable("etaty");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("STRING", "nazwa"));
		patternRow.addPatternNode(new PatternNode("INTEGER", "placa_min"));
		patternTable.setMainPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);
		outputAll.addTable(new OutputTable(patternTable));

		patternTable = new PatternTable("zespoly");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("STRING", "zespol_nazwa"));
		patternRow.addPatternNode(new PatternNode("INTEGER", "dodatek_min"));
		patternRow.addPatternNode(new PatternNode("INTEGER", "placa_dod"));
		patternTable.setMainPatternRow(patternRow);
		patternAll.addPatternTable(patternTable);
		outputAll.addTable(new OutputTable(patternTable));
		
		patternAll.initiatePatternRow();
		
		
//		OutputTable table = new OutputTable("pracownicy");
//		table.addColumn("nazwisko");
//		table.addColumn("etat");
//		table.addColumn("placa_pod");
//		table.addColumn("placa_dod");
//		outputAll.addTable(table);
//
//		table = new OutputTable("etaty");
//		table.addColumn("nazwa");
//		table.addColumn("placa_min");
//		outputAll.addTable(table);
//
//		table = new OutputTable("zespoly");
//		table.addColumn("zespol_nazwa");
//		table.addColumn("dodatek_min");
//		table.addColumn("placa_dod");
//		outputAll.addTable(table);
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
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
