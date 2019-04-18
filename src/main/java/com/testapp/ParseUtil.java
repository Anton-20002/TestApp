package com.testapp;

import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Stateless
public class ParseUtil {


  private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


  public DocumentBuilder getDocumentBuilder() {
    try {
      return factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

}

