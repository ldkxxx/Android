package com.lucabellon.mozzylovetechno;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class DataSourceManager {
	
	ArrayList<ListItem> _myItems;	
	
	public DataSourceManager(String url)
	{
		this.process(url);	
	}
	
	private void process(String url)
	{						
		try {
			_myItems = new ArrayList<ListItem>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(url);			
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("Item");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {
					Element el = (Element)nl.item(i);					
					String text = getTextValue(el,"Text");
					String value = getTextValue(el,"Value");								
					_myItems.add(new ListItem(text, value));
				}
			}						
		} catch (ParserConfigurationException e) {
			Log.e("ParserConfigurationException", e.getMessage());			
		} catch (SAXException e) {
			Log.e("SAXException", e.getMessage());			
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());			
		}		
	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	public ArrayList<ListItem> getItems(){
		return this._myItems;
	}
		
	
	
}
