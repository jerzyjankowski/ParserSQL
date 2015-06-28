package com.dateGenerator.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dateGenerator.structures.PatternRow;

public class JoinedRow {
		

		List<PatternRow> patternRows;
		List<Integer> numbersToGenerate;
		List<Boolean> errorFlags;

		public JoinedRow() {
			patternRows = new ArrayList<PatternRow>();
			numbersToGenerate = new ArrayList<Integer>();
			errorFlags = new ArrayList<Boolean>();
		}

		public void add(PatternRow row) {
			patternRows.add(row);
		}
		
		public boolean containts(int rowId){
			for(PatternRow patternRow: patternRows)
				if(patternRow.getId() == rowId)  
					return true;
			return false;
		}
		
		public List<PatternRow> getPatternRows() {
			return patternRows;
		}

		public void setPatternRows(List<PatternRow> patternRows) {
			this.patternRows = patternRows;
		}
		
		public void removeDuplicates(){
			Set<PatternRow> OrderedRows = new HashSet<>(); //usuwanie duplikatów 
			OrderedRows.addAll(patternRows);
			patternRows.clear();
			patternRows.addAll(OrderedRows);
		}
		

		@Override
		public String toString() {
			String rows="";
			for(PatternRow row: patternRows)
			{
				rows += row.getId() + ",";
			}
			return "JoinedRow [" + rows + "]";
		}

		
		
	}
