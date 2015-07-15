package Util;

import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;


public class TestData {
	private String sqlString = new String();
	private PatternAll patternAll = new PatternAll();

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
			sqlString = "SELECT * FROM pracownicy join etaty on etat = nazwa join zespoly on placa_dod > dodatek_min where placa_pod >1000;  ";
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
		patternAll.addPatternTables(patternTable);

		patternTable = new PatternTable("etaty");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("integer", "nazwa"));
		patternRow.addPatternNode(new PatternNode("integer", "placa_min"));
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTables(patternTable);

		patternTable = new PatternTable("zespoly");
		patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("integer", "zespol_nazwa"));
		patternRow.addPatternNode(new PatternNode("integer", "dodatek_min"));
		patternTable.addPatternRow(patternRow);
		patternAll.addPatternTables(patternTable);
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
}
