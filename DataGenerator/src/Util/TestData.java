package Util;

public class TestData {
	private String sqlString = new String();
	private PatternAll patternAll = new PatternAll();
	public void TestData(int i) {
		switch(i) {
			case 10: sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE placa_pod + placa_dod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			case 11: sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE placa_pod > 500 AND (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  ";
			case 12: sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE (etat = 'ADIUNKT' OR etat = 'ASYSTENT');  "; 
			case 13: sqlString = "SELECT nazwisko, etat FROM pracownicy WHERE etat = 'ASYSTENT';  "; 
			default: sqlString = "SELECT nazwisko, etat FROM pracownicy;  ";
		}
	}
}
