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
			sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE placa_pod + placa_dod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 11:
			sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE placa_pod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 12:
			sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			break;
		case 13:
			sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE etat = 'ASYSTENT';  ";
			break;
		default:
			sqlString = "SELECT nazwisko, etat FROM pracownicy;  ";
			break;
		}

		PatternTable patternTable = new PatternTable("pracownicy");
		PatternRow patternRow = new PatternRow();
		patternRow.addPatternNode(new PatternNode("nazwisko"));
		patternRow.addPatternNode(new PatternNode("etat"));
		patternRow.addPatternNode(new PatternNode("placa_pod"));
		patternRow.addPatternNode(new PatternNode("placa_dod"));
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
