package pl.put.tpd.datagenerator.datagenerating;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import pl.put.tpd.datagenerator.structures.output.OutputAll;
import pl.put.tpd.datagenerator.structures.output.OutputTable;
import pl.put.tpd.datagenerator.structures.pattern.PatternAll;
import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRestriction;
import pl.put.tpd.datagenerator.structures.pattern.PatternRow;
import pl.put.tpd.datagenerator.structures.pattern.PatternTable;

public class DataGenerator {
	private PatternAll patternAll;
	private OutputAll outputAll;
	private Map<PatternNode, PatternRow> nodeToRow = new HashMap<>();
	private Map<PatternRow, PatternTable> rowToTable = new HashMap<>();
	private Set<PatternRow> allRows = new HashSet<>();
	
	public DataGenerator(PatternAll patternAll, OutputAll outputAll) {
		this.patternAll = patternAll;
		this.outputAll = outputAll;
	}
	
	//fills maps and set
	private void prepare() {
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
	
	//prints restriction for test purposes
	private void testPatternAll() {
		for(PatternTable pTab : patternAll.getPatternTables()) {
			for(PatternRow pRow : pTab.getPatternRows()) {
				System.out.println("-----");
				for(PatternNode pNod : pRow.getPatternNodes()) {
					for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
						if(pRes.getRestriction().getExpression().toString().contains("n")) {
							System.out.println(pRes.getRestriction().getExpression().toString());
							for(PatternNode pNodRes : pRes.getPatternNodes()) {
								System.out.print("   " + pNodRes.getName() + ":" + pNodRes.getValue());
							}
							System.out.println();
						}
					}
				}
			}
		}
	}
	
	/**
	 * for every patternRow in graph where edge is restriction connecting nodes from patternRows marks them as done, there won't be createing instances of them anymore because for at least one patternRow patternTable that contains it is full
	 * @param patternRow patternRow that belongs to graph which elements will be marked as done
	 */
	private void markPatternRowGraphDone (PatternRow patternRow) {
		Stack<PatternRow> rowsToMark = new Stack<>();
		Set<PatternRow> rowsMarked = new HashSet<>();
		rowsToMark.push(patternRow);
		while(!rowsToMark.isEmpty()) {
			PatternRow pRow = rowsToMark.pop();
			rowsMarked.add(pRow);
			pRow.markDone();
			//add neighbours to the stack if they weren't visited
			for(PatternNode pNod : pRow.getPatternNodes()) {
				for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
					for(PatternNode pNodRes : pRes.getPatternNodes()) {
						PatternRow tempPRow = nodeToRow.get(pNodRes);
						if(!rowsMarked.contains(tempPRow)) {
							rowsToMark.push(tempPRow);
						}
					}
				}
			}
		}
	}
	
	/**
	 * generate data based on Pattern family to the Output family
	 * @param maxGeneratingTries number of tries of generating one value before taking to account that it is impossible to generate value that fulfill every restriction
	 * @param maxCollisionCnt number of changes in sequence of generating value for restriction before taking into account that this restriction is impossible to fulfill
	 */
	public void generate(int maxGeneratingTries, int maxCollisionCnt) {
		
		prepare();
		
		for(int j = 0; j < 10000; j++) {
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
			boolean print = false;
			boolean isUndoneRow = false;
			for(PatternRow pr : allRows) {
				if(pr.isDone()) {
					usedRows.put(pr, true);
				}
				else {
					if(!usedRows.get(pr)) {
						if(print)System.out.println("row from list, patternRow.getId()=" + pr.getId());
						rowsToMake.push(pr);
						visitedRows.put(pr, true);
						while(!rowsToMake.empty()) {
							
							patternRow = rowsToMake.pop();
							if(print)System.out.println("row popped from stack, patternRow.getId()=" + patternRow.getId());
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
										}
									}
								}
								PatternRestriction collisionRestriction = null;
								
								for(int i = 0; i <= maxGeneratingTries; i++) {
									
									boolean correctFlag = true;
									pNod.generateValue();
									for(PatternRestriction pRes : pNod.getPatternRestrictions()) {
	
										try{
											correctFlag = pRes.check();
	
											if(!correctFlag) {
												collisionRestriction = pRes;
												break;
											}
										} 
										catch(Exception e) {
											System.out.println("[DataGenerator]Exception: " + e);
											e.printStackTrace();
										}
									}
									if(correctFlag) 
										break;
	
									if(i == maxGeneratingTries) {
										collisionRestriction.incrementCollisionCnt();
										if(collisionRestriction.getCollisionCnt() > maxCollisionCnt) {
											System.out.println("Couldn't make that restriction to happen: " + collisionRestriction);
											break;//there may be no chance to satisfy that restriction
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
					isUndoneRow = true;
					boolean correct = outputAll.addRow(pr, rowToTable.get(pr));
					if(!correct) {
						markPatternRowGraphDone(pr);
					}
				}
			}
			if(isUndoneRow == false)
				break;
//			testPatternAll();
		}
		
		//generating spamRows example:	
		for(OutputTable outputTable : outputAll.getTables()) {
			PatternRow patternMainRow = patternAll.getMainPatternRow(outputTable.getName());
			for(int i = outputTable.getRows().size(); i < outputTable.getRowNum(); i++) {
				PatternRow pr = new PatternRow();
				for(PatternNode pNod : patternMainRow.getPatternNodes()) {
					pNod.generateValue();
					pr.addPatternNode(pNod);
				}
				outputTable.addRow(pr);
			}
		}
		
//		for(PatternTable patternTable : patternAll.getPatternTables()) {
//			int rowNum = outputAll.getRowNumInTable(patternTable.getName());
//			System.out.println(patternTable.getName() + " " + rowNum);
//			for(PatternRow patternMainRow = patternTable.getMainPatternRow(); 
//					rowNum < 42;
//					rowNum++) {
//				PatternRow pr = new PatternRow();
//				for(PatternNode pNod : patternMainRow.getPatternNodes()) {
//					pNod.generateValue();
//					pr.addPatternNode(pNod);
//				}
//				outputAll.addRow(pr, patternTable);
//			}
//		}
		
//		System.out.println("********\n" + outputAll + "\n*********");
	}
}
