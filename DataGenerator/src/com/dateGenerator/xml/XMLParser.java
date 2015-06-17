package com.dateGenerator.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLParser {
	
	private List<XMLTable> tables = new ArrayList<XMLTable>();
	private boolean printMode = false;
	
	public void parseToObjects(String path)
	{
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
 
			NodeList tabList = doc.getElementsByTagName("TABLE");		 
			for (int i = 0; i < tabList.getLength(); i++) {

				XMLTable table = new XMLTable();
				
				Node tabNode = tabList.item(i);
				if (tabNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					//NAME, ROWS_NUM,DISTRIBUTION,MIN_ROW_SIZE
					Element tabElement = (Element) tabNode;
					table.setName(getNodeValue(tabElement, "NAME"));
					table.setRowNum(Integer.parseInt(getNodeValue(tabElement, "ROWS_NUM")));
					table.setDistribution(getNodeValue(tabElement, "DISTRIBUTION"));
					
					String rec = getNodeValue(tabElement, "MIN_ROW_SIZE");
					if(!rec.equals("")) {
						table.setMinRowSize(Integer.parseInt(rec));
					}
					
					if(printMode)System.out.println(table.toString());
					
					// ATTRIBUTES
					NodeList colList = tabElement.getElementsByTagName("ATTRIBUTE");
					parseAttributes(table, colList);
					
		 
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private void parseAttributes(XMLTable table, NodeList colList)
	{
		for (int i = 0; i < colList.getLength(); i++) {

			XMLColumn column = new XMLColumn();
			
			Node colNode = colList.item(i);
			if (colNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element colElement = (Element) colNode;
				column.setName(getNodeValue(colElement, "NAME"));
				column.setType(getNodeValue(colElement, "TYPE"));
			
				String rec = getNodeValue(colElement, "NULL_PERCENTAGE");
				if(!rec.equals("")) {
					column.setNullPercentage(Float.parseFloat(rec));
				}
				rec = getNodeValue(colElement, "MIN_VALUE");
				if(!rec.equals("")) {
					column.setMinValue(Integer.parseInt(rec));
				}
				rec = getNodeValue(colElement, "MAX_VALUE");
				if(!rec.equals("")) {
					column.setMaxValue(Integer.parseInt(rec));
				}
				rec = getNodeValue(colElement, "MIN_UNIQUE_PERCENTAGE");
				if(!rec.equals("")) {
					column.setMinUniquePercentage(Float.parseFloat(rec));
				}
				rec = getNodeValue(colElement, "MAX_UNIQUE_PERCENTAGE");
				if(!rec.equals("")) {
					column.setMaxUniquePercentage(Float.parseFloat(rec));
				}
				
				if(printMode)System.out.println(column.toString());
				
				// VALUES
				NodeList valList = colElement.getElementsByTagName("VALUE");
				parseValues(column, valList);
				
				table.addColumn(column);
				
	 
			}
		}
	}
	
	
	private void parseValues(XMLColumn column, NodeList valList) {
		for (int i = 0; i < valList.getLength(); i++) {

				column.addValue(valList.item(i).getTextContent());
				if(printMode)System.out.println("Value ["+valList.item(i).getTextContent()+"]");
		}
		
	}

	private String getNodeValue(Element element, String name)
	{
		NodeList nodes = element.getElementsByTagName(name);
		if(nodes.getLength() == 0)
		{
			return "";
		}
		else
		{
			return nodes.item(0).getTextContent();
		}
	}
	
	public void enablePrintMode(boolean mode)
	{
		this.printMode=mode;
	}

	
}
