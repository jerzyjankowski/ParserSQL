package pl.put.tpd.datagenerator.datagenerating;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import pl.put.tpd.datagenerator.structures.pattern.PatternNode;
import pl.put.tpd.datagenerator.structures.pattern.PatternRestriction;

public class NodeValueGenerator {

	private enum RestrictionType {
		INT_SIMPLE_AB, INT_SIMPLE_A2, INT_SIMPLE_1B, OTHER
	}

	private static RestrictionType getRestrictionType(
			PatternRestriction patternRestriction) {

		String var = "[.a-zA-Z0-9_ ]*";
		String oper = "[<>=]{1,2}";
		String num = "[ ]*[+-]?[ ]*[0-9]*[ ]*";

		Pattern pattern_a2 = Pattern.compile(var + oper + num);
		Pattern pattern_1b = Pattern.compile(num + oper + var);
		Pattern pattern_ab = Pattern.compile(var + oper + var);

		String s = patternRestriction.getRestriction().getBinaryExpression()
				.toString();

		// default: integer,
		// TODO check types

		if (pattern_a2.matcher(s).matches())
			return RestrictionType.INT_SIMPLE_A2;
		else if (pattern_1b.matcher(s).matches())
			return RestrictionType.INT_SIMPLE_1B;
		else if (pattern_ab.matcher(s).matches())
			return RestrictionType.INT_SIMPLE_AB;
		else
			return RestrictionType.OTHER;
	}

	public static void generateValue(PatternNode patternNode) {
		generateIntValue(patternNode);
	}

	public static void generateSpamValue(PatternNode patternNode) {
		generateSpamIntValue(patternNode);
	}
	
	private static void generateSpamIntValue(PatternNode patternNode) {
		int minValue  = 0;
		int maxValue = 1000;
		Set<Integer> forbiddenVal = new HashSet<>();
		
		int value;
		Random rand = new Random();
		do {
			if (maxValue > minValue)
				value = rand.nextInt(maxValue - minValue) + minValue;
			else
				value = minValue;
		} while (forbiddenVal.contains(value));
		
		patternNode.setValue(value);
	}
	
	private static void generateIntValue(PatternNode patternNode) {

		int minStartValue  = -1000;
		int maxStartValue = 1000;
		int minValue = minStartValue;
		int maxValue = maxStartValue;
		int tempMinValue, tempMaxValue;
		Set<Integer> forbiddenVal = new HashSet<>();
		List<PatternRestriction> multipleNodeRestrictions = new ArrayList<>();
		Random rand = new Random();
		List<PatternRestriction> tempPatternRestrictionList = new ArrayList<>();
		
		//find all restrictions, get minValue and maxValue from simple oneargument restrictions 
		//and remember all multiargument restrictions
		tempPatternRestrictionList.addAll(patternNode.getPatternRestrictions());
		Random random = new Random();
		
//		while(!tempPatternRestrictionList.isEmpty()) {
//			//randomize patternRestrictions because sometimes there isn't the one way possible but different way could be ok
//			//these should be changed into one better solution
//			int index = random.nextInt(tempPatternRestrictionList.size());
//			PatternRestriction pRes = tempPatternRestrictionList.remove(index);
		boolean print = false;
		if(print)System.out.println("\n--------" + patternNode.getId());
		for(PatternRestriction pRes : patternNode.getPatternRestrictions()) {
			String operation = pRes.getRestriction().getBinaryExpression()
					.getStringExpression();
			String expression = pRes.getRestriction().getBinaryExpression()
					.toString();

			String[] expressionArr = new String[2];
			expressionArr[0] = pRes.getRestriction().getBinaryExpression()
					.getLeftExpression().toString().trim();
			expressionArr[1] = pRes.getRestriction().getBinaryExpression()
					.getRightExpression().toString().trim();
			
			tempMinValue = minValue;
			tempMaxValue = maxValue;
			switch (getRestrictionType(pRes)) {
			
				case INT_SIMPLE_A2: {
					int intVal =  Integer.parseInt(expressionArr[1]);
					if (operation.equals("=")) {
						minValue = maxValue = intVal;
					} else if (operation == "<>") {
						forbiddenVal.add(intVal);
					} else if (operation == ">") {
						minValue = intVal + 1;
					} else if (operation == "<=") {
						maxValue = intVal;
					} else if (operation == "<") {
						maxValue = intVal - 1;
					} else if (operation == ">=") {
						minValue = intVal;
					}
				}
				break;
				
				case INT_SIMPLE_1B: {
					int intVal =  Integer.parseInt(expressionArr[1]);
					if (operation.equals("=")) {
						minValue = maxValue = intVal;
					} else if (operation == "<>") {
						forbiddenVal.add(intVal);
					} else if (operation == ">") {
						maxValue = intVal - 1;
					} else if (operation == "<=") {
						minValue = intVal;
					} else if (operation == "<") {
						minValue = intVal + 1;
					} else if (operation == ">=") {
						maxValue = intVal;
					}
				}
				break;
				
				case INT_SIMPLE_AB: {
					multipleNodeRestrictions.add(pRes);
				}
				break;
				
				case OTHER: {
					System.out.println("NodeValueGenerator.generateIntValue not allowed OTHER restriction");
				}
				break;
				
				default:
				System.out.println("NodeValueGenerator.generateIntValue not allowed restriction - incorrect parsed");
			}
		}
		//iterate by each multiargument restriction in random fashion and get minValue or maxValue if it won't be improper
		//minValue must be lesser than maxValue
		while(!multipleNodeRestrictions.isEmpty()) {
			int i = rand.nextInt(multipleNodeRestrictions.size());
			PatternRestriction pRes = multipleNodeRestrictions.remove(i);
			
			String operation = pRes.getRestriction().getBinaryExpression()
					.getStringExpression();
			String expression = pRes.getRestriction().getBinaryExpression()
					.toString();

			String[] expressionArr = new String[2];
			expressionArr[0] = pRes.getRestriction().getBinaryExpression()
					.getLeftExpression().toString().trim();
			expressionArr[1] = pRes.getRestriction().getBinaryExpression()
					.getRightExpression().toString().trim();
			
			tempMinValue = minValue;
			tempMaxValue = maxValue;
			
			if (operation.equals("=")) {
				if(print)System.out.println("restriction: " + pRes);
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode)
						if(print)System.out.println("  " + pNodRes.getTableAlias() + "." + pNodRes.getName() + "=" + pNodRes.getValue());
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						tempMinValue = tempMaxValue = pNodRes.getValue();
						if(print)System.out.println("  not null, tempMinValue=" + tempMinValue + ", tempMaxValue=" + tempMaxValue);
					}
				}
			} else if (operation == "<>") {
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						forbiddenVal.add(pNodRes.getValue());
					}
				}
			} else if (operation == ">") {
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						if (expressionArr[0].contains(pNodRes.getName()))
							tempMaxValue = pNodRes.getValue() - 1;
						else
							tempMinValue = pNodRes.getValue() + 1;

					}
				}
			} else if (operation == "<=") {
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						if (expressionArr[0].contains(pNodRes.getName()))
							tempMinValue = pNodRes.getValue();
						else
							tempMaxValue = pNodRes.getValue();
					}
				}
			} else if (operation == "<") {
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						if (expressionArr[0].contains(pNodRes.getName()))
							tempMinValue = pNodRes.getValue() + 1;
						else
							tempMaxValue = pNodRes.getValue() - 1;

					}
				}
			} else if (operation == ">=") {
				for (PatternNode pNodRes : pRes.getPatternNodes()) {
					if (pNodRes != patternNode && pNodRes.getValue() != null) {
						if (expressionArr[0].contains(pNodRes.getName()))
							tempMaxValue = pNodRes.getValue();
						else
							tempMinValue = pNodRes.getValue();
					}
				}
			}
			if(print)System.out.println("    tempMinValue=" + tempMinValue + ", tempMaxValue=" + tempMaxValue);
			if(print)System.out.println("    minValue=" + minValue + ", maxValue=" + maxValue + ", ifIf=" + (tempMinValue <= maxValue && tempMaxValue >= minValue) );
			if(tempMinValue <= maxValue && tempMaxValue >= minValue) {
				if(minValue < tempMinValue)
					minValue = tempMinValue;
				if(maxValue > tempMaxValue)
					maxValue = tempMaxValue;
			}
			else {
				//in this case later on other nodes that spoil that min/max value restriction will be generated from scratch
			}
		}
		//generating value thanks to gained information about minValue and maxValue
		int value;
		do {
			if (maxValue > minValue)
				value = rand.nextInt(maxValue - minValue) + minValue;
			else
				value = minValue;
		} while (forbiddenVal.contains(value));
		if(print)System.out.println("    minValue=" + minValue + ", maxValue=" + maxValue);
		if(print)System.out.println("generated: " + value);
		patternNode.setValue(value);
	}
}
