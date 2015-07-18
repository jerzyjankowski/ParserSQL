package com.dateGenerator.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.dateGenerator.structures.PatternAll;
import com.dateGenerator.structures.PatternNode;
import com.dateGenerator.structures.PatternRestriction;
import com.dateGenerator.structures.PatternRow;
import com.dateGenerator.structures.PatternTable;
import com.dateGenerator.structures.output.OutputAll;

public class DataGenerator {
	private PatternAll patternAll;
	private OutputAll outputAll;
	private Map<PatternNode, PatternRow> nodeToRow = new HashMap<>();
	private Map<PatternRow, PatternTable> rowToTable = new HashMap<>();
	private Set<PatternRow> rowLeaves = new HashSet<PatternRow>();
	private Set<PatternRow> allRows = new HashSet<>();
	
	public DataGenerator(PatternAll patternAll, OutputAll outputAll) {
		this.patternAll = patternAll;
		this.outputAll = outputAll;
	}
	
	private void prepareMap() {
		for(PatternTable pTab : patternAll.getPatternTables()) {
			for(PatternRow pRow : pTab.getPatternRows()) {
				rowToTable.put(pRow,pTab);
				allRows.add(pRow);
				for(PatternNode pNod : pRow.getPatternNodes()) {
					nodeToRow.put(pNod, pRow);
				}
			}
		}
	}
	
	private void prepareLeaves() {
		for(PatternTable pTab : patternAll.getPatternTables()) {
			for(PatternRow pRow : pTab.getPatternRows()) {
				Set<PatternRow> resRow = new HashSet<>();
				for(PatternNode pNod : pRow.getPatternNodes()) {
					for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
						for(PatternNode pNodRes : pRes.getPatternNodes()) {
							if(nodeToRow.get(pNodRes) != pRow)
								resRow.add(nodeToRow.get(pNodRes));
						}
					}
				}
//				System.out.print("\n" + pRow + " ::: " + resRow.size() + ":::");
//				for(PatternRow pRowTemp : resRow) {
//					System.out.print(pRowTemp.getId() + " ");
//				}
//				System.out.println();
				if(resRow.size() <= 1)
					rowLeaves.add(pRow);
			}
		}
	}
	
	private void testPatternAll() {
		for(PatternTable pTab : patternAll.getPatternTables()) {
			for(PatternRow pRow : pTab.getPatternRows()) {
				for(PatternNode pNod : pRow.getPatternNodes()) {
					for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
						System.out.println(pRes.getRestriction().getBinaryExpression().toString());
						for(PatternNode pNodRes : pRes.getPatternNodes()) {
							System.out.print("   " + pNodRes.getName() + ":" + pNodRes.getValue());
						}
						System.out.println();
					}
				}
			}
		}
	}
	
	public void generate() {
		
		prepareMap();
		prepareLeaves();
		
		for(int j = 0; j < 2; j++) {
			patternAll.clearValues();		
			Stack<PatternRow> rowsToMake = new Stack<>();
			Stack<PatternRow> rowMade = new Stack<>();
			Map<PatternRow, Boolean> usedRows = new HashMap<>();
			Map<PatternRow, Boolean> visitedRows = new HashMap<>();
			PatternRow patternRow;
			
			for(PatternRow pr : allRows)
				usedRows.put(pr, false);
			
			for(PatternRow pr : allRows) {
				visitedRows.put(pr, false);
			}
			
			for(PatternRow pr : allRows) {
				if(!usedRows.get(pr)) {
//					System.out.println("got leaf: " + pr.getId());
					rowsToMake.push(pr);
					visitedRows.put(pr, true);
					while(!rowsToMake.empty()) {
						patternRow = rowsToMake.pop();
//						System.out.println("  got row: " + patternRow.getId());
						usedRows.put(patternRow, true);
						for(PatternNode pNod : patternRow.getPatternNodes()) {
							if(pNod.getValue()!=null)
								continue;
							//update stacks
							for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
								for(PatternNode pNodRes : pRes.getPatternNodes()) {
									if(!visitedRows.get(nodeToRow.get(pNodRes))) {
										visitedRows.put(nodeToRow.get(pNodRes), true);
										rowsToMake.push(nodeToRow.get(pNodRes));
//										System.out.println("    pushed: " + nodeToRow.get(pNodRes).getId());
									}
								}
							}
							int endFor = 10, maxCollisionCnt = 10;
							PatternRestriction collisionRestriction = null;
							for(int i = 0; i <= endFor; i++) {
								boolean correctFlag = true;
								pNod.generateValue();
								for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
									try{
										correctFlag = pRes.check();
										if(!correctFlag) 
											collisionRestriction = pRes;
									} catch(PatternRestriction.Unfinished u) {
										
									}
									catch(Exception e) {
										System.out.println("[DataGenerator]Exception: " + e);
									}
								}
								if(correctFlag) 
									break;
								if(i == endFor) {
									collisionRestriction.incrementCollisionCnt();
									if(collisionRestriction.getCollisionCnt() > maxCollisionCnt) {
										System.out.println("Couldn't make that restriction to happen: " + collisionRestriction);
										break;//there may be no chance to satisfie that restriction
									}
									for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
										for(PatternNode pNodRes : pRes.getPatternNodes()) {
											pNodRes.setValue(null);
											if(usedRows.get(nodeToRow.get(pNodRes))) {
												usedRows.put(nodeToRow.get(pNodRes), false);
												rowsToMake.push(nodeToRow.get(pNodRes));
											}
										}
									}
									pNod.generateValue();
								}
							} 
						}
					}
				}
				outputAll.addRow(pr, rowToTable.get(pr));
			}
//			testPatternAll();
		}
		System.out.println("********\n" + outputAll + "\n*********");
	}
}
